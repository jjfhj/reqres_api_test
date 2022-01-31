package com.github.jjfhj.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SingleUserData {

    private SingleUser data;

    public SingleUser getData() {
        return data;
    }

    public void setData(SingleUser data) {
        this.data = data;
    }
}
