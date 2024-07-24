package tn.esprit.artifact.Service;



import tn.esprit.artifact.entity.*;

import java.util.List;
import java.util.Optional;

public interface IServiceQuiz {
    void addQuiz(Quiz quiz);
    public void addReponses(Response reponse);
    public Question addQuestion(int id_quiz , Question question);
    void addQuestionToQuiz(Integer quizId, Question question);
    void addResponseToQuestion(Integer questionId, List<String> studentResponses, int studentId) ;
    List<Quiz> displayQuiz();
    List<Quiz> displayQuizByIdSociete(int idSociete);
    List<Question> displayQuestion();
    List<Question> displayQuestionByIdQuiz(int idQuiz);
    List<Response> displayResponse();
    List<Response> displayResponseByIdQuestion(int idQuestion);
    Quiz deleteQuiz(int q);
    Question deleteQuestion(int q);
    Quiz updateQuiz(int idQuiz , Quiz quiz);
    void updateQuestion(Question question);

    public Quiz showQuiz(int q);
   // public List<Question> shuffleResponses(int idQuiz);

    Question getMostRespondedQuestionOnQuiz(int idQuiz);
    public Optional<Quiz> displayQuizById(int id);

}
