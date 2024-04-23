package com.ivon.purba.domain.event.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "event_type")
public class EventType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "type_name", nullable = false, unique = true, updatable = false)
    private String name;
}
