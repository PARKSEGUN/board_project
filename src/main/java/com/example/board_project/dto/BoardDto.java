package com.example.board_project.dto;

import com.example.board_project.entity.Board;
import com.example.board_project.entity.Category;
import lombok.*;


import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BoardDto { //오직 Board의 정보만 갖고있다.

    private int id;

    private Category category;

    private String title;

    private String content;

    private int hit;    //board 가 작성되는 순간 0 으로 설정

    private int userComputer;

    private Timestamp createTime;

    public static BoardDto boardToBoardDto(Board board) {
        BoardDto boardDto = BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .category(board.getCategory())
                .content(board.getContent())
                .hit(board.getHit())
                .createTime(board.getCreateTime())
                .build();
        return boardDto;

    }
}
