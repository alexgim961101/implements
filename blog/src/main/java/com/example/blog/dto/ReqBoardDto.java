package com.example.blog.dto;

import com.example.blog.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ReqBoardDto {
    private String title;
    private String content;

    public Board toEntity() {
        return new Board();
    }
}
