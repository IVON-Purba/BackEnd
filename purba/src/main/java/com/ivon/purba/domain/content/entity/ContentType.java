package com.ivon.purba.domain.content.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Table(name = "content_type")
public class ContentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(name = "type_name", nullable = false, unique = true, updatable = false)
    private String name;
}
