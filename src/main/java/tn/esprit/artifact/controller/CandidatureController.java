package tn.esprit.artifact.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.artifact.Service.CandidatureFileService;
import tn.esprit.artifact.Service.CandidatureServiceIMPL;
import tn.esprit.artifact.Service.ICondidatureService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.artifact.entity.Conditature;
import tn.esprit.artifact.entity.FileCandidature;
import tn.esprit.artifact.entity.Stage;
import tn.esprit.artifact.Service.ICondidatureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tn.esprit.artifact.Service.CandidatureServiceIMPL.DIRECTORYlettre;
import static tn.esprit.artifact.Service.CandidatureServiceIMPL.DIRECTORYcv;

@RestController
@RequestMapping("/api")
public class CandidatureController {

    private final ICondidatureService candidatureService;
    @Autowired
     CandidatureFileService candidatureFileService;

    @Autowired
    public CandidatureController(CandidatureServiceIMPL candidatureService, CandidatureFileService candidatureFileService) {
        this.candidatureService = candidatureService;
        this.candidatureFileService = candidatureFileService;
    }

    @PostMapping("/candidature")
    public ResponseEntity<Conditature> addCandidature(@RequestBody Conditature conditature) {
        Conditature addConditature = candidatureService.addConditature(conditature);
        return new ResponseEntity<>(addConditature, HttpStatus.CREATED);
    }

