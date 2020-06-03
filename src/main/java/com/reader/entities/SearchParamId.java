package com.reader.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author papajuan
 * @date 6/1/2020
 * Composite id for SearchParam
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchParamId implements Serializable {

    private int channel;
    private String tag;
}
