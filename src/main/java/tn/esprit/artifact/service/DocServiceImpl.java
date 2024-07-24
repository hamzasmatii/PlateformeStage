package tn.esprit.artifact.service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.artifact.entity.Doc;
import tn.esprit.artifact.entity.Stage;
import tn.esprit.artifact.repository.DocRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DocServiceImpl implements IDocService {

    @Autowired
    DocRepository docRep;
    @Override
    public Doc addDoc(Doc d) {
        docRep.save(d);
        return d;
    }

    @Override
    public Doc showDoc(Long d) {
        return docRep.findById(d).orElse(null);
    }

    @Override
    public List<Doc> allDocs() {
        Iterable<Doc> docsIterable = docRep.findAll();
        List<Doc> docsList = new ArrayList<>();
        for (Doc doc : docsIterable) {
            docsList.add(doc);
        }
        return docsList;
    }

    @Override
    public Doc updateDoc(Long DocId, Doc updateDoc) {
        Optional<Doc> optionalDoc = docRep.findById(DocId);

        if (optionalDoc.isPresent()) {
            Doc existingDoc = optionalDoc.get();
            existingDoc.setId_societ(updateDoc.getId_societ());
            existingDoc.setId_etud(updateDoc.getId_etud());
            // Update the existing stage entity with the fields from the updatedStage object
            existingDoc.setJournal(updateDoc.getJournal());
            existingDoc.setRapport(updateDoc.getRapport());
            existingDoc.setAttestation(updateDoc.getAttestation());

            // You can update more fields as needed

            // Save the updated stage entity
            return docRep.save(existingDoc);
        } else {
            // Handle the case where the stage with the given ID is not found
            throw new IllegalArgumentException("Stage not found with ID: " + DocId);
        }
    }

    @Override
    public Doc deleteDoc(Long d) {
        try{
            Optional<Doc> optionalDoc = docRep.findById(d);



            // If the stage exists, retrieve it
            Doc docToDelete = optionalDoc.get();

            // Delete the stage by its ID
            docRep.deleteById(d);

            // Return the deleted stage
            return docToDelete;
        } catch(Exception e) {
            // If the stage does not exist, throw an exception or handle it in any other appropriate way
            throw new IllegalArgumentException("doc not found");
        }
    }

    @Override
    public Doc getdocbyidetud(Long id) {
        return docRep.findByIdetudiant(id);
    }

    @Override
    public List<Doc> getdocbyidsociet(Long idsociet) {
        Iterable<Doc> docsIterable = docRep.findByIdsociete(idsociet);
        List<Doc> docsList = new ArrayList<>();
        for (Doc doc : docsIterable) {
            docsList.add(doc);
        }
        return docsList;
    }

}
