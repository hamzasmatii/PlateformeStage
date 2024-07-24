package tn.esprit.artifact.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.artifact.entity.Conditature;
import tn.esprit.artifact.entity.FileCandidature;

import java.util.List;

@Repository
public interface CandidatureFileRepository extends JpaRepository<FileCandidature,Long> {
    @Query("SELECT c FROM FileCandidature c WHERE c.id_candidature = :idCandidature and c.idStudent = :idStudent")
    FileCandidature findByIdCandidature(@Param("idCandidature") Long IdCandidature , @Param("idStudent") Long idStudent);
    @Query("SELECT c FROM FileCandidature c WHERE c.id_candidature =:idCandidature")
    List<FileCandidature> findByIdCandidatureIdStudent(@Param("idCandidature") Long idCandidature);



}
