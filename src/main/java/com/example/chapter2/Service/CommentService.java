package com.example.chapter2.Service;

import com.example.chapter2.Dto.CommentDto;
import com.example.chapter2.Entity.ArticleEntity;
import com.example.chapter2.Entity.CommentEntity;
import com.example.chapter2.Repository.ArticleRepositoryInterface;
import com.example.chapter2.Repository.CommentRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepositoryInterface commentRepositoryInterface;
    @Autowired
    private ArticleRepositoryInterface articleRepositoryInterface;
    @Transactional
    public CommentDto CreateComment(Long articleId, CommentDto commentDto) {
        ArticleEntity articleEntity = articleRepositoryInterface.findById(articleId).orElseThrow(()-> new IllegalArgumentException(
                "댓글 생성 실패! " +"대상 게시글이 없습니다."));
        CommentEntity commentEntity = CommentEntity.createComment(commentDto,articleEntity);
        CommentEntity created = commentRepositoryInterface.save(commentEntity); //db에 댓글엔티티 저장
        return CommentDto.createCommentDto(created); // dto로 변환해 반환
    }

    public List<CommentDto> ReadComment(Long articleId) { // for문을 이용하는 방법
//        List<CommentEntity> commentEntities = commentRepositoryInterface.findByArticleId(articleId); //댓글 조회
//        List<CommentDto> dtos = new ArrayList<CommentDto>();
//        for (int i = 0; i <commentEntities.size() ; i++){ // 엔티티 -> dto반환
//            CommentEntity c = commentEntities.get(i);
//            CommentDto dto = CommentDto.createCommentDto(c);
//            dtos.add(dto);
//        }
//        return dtos;
        return commentRepositoryInterface.findByArticleId(articleId)
                .stream() //가져온 댓글 엔티티를 스트림화
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList()); // 스트림 데이터를 리스트 자료형으로 변환
    }

    @Transactional // db내용 변경 중 실패항 경우 대비
    public CommentDto UpdateComment(Long id, CommentDto commentDto) {
        CommentEntity target = commentRepositoryInterface.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! " +
                        "대상 댓글이 없습니다."));
        target.patch(commentDto);
        CommentEntity updated = commentRepositoryInterface.save(target);
        return CommentDto.createCommentDto(updated);
    }

    @Transactional
    public CommentDto DeleteComment(Long id) {
        CommentEntity target = commentRepositoryInterface.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패 ! " +
                        "대상 댓글이 없습니다."));
        commentRepositoryInterface.delete(target);
        return CommentDto.createCommentDto(target);
    }
}
