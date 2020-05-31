package com.reader.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;

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
    private long tagId;

    @Column(name = "tag", length = 10)
    private String tag;

    @Column(name = "text", length = 10000)
    @Nullable
    private String text;

    @Column(name = "source", length = 1000)
    @Nullable
    private String source;

    @Column(name = "class")
    @Nullable
    private String selector;

    @ManyToOne
    @JoinColumn(name = "channelId", referencedColumnName = "id", nullable = false)
    private Channel channel;

    public ChannelTag(String tag, String text, String source, String selector, Channel channel) {
        this.tag = tag;
        this.text = text;
        this.source = source;
        this.selector = selector;
        this.channel = channel;
    }
}
