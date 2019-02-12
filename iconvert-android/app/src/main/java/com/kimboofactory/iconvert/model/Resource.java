package com.kimboofactory.iconvert.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 25/01/2019
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Resource<T> {
    private T value;
}
