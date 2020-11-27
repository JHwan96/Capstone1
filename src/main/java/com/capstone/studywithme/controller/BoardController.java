package com.capstone.studywithme.controller;

import com.capstone.studywithme.domain.Board;
import com.capstone.studywithme.service.BoardService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.junit.runner.Request;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/make/board")
    public CreateBoardResponse makeBoard(@RequestBody CreateBoardRequest request){
        Board board = new Board();
        board.setName(request.getName());
        Long id = boardService.makeBoard(board);
        return new CreateBoardResponse(id, board.getName());
    }

    @GetMapping("/boards")
    public Result checkBoards(){
        List<Board> findBoards = boardService.findBoards();
        List<BoardController.BoardDto> collect = findBoards.stream()
                .map(m->new BoardController.BoardDto(m.getName()))
                .collect(Collectors.toList());
        return new Result(collect.size(),collect);
    }

    @GetMapping("/boards/{boardName}")
    public SearchBoardResponse checkBoard(@PathVariable("boardName") SearchBoardRequest boardName){
        Board findBoard = boardService.findByName(boardName.getName());
        return new SearchBoardResponse(findBoard.getId(), findBoard.getName());
    }

    @PutMapping("/boards/{id}")
    public UpdateBoardResponse updateBoard(
            @PathVariable("id") Long boardId,
            @RequestBody UpdateBoardRequest request){
        boardService.update(boardId, request.getName());
        Board findboard = boardService.findOne(boardId);
        return new UpdateBoardResponse(findboard.getId(), findboard.getName());
    }

    @Data
    @AllArgsConstructor
    static class Result<T>{
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class BoardDto{
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class CreateBoardResponse{
        @NotEmpty
        private Long id;
        @NotEmpty
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class CreateBoardRequest{
        @NotEmpty
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class SearchBoardResponse{
        @NotEmpty
        private Long id;
        @NotEmpty
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class SearchBoardRequest{
        @NotEmpty
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateBoardResponse{
        @NotEmpty
        private Long id;
        @NotEmpty
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateBoardRequest{
        @NotEmpty
        private String name;
    }



}