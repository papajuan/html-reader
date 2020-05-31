package com.reader.controllers;

import com.reader.entities.Channel;
import com.reader.entities.ChannelTag;
import com.reader.repositories.ChannelRepository;
import com.reader.repositories.ChannelTagRepository;
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
    private ChannelTagRepository channelTagRepository;

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
        List<ChannelTag> channelTags = new ArrayList<>();
        try {
            Document document = Jsoup.parse(new URL(channel.getLink()), 6000);
            for(String tag : neededTags) {
                for (Element element : document.getElementsByTag(tag)) {
                    String text = element.hasText() ? element.text() : element.outerHtml();
                    String source = getSource(element);
                    String selector = element.hasAttr("class") ? element.attributes().get("class") : null;
                    ChannelTag channelTag = new ChannelTag(tag, text, source, selector, channel);
                    channelTags.add(channelTag);

                }
            }
        } catch (IOException e) { }

        channelTagRepository.saveAll(channelTags);

        return "Tags have been added successfully!";
    }

    @GetMapping(path = "/getChannelInfo", produces = "application/json")
    public @ResponseBody
    List<ChannelTag> getChannelInfo(@RequestParam int channelId) throws IOException {
        Channel channel = channelRepository.findById(channelId).get();
        return channel.getChannelTags();
    }

    @PostMapping(path = "/saveTags")
    public void saveTags(@RequestParam int channelId, @RequestBody List<String> selectedTags) {
//        Channel channel = channelRepository.findById(channelId).get();
//        try {
//            Document document = Jsoup.parse(new URL(channel.getLink()), 10000);
//            for(String tag : selectedTags) {
//                for (Element element : document.getElementsByTag(tag)) {
//                    ChannelTag channelTag = new ChannelTag(element.tagName(), channel);
//                    channelTagRepository.save(channelTag);
//                }
//            }
//        } catch (IOException e) {
//
//        }
    }

    private String getSource(Element element) {
        return element.hasAttr("src") ? element.attributes().get("src") :
                (element.hasAttr("href") ? element.attributes().get("href") : null);
    }
}
