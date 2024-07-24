package tn.esprit.artifact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.artifact.service.IStageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.artifact.entity.Stage;
import tn.esprit.artifact.service.IStageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class StageController {
    @Autowired
    IStageService stageService;

    @PostMapping("/stage")
    public ResponseEntity<Stage> addEvent(@RequestBody Stage stage) {
        Stage addedStage = stageService.addStage(stage);
        return new ResponseEntity<>(addedStage, HttpStatus.CREATED);
    }

    @PostMapping("/stage/postulerAuStage/{idEtudiant}/{idSociete}")
    public String postulerAuStage(@PathVariable("idEtudiant") Long idEtudiant, @PathVariable("idSociete") Long idSociete) {
        stageService.postulerAuStage(idEtudiant, idSociete);
        return "Postulation réussie";
    }


    @GetMapping("/stage/societe/{idsociete}")
    public ResponseEntity<List<Stage>> showStageSociete(@PathVariable("idsociete") Long stageId) {
        List<Stage> stages  = stageService.showStageSociete(stageId);
        if (stages != null) {
            return ResponseEntity.ok(stages);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/stage/{stageId}")
    public ResponseEntity<Stage> showStage(@PathVariable Long stageId) {
        Stage stage = stageService.showStage(stageId);
        if (stage != null) {
            return ResponseEntity.ok(stage);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/stage/search")
    public ResponseEntity<List<Stage>> searchStagebyttil(@RequestParam("query") String query) {
        List<Stage> stages = stageService.searchStagebyttile(query);
        return ResponseEntity.ok(stages);
    }

    @GetMapping("/stage")
    public ResponseEntity<List<Stage>> getAllStages() {
        List<Stage> stages = stageService.allStages();
        return ResponseEntity.ok(stages);
    }

    @PutMapping("/stage/{stageId}")
    public ResponseEntity<Stage> updateStage(@PathVariable("stageId") Long stageId, @RequestBody Stage updatedStage) {
        try {
            Stage updated = stageService.updateStage(stageId, updatedStage);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/stage/{stageId}")
    public ResponseEntity<Void> deleteStage(@PathVariable Long stageId) {
        try {
            Stage deletedStage = stageService.deleteStage(stageId);

            // Create the response message
            String message = "Stage with ID " + stageId + " deleted successfully.";

            // Create the response headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("Message", message);
            // Return the response entity with the response object and status OK
            return new ResponseEntity<>(headers, HttpStatus.OK);
        }

        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/stage/byDatedebut/{datedebut}")
    public ResponseEntity<List<Stage>> findStagesByDatedebut(@PathVariable("datedebut") @DateTimeFormat(pattern = "yyyy-MM-dd") Date datedebut) {
        List<Stage> stages = stageService.findStagesByDatedebut(datedebut);
        return ResponseEntity.ok(stages);
    }


}
