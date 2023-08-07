package com.example.chapter2.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FirstController {
    @GetMapping("/hi") //페이지를 반환해달라는 URL요청을 접수
    public String niceToMeetYou(Model model){ // model 객체 받아오기
        model.addAttribute("username","한동근"); //model 객체가 value를 name에 연결해 브라우저 전송
        return "greetings"; //서버가 알아서 templates 디렉토리에서 mustaches 파일을 찾아 브라우저로 전송
    }
    @GetMapping("/bye")
    public String seeYouNext(Model model){
        model.addAttribute("nickname","한동근");
        return "goodbye";
    }
}
