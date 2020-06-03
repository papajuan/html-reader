package com.reader.services;

import com.reader.entities.Channel;
import com.reader.entities.ChannelTag;
import com.reader.entities.SearchParam;
import com.reader.repositories.ChannelRepository;
import com.reader.repositories.ChannelTagRepository;
import com.reader.repositories.SearchParamRepository;
import lombok.extern.java.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @author papajuan
 * @date 6/1/2020
 * Main service for all logic
 **/
@Service
@Log
public class ChannelService {

    private HashMap<Element, ChannelTag> tagMap = new HashMap<>();

    @Autowired
    private ChannelRepository channelRepository;

    @Autowired
    private ChannelTagRepository channelTagRepository;

    @Autowired
    private SearchParamRepository searchParamRepository;

    public void saveOrUpdate(Channel entityToSave) {
//        Optional<Channel> dbEntity = getDbEntity(entityToSave);
//
//        if(dbEntity.isPresent())
//            return;
//        else
            channelRepository.save(entityToSave);
    }

//    public Optional<Channel> getDbEntity(Channel entity) {
//        return channelRepository.findByLink(entity.getLink());
//    }

    public void addTagsToChannel(int channelId, List<String> neededTags) {
        Channel channel = channelRepository.findById(channelId).get();
        setNeededTags(neededTags, channel);
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
        log.info("Tags for channel with id = " + channelId + " have been added successfully!");
    }

    private ChannelTag setTag(Element element, Channel channel, String tag) {
        String text = element.hasText() ? element.text() : element.outerHtml();
        String source = getSource(element);
        String selector = element.hasAttr("class") ? element.attributes().get("class") : null;
        ChannelTag channelTag = new ChannelTag(tag, text, source, selector, channel);

        channelTagRepository.save(channelTag);

        return channelTag;
    }

    private void setParents() {
        for(Element element : tagMap.keySet()) {
            if(element.hasParent()) {
                ChannelTag parentTag = tagMap.get(element.parent());
                ChannelTag newTag = tagMap.get(element);
                newTag.setParent(parentTag);
                channelTagRepository.save(newTag);
            }
        }
    }

    private void setNeededTags(List<String> neededTags, Channel channel) {
        for(String tag : neededTags) {
            SearchParam param = new SearchParam(channel, tag);
            searchParamRepository.save(param);
        }
    }

    private String getSource(Element element) {
        return element.hasAttr("src") ? element.attributes().get("src") :
                (element.hasAttr("href") ? element.attributes().get("href") : null);
    }
}
