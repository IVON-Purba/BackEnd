package com.ivon.purba.domain.comment.dto;


import com.ivon.purba.domain.comment.entity.Comment;
import com.ivon.purba.domain.content.entity.Content;
import lombok.Getter;
import lombok.Setter;

@Getter
public class CommentRequestDto {
    private String commentData;
    private Long parentId; // 상위 댓글 ID, 대댓글인 경우 사용
}
