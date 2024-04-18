package com.ivon.purba.domain.comment.dto;

import com.ivon.purba.domain.comment.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;
    private Long contentId;
    private Long userId;
    private String commentData;
    private Long parentId; // 상위 댓글 ID, 대댓글인 경우 사용



    public static CommentResponseDto createComment(Comment created) {
        return new CommentResponseDto(
                created.getId(),
                created.getContent().getId(),
                created.getUser().getId(),
                created.getData(),
                created.getParentId().getId()
        );
    }
}
