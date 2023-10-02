package com.example.chapter2.Entity;

import com.example.chapter2.Dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;


@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne //댓글과 게시글은 다대일.
    @JoinColumn(name = "article_id") //외래키 생성, ArticleEntity의 기본키와 매핑
    private ArticleEntity articleEntity; // 해당 댓글의 부모 게시글
    @Column
    private String nickname;
    @Column
    private String body;

    public static CommentEntity createComment(CommentDto commentDto, ArticleEntity articleEntity) {
        if(commentDto.getId() != null)
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 합니다.");
        if(commentDto.getArticleId() != null)
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못됐습니다.");
        return new CommentEntity(
                commentDto.getId(),
                articleEntity,
                commentDto.getNickname(),
                commentDto.getBody()
        );
    }

    public void patch(CommentDto commentDto) {
        if(this.id != commentDto.getId())
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력되었습니다.");
        if(commentDto.getNickname() != null) // 수정할 닉네임 데이터가 있다면
            this.nickname = commentDto.getNickname();
        if(commentDto.getBody() != null) //수정할 본문 데이터가 있다면
            this.body = commentDto.getBody();
    }
}
