package tn.esprit.artifact.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tn.esprit.artifact.Repository.CandidatureRepository;
import tn.esprit.artifact.entity.Conditature;
import tn.esprit.artifact.entity.Event;

import java.util.*;


@Service
@Slf4j
public class CandidatureServiceIMPL implements ICondidatureService{

    @Autowired
    CandidatureRepository candidatureRep;
    public static final String DIRECTORYlettre = System.getProperty("user.dir") + "/src/main/resources/Stagefile/lettre/";
    public static final String DIRECTORYcv = System.getProperty("user.dir") + "/src/main/resources/Stagefile/cv/";
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public Conditature addConditature(Conditature c) {
        candidatureRep.save(c);
        return c;
    }

    @Override
    public Conditature showConditature(Long c) {
        return candidatureRep.findById(c).orElse(null);
    }

    @Override
    public List<Conditature> allCandidature() {
        Iterable<Conditature> candidatureIterable=candidatureRep.findAll();
        List<Conditature> CanditatureList=new ArrayList<>();
        for (Conditature condidature : candidatureIterable){
            CanditatureList.add(condidature);
        }
        return CanditatureList;
    }

    @Override
    public List<Conditature> searchCandidatureByNom(String c) {
        Iterable<Conditature> candidatureIterable = candidatureRep.findByNomContainingIgnoreCase(c);
        List<Conditature> candidatureList = new ArrayList<>();
        for (Conditature candidature : candidatureIterable) {
            candidatureList.add(candidature);
        }
        return candidatureList;
    }

    @Override
    public Conditature updateCandidature(Long CandidatureId,Conditature c) {
        Optional<Conditature> optionalCandidature = candidatureRep.findById(CandidatureId);

        if (optionalCandidature.isPresent()) {
            Conditature existingCandidature = optionalCandidature.get();
            // Update the existing stage entity with the fields from the updatedStage object
            existingCandidature.setNom(c.getNom());
            existingCandidature.setCv(c.getCv());
            existingCandidature.setLettre_motivation(c.getLettre_motivation());

            return candidatureRep.save(existingCandidature);
        } else {
            // Handle the case where the stage with the given ID is not found
            throw new IllegalArgumentException("Candidature not found with ID: " + CandidatureId);
        }



    }

    @Override
    public Conditature deleteCandidature(Long c) {

        try {
            Optional<Conditature> optionalConditature=candidatureRep.findById(c);

            Conditature conditatureToDelete = optionalConditature.get();

            candidatureRep.deleteById(c);

            return conditatureToDelete;
        }catch (Exception e) {
            throw new IllegalArgumentException("candidature not found");
        }
    }

    public List<Conditature> getCandidatureByIdStudent(Long idStudent) {
        return candidatureRep.findByIdStudent(idStudent);
    }

    @Override
    public List<Conditature> getCandidatureByIdSociete(Long idsociete) {
        return candidatureRep.findByIdSociete(idsociete);
    }

    @Override
    public void sendSimpleEmail(String toEmail,
                                String subject,
                                String body
    ) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("manefsfeihi9@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        mailSender.send(message);
        System.out.println("Mail Send...");


    }
    @Override
    public Map<String, Object> getUserDataByUserId(Long userId) {
        List<Object[]> userDataList = candidatureRep.findUserDataByUserId(userId);

        Object[] userData = userDataList.get(0);
        Map<String, Object> userDataMap = new HashMap<>();
        userDataMap.put("descripsociete", userData[0]);
        userDataMap.put("email", userData[1]);

        userDataMap.put("image", userData[2]);
        userDataMap.put("localisationsocie", userData[3]);
        userDataMap.put("mdp", userData[4]);
        userDataMap.put("niveau", userData[5]);
        userDataMap.put("nom", userData[6]);
        userDataMap.put("num", userData[7]);
        userDataMap.put("prenom", userData[8]);
        userDataMap.put("specialite", userData[9]);

        return userDataMap;
    }


}
