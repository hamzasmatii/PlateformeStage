package tn.esprit.artifact.service;
import tn.esprit.artifact.entity.Doc;

import java.util.List;

public interface IDocService {
    public Doc addDoc(Doc d);
    public Doc showDoc(Long d);
    public List<Doc> allDocs ();
    public Doc updateDoc(Long DocId,Doc d);
    public Doc deleteDoc(Long d);
    public Doc getdocbyidetud(Long idetud);
    public List<Doc>  getdocbyidsociet(Long idetud);
}
