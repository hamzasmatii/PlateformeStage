package tn.esprit.artifact.Controller;



import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.artifact.Service.IServiceQuiz;
import tn.esprit.artifact.entity.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/quiz")
public class QuizController {
    private IServiceQuiz serviceQuiz;

    public QuizController(IServiceQuiz serviceQuiz) {
        this.serviceQuiz = serviceQuiz;
    }
    @PostMapping("/addQuiz")
    public void addQuiz(@RequestBody Quiz quiz){
        serviceQuiz.addQuiz(quiz);
    }
    @PostMapping("/addReponses")
    public void addReponses(@RequestBody Response reponses){
        serviceQuiz.addReponses(reponses);
    }
    @PostMapping("/addQuestionToQuiz/{idQuiz}")
    public  ResponseEntity<Question> addQuestionToQuiz(@PathVariable int idQuiz,@RequestBody Question question){
        serviceQuiz.addQuestion(idQuiz, question);
        return new ResponseEntity<>(question, HttpStatus.CREATED);
    }

    @GetMapping("/displayQuiz/{idSociete}")
    public List<Quiz> displayQuiz(@PathVariable int idSociete){
        return serviceQuiz.displayQuizByIdSociete(idSociete);
    }
    @GetMapping("/displayQuizByid/{id}")
    public Optional<Quiz> displayQuizByid(@PathVariable int id){
        return serviceQuiz.displayQuizById(id);
    }
    @GetMapping("/displayQuestion/{idquiz}")
    public List<Question> displayQuestion(@PathVariable int idquiz){
        return serviceQuiz.displayQuestionByIdQuiz(idquiz);
    }
    @GetMapping("/displayReponse/{idquestion}")
    public List<Response> displayReponse(@PathVariable int idquestion){
        return serviceQuiz.displayResponseByIdQuestion(idquestion);
    }

    @DeleteMapping("/deleteQuiz/{quizId}")
    public ResponseEntity<Quiz> deleteQuiz(@PathVariable int quizId) {
        try {
            Quiz deletedQuiz = serviceQuiz.deleteQuiz(quizId);

            // Create the response message
            String message = "Quiz with ID " + quizId + " deleted successfully.";

            // Create the response headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("Message", message);
            // Return the response entity with the response object and status OK
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }

        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateQuiz/{quizId}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable("quizId") int quizId, @RequestBody Quiz updatedQuiz) {
        try {
            Quiz updated = serviceQuiz.updateQuiz(quizId, updatedQuiz);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{quizId}")
    public ResponseEntity<Quiz> showQuiz(@PathVariable int quizId) {
        Quiz quiz = serviceQuiz.showQuiz(quizId);
        if (quiz != null) {
            return ResponseEntity.ok(quiz);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/deleteQuestionn/{idQuestion}")
    public ResponseEntity<Question> deleteQuestion(@PathVariable int idQuestion) {
        try {
            Question deletedQuestion = serviceQuiz.deleteQuestion(idQuestion);

            // Create the response message
            String message = "Quiz with ID " + idQuestion + " deleted successfully.";

            // Create the response headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("Message", message);
            // Return the response entity with the response object and status OK
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }

        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    /*@PostMapping("/addResponseToQuestion/{questionId}/{studentId}")
    public void addResponseToQuestion(@PathVariable Integer questionId, @RequestBody StudentResponse studentResponses, @PathVariable int studentId){
        serviceQuiz.addResponseToQuestion(questionId, studentResponses.getStudentResponse(), studentId);
    }

    @GetMapping("/displayQuiz")
    public List<Quiz> displayQuiz(){
        return serviceQuiz.displayQuiz();
    }
    @GetMapping("/displayQuestion")
    public List<Question> displayQuestion(){
        return serviceQuiz.displayQuestion();
    }
    @GetMapping("/displayResponse")
    public List<Response> displayResponse(){
        return serviceQuiz.displayResponse();
    }
    @DeleteMapping("/deleteQuiz/{idQuiz}")
    public void deleteQuiz(@PathVariable Integer idQuiz){
        serviceQuiz.deleteQuiz(idQuiz);
    }
    @DeleteMapping("/deleteQuestion/{idQuestion}")
    public void deleteQuestion(@PathVariable Integer idQuestion){
        serviceQuiz.deleteQuestion(idQuestion);
    }
    @PutMapping("/updateQuiz")
    public void updateQuiz(@RequestBody Quiz quiz){
        serviceQuiz.updateQuiz(quiz);
    }
    @PutMapping("/updateQuestion")
    public void updateQuestion(@RequestBody Question Question){
        serviceQuiz.updateQuestion(Question);
    }
  /*  @GetMapping("/diplayShuffledQuestionOfQuiz/{idQuiz}")
    public List<Question> shuffleResponses(@PathVariable int idQuiz){
        return serviceQuiz.shuffleResponses(idQuiz);
    }

    @GetMapping("/getMostRespondedQuestionOnQuiz/{idQuiz}")
    public Question getMostRespondedQuestionOnQuiz(@PathVariable int idQuiz){
        return serviceQuiz.getMostRespondedQuestionOnQuiz(idQuiz);
    }*/



    }

/*
@Getter
@Setter
class StudentResponse{
    List<String> studentResponse;
}*/