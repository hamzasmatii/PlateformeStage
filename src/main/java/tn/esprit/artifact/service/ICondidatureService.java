package tn.esprit.artifact.Service;

import tn.esprit.artifact.entity.Conditature;

import java.util.List;
import java.util.Map;

public interface ICondidatureService {

    public Conditature addConditature(Conditature c);
    public Conditature showConditature(Long c);
    public List<Conditature> allCandidature();
    public Conditature updateCandidature(Long CondidatureId ,Conditature c );
    public List<Conditature> searchCandidatureByNom (String s);

    public Conditature deleteCandidature (Long c);
    public List<Conditature> getCandidatureByIdStudent(Long idStudent);
    public List<Conditature> getCandidatureByIdSociete(Long idsociete);
    public void sendSimpleEmail(String toEmail,
                                String subject,
                                String body
    );
    Map<String, Object> getUserDataByUserId(Long userId);;

}
