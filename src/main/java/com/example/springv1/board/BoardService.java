package com.example.springv1.board;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardResponse.DTO> 게시글목록() {
        List<Board> boards = boardRepository.findAll();
        List<BoardResponse.DTO> dtoList = boards.stream()
                .map(board -> new BoardResponse.DTO(board))
                .toList();
        return dtoList;
    }

    public BoardResponse.DetailDTO 게시글상세(Integer id) {
        Board board = boardRepository.findById(id).get();
        BoardResponse.DetailDTO dto = new BoardResponse.DetailDTO(board);
        return dto;
    }

    @Transactional
    public void 게시글추가(BoardRequest.SaveDTO requestDTO) {
        Board board = requestDTO.toEntity();
        boardRepository.save(board);
    }

    @Transactional
    public void 게시글삭제(Integer id) {
        Board board = boardRepository.findById(id).get();
        boardRepository.delete(board);
    }

    public Board 게시글수정폼(Integer id) {
        Board board = boardRepository.findById(id).get();
        return board;
    }

    @Transactional
    public void 게시글수정(Integer id, BoardRequest.UpdateDTO requestDTO) {
        Board board = boardRepository.findById(id).get();
        board.setTitle(requestDTO.getTitle());
        board.setContent(requestDTO.getContent());
    }
}
