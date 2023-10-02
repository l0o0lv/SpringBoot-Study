package com.example.chapter2.Controller;

import com.example.chapter2.Dto.ArticleDto;
import com.example.chapter2.Entity.ArticleEntity;
import com.example.chapter2.Service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleRestController {
    @Autowired
    private ArticleService articleService;
    @GetMapping("api/articles")
    public List<ArticleEntity> ReadAllArticle(){
        return articleService.ReadAllArticle();
    }

    @GetMapping("api/articles/{id}")
    public ArticleEntity ReadArticle(@PathVariable Long id){
        return articleService.ReadArticle(id);
    }

    @PostMapping("/api/articles") // JSON 데이터를 받아와야 하므로 RequestBody 어노테이션 추가해 본문에 적은 데이터를 매개변수로 받아올 수 있음.
    public ResponseEntity<ArticleEntity> CreateArticle(@RequestBody ArticleDto articleDto){
        ArticleEntity articleEntity = articleService.create(articleDto);
        return (articleEntity != null) ? //생성하면 정상, 실패하면 오류 응답
                ResponseEntity.status(HttpStatus.OK).body(articleEntity) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<ArticleEntity> PatchArticle(@PathVariable Long id,
                                                     @RequestBody ArticleDto articleDto){ // ArticleEntity를 ResponseEntity에 담아서 반환데이터에 상태코드 실어보냄.
       ArticleEntity articleEntity = articleService.PatchArticle(id,articleDto);
       return (articleEntity !=null) ?
               ResponseEntity.status(HttpStatus.OK).body(articleEntity) :
               ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<ArticleEntity> DeleteArticle(@PathVariable Long id){
        ArticleEntity articleEntity = articleService.DeleteArticle(id);
        return ( articleEntity != null ) ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).build() : //잘 삭제됐으면 NO_CONTENT, 본문은 빌드만 해서 보낸다.
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<ArticleEntity>> transactionTest(@RequestBody List<ArticleDto> articleDtos){
        List<ArticleEntity> createdList = articleService.CreateArticles(articleDtos);
        return (createdList != null) ?
                ResponseEntity.status(HttpStatus.OK).body(createdList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
