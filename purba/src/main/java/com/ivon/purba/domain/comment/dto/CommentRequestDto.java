package com.ivon.purba.domain.comment.dto;


import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String commentData;
    private Long parentId; // 상위 댓글 ID, 대댓글인 경우 사용
}
