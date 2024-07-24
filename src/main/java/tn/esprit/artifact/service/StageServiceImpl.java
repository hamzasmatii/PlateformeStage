package tn.esprit.artifact.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.artifact.entity.Doc;
import tn.esprit.artifact.entity.Stage;
import tn.esprit.artifact.repository.DocRepository;
import tn.esprit.artifact.repository.StageRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class StageServiceImpl implements IStageService{

    @Autowired
    StageRepository stageRep;
    DocRepository docRep;
    DocRepository docRepository;


    @Override
    public Stage addStage(Stage s) {
        s.calculateDuration();
        stageRep.save(s);
        return s;
    }


    @Override
    public Stage showStage(Long s) {
        return stageRep.findById(s).orElse(null);
    }

    @Override
    public List<Stage> showStageSociete(Long idsociete) {
        Iterable<Stage> satagesIterable = stageRep.findByIdsociete(idsociete);
        List<Stage> stagesList = new ArrayList<>();
        for (Stage stage : satagesIterable) {
            stagesList.add(stage);
        }
        return stagesList;
    }

    @Override
    public List<Stage> allStages() {
        Iterable<Stage> satagesIterable = stageRep.findAll();
        List<Stage> stagesList = new ArrayList<>();
        for (Stage stage : satagesIterable) {
            stagesList.add(stage);
        }
        return stagesList;
    }


    @Override
    public Stage updateStage(Long stageId, Stage updatedStage) {
        Optional<Stage> optionalStage = stageRep.findById(stageId);

        if (optionalStage.isPresent()) {
            Stage existingStage = optionalStage.get();
            // Update the existing stage entity with the fields from the updatedStage object
            existingStage.setTitle(updatedStage.getTitle());
            existingStage.setDatedebut(updatedStage.getDatedebut());
            existingStage.setDatefin(updatedStage.getDatefin());
            existingStage.setSujet(updatedStage.getSujet());
            existingStage.setEmail(updatedStage.getEmail());
            existingStage.setNum(updatedStage.getNum());
            // You can update more fields as needed
            existingStage.calculateDuration();
            // Save the updated stage entity
            return stageRep.save(existingStage);
        } else {
            // Handle the case where the stage with the given ID is not found
            throw new IllegalArgumentException("Stage not found with ID: " + stageId);
        }
    }


    @Override
    public Stage deleteStage(Long s) {
        try{
            Optional<Stage> optionalStage = stageRep.findById(s);



            // If the stage exists, retrieve it
            Stage stageToDelete = optionalStage.get();

            // Delete the stage by its ID
            stageRep.deleteById(s);

            // Return the deleted stage
            return stageToDelete;
        } catch(Exception e) {
            // If the stage does not exist, throw an exception or handle it in any other appropriate way
            throw new IllegalArgumentException("stage not found");
        }
    }

    @Override
    public List<Stage> searchStagebyttile(String s) {
        Iterable<Stage> satagesIterable = stageRep.findByTitleContainingIgnoreCase(s);
        List<Stage> stagesList = new ArrayList<>();
        for (Stage stage : satagesIterable) {
            stagesList.add(stage);
        }
        return stagesList;

    }






    @Override
    @Transactional
    public void postulerAuStage(Long idEtudiant, Long idSociete) {
        stageRep.postulerAuStage(idEtudiant, idSociete);
    }

    @Override
    public List<Stage> findStagesByDatedebut(Date datedebut) {
        Iterable<Stage> satagesIterable = stageRep.findStagesByDatedebutAfter(datedebut);
        List<Stage> stagesList = new ArrayList<>();
        for (Stage stage : satagesIterable) {
            stagesList.add(stage);
        }
        return stagesList;
    }
}
