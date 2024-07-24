package tn.esprit.artifact.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.artifact.entity.Conditature;
import tn.esprit.artifact.entity.FileCandidature;

import java.util.List;

@Repository
public interface CandidatureRepository extends JpaRepository<Conditature,Long> {

    List<Conditature> findByNomContainingIgnoreCase(String nom);
    @Query("SELECT c FROM Conditature c WHERE c.idStudent = :idStudent")
    List<Conditature> findByIdStudent(Long idStudent);
    @Query("SELECT c FROM Conditature c WHERE c.idSociete = :idSociete")
    List<Conditature> findByIdSociete(Long idSociete);
    @Query(value = "SELECT descripsociete,email,image,localisationsocie,mdp,niveau,nom,num,prenom,specialite FROM user WHERE id_user = :userId", nativeQuery = true)
    List<Object[]> findUserDataByUserId(@Param("userId") Long userId);





}
