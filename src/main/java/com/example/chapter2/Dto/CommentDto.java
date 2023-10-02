package com.example.chapter2.Dto;

import com.example.chapter2.Entity.CommentEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.stream.events.Comment;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDto {
    private Long id;
    @JsonProperty("article_id") //json데이터의 키 이름과 이를 받아 저장하는 dto 선언 필드 변수명이 다를경우 사용하면 키와 변수 자동매핑.
    private Long articleId;
    private String nickname;
    private String body;

    //public static = 정적 메소드로 객체 생성 없이 호출 가능한 메소드
    public static CommentDto createCommentDto(CommentEntity comment) { // EntityToDto
        return new CommentDto(
                comment.getId(),
                comment.getArticleEntity().getId(),
                comment.getNickname(),
                comment.getBody()
        );
    }
}
