package com.example.springv1.board;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public List<Board> 게시글목록() {
        List<Board> boards = boardRepository.findAll();
        return boards;
    }

    public Board 게시글상세(Integer id) {
        Board board = boardRepository.findById(id);
        return board;
    }
}
