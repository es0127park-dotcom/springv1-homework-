package com.example.springv1.board;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BoardRepository {

    private final EntityManager em;

    public Board findById(Integer id) {
        Board board = em.find(Board.class, id);
        return board;
    }

    public List<Board> findAll() {
        List<Board> boards = em.createQuery("select b from Board b", Board.class).getResultList();
        return boards;
    }

    public void save(Board board) {
        em.persist(board);
    }

    public void delete(Board board) {
        em.remove(board);
    }
}
