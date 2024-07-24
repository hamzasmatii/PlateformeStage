package tn.esprit.artifact.entity;
import lombok.*;

import jakarta.persistence.*;
import org.springframework.core.SpringVersion;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_event;
    private String name;
    private Date startdate;
    private Date enddate;
    private String descrp;
    private String localisation;
    @Enumerated(EnumType.STRING)
    private TypeEvent typeevent;
    @ManyToMany(mappedBy = "events")
    private Set<User> users;



}
