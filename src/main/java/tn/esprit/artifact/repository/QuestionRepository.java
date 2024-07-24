package tn.esprit.artifact.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.artifact.entity.Question;
import tn.esprit.artifact.entity.Quiz;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {
    @Query("SELECT q FROM Question q WHERE q.id_quiz = :idQuiz")
    List<Question> findByIdQuiz(@Param("idQuiz") int idQuiz);
    @Query("SELECT SUM(q.questionNote) FROM Question q WHERE q.id_quiz = :idQuiz")
    Integer CalculNoteQuiz(@Param("idQuiz") int idQuiz);
}
