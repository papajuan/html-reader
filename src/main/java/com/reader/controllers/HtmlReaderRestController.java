package com.reader.controllers;

import com.reader.entities.Channel;
import com.reader.entities.ChannelTag;
import com.reader.repositories.ChannelRepository;
import com.reader.repositories.ChannelTagRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
    public void addChannel(@RequestParam String link) {
        Channel channel = new Channel(link);
        channelRepository.save(channel);
    }

    @GetMapping(path = "/listAvailableTags")
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

    @GetMapping(path = "/getChannelInfo")
    public @ResponseBody Map<String, String> getChannelInfo(@RequestParam Channel channel) {
        return null;
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
}
