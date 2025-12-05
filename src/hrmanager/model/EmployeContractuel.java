package hrmanager.model;

import java.time.LocalDate;

import hrmanager.exceptions.SalaireInvalideException;
import hrmanager.exceptions.ContratInvalideException;

public class EmployeContractuel extends Employe {

    private LocalDate dateFinContrat;
    
    public LocalDate getDateFinContrat() {
        return dateFinContrat;
    }

    public void setDateFinContrat(LocalDate dateFinContrat) {
        this.dateFinContrat = dateFinContrat;
    }


    public EmployeContractuel(int id, String nom, String prenom, String poste,
                              double salaireBase, LocalDate dateEmbauche, LocalDate dateFinContrat)
            throws SalaireInvalideException, ContratInvalideException {

        super(id, nom, prenom, poste, salaireBase, dateEmbauche);

        validerSalaire(salaireBase);
        validerContrat(dateEmbauche, dateFinContrat);

        this.dateFinContrat = dateFinContrat;
    }

    public EmployeContractuel(String nom, String prenom, String poste,
                              double salaireBase, LocalDate dateEmbauche, LocalDate dateFinContrat)
            throws SalaireInvalideException, ContratInvalideException {

        super(nom, prenom, poste, salaireBase, dateEmbauche);

        validerSalaire(salaireBase);
        validerContrat(dateEmbauche, dateFinContrat);

        this.dateFinContrat = dateFinContrat;
    }

   
    private void validerSalaire(double salaireBase) throws SalaireInvalideException {
        if (salaireBase <= 0)
            throw new SalaireInvalideException("Le salaire doit être strictement positif.");
    }

   
    private void validerContrat(LocalDate debut, LocalDate fin) throws ContratInvalideException {
        if (fin.isBefore(debut))
            throw new ContratInvalideException("La date de fin de contrat doit être après la date de début.");
    }

    @Override
    public double calculerSalaire() {
        return getSalaireBase();
    }

    @Override
    public String toString() {
        return "EmployeContractuel{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", poste='" + getPoste() + '\'' +
                ", salaireBase=" + getSalaireBase() +
                ", dateEmbauche=" + getDateEmbauche() +
                ", dateFinContrat=" + dateFinContrat +
                '}';
    }
}
