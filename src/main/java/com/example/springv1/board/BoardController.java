package com.example.springv1.board;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.springv1.core.ex.Exception401;
import com.example.springv1.user.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;
    private final HttpSession session;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        List<BoardResponse.DTO> boards = boardService.게시글목록();
        request.setAttribute("models", boards);
        return "index";
    }

    @GetMapping("/boards/{id}")
    public String detail(@PathVariable("id") Integer id, HttpServletRequest request) {
        BoardResponse.DetailDTO board = boardService.게시글상세(id);
        request.setAttribute("model", board);
        return "board/detail";
    }

    @GetMapping("/boards/save-form")
    public String saveForm() {
        // 인증
        User sessionUser = (User) session.getAttribute("session");
        if (sessionUser == null) {
            throw new Exception401("로그인이 필요합니다.");
        }
        return "board/save-form";
    }

    @GetMapping("/boards/{id}/update-form")
    public String updateForm(@PathVariable("id") Integer id, HttpServletRequest request) {
        // 인증
        User sessionUser = (User) session.getAttribute("session"); // 세션 꺼냄
        if (sessionUser == null) {
            throw new Exception401("로그인이 필요합니다.");
        }
        BoardResponse.DTO board = boardService.게시글수정폼(id, sessionUser.getId());
        request.setAttribute("model", board);
        return "board/update-form";
    }

    @PostMapping("/boards/save")
    public String save(BoardRequest.SaveDTO requestDTD) {
        // 인증
        User sessionUser = (User) session.getAttribute("session"); // 세션 꺼냄
        if (sessionUser == null) {
            throw new Exception401("로그인이 필요합니다.");
        }

        boardService.게시글추가(requestDTD, sessionUser);
        return "redirect:/";
    }

    @PostMapping("/boards/{id}/delete")
    public String deleteById(@PathVariable("id") Integer id) {
        // 인증
        User sessionUser = (User) session.getAttribute("session"); // 세션 꺼냄
        if (sessionUser == null) {
            throw new Exception401("로그인이 필요합니다.");
        }
        boardService.게시글삭제(id, sessionUser.getId());
        return "redirect:/";
    }

    @PostMapping("/boards/{id}/update")
    public String updateById(@PathVariable("id") Integer id, BoardRequest.UpdateDTO requestDTO) {
        // 인증
        User sessionUser = (User) session.getAttribute("session"); // 세션 꺼냄
        if (sessionUser == null) {
            throw new Exception401("로그인이 필요합니다.");
        }
        boardService.게시글수정(id, requestDTO, sessionUser.getId());
        return "redirect:/boards/" + id;
    }
}
