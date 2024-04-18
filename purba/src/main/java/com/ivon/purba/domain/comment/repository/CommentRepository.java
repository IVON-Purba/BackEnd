package com.ivon.purba.domain.comment.repository;

import com.ivon.purba.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT * FROM comment WHERE content_id = :contentId"
            ,nativeQuery = true)
    List<Comment> findByContentId(Long contentId);
}
