package tn.esprit.artifact.Service;

import org.springframework.web.multipart.MultipartFile;
import tn.esprit.artifact.entity.Conditature;
import tn.esprit.artifact.entity.Doc;
import tn.esprit.artifact.entity.FileCandidature;

import java.io.IOException;
import java.util.List;

public interface CandidatureFileService {
    public FileCandidature addFileConditature(FileCandidature c);
    public FileCandidature showFileConditature(Long c);
    public List<FileCandidature> allFileCandidature();
    public FileCandidature updateFileCandidature(Long FileId ,FileCandidature c );
    public FileCandidature deleteFileCandidature (Long c);
    public List<FileCandidature> getFileCandidatureByIdCandidature(Long idCandidature);
     //FileCandidature updatecv(Long idFile, Long idStudent, MultipartFile cvFile) throws IOException;
     FileCandidature updatecv(Long iddoc, MultipartFile newCv) throws IOException;
    FileCandidature updatelettre(Long iddoc, MultipartFile newlettre) throws IOException;
    public FileCandidature getFileCandidatureByIdCandidatureAndIdStudent(Long idCandidature , Long idStudent);


}
