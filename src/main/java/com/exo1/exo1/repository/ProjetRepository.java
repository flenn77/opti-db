package com.exo1.exo1.repository;

import com.exo1.exo1.entity.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjetRepository extends JpaRepository<Projet, Long> {

    //Test Index (inutile)
    List<Projet> findByName(String name);

    //Test requete N+1
    @Query("SELECT distinct p from Projet p join fetch Task where p.name like %:name% ")
    List<Projet> findByTest(String name);

}
