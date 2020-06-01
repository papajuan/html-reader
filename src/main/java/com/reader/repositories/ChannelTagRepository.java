package com.reader.repositories;

import com.reader.entities.Channel;
import com.reader.entities.ChannelTag;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChannelTagRepository extends CrudRepository<ChannelTag, Long> {

    @Query("from ChannelTag ct where ct.channel = :channel")
    public Iterable<ChannelTag> findAllByChannel(@Param("channel") Channel channel);

    @Query("from ChannelTag ct where ct.tag = :tag " +
            "and ct.text = :text " +
            "and ct.source = :source " +
            "and ct.selector = :selector " +
            "and ct.channel = :channel")
    public Optional<ChannelTag> findByParams(@Param("tag") String tag,
                                             @Param("text") String text,
                                             @Param("source") String source,
                                             @Param("selector") String selector,
                                             @Param("channel") Channel channel);

    @Query("from ChannelTag ct where ct.parent = :parent")
    public Iterable<ChannelTag> listChildrenTags(@Param("parent") ChannelTag parent);

    @Query("from ChannelTag  ct where ct.parent is null and ct.channel = :channel")
    public Iterable<ChannelTag> findRootTags(@Param("channel") Channel channel);
}
