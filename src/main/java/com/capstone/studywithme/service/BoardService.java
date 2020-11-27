package com.capstone.studywithme.service;

import com.capstone.studywithme.domain.Board;
import com.capstone.studywithme.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor
public class BoardService {
    private BoardRepository boardRepository;

    @Transactional
    public Long makeBoard(Board board){
        validateDuplicateBoard(board);
        boardRepository.save(board);
        return board.getId();
    }

    private void validateDuplicateBoard(Board board){
        List<Board> findBoard = boardRepository.findByName(board.getName());
        if(!findBoard.isEmpty()){
            throw new IllegalStateException("이미 존재하는 게시판 이름입니다.");
        }
    }

    public List<Board> findBoards(){return boardRepository.findAll();}

    public Board findOne(Long boardId){return boardRepository.findOne(boardId);}

    public Board findByName(String boardName){return boardRepository.findOneByName(boardName);}

    @Transactional
    public void update(Long id, String name){
        Board board = boardRepository.findOne(id);
        board.setName(name);
    }
}
