package com.reader.controllers;

import com.google.common.collect.Lists;
import com.reader.entities.Channel;
import com.reader.entities.ChannelTag;
import com.reader.repositories.ChannelRepository;
import com.reader.repositories.ChannelTagRepository;
import com.reader.services.ChannelService;
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

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class HtmlReaderRestController {

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private ChannelTagRepository channelTagRepository;

    @Autowired
    private ChannelService channelService;

    @GetMapping(path = "/addChannel", produces = "application/json")
    public Channel addChannel(@RequestParam String link) {
        Channel channel = new Channel(link);
        channelService.saveOrUpdate(channel);
        return channel;
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
        channelService.addTagsToChannel(channelId, neededTags);
        return "Tags have been added successfully!";
    }

    @GetMapping(path = "/getChannelInfo", produces = "application/json")
    public @ResponseBody Iterable<ChannelTag> getChannelInfo(@RequestParam int channelId) {
        return getRootTags(channelId, null);
    }

    private void listChildren(ChannelTag parent) {
        List<ChannelTag> children = Lists.newArrayList(channelTagRepository.listChildrenTags(parent));
        if(children != null && !children.isEmpty()) {
            parent.setChildren(children);
            for(ChannelTag child : children)
                listChildren(child);
        }
    }

    private Iterable<ChannelTag> getRootTags(int channelId, String searchString) {
        Channel channel = channelRepository.findById(channelId).get();
        Iterable<ChannelTag> rootTags = searchString == null ?
                channelTagRepository.findRootTags(channel) : channelTagRepository.findTagByString(channelId, searchString);

        for(ChannelTag rootTag : rootTags)
            listChildren(rootTag);

        return rootTags;
    }

    @GetMapping(path = "/getChannelInfoWithParams", produces = "application/json")
    public @ResponseBody Iterable<ChannelTag> getChannelInfo(@RequestParam int channelId, @RequestParam String searchString) {
        return getRootTags(channelId, searchString);
    }
}
