package com.example.chapter2.Controller;

import com.example.chapter2.Dto.ArticleDto;
import com.example.chapter2.Dto.CommentDto;
import com.example.chapter2.Entity.ArticleEntity;
import com.example.chapter2.Repository.ArticleRepositoryInterface;
import com.example.chapter2.Service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ArticleController {
    @Autowired // 객체를 생성해주는 어노테이션으로 스프링부트가 미리 생성해 놓은 레포지토리 객체 주입.
    private ArticleRepositoryInterface articleRepositoryInterface;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private CommentService commentService;

    @GetMapping("/articles/new")
    public String newArticleFrom(){
        return "articles/new";
    }
    @PostMapping("/articles/create") //데이터 생성
    public String createArticle(ArticleDto articleDto){
        logger.info(articleDto.toString());
        ArticleEntity articleEntity = articleDto.toEntity();
        logger.info(articleEntity.toString());
        ArticleEntity saved = articleRepositoryInterface.save(articleEntity);
        logger.info(saved.toString());
        return "redirect:/articles/" + saved.getId(); //Post를 할 때마다 저장한 entity의 id에 따른 목록 보여줌.
    }
    @GetMapping("/articles/{id}") //데이터 단건 조회
    public String ReadArticle(@PathVariable Long id, Model model) //매개변수로 id를 받아옴
    {
        logger.info("id = " + id);
        ArticleEntity articleEntity = articleRepositoryInterface.findById(id).orElse(null); //crudrepository가 제공하는 메소드
        List<CommentDto> commentDtos = commentService.ReadComment(id);
        // id값으로 데이터를 찾을 때 해당 id가 없으면 null을 반환한다.
        model.addAttribute("article",articleEntity); //article이라는 이름으로 articleenity객체 등록
        model.addAttribute("commentDtos",commentDtos);
        return "articles/show";
    }
    @GetMapping("/articles") //데이터 모두 조회
    public String ReadAllArticles(Model model){
        List<ArticleEntity> articleEntity = articleRepositoryInterface.findAll(); // 형변환 필요. findAll 반환타입은 Iterable
        model.addAttribute("articleList",articleEntity);
        return "articles/index";
    }
    @GetMapping("/articles/{id}/edit")
    public String update(@PathVariable Long id,Model model){
        ArticleEntity articleEntity = articleRepositoryInterface.findById(id).orElse(null);
        model.addAttribute("article",articleEntity);
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String UpdateArticles(ArticleDto articleDto){
        logger.info(articleDto.toString());
        ArticleEntity articleEntity = articleDto.toEntity(); //DTO를 엔티티로 변환
        ArticleEntity target = articleRepositoryInterface.findById(articleEntity.getId()).orElse(null); //DB에서 기존 데이터 가져오기
        if (target !=null){
            articleRepositoryInterface.save(articleEntity); // 엔티티를 DB에 갱신
        }
        return "redirect:/articles/" + articleEntity.getId(); //리다이렉트
    }

    @GetMapping("/articles/{id}/delete")
    public String DeleteArticles(@PathVariable Long id, RedirectAttributes rttr){
        logger.info("삭제 요청이 들어왔습니다!");
        ArticleEntity target = articleRepositoryInterface.findById(id).orElse(null);
        logger.info(target.toString());
        if( target != null )
            articleRepositoryInterface.delete(target);
        rttr.addFlashAttribute("msg","삭제됐습니다!");

        return "redirect:/articles";
    }
}
