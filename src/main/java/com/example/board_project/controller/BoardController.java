package com.example.board_project.controller;

import com.example.board_project.config.auth.PrincipalDetail;
import com.example.board_project.entity.Board;
import com.example.board_project.entity.Reply;
import com.example.board_project.entity.User;
import com.example.board_project.service.BoardService;
import com.example.board_project.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;
    @Autowired
    private ReplyService replyService;

    @GetMapping("/board/write")
    public String write() {
        return "board/writeForm";
    }

    @GetMapping("/board/detail/{boardId}")
    public String loginDetailForm(@PathVariable int boardId, Model model, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        User loginUser = principalDetail.getUser();
        Board board = boardService.detail(boardId);
        List<Reply> replyList = replyService.findByBoardId(boardId);
        model.addAttribute("replyList", replyList);
        model.addAttribute("board", board);
        model.addAttribute("loginUser", loginUser);
        return "/board/detail";
    }
    @GetMapping("/auth/board/detail/{boardId}")
    public String detailForm(@PathVariable int boardId, Model model) {
        Board board = boardService.detail(boardId);
        List<Reply> replyList = replyService.findByBoardId(boardId);
        model.addAttribute("replyList", replyList);
        model.addAttribute("board", board);
        return "/board/detail";
    }

    @GetMapping("/board/modifyForm/{boardId}")
    public String modifyForm(@PathVariable int boardId,Model model) {
        Board board = boardService.modifyForm(boardId);
        model.addAttribute("board", board);
        return "/board/modifyForm";
    }

    @GetMapping("/auth/board/search")
    public String search(@RequestParam("searchTitle") String searchTitle, Model model,@PageableDefault(size = 8, page = 0, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Board> boardList=boardService.searchBoard(searchTitle,pageable);
        System.out.println(boardList.getNumberOfElements());
        int nowPage = boardList.getPageable().getPageNumber() + 1;
        int startPage = (nowPage - 1) / 5 * 5 + 1;
        int endPage = (startPage + 4 > boardList.getTotalPages()) ? boardList.getTotalPages() : startPage + 4;
        model.addAttribute("boardList", boardList);
        model.addAttribute("searchTitle", searchTitle);
        model.addAttribute("startPage", startPage);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("endPage", endPage);
        return "index";
    }
}
