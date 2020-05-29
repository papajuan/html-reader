package com.reader.entities;

import com.sun.istack.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(name = "id")
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "channel", referencedColumnName = "id")
    private Channel channel;

    public ChannelTag(String tag, String text, String source, String selector, Channel channel) {
        this.tag = tag;
        this.text = text;
        this.source = source;
        this.selector = selector;
        this.channel = channel;
    }
}
