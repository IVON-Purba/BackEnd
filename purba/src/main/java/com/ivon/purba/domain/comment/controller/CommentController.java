package com.ivon.purba.domain.comment.controller;

import com.ivon.purba.domain.comment.dto.CommentRequestDto;
import com.ivon.purba.domain.comment.dto.CommentResponseDto;
import com.ivon.purba.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contents")
@RequiredArgsConstructor
public class CommentController {

    private CommentService commentService;

    // 댓글 조회
    @GetMapping("/{contentId}/comments")
    public ResponseEntity<List<CommentResponseDto>> allComment(@PathVariable Long contentId){
        // 서비스 위임
        List<CommentResponseDto> comments = commentService.findComments(contentId);

        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }


    // 댓글 달기
    @PostMapping("/{contentId}/comments")
    public ResponseEntity<CommentResponseDto> addComment(@RequestBody CommentRequestDto requestDto,
                                              @PathVariable Long contentId,
                                              @RequestAttribute("userId") Long userId){
        // 서비스에 위임 해버리기
        CommentResponseDto comment = commentService.createComment(requestDto, contentId, userId);

        return ResponseEntity.status(HttpStatus.OK).body(comment);
    }

    // 댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId,
                                                            @RequestBody CommentRequestDto requestDto){
        // 서비스에 위임 해버리기
        CommentResponseDto update = commentService.updateComment(commentId, requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(update);

    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> deleteComment(@PathVariable Long commentId){

        CommentResponseDto comment = commentService.deleteComment(commentId);

        return ResponseEntity.status(HttpStatus.OK).body(comment);

    }

}
