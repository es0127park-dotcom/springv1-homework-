package com.example.springv1.board;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springv1.core.ex.Exception403;
import com.example.springv1.core.ex.Exception404;
import com.example.springv1.user.User;

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
        // Board board = boardRepository.findById(id).get();
        Board board = boardRepository.findByIdJoinUser(id)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));
        BoardResponse.DetailDTO dto = new BoardResponse.DetailDTO(board);
        return dto;
    }

    @Transactional
    public void 게시글추가(BoardRequest.SaveDTO requestDTO, User sessionUser) {
        Board board = requestDTO.toEntity(sessionUser);
        boardRepository.save(board);
    }

    @Transactional
    public void 게시글삭제(Integer boardId, Integer sessionUserId) {
        Board board = boardRepository.findByIdJoinUser(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));

        // 권한
        if (board.getUser().getId() != sessionUserId) {
            throw new Exception403("게시글을 삭제할 권한이 없습니다.");
        }

        boardRepository.delete(board);
    }

    public BoardResponse.DTO 게시글수정폼(Integer boardId, Integer sessionUserId) {
        Board board = boardRepository.findByIdJoinUser(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));

        // 권한
        if (board.getUser().getId() != sessionUserId) {
            throw new Exception403("게시글을 수정할 권한이 없습니다.");
        }

        BoardResponse.DTO dto = new BoardResponse.DTO(board);
        return dto;
    }

    @Transactional
    public void 게시글수정(Integer boardId, BoardRequest.UpdateDTO requestDTO, Integer sessionUserId) {
        Board board = boardRepository.findByIdJoinUser(boardId)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));

        // 권한
        if (board.getUser().getId() != sessionUserId) {
            throw new Exception403("게시글을 수정할 권한이 없습니다.");
        }

        board.setTitle(requestDTO.getTitle());
        board.setContent(requestDTO.getContent());
    }
}
