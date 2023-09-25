package com.example.chapter2.Repository;

import com.example.chapter2.Entity.ArticleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
//JPA에서 제공하는 인터페이스로 엔티티를 관리할 수 있다. 2개의 제네릭 요소를 받는다. 관리 대상 엔티티 클래스 타입, 엔티티 대푯값 타입
public interface ArticleRepositoryInterface extends CrudRepository<ArticleEntity, Long> {
}
