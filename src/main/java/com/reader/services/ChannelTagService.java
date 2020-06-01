package com.reader.services;

import com.reader.entities.ChannelTag;
import com.reader.repositories.ChannelTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author papajuan
 * @date 6/1/2020
 **/

@Service
public class ChannelTagService {

    @Autowired
    private ChannelTagRepository channelTagRepository;

    public void saveOrUpdate(ChannelTag entityToSave) {
        Optional<ChannelTag> dbEntity = getDbEntity(entityToSave);

        if(dbEntity.isPresent())
            return;
        else
            channelTagRepository.save(entityToSave);
    }

    public void saveOrUpdateAll(Iterable<ChannelTag> entititesToSave) {
        for(ChannelTag tag : entititesToSave)
            saveOrUpdate(tag);
    }

    public Optional<ChannelTag> getDbEntity(ChannelTag entity) {
        return channelTagRepository.findByParams(
                entity.getTag(),
                entity.getText(),
                entity.getSource(),
                entity.getSelector(),
                entity.getChannel());
    }
}
