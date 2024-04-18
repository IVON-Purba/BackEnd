package com.ivon.purba.domain.content.entity;

public enum ContentTypeEnum {
    IMAGE("image"),
    EVENT("event");

    private final String name;

    ContentTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
