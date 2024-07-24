package tn.esprit.artifact.Service;


import org.springframework.stereotype.Service;
import tn.esprit.artifact.Repository.QuestionRepository;
import tn.esprit.artifact.Repository.QuizRepository;
import tn.esprit.artifact.Repository.ResponseRepository;
import tn.esprit.artifact.entity.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ServiceQuiz implements IServiceQuiz {
    private QuizRepository quizRepository;
    private QuestionRepository questionRepository;
    private ResponseRepository responseRepository;


    public ServiceQuiz(QuizRepository quizRepository, QuestionRepository questionRepository, ResponseRepository responseRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
        this.responseRepository = responseRepository;

    }

    @Override
    public void addQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }
    @Override
    public void addReponses( Response reponse) {

        responseRepository.save(reponse);

    }
    @Override
    public Question addQuestion( int id_quiz ,Question question) {
        question.setId_quiz(id_quiz);
        questionRepository.save(question);
        int note=0;
        note=questionRepository.CalculNoteQuiz(id_quiz);
        Optional<Quiz> quizOptional = quizRepository.findById(id_quiz);
        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();
            quiz.setScore(note);
            quizRepository.save(quiz);
        }
        return question;
    }



    @Override
    public void addQuestionToQuiz(Integer quizId, Question question) {
        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        if(quiz.getQuestions()!=null){
            int restpoint=quiz.getScore()-quiz.getQuestions().stream().mapToInt(q->q.getQuestionNote()).sum();
            if(restpoint-question.getQuestionNote()>=0){
                question.setQuiz(quiz);
                questionRepository.save(question);
                System.out.println("ajout question avec success");
            }
            else{
                System.out.println("On peut pas ajouter");
            }

        }
        else{
            if(quiz.getScore()-question.getQuestionNote()>=0){
                question.setQuiz(quiz);
                questionRepository.save(question);
                System.out.println("ajout question avec success");
            }
            else{
                System.out.println("On peut pas ajouter");
            }
        }


    }
    @Override
    public void addResponseToQuestion(Integer questionId, List<String> studentResponses, int studentId) {
      /*  Question question = questionRepository.findById(questionId).orElse(null);

        Response response = new Response();

        response.setQuestion(question);
        response.setStudentResponses(studentResponses);

        Set<Integer> correctResponses = question.getCorrectResponsesIndex();
        double score = calculateScore(studentResponses, question);

        response.setScore(score);
        response.setStatus(getResponseStatus(score, correctResponses.size(),question.getQuestionNote()));

        responseRepository.save(response);*/
    }

    @Override
    public List<Quiz> displayQuiz() {
        return quizRepository.findAll();
    }

    @Override
    public List<Quiz> displayQuizByIdSociete(int idSociete) {
        return quizRepository.findByIdSociete(idSociete);
    }
    public Optional<Quiz> displayQuizById(int id) {
        return quizRepository.findById(id);
    }

    @Override
    public List<Question> displayQuestion() {
        return questionRepository.findAll();
    }

    @Override
    public List<Question> displayQuestionByIdQuiz(int idQuiz) {
        return questionRepository.findByIdQuiz(idQuiz);
    }

    @Override
    public List<Response> displayResponse() {
        return responseRepository.findAll();
    }

    @Override
    public List<Response> displayResponseByIdQuestion(int idQuestion) {
        return responseRepository.findByIdQuestion(idQuestion);
    }

    @Override
    public Quiz deleteQuiz(int q) {
        try {
            Optional<Quiz> optionalQuiz=quizRepository.findById(q);

            Quiz quizToDelete = optionalQuiz.get();

            quizRepository.deleteById(q);

            return quizToDelete;
        }catch (Exception e) {
            throw new IllegalArgumentException("Quiz not found");
        }
    }


    @Override
    public Question deleteQuestion(int q ) {
       try {
            Optional<Question> optionalQuestion=questionRepository.findById(q);

            Question questionToDelete = optionalQuestion.get();
           int id_quiz=questionToDelete.getId_quiz();

            questionRepository.deleteById(q);
           int note=0;
           note=questionRepository.CalculNoteQuiz(id_quiz);
           Optional<Quiz> quizOptional = quizRepository.findById(id_quiz);
           if (quizOptional.isPresent()) {
               Quiz quiz = quizOptional.get();
               quiz.setScore(note);
               quizRepository.save(quiz);
           }

            return questionToDelete;
        }catch (Exception e) {
            throw new IllegalArgumentException("Question not found");
        }
    }

    @Override
    public Quiz updateQuiz(int idQuiz , Quiz q) {
        Optional<Quiz> optionalQuiz = quizRepository.findById(idQuiz);

        if (optionalQuiz.isPresent()) {
            Quiz existingQuiz = optionalQuiz.get();
            // Update the existing stage entity with the fields from the updatedStage object
            existingQuiz.setNom(q.getNom());
            existingQuiz.setDescription(q.getDescription());
            existingQuiz.setTimelimit(q.getTimelimit());
            existingQuiz.setScore(q.getScore());
            existingQuiz.setId_societe(q.getId_societe());
            existingQuiz.setNote(q.getNote());

            return quizRepository.save(existingQuiz);
        } else {
            // Handle the case where the stage with the given ID is not found
            throw new IllegalArgumentException("Quiz not found with ID: " + idQuiz);
        }


    }

    @Override
    public void updateQuestion(Question question) {
      /*  Question q=questionRepository.findById(question.getId()).orElse(null);
        if(q.getStudentResponses()!=null){
            question.setResponses(q.getResponses());
        }
        question.setQuiz(q.getQuiz());
        questionRepository.save(question);*/
    }

    @Override
    public Quiz showQuiz(int q)  {
        return quizRepository.findById(q).orElse(null);
    }

   /* private double calculateScore(List<String> studentResponses, Question question) {
       List<String> correctAnswers = new ArrayList<>();

        List<Integer> correct=new ArrayList<>(question.getCorrectResponsesIndex());
        Collections.sort(correct);
        System.out.println(correct);
        for (int i : correct) {

            correctAnswers.add(question.getResponses().get(i));

        }

        System.out.println("***************");
        System.out.println(studentResponses);
        System.out.println(correctAnswers);
        int count = 0;
        for (String str1 : correctAnswers) {
            for (String str2 : studentResponses) {
                if (str1.equals(str2)) {
                    count++;
                    break;
                }
            }
        }
        double result=((double) count / correctAnswers.size() * question.getQuestionNote());

        return result;
    }*/

    private ResponseStatus getResponseStatus(double score, int numCorrectResponses, int note) {
        if (score == 0) {
            return ResponseStatus.INCORRECT;
        } else if (score < note) {
            return ResponseStatus.PARTIAL;
        } else {
            return ResponseStatus.CORRECT;
        }
    }
    //randomize question and response
   /* public List<Question> shuffleResponses(int idQuiz) {
        List<Question> result=new ArrayList<>();
        Quiz quiz=quizRepository.findById(idQuiz).orElse(null);
        for (Question question : quiz.getQuestions()) {
            List<String> responses = question.getResponses();
            Set<Integer> correctResponsesIndex = question.getCorrectResponsesIndex();
            List<Integer> indices = new ArrayList<>();
            for (int i = 0; i < responses.size(); i++) {
                indices.add(i);
            }
            Collections.shuffle(indices);
            List<String> shuffledResponses = new ArrayList<>();
            Set<Integer> shuffledCorrectResponsesIndex = new HashSet<>();
            for (int i = 0; i < indices.size(); i++) {
                int index = indices.get(i);
                shuffledResponses.add(responses.get(index));
                if (correctResponsesIndex.contains(index)) {
                    shuffledCorrectResponsesIndex.add(i);
                }
            }
            question.setResponses(shuffledResponses);
            question.setCorrectResponsesIndex(shuffledCorrectResponsesIndex);
            result.add(question);
        }
        Collections.shuffle(result);
        return (result);
    }*/

  @Override
    public Question getMostRespondedQuestionOnQuiz(int idQuiz){
        Quiz quiz=quizRepository.findById(idQuiz).orElse(null);
        Map<Question,List<Response>> questionResponses=new HashMap<>();
        for(Question question:quiz.getQuestions()){
            questionResponses.put(question,question.getStudentResponses());
        }

        Map<Question,Integer> questionCorrectCounts=new HashMap<>();
        for(Map.Entry<Question,List<Response>> entry:questionResponses.entrySet()){
            Question question=entry.getKey();
            int correctCount=0;
            for(Response response:entry.getValue()){
                if(response.getStatus().equals(ResponseStatus.CORRECT)){
                    correctCount++;
                }
            }
            questionCorrectCounts.put(question,correctCount);
        }
        //System.out.println(questionCorrectCounts);
        Map<Question,Integer> sortedMap=questionCorrectCounts.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue,newValue)->oldValue,
                        LinkedHashMap::new
                ));
        return sortedMap.keySet().iterator().next();
    }


}
