package com.ivon.purba.domain.photo.entity;

import com.ivon.purba.domain.content.entity.Content;
import com.ivon.purba.domain.content.entity.ContentType;
import com.ivon.purba.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "photo")
public class Photo extends Content {
    @Column(name = "photo_url", nullable = false, length = 200)
    private String url;

    public Photo(User user, ContentType contentType, String url) {
        super(user, contentType);
        this.url = url;
    }

    public Photo() {

    }
}