package com.reader.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author papajuan
 * @date 6/1/2020
 **/

@Entity
@Table(name = "search_param")
@IdClass(SearchParamId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchParam {

    @Id
    @ManyToOne
    @JoinColumn(name = "channel_id", referencedColumnName = "id", nullable = false)
    private Channel channel;

    @Id
    @Column(name = "tag")
    private String tag;
}
