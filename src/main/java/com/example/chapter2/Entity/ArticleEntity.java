package com.example.chapter2.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@AllArgsConstructor
@ToString
@NoArgsConstructor
@Entity
@Getter
public class ArticleEntity {
    @Id // 대푯값
    @GeneratedValue (strategy = GenerationType.IDENTITY) // db가 id 자동 생성
    private Long id;
    @Column // DB 테이블의 title 열과 연결
    private String title;
    @Column
    private String content;

    public void patch(ArticleEntity articleEntity) { //데이터의 일부만 수정하기 위한 메소드 생성
        if(articleEntity.title != null)
            this.title = articleEntity.title;
        if(articleEntity.content !=null)
            this.content = articleEntity.content;
    }
}
