package com.reader.repositories;

import com.reader.entities.Channel;
import com.reader.entities.ChannelTag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ChannelTagRepository extends CrudRepository<ChannelTag, Long> {

    @Query("from ChannelTag ct where ct.channel = :channel")
    public Iterable<ChannelTag> findAllByChannel(@Param("channel") Channel channel);
}
