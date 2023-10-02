package com.example.chapter2.Repository;

import com.example.chapter2.Entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepositoryInterface extends JpaRepository<CommentEntity, Long> {
    // JpaRepository<대상 엔티티, 대표키 값의 타입>
    @Query(value = "SELECT * FROM comment_entity WHERE article_id = :articleId",
    nativeQuery = true)
    List<CommentEntity> findByArticleId(Long articleId); //쿼리문을 사용

    List<CommentEntity> findByNickname(String nickname); //orm.xml을 사용
}
