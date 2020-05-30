package com.reader.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.Set;

/**
 * @author papajuan
 * @date 5/29/2020
 **/

@Entity
@Table(name = "channel")
@Data
@NoArgsConstructor
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "channelId", nullable = false)
    private int id;

    @Column(name = "link")
    private String link;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "channel")
    @Nullable
    private Set<ChannelTag> channelTags;

    public Channel(String link) {
        this.link = link;
    }
}
