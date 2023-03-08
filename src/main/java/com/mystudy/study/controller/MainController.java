package com.mystudy.study.controller;

import com.mystudy.study.entity.Board;
import com.mystudy.study.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MainController {
    @Autowired
    private BoardService boardService;
    @GetMapping("/board/write")
    public String boardWriteForm(){
        return "boardWrite";
    }

    @PostMapping("/board/writedo")
    public String boardWriteDo(Board board, Model model, MultipartFile file) throws Exception{
        boardService.boardWrite(board, file);
        model.addAttribute("message", "게시글이 작성되었습니다.");
        model.addAttribute("rtnUrl", "/board/list");
        return "alertMessage";
    }

    @GetMapping("/board/list")
    public String boardList(Model model){
        model.addAttribute("list", boardService.boardList());
        return "boardList";
    }

    @GetMapping("/board/view")
    public String boardView(Model model, Integer id){
        model.addAttribute("board", boardService.boardView(id));
        return "boardView";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id){
        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify")
    public String boardModify(Model model, Integer id){
        model.addAttribute("board", boardService.boardView(id));
        return "boardModify";
    }

    @PostMapping("/board/modifyDo")
    public String boardModifyDo(Board board, MultipartFile file) throws Exception{
        Board temp = boardService.boardView(board.getId());
        temp.setTitle(board.getTitle());
        temp.setContent(board.getContent());
        boardService.boardWrite(temp, file);

        return "redirect:/board/view(id="+board.getId()+"})";
    }
}
