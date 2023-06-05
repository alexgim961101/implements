package com.example.blog.controller;

import com.example.blog.dto.ReqBoardDto;
import com.example.blog.entity.Board;
import com.example.blog.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final ModelMapper modelMapper;

    @GetMapping("/board/write")
    public String boardWriteForm() {
        return "boardwrite";
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(ReqBoardDto reqBoardDto, Model model) {

        log.info("제목 : {}", reqBoardDto.getTitle());
        log.info("내용 : {}", reqBoardDto.getContent());

        Board board = modelMapper.map(reqBoardDto, Board.class);

        boardService.write(board);

        model.addAttribute("message", "글 작성이 완료되었습니다");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }

    @GetMapping("/board/list")
    public String boardList(Model model) {
        model.addAttribute("list", boardService.boardList());
        return "boardlist";
    }

    @GetMapping("/board/view/{id}")
    public String boardView(@PathVariable Long id, Model model) {

        model.addAttribute("board", boardService.boardView(id));

        return "boardview";
    }

    @GetMapping("/board/delete/{id}")
    public String boardDelete(@PathVariable Long id) {
        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable Long id, Model model) {
        model.addAttribute("board", boardService.boardView(id));
        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String update(@PathVariable Long id, ReqBoardDto reqBoardDto) {

        Board tmp = boardService.boardView(id);

        tmp.setTitle(reqBoardDto.getTitle());
        tmp.setContent(reqBoardDto.getContent());

        boardService.write(tmp);

        return "redirect:/board/list";
    }
}
