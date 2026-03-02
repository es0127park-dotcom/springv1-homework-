package com.example.springv1.board;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import jakarta.persistence.EntityManager;

@Import(BoardRepository.class)
@DataJpaTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void findById_test() {
        // given
        int id = 2;

        // then
        Board board = boardRepository.findById(id).get();

        // eye
        System.out.println("=================================================");
        System.out.println(board);
    }

    @Test
    public void findAll_test() {
        // given

        // then
        List<Board> boards = boardRepository.findAll();

        // eye
        System.out.println("=================================================");
        for (Board board : boards) {
            System.out.println(board);
        }
    }

    @Test
    public void save_test() {
        // given
        Board board = Board.builder()
                .title("title3")
                .content("content3")
                .build();

        // then
        boardRepository.save(board);

        // eye
        List<Board> boards = boardRepository.findAll();
        System.out.println("=================================================");
        for (Board b : boards) {
            System.out.println(b);
        }
    }

    @Test
    public void update_test() {
        // given
        int id = 2;

        // then
        Board board = boardRepository.findById(id).get();
        board.setTitle("title2-update");
        board.setContent("content2-update");
        em.flush();
        em.clear();

        // eye
        Board result = boardRepository.findById(id).get();
        System.out.println("=================================================");
        System.out.println(result);
    }

    @Test
    public void delete_test() {
        // given
        int id = 2;
        Board board = boardRepository.findById(id).get();

        // then
        boardRepository.delete(board);
        em.flush();

        // eye
        List<Board> boards = boardRepository.findAll();
        System.out.println("=================================================");
        for (Board b : boards) {
            System.out.println(b);
        }
    }

    @Test
    public void findByIdEager_test() {
        // given
        int id = 1;

        // then
        Board board = boardRepository.findById(id).get();

        // eye
        System.out.println("=================================================");
        System.out.println(board);
    }

    @Test
    public void findByIdLazy_test() {
        // given
        int id = 1;

        // then
        Board board = boardRepository.findById(id).get();

        // eye
        System.out.println("=================================================");
        System.out.println(board);
    }

    @Test
    public void findByIdJoinUser_test() {
        // given
        int id = 1;

        // then
        Board board = boardRepository.findByIdJoinUser(id).get();

        // eye
        System.out.println("=================================================");
        System.out.println(board);
    }

    @Test
    public void findByIdJoinUserAndReply_test() {
        // given
        int id = 1;

        // when
        Board board = boardRepository.findByIdJoinUserAndReply(id).get();

        // eye
        System.out.println("=================================================");
        System.out.println("Board ID : " + board.getId());
        System.out.println("username : " + board.getUser().getUsername());
        System.out.println("Reply : " + board.getReplies().get(1).getComment());
    }
}
