package hrmanager.model;

import java.time.LocalDate;
import hrmanager.exceptions.EvaluationInvalideException;
public class Evaluation {

    private int idEvaluation;
    private int idEmploye;
    private LocalDate dateEvaluation;
    private int note; // 1 à 5
    private String commentaire;

    public Evaluation(int idEvaluation, int idEmploye, LocalDate dateEvaluation,
                      int note, String commentaire) {
        this.idEvaluation = idEvaluation;
        this.idEmploye = idEmploye;
        this.dateEvaluation = dateEvaluation;
        this.note = note;
        this.commentaire = commentaire;
    }

    public Evaluation(int idEmploye, LocalDate dateEvaluation,
                      int note, String commentaire) {
        this(0, idEmploye, dateEvaluation, note, commentaire);
    }

    public int getIdEvaluation() {
        return idEvaluation;
    }

    public void setIdEvaluation(int idEvaluation) {
        this.idEvaluation = idEvaluation;
    }

    public int getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(int idEmploye) {
        this.idEmploye = idEmploye;
    }

    public LocalDate getDateEvaluation() {
        return dateEvaluation;
    }

    public void setDateEvaluation(LocalDate dateEvaluation) {
        this.dateEvaluation = dateEvaluation;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
    public void valider() throws EvaluationInvalideException {
        if (note < 1 || note > 5) {
            throw new EvaluationInvalideException("La note doit être comprise entre 1 et 5.");
        }
    }
    @Override
    public String toString() {
        return "Evaluation{" +
                "idEvaluation=" + idEvaluation +
                ", idEmploye=" + idEmploye +
                ", dateEvaluation=" + dateEvaluation +
                ", note=" + note +
                ", commentaire='" + commentaire + '\'' +
                '}';
    }
}
