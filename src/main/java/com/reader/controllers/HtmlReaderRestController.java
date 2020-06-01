package com.reader.controllers;

import com.google.common.collect.Lists;
import com.reader.entities.Channel;
import com.reader.entities.ChannelTag;
import com.reader.repositories.ChannelRepository;
import com.reader.repositories.ChannelTagRepository;
import com.reader.services.ChannelTagService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @author papajuan
 * @date 5/29/2020
 **/

@RestController
public class HtmlReaderRestController {

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private ChannelTagService channelTagService;

    @Autowired
    private ChannelTagRepository channelTagRepository;

    private HashMap<Element, ChannelTag> tagMap = new HashMap<>();

    @RequestMapping(path = "/addChannel")
    public String addChannel(@RequestParam String link) {
        Channel channel = new Channel(link);
        channelRepository.save(channel);
        return "The channel with id " + channel.getId() + " has been added!";
    }

    @GetMapping(path = "/listAvailableTags", produces = "application/json")
    public Set<String> listAvailableTags(@RequestParam int id)  {
        Channel channel = channelRepository.findById(id).get();
        Set<String> tags;
        try {
            Document document = Jsoup.parse(new URL(channel.getLink()), 6000);
            tags = new TreeSet<>(Comparator.naturalOrder());
            for (Element element : document.getAllElements())
                tags.add(element.tagName());
        } catch (IOException e) {
            tags = new HashSet<>();
            tags.add(e.getMessage());
        }

        return tags;
    }

    @PostMapping(path = "/addTags", consumes = "application/json")
    public String addTags(@RequestParam int channelId, @RequestBody List<String> neededTags) {
        Channel channel = channelRepository.findById(channelId).get();
        try {
            Document document = Jsoup.parse(new URL(channel.getLink()), 6000);
            for(String tag : neededTags) {
                for (Element element : document.getElementsByTag(tag)) {
                    ChannelTag newTag = setTag(element, channel, tag);
                    tagMap.put(element, newTag);
                }
            }
        } catch (IOException e) { }

        setParents();

        return "Tags have been added successfully!";
    }

    private ChannelTag setTag(Element element, Channel channel, String tag) {
        String text = element.hasText() ? element.text() : element.outerHtml();
        String source = getSource(element);
        String selector = element.hasAttr("class") ? element.attributes().get("class") : null;
        ChannelTag channelTag = new ChannelTag(tag, text, source, selector, channel);

        channelTagService.saveOrUpdate(channelTag);

        return channelTag;
    }

    private void setParents() {
        for(Element element : tagMap.keySet()) {
            if(element.hasParent()) {
                ChannelTag parentTag = tagMap.get(element.parent());
                ChannelTag newTag = tagMap.get(element);
                newTag.setParent(parentTag);
                channelTagService.saveOrUpdate(newTag);
            }
        }
    }

    @GetMapping(path = "/getChannelInfo", produces = "application/json")
    public @ResponseBody Iterable<ChannelTag> getChannelInfo(@RequestParam int channelId) {
        Channel channel = channelRepository.findById(channelId).get();
        Iterable<ChannelTag> rootTags = channelTagRepository.findRootTags(channel);

        for(ChannelTag rootTag : rootTags)
            listChildren(rootTag);

        return rootTags;
    }

    private void listChildren(ChannelTag parent) {
        List<ChannelTag> children = Lists.newArrayList(channelTagRepository.listChildrenTags(parent));
        if(children != null && !children.isEmpty()) {
            parent.setChildren(children);
            for(ChannelTag child : children)
                listChildren(child);
        }
    }

    private String getSource(Element element) {
        return element.hasAttr("src") ? element.attributes().get("src") :
                (element.hasAttr("href") ? element.attributes().get("href") : null);
    }
}
