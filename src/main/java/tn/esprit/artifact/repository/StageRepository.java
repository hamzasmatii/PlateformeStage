package tn.esprit.artifact.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.artifact.entity.Stage;

import java.util.Date;
import java.util.List;

@Repository
public interface StageRepository extends JpaRepository<Stage,Long> {
    List<Stage> findByTitleContainingIgnoreCase(String title);
    List<Stage> findByIdsociete(Long idsociete);
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO doc (id_etud, id_societ) VALUES (:idEtudiant, :idSociete)", nativeQuery = true)
    void postulerAuStage(@Param("idEtudiant") Long idEtudiant, @Param("idSociete") Long idSociete);

     List<Stage> findStagesByDatedebutAfter(Date datedebut) ;







}


