package tn.esprit.artifact.controller;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import org.apache.commons.io.FileUtils;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.artifact.entity.Stage;
import tn.esprit.artifact.service.IDocService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.artifact.entity.Doc;
import tn.esprit.artifact.service.IDocService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.nio.file.*;
import java.util.*;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import org.apache.commons.io.FilenameUtils;

@RestController
@RequestMapping("/api")
public class DocController {

    @Autowired
    IDocService docService;

    @Autowired
    ServletContext context;
    public static final String DIRECTORYjournal = System.getProperty("user.dir") + "/src/main/resources/Stagefile/journal/";
    public static final String DIRECTORYattestation = System.getProperty("user.dir") + "/src/main/resources/Stagefile/attestation/";
    public static final String DIRECTORYrapport = System.getProperty("user.dir") + "/src/main/resources/Stagefile/rapport/";


    /*@PostMapping("/doc")
    public ResponseEntity<Doc> addEvent(@RequestBody Doc doc) {
        Doc addedDoc = docService.addDoc(doc);
        return new ResponseEntity<>(addedDoc, HttpStatus.CREATED);
    }*/

    @GetMapping("/doc/{docId}")
    public ResponseEntity<Doc> showDoc(@PathVariable Long docId) {
        Doc doc = docService.showDoc(docId);
        if (doc != null) {
            return ResponseEntity.ok(doc);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Define a method to upload files
    @PostMapping("/doc/uploadjournal")
    public ResponseEntity<List<String>> uploadFiles(@RequestParam("files")List<MultipartFile> multipartFiles,@RequestBody Doc doc) throws IOException {
        List<String> filenames = new ArrayList<>();
        for(MultipartFile file : multipartFiles) {
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            Path fileStorage = get(DIRECTORYjournal, filename).toAbsolutePath().normalize();
            copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);
            filenames.add(filename);
        }
        return ResponseEntity.ok().body(filenames);
    }

    @PostMapping(value = "/doc/uploadjournal", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFiles(@RequestParam("files") MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file uploaded");
        }

        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        Path fileStorage = Paths.get(DIRECTORYjournal).resolve(filename).toAbsolutePath().normalize();
        Files.copy(file.getInputStream(), fileStorage, StandardCopyOption.REPLACE_EXISTING);

        return ResponseEntity.ok().body(filename);
    }



    /*@PostMapping(value = "/doc", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Doc> addEvent(@RequestPart(value = "file", required = false) MultipartFile filejournal, @ModelAttribute Doc doc) throws IOException {
        if (filejournal != null && !filejournal.isEmpty()) {
            String journalFilename = StringUtils.cleanPath(filejournal.getOriginalFilename());
            Path journalFileStorage = Paths.get(DIRECTORYjournal).resolve(journalFilename).toAbsolutePath().normalize();
            filejournal.transferTo(journalFileStorage.toFile());
            doc.setJournal(journalFilename);
        }

        Doc addedDoc = docService.addDoc(doc);
        return ResponseEntity.ok().body(addedDoc);
    }*/
    /*@PostMapping(value = "/doc", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Response> addEvent(@RequestParam(value = "file", required = false) MultipartFile file,
                                             @RequestParam("doc") String doc)
                                         throws JsonParseException, JsonMappingException,Exception {
        Doc docc = new ObjectMapper().readValue(doc,Doc.class);
        boolean isExit = new File(context.getRealPath("/journal/")).exists();
        if(!isExit)
        {
            new File(context.getRealPath("/journal/")).mkdirs();

        }
        String filename = file.getOriginalFilename();
        String newFileName = FilenameUtils.getBaseName(filename)+"."+FilenameUtils.getExtension(filename);
        File serverFile = new File(context.getRealPath("/journal/"+File.separator+newFileName));
        try {
            FileUtils.writeByteArrayToFile(serverFile,file.getBytes());

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        docc.setJournal(newFileName);
        Doc doccc = docService.addDoc(docc);
        if (doccc!=null)
        {
            return new ResponseEntity<Response>(HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<Response>(HttpStatus.BAD_REQUEST);
        }
    }*/
    @GetMapping("/doc")
    public ResponseEntity<List<Doc>> getAllDocs() {
        List<Doc> docs = docService.allDocs();
        return ResponseEntity.ok(docs);
    }

    @PutMapping("/doc/{docId}")
    public ResponseEntity<Doc> updateDoc(@PathVariable("docId") Long docId, @RequestBody Doc updatedDoc) {
        try {
            Doc updated = docService.updateDoc(docId, updatedDoc);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/doc/{docId}")
    public ResponseEntity<Void> deleteDoc(@PathVariable Long docId) {
        try {
            Doc deleteDoc = docService.deleteDoc(docId);

            // Create the response message
            String message = "Doc with ID " + docId + " deleted successfully.";

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






    // Define a method to download files
    @GetMapping("/doc/downloadjournal/{filename}")
    public ResponseEntity<Resource> downloadFiles(@PathVariable("filename") String filename) throws IOException {
        Path filePath = get(DIRECTORYjournal).toAbsolutePath().normalize().resolve(filename);
        if(!Files.exists(filePath)) {
            throw new FileNotFoundException(filename + " was not found on the server");
        }
        Resource resource = (Resource) new UrlResource(filePath.toUri());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("File-Name", filename);
        httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + ((UrlResource) resource).getFilename());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                .headers(httpHeaders).body(resource);
    }

    @GetMapping("/doc/byidetud/{idetud}")
    public ResponseEntity<Doc> findStagesByDatedebut(@PathVariable("idetud") Long idetud) {
        Doc docs = docService.getdocbyidetud(idetud);
        return ResponseEntity.ok(docs);
    }

    @GetMapping("/doc/byidsociet/{idsocie}")
    public ResponseEntity<List<Doc>> finddocbyidsocie(@PathVariable("idsocie") Long idsociet) {
        List<Doc> docs = docService.getdocbyidsociet(idsociet);
        return ResponseEntity.ok(docs);
    }


}
