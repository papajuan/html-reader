package com.reader.repositories;

import com.reader.entities.Channel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChannelRepository extends CrudRepository<Channel, Integer> {

    @Query("from Channel ch where ch.link = :link")
    public Optional<Channel> findByLink(@Param("link") String link);
}
