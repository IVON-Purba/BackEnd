package com.ivon.purba.domain.content.repository;

import com.ivon.purba.domain.content.entity.Content;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {
}
