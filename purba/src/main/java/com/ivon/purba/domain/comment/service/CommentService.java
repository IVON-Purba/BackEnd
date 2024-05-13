package com.ivon.purba.domain.comment.service;

import com.ivon.purba.domain.comment.dto.CommentRequestDto;
import com.ivon.purba.domain.comment.dto.CommentResponseDto;
import com.ivon.purba.domain.comment.entity.Comment;
import com.ivon.purba.domain.comment.repository.CommentRepository;
import com.ivon.purba.domain.content.entity.Content;
import com.ivon.purba.domain.content.repository.ContentRepository;
import com.ivon.purba.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private ContentRepository contentRepository;
    private CommentRepository commentRepository;

    /*
     * 댓글 리스트 조회
     */
    public List<CommentResponseDto> findComments(Long contentId) {
        // 댓글 리스트 조회
        List<Comment> comments = commentRepository.findByContentId(contentId);

        // 엔티티 -> dto 변환
        List<CommentResponseDto> dtos = new ArrayList<CommentResponseDto>();

        for(int i = 0; i < comments.size(); i++){
            // 먼저 댓글 엔티티 하나씩 가져오기
            Comment c = comments.get(i);
            // 엔티티 -> DTO로 변환
            CommentResponseDto dto = CommentResponseDto.createComment(c);
            // dtos 리스트에 추가
            dtos.add(dto);
        }

        // dto 리스트 반환
        return dtos;
    }


    /*
    * 댓글 생성
    */
    public CommentResponseDto createComment(CommentRequestDto requestDto, Long contentId, Long userId){
        Content content = contentRepository.findById(contentId)
                .orElseThrow(() -> new RuntimeException("콘텐츠를 찾을 수 없습니다."));
        User user = new User(); // 실제 구현에서는 인증된 사용자 정보를 가져와야 합니다.
        user.setId(userId);

        // DTO로 가져온 값들을 엔티티 변환
        Comment comment = Comment.createComment(content, user, requestDto.getCommentData());

        if (requestDto.getParentId() != null) {
            Comment parentComment = commentRepository.findById(requestDto.getParentId())
                    .orElseThrow(() -> new RuntimeException("상위 댓글을 찾을 수 없습니다."));
            comment.setParentId(parentComment);
        }

        Comment created = commentRepository.save(comment);

        return CommentResponseDto.createComment(created);
    }

    /*
     * 댓글 수정
     */
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto) {

        // 댓글 조회 및 예외 처리
        Comment target = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패!"+
                        "대상 댓글이 없습니다."));

        // 댓글 수정
        target.patch(requestDto);

        // 댓글 수정 후 DB 저장
        Comment updated = commentRepository.save(target);

        // 엔티티 dto 변환 후 반환
        return CommentResponseDto.createComment(updated);

    }

    /*
     * 댓글 삭제
     */
    public CommentResponseDto deleteComment (Long commentId) {

        // 댓글 조회 및 예외 처리
        Comment target = commentRepository.findById(commentId)
                        .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패" +
                                "대상 댓글이 없습니다!"));

        // 댓글 삭제
        commentRepository.delete(target);

        return CommentResponseDto.createComment(target);

    }
}
