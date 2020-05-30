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
@Table(name = "channel_tag")
@Data
@NoArgsConstructor
public class ChannelTag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "channelTagId")
    private long id;

    @Column(name = "tag")
    private String tag;

    @Column(name = "text")
    @Nullable
    private String text;

    @Column(name = "source")
    @Nullable
    private String source;

    @Column(name = "class")
    @Nullable
    private String selector;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id", referencedColumnName = "id", insertable = false, updatable = false)
    private Channel channel;

    public ChannelTag(String tag, String text, String source, String selector, Channel channel) {
        this.tag = tag;
        this.text = text;
        this.source = source;
        this.selector = selector;
        this.channel = channel;
    }
}
