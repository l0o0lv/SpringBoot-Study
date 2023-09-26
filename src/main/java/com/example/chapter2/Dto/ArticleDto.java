package com.example.chapter2.Dto;

import com.example.chapter2.Entity.ArticleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString

public class ArticleDto {
    private Long id;
    private String title;
    private String content;

    //DTO인 articleDto 객체를 엔티티 객체로 변환하는 역할.
    public ArticleEntity toEntity() {
        return new ArticleEntity(id,title,content);
    }
}
