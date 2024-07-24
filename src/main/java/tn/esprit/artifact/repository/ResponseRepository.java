package tn.esprit.artifact.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.artifact.entity.Quiz;
import tn.esprit.artifact.entity.Response;

import java.util.List;

@Repository
public interface ResponseRepository extends JpaRepository<Response,Integer> {
    @Query("SELECT r FROM Response r WHERE r.id_question = :idQuestion")
    List<Response> findByIdQuestion(@Param("idQuestion") int idQuestion);
}
