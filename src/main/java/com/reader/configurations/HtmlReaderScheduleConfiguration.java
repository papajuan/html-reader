package com.reader.configurations;

import com.google.common.collect.Lists;
import com.reader.entities.Channel;
import com.reader.entities.SearchParam;
import com.reader.repositories.ChannelRepository;
import com.reader.repositories.SearchParamRepository;
import com.reader.services.ChannelService;
import lombok.extern.java.Log;
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
@Log
public class HtmlReaderScheduleConfiguration {

    private final static int TEN_MIN = 600000;

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private ChannelService channelService;

    @Autowired
    private SearchParamRepository searchParamRepository;

    @Scheduled(fixedDelay = TEN_MIN)
    public void scheduleSaveChannelTags() {
        Iterable<Channel> channels = channelRepository.findAll();
        for(Channel channel : channels) {
            int id = channel.getId();
            Iterable<String> params = searchParamRepository.findAllByChannel(id);
            channelService.addTagsToChannel(channel.getId(), Lists.newArrayList(params));
            log.info("channel update has been uploaded by schedule: id = " + id);
        }
    }
}
