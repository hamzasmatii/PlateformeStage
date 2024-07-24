package tn.esprit.artifact.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;
    private String question;
    private QuestionType type;
    private int questionNote;
    private int id_quiz;

    /*@ElementCollection
    @CollectionTable(name = "question_responses")
    private List<String> responses;

    @ElementCollection
    @CollectionTable(name = "question_correct_responses_index")
    private Set<Integer> correctResponsesIndex; */
    @ManyToOne
    @JsonIgnore
    private Quiz quiz;
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Response> studentResponses;




}
