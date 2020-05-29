package com.reader.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @Column(name = "id")
    private int id;

    @Column(name = "link")
    private String link;

    public Channel(String link) {
        this.link = link;
    }
}
