package tn.esprit.artifact.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.artifact.entity.Conditature;
import tn.esprit.artifact.entity.Quiz;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz,Integer> {
    @Query("SELECT q FROM Quiz q WHERE q.id_societe = :idSociete")
    List<Quiz> findByIdSociete(@Param("idSociete") int idSociete);


}
