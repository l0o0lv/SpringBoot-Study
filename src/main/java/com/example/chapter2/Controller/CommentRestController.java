package com.example.chapter2.Controller;

import com.example.chapter2.Dto.CommentDto;
import com.example.chapter2.Repository.CommentRepositoryInterface;
import com.example.chapter2.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentRestController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> ReadComment(@PathVariable Long articleId){
        List<CommentDto> dtos = commentService.ReadComment(articleId);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> CreateComment(@PathVariable Long articleId,
                                                    @RequestBody CommentDto commentDto){
        CommentDto createdDto = commentService.CreateComment(articleId, commentDto);
        return ResponseEntity.status(HttpStatus.OK).body(createdDto);
    }

    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> UpdateComment(@PathVariable Long Id,
                                                    @RequestBody CommentDto commentDto){
        CommentDto updatedDto = commentService.UpdateComment(Id,commentDto);
        return ResponseEntity.status(HttpStatus.OK).body(commentDto);
    }

    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> DeleteComment(@PathVariable Long Id){
        CommentDto deleteDto = commentService.DeleteComment(Id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
