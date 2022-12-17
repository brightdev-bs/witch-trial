package vanilla.witchtrial.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/board")
@Controller
public class PostController {

    @GetMapping
    public String getBoardListView(ModelMap map) {
        map.addAttribute("boardList", List.of());
        return "board/index";
    }

    @GetMapping("/{postId}")
    public String post(@PathVariable Long postId, ModelMap map) {
        map.addAttribute("post", "post"); // Todo : 실제 데이터 넣어줘야 함.
        return "board/postDetail";
    }


}
