package com.example.blog.service;

import com.example.blog.entity.Board;
import com.example.blog.entity.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void write(Board board) {
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public List<Board> boardList() {
        List<Board> all = boardRepository.findAll();
        return all;
    }

    @Transactional
    public Board boardView(Long id) {

        Board boardEntity = boardRepository.findByIdForUpdate(id).get();
        boardEntity.upCnt();

        return boardEntity;
    }

    @Transactional
    public void boardDelete(Long id) {
        boardRepository.deleteById(id);
    }
}
