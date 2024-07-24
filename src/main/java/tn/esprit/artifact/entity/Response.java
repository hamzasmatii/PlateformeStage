package tn.esprit.artifact.entity;

//import com.example.intermove.Entities.User.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int id_question;
    private boolean correctReponses;
    private String reponse;

   /* @ElementCollection
    private List<String> studentResponses;
*/
    //private double score;

    @Enumerated(EnumType.STRING)
    private ResponseStatus status;

    @ManyToOne
    @JsonIgnore
    private Question question;

    //@ManyToOne
    //private User student;

}
