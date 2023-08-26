package com.example.board_project.controller;

import com.example.board_project.config.auth.PrincipalDetail;
import com.example.board_project.dto.*;
import com.example.board_project.entity.Board;
import com.example.board_project.entity.Category;
import com.example.board_project.service.BoardService;
import com.example.board_project.service.ReplyService;
import com.example.board_project.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Transactional
public class BoardController {

    private final UserService userService;
    private final BoardService boardService;
    private final ReplyService replyService;

    public BoardController(UserService userService, BoardService boardService, ReplyService replyService) {
        this.userService = userService;
        this.boardService = boardService;
        this.replyService = replyService;
    }

    @GetMapping("/board/write")
    public String write() {
        return "board/writeForm";
    }

    @GetMapping("/board/detail/{boardId}")
    public String loginDetailForm(@PathVariable int boardId, Model model, @AuthenticationPrincipal PrincipalDetail principalDetail) {
        UserSelectDto userSelectDto = userService.takeLoginUser(principalDetail);
        BoardSelectDto boardSelectDto = boardService.detail(boardId);
        List<ReplyCommentDto> replyCommentDtoList = replyService.findByBoardId(boardId);
        model.addAttribute("replyList", replyCommentDtoList);
        model.addAttribute("board", boardSelectDto);
        model.addAttribute("loginUser", userSelectDto);
        return "board/detail";
    }
    @GetMapping("/auth/board/detail/{boardId}")
    public String detailForm(@PathVariable int boardId, Model model) {
        BoardSelectDto boardSelectDto = boardService.detail(boardId);
        List<ReplyCommentDto> replyCommentDtoList = replyService.findByBoardId(boardId);
        model.addAttribute("replyList", replyCommentDtoList);
        model.addAttribute("board", boardSelectDto);
      //  model.addAttribute("loginUser", null);
        return "board/detail";
    }

    @GetMapping("/board/modifyForm/{boardId}")
    public String modifyForm(@PathVariable int boardId,Model model) {
        BoardSelectDto boardSelectDto = boardService.modifyForm(boardId);
        model.addAttribute("board", boardSelectDto);
        return "/board/modifyForm";
    }

//    @GetMapping("/auth/board/search")
//    public String search(@RequestParam("searchTitle") String searchTitle, Model model,@PageableDefault(size = 8, page = 0, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
//        Page<BoardSelectDto> boardSelectDtoPage=boardService.searchBoard(searchTitle,pageable);
//        int nowPage = boardSelectDtoPage.getPageable().getPageNumber() + 1;
//        int startPage = (nowPage - 1) / 5 * 5 + 1;
//        int endPage = (startPage + 4 > boardSelectDtoPage.getTotalPages()) ? boardSelectDtoPage.getTotalPages() : startPage + 4;
//        model.addAttribute("boardList", boardSelectDtoPage);
//        model.addAttribute("searchTitle", searchTitle);
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("nowPage", nowPage);
//        model.addAttribute("endPage", endPage);
//        return "index";
//    }
//
//    @GetMapping("/auth/board/category")
//    public String category(@RequestParam("category") Category category, Model model, @PageableDefault(size = 8, page = 0, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
//
//
//        Page<BoardSelectDto> boardSelectDtoPage=boardService.categoryBoard(category,pageable);
//        int nowPage = boardSelectDtoPage.getPageable().getPageNumber() + 1;
//        int startPage = (nowPage - 1) / 5 * 5 + 1;
//        int endPage = (startPage + 4 > boardSelectDtoPage.getTotalPages()) ? boardSelectDtoPage.getTotalPages() : startPage + 4;
//        model.addAttribute("boardList", boardSelectDtoPage);
//        model.addAttribute("category", category);
//        model.addAttribute("startPage", startPage);
//        model.addAttribute("nowPage", nowPage);
//        model.addAttribute("endPage", endPage);
//
//        return "index";
//    }
}
