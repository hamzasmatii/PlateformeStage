package tn.esprit.artifact.service;

import tn.esprit.artifact.entity.Stage;

import java.util.Date;
import java.util.List;

public interface IStageService {
    public Stage addStage(Stage s);

    public Stage showStage(Long s);

    public List<Stage> showStageSociete(Long idsociete);

    public List<Stage> allStages();

    public Stage updateStage(Long stageId, Stage s);

    public Stage deleteStage(Long s);

    public List<Stage> searchStagebyttile(String s);

    void postulerAuStage(Long idEtudiant, Long idSociete);

    public List<Stage> findStagesByDatedebut(Date datedebut);

}