package com.reader.controllers;

import com.reader.entities.Channel;
import com.reader.repositories.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author papajuan
 * @date 5/29/2020
 **/

@RestController
public class HtmlReaderRestController {

    @Autowired
    private ChannelRepository channelRepository;

    @RequestMapping(path = "/addChannel")
    public void addChannel(@RequestParam String link) {
        Channel channel = new Channel(link);
        channelRepository.save(channel);
    }
}
