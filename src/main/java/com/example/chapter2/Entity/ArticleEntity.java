package com.example.chapter2.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@AllArgsConstructor
@ToString
@NoArgsConstructor
@Entity
@Getter
public class ArticleEntity {
    @Id // 대푯값
    @GeneratedValue // 자동 생성 기능
    private Long id;
    @Column // DB 테이블의 title 열과 연결
    private String title;
    @Column
    private String content;
}
