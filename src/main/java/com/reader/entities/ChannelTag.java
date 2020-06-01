package com.reader.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

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
    @JsonIgnore
//    @Column(name = "channelTagId")
    private long id;

    @Column(name = "tag", length = 20)
    private String tag;

    @Column(name = "text", length = 100000)
    @Nullable
    private String text;

    @Column(name = "source", length = 1000)
    @Nullable
    private String source;

    @Column(name = "class")
    @Nullable
    private String selector;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "channel_id", referencedColumnName = "id", nullable = false)
    private Channel channel;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id", nullable = true)
    private ChannelTag parent;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ChannelTag> children;

    public ChannelTag(String tag, String text, String source, String selector, Channel channel) {
        this.tag = tag;
        this.text = text;
        this.source = source;
        this.selector = selector;
        this.channel = channel;
    }
}
