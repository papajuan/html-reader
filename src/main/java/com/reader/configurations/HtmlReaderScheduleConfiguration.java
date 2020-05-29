package com.reader.configurations;

import com.reader.entities.Channel;
import com.reader.repositories.ChannelRepository;
import com.reader.repositories.ChannelTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author papajuan
 * @date 5/29/2020
 **/

@Configuration
@EnableScheduling
public class HtmlReaderScheduleConfiguration {

    private final static int TEN_MIN = 60000;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private ChannelTagRepository channelTagRepository;

    @Scheduled(fixedDelay = TEN_MIN)
    public void scheduleSaveChannelTags() {
        Iterable<Channel> channels = channelRepository.findAll();
        for(Channel channel : channels) {

        }
//        channelTagRepository.saveAll();
    }
}
