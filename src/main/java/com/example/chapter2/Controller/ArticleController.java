package com.example.chapter2.Controller;

import com.example.chapter2.Dto.ArticleDto;
import com.example.chapter2.Entity.ArticleEntity;
import com.example.chapter2.Repository.ArticleRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {
    @Autowired // 객체를 생성해주는 어노테이션으로 스프링부트가 미리 생성해 놓은 레포지토리 객체 주입.
    private ArticleRepositoryInterface articleRepositoryInterface;

    @GetMapping("/articles/new")
    public String newArticleFrom(){
        return "articles/new";
    }
    @PostMapping("/articles/create")
    public String createArticle(ArticleDto articleDto){
        System.out.println(articleDto.toString());
        ArticleEntity articleEntity = articleDto.toEntity();
        ArticleEntity saved = articleRepositoryInterface.save(articleEntity);
        return "";
    }
}