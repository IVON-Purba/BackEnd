package com.ivon.purba.domain.content.photo.entity;

import com.ivon.purba.domain.content.entity.Content;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "photo")
public class Photo extends Content {

    @Column(name = "photo_url", nullable = false, length = 200)
    private String url;
}
