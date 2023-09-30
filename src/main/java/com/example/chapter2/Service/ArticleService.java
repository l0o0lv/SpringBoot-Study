package com.example.chapter2.Service;

import com.example.chapter2.Dto.ArticleDto;
import com.example.chapter2.Entity.ArticleEntity;
import com.example.chapter2.Repository.ArticleRepositoryInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {
    @Autowired
    private ArticleRepositoryInterface articleRepositoryInterface;

    public List<ArticleEntity> ReadAllArticle() {
        return articleRepositoryInterface.findAll();
    }

    public ArticleEntity ReadArticle(Long id) {
       return articleRepositoryInterface.findById(id).orElse(null);
    }

    public ArticleEntity create(@RequestBody ArticleDto articleDto) {
        ArticleEntity articleEntity = articleDto.toEntity();
        if(articleEntity.getId()!=null){ //article 객체에 id가 이미 존재한다면 null을 반환. 이미 있는 1번 id를 수정하지 않기 위해
            return null;
        }
        return articleRepositoryInterface.save(articleEntity);
    }

    public ArticleEntity PatchArticle(Long id, ArticleDto articleDto) {
        ArticleEntity articleEntity = articleDto.toEntity();
        log.info("id: {}, article : {}",id, articleEntity.toString());
        ArticleEntity target = articleRepositoryInterface.findById(id).orElse(null);
        if (target == null || id != articleEntity.getId()) {
            log.info("잘못된 요청! id:{}, article: {}", id, articleEntity.toString());
            return null; //컨트롤러가 응답하니 여기서는 null반환
        }
        target.patch(articleEntity);
        return articleRepositoryInterface.save(articleEntity);
    }

    public ArticleEntity DeleteArticle(Long id) {
        ArticleEntity target = articleRepositoryInterface.findById(id).orElse(null);
        if(target == null) {
            return null;
        }
        articleRepositoryInterface.delete(target);
        return target; //db에서 삭제한 대상 컨트롤러에 반환
    }

    @Transactional
    public List<ArticleEntity> CreateArticles(List<ArticleDto> articleDtos) { // 트랜잭션 테스트 할 서비스 메소드
        List<ArticleEntity> articleEntityList = articleDtos.stream()
                .map(articleDto -> articleDto.toEntity())
                .collect(Collectors.toList());
        articleEntityList.stream()
                .forEach(articleEntity -> articleRepositoryInterface.save(articleEntity));
        articleRepositoryInterface.findById(-1L)
                .orElseThrow(() -> new IllegalArgumentException("결제 실패!"));
        return articleEntityList;
    }
}
