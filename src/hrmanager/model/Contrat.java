package hrmanager.model;
import hrmanager.exceptions.ContratInvalideException;


import java.time.LocalDate;

public class Contrat {

    private int idContrat;
    private int idEmploye; 
    private String typeContrat;
    private LocalDate dateDebut;
    private LocalDate dateFin;  
    private double salaire;

    public Contrat(int idContrat, int idEmploye, String typeContrat,
                   LocalDate dateDebut, LocalDate dateFin, double salaire) {
        this.idContrat = idContrat;
        this.idEmploye = idEmploye;
        this.typeContrat = typeContrat;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.salaire = salaire;
    }

    public Contrat(int idEmploye, String typeContrat,
                   LocalDate dateDebut, LocalDate dateFin, double salaire) {
        this(0, idEmploye, typeContrat, dateDebut, dateFin, salaire);
    }

    public int getIdContrat() {
        return idContrat;
    }

    public void setIdContrat(int idContrat) {
        this.idContrat = idContrat;
    }

    public int getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(int idEmploye) {
        this.idEmploye = idEmploye;
    }

    public String getTypeContrat() {
        return typeContrat;
    }

    public void setTypeContrat(String typeContrat) {
        this.typeContrat = typeContrat;
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

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }
    public void valider() throws ContratInvalideException {
        if (dateDebut == null || dateFin == null) {
            throw new ContratInvalideException("Les dates du contrat sont obligatoires.");
        }
        if (dateDebut.isAfter(dateFin)) {
            throw new ContratInvalideException("La date de début du contrat ne peut pas être après la date de fin.");
        }
    }


    @Override
    public String toString() {
        return "Contrat{" +
                "idContrat=" + idContrat +
                ", idEmploye=" + idEmploye +
                ", typeContrat='" + typeContrat + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", salaire=" + salaire +
                '}';
    }
}