    @GetMapping("candidature/{candidatureId}")
    public ResponseEntity<Conditature> showCandidature(@PathVariable Long candidatureId) {
        Conditature conditature = candidatureService.showConditature(candidatureId);
        if (conditature != null) {
            return ResponseEntity.ok(conditature);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/candidature")
    public ResponseEntity<List<Conditature>> getAllCandidature() {
        List<Conditature> candidatures = candidatureService.allCandidature();
        return ResponseEntity.ok(candidatures);
    }

    @PutMapping("/candidature/{candidatureId}")
    public ResponseEntity<Conditature> updateCandidature(@PathVariable("candidatureId") Long candidatureId, @RequestBody Conditature updatedCandidature) {
        try {
            Conditature updated = candidatureService.updateCandidature(candidatureId, updatedCandidature);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/candidature/search")
    public ResponseEntity<List<Conditature>> searchCandidatureByNom(@RequestParam("query") String query) {
        List<Conditature> conditatures = candidatureService.searchCandidatureByNom(query);
        return ResponseEntity.ok(conditatures);
    }

    @DeleteMapping("/candidature/{candidatureId}")
    public ResponseEntity<Void> deleteCandidature(@PathVariable Long candidatureId) {
        try {
            Conditature deletedCandidature = candidatureService.deleteCandidature(candidatureId);

            // Create the response message
            String message = "Candidature with ID " + candidatureId + " deleted successfully.";

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
    @GetMapping("/candidature/searchByIdStudent/{idStudent}")
    public ResponseEntity<List<Conditature>> getCandidatureByIdStudent(@PathVariable Long idStudent) {
        List<Conditature> candidatures = candidatureService.getCandidatureByIdStudent(idStudent);
        return ResponseEntity.ok(candidatures);
    }

    @GetMapping("/candidature/searchByIdSociete/{idSociete}")
    public ResponseEntity<List<Conditature>> getCandidatureByIdSociete(@PathVariable Long idSociete) {
        List<Conditature> candidatures = candidatureService.getCandidatureByIdSociete(idSociete);
        return ResponseEntity.ok(candidatures);
    }

    @GetMapping("/candidature/lettre/{filename}")
    public ResponseEntity<InputStreamResource> getlettre(@PathVariable String filename) throws IOException {
        Path path = null;

        path = Paths.get(DIRECTORYlettre + filename);


        if (Files.exists(path)) {
            InputStreamResource resource = new InputStreamResource(Files.newInputStream(path));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } else {
            // Handle file not found
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/candidature/cv/{filename}")
    public ResponseEntity<InputStreamResource> getcv(@PathVariable String filename) throws IOException {
        Path path = null;

        path = Paths.get(DIRECTORYcv + filename);


        if (Files.exists(path)) {
            InputStreamResource resource = new InputStreamResource(Files.newInputStream(path));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } else {
            // Handle file not found
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/candidature/file")
    public ResponseEntity<FileCandidature> addFileCandidature(@RequestBody FileCandidature file) {
        FileCandidature addFileConditature = candidatureFileService.addFileConditature(file);
        return new ResponseEntity<>(addFileConditature, HttpStatus.CREATED);
    }
    @GetMapping("/candidature/file-codida/{idcondida}")
    public ResponseEntity<List<FileCandidature>> getCandidatureFileByIdCondidature(@PathVariable Long idcondida) {
        List<FileCandidature> candidatures = candidatureFileService.getFileCandidatureByIdCandidature(idcondida);
        return ResponseEntity.ok(candidatures);
    }
    @GetMapping("/candidature/file/{candidatureFileId}")
    public ResponseEntity<FileCandidature> showCandidatureFileBYid(@PathVariable Long candidatureFileId) {
        FileCandidature conditature = candidatureFileService.showFileConditature(candidatureFileId);
        if (conditature != null) {
            return ResponseEntity.ok(conditature);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/candidature/file/{candidatureId}")
    public ResponseEntity<FileCandidature> updateCandidatureFile(@PathVariable("candidatureId") Long candidatureId, @RequestBody FileCandidature updatedCandidature) {
        try {
            FileCandidature updated = candidatureFileService.updateFileCandidature(candidatureId, updatedCandidature);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @PutMapping(value ="/candidature/file/cv/{candidatureId}",consumes = "multipart/form-data")
    public ResponseEntity<Void> updateCv(@PathVariable Long candidatureId, @RequestParam(name = "cvFile", required = false) MultipartFile cvFile) throws IOException {
        candidatureFileService.updatecv(candidatureId, cvFile);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value ="/candidature/file/lettre/{candidatureId}",consumes = "multipart/form-data")
    public ResponseEntity<Void> updateLettre(@PathVariable Long candidatureId, @RequestParam(name = "lettreFile", required = false) MultipartFile lettreFile) throws IOException {
        candidatureFileService.updatelettre(candidatureId, lettreFile);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /*@PutMapping(value ="/candidature/cv/{idfile}/{idStudent}",consumes = "multipart/form-data")
    public ResponseEntity<FileCandidature> updatecv(@PathVariable Long idfile,@PathVariable Long idStudent, @RequestParam(name = "cvFile", required = false) MultipartFile cvFile) throws IOException {
         candidatureFileService.updatecv(idfile,idStudent, cvFile);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }*/

    @DeleteMapping("/candidature/file/{candidatureId}")
    public ResponseEntity<Void> deleteCandidatureFile(@PathVariable Long candidatureId) {
        try {
            FileCandidature deletedCandidature = candidatureFileService.deleteFileCandidature(candidatureId);

            // Create the response message
            String message = "Candidature with ID " + candidatureId + " deleted successfully.";

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

    @GetMapping("/candidature/file-cod/{idcondida}/{idstudent}")
    public ResponseEntity<FileCandidature> getCandidatureFileByIdCondidatureAndIdStudent(@PathVariable Long idcondida , @PathVariable Long idstudent) {
       FileCandidature candidatures = candidatureFileService.getFileCandidatureByIdCandidatureAndIdStudent(idcondida , idstudent);
        return ResponseEntity.ok(candidatures);
    }

    @GetMapping("/candidature/send")
    public String sendSimpleEmail(@RequestParam("to") String toEmail,
                                  @RequestParam("subject") String subject,
                                  @RequestParam("body") String body) {
        candidatureService.sendSimpleEmail(toEmail, subject, body);
        return "Email sent successfully";
    }
    @GetMapping("/candidature/user/{iduser}")
    public ResponseEntity<Map<String, Object>> getUserDataByUserId(@PathVariable("iduser") Long userId) {
        Map<String, Object> userData = candidatureService.getUserDataByUserId(userId);

        return ResponseEntity.ok().body(userData);
    }

}
