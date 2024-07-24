package tn.esprit.artifact.repository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.artifact.entity.Doc;

import java.util.List;
@Repository
public interface DocRepository extends JpaRepository<Doc,Long> {

    @Transactional
    @Query(value ="SELECT * FROM doc WHERE id_etud = :idetud", nativeQuery = true)
    Doc findByIdetudiant(@Param("idetud") Long idetud);

    @Query(value ="SELECT * FROM doc WHERE id_societ = :idsociet", nativeQuery = true)
    List<Doc> findByIdsociete(@Param("idsociet") Long idsociet);
}
