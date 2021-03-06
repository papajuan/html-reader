package com.reader.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * @author papajuan
 * @date 5/29/2020
 * Channel entity
 **/

@Entity
@Table(name = "channel")
@Data
@NoArgsConstructor
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "link")
    private String link;

    @OneToMany(mappedBy = "channel")
    private List<ChannelTag> channelTags;

    @OneToMany(mappedBy = "channel")
    private Set<SearchParam> searchParams;

    public Channel(String link) {
        this.link = link;
    }
}
