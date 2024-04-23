package com.ivon.purba.domain.comment.entity;

import com.ivon.purba.config.baseEntity.BaseTimeEntity;
import com.ivon.purba.domain.comment.dto.CommentRequestDto;
import com.ivon.purba.domain.content.entity.Content;
import com.ivon.purba.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "comment")
@NoArgsConstructor
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    @Column(name = "comment_data", length = 10000)
    private String data;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentId;

    public Comment(Content content, User user, String commentData) {
        this.content = content;
        this.user = user;
        this.data = commentData;
    }

    public static Comment createComment(Content content, User user, String commentData) {
        return new Comment(
                content, user, commentData
        );
    }

    // 댓글 수정
    public void patch(CommentRequestDto requestDto) {
        this.data = requestDto.getCommentData();
    }

}
