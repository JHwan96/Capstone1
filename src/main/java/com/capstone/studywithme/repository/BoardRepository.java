package com.capstone.studywithme.repository;

import com.capstone.studywithme.domain.Board;
import com.capstone.studywithme.domain.Room;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor

public class BoardRepository {
    private final EntityManager em;

    public void save(Board board){em.persist(board);}

    public Board findOne(Long id){return em.find(Board.class,id);}

    public List<Board> findAll(){
        return em.createQuery("select m from Board m", Board.class)
                .getResultList();
    }

    public List<Board> findByName(String name){
        return em.createQuery("select m from Board m where m.name = :name", Board.class)
                .setParameter("name",name)
                .getResultList();
    }

    public Board findOneByName(String name){
        return em.createQuery("select m from Board m where m.name = :name", Board.class)
                .setParameter("name",name)
                .getSingleResult();
    }

}
