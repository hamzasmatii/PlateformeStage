package tn.esprit.artifact.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.artifact.Repository.CandidatureFileRepository;
import tn.esprit.artifact.entity.Conditature;
import tn.esprit.artifact.entity.Doc;
import tn.esprit.artifact.entity.FileCandidature;
import tn.esprit.artifact.entity.Event;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static tn.esprit.artifact.Service.CandidatureServiceIMPL.DIRECTORYcv;

@Service
@Slf4j
public class CandidatureFileServiceIMPL implements CandidatureFileService {
    @Autowired
    CandidatureFileRepository fileRepository;
    public static final String DIRECTORYlettre = System.getProperty("user.dir") + "/src/main/resources/Stagefile/lettre/";
    public static final String DIRECTORYcv = System.getProperty("user.dir") + "/src/main/resources/Stagefile/cv/";

    @Override
    public FileCandidature addFileConditature(FileCandidature c) {
        fileRepository.save(c);
        return c;
    }

    @Override
    public FileCandidature showFileConditature(Long c) {
        return fileRepository.findById(c).orElse(null);
    }

    @Override
    public List<FileCandidature> allFileCandidature() {
        Iterable<FileCandidature> fileCandidatureIterable=fileRepository.findAll();
        List<FileCandidature> FileCanditatureList=new ArrayList<>();
        for (FileCandidature file : fileCandidatureIterable){
            FileCanditatureList.add(file);
        }
        return FileCanditatureList;    }

    @Override
    public FileCandidature updateFileCandidature(Long FileId, FileCandidature c) {
        Optional<FileCandidature> optionalFileCandidature = fileRepository.findById(FileId);

        if (optionalFileCandidature.isPresent()) {
            FileCandidature existingFileCandidature = optionalFileCandidature.get();
            // Update the existing stage entity with the fields from the updatedStage object

            //existingFileCandidature.setCv(c.getCv());
            existingFileCandidature.setAccepter(c.getAccepter());
            existingFileCandidature.setNote(c.getNote());
            //existingFileCandidature.setLettre_motivation(c.getLettre_motivation());

            return fileRepository.save(existingFileCandidature);
        } else {
            // Handle the case where the stage with the given ID is not found
            throw new IllegalArgumentException("File not found with ID: " + FileId);
        }    }

    @Override
    public FileCandidature deleteFileCandidature(Long c) {
        try {
            Optional<FileCandidature> optionalFileConditature=fileRepository.findById(c);

            FileCandidature FileconditatureToDelete = optionalFileConditature.get();

            fileRepository.deleteById(c);

            return FileconditatureToDelete;
        }catch (Exception e) {
            throw new IllegalArgumentException("File not found");
        }    }

    @Override
    public List<FileCandidature> getFileCandidatureByIdCandidature(Long idCandidature) {
        return fileRepository.findByIdCandidatureIdStudent(idCandidature);
    }

    @Override
    public FileCandidature updatecv(Long iddoc, MultipartFile newCv) throws IOException {
        Optional<FileCandidature> optionalDoc = fileRepository.findById(iddoc);
        FileCandidature existingDoc = optionalDoc.get();


        String journalFilename = iddoc +"_" + StringUtils.cleanPath(newCv.getOriginalFilename());
        Path journalFileStorage = Paths.get(DIRECTORYcv).resolve(journalFilename).toAbsolutePath().normalize();
        Files.copy(newCv.getInputStream(), journalFileStorage, StandardCopyOption.REPLACE_EXISTING);
        existingDoc.setCv(journalFilename);
        return fileRepository.save(existingDoc);
    }

    @Override
    public FileCandidature updatelettre(Long iddoc, MultipartFile newlettre) throws IOException {
        Optional<FileCandidature> optionalDoc = fileRepository.findById(iddoc);
        FileCandidature existingDoc = optionalDoc.get();


        String journalFilename = iddoc +"_" + StringUtils.cleanPath(newlettre.getOriginalFilename());
        Path journalFileStorage = Paths.get(DIRECTORYlettre).resolve(journalFilename).toAbsolutePath().normalize();
        Files.copy(newlettre.getInputStream(), journalFileStorage, StandardCopyOption.REPLACE_EXISTING);
        existingDoc.setLettre_motivation(journalFilename);
        return fileRepository.save(existingDoc);
    }

    @Override
    public FileCandidature getFileCandidatureByIdCandidatureAndIdStudent(Long idCandidature, Long idStudent) {
        return fileRepository.findByIdCandidature(idCandidature ,idStudent);
    }



    /*@Override
    public FileCandidature updatecv(Long idCandidature, Long idStudent , MultipartFile cvFile) throws IOException {

        FileCandidature optionalFile = fileRepository.findByIdCandidatureEtIdStudent(idCandidature , idStudent);
        FileCandidature existingDoc = optionalFile;


        String cvFilename = idCandidature +"_" + StringUtils.cleanPath(cvFile.getOriginalFilename());
        Path cvFileStorage = Paths.get(DIRECTORYcv).resolve(cvFilename).toAbsolutePath().normalize();
        Files.copy(cvFile.getInputStream(), cvFileStorage, StandardCopyOption.REPLACE_EXISTING);
        existingDoc.setCv(cvFilename);
            return fileRepository.save(existingDoc);

    }*/
}
