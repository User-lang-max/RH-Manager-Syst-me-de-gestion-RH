package hrmanager.model;

import java.time.LocalDate;

public class Conge {

    private int idConge;
    private int idEmploye;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String type; // Annuel, Maladie, Sans solde
    private String statut; // EN_ATTENTE, ACCEPTE, REFUSE

    public Conge(int idConge, int idEmploye, LocalDate dateDebut,
                 LocalDate dateFin, String type, String statut) {
        this.idConge = idConge;
        this.idEmploye = idEmploye;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.type = type;
        this.statut = statut;
    }

    public Conge(int idEmploye, LocalDate dateDebut,
                 LocalDate dateFin, String type) {
        this(0, idEmploye, dateDebut, dateFin, type, "EN_ATTENTE");
    }

    

	public int getIdConge() {
        return idConge;
    }

    public void setIdConge(int idConge) {
        this.idConge = idConge;
    }

    public int getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(int idEmploye) {
        this.idEmploye = idEmploye;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Conge{" +
                "idConge=" + idConge +
                ", idEmploye=" + idEmploye +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", type='" + type + '\'' +
                ", statut='" + statut + '\'' +
                '}';
    }
}
