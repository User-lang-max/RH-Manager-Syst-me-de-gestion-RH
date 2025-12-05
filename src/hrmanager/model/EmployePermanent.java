package hrmanager.model;

import java.time.LocalDate;

import hrmanager.exceptions.CongeRefuseException;
import hrmanager.exceptions.SalaireInvalideException;

public class EmployePermanent extends Employe {

    private double primeAnciennete;

    public EmployePermanent(int id, String nom, String prenom, String poste,
                            double salaireBase, LocalDate dateEmbauche, double primeAnciennete)
            throws SalaireInvalideException {

        super(id, nom, prenom, poste, salaireBase, dateEmbauche);

        validerSalaire(salaireBase);
        this.primeAnciennete = primeAnciennete;
    }

    public EmployePermanent(String nom, String prenom, String poste,
                            double salaireBase, LocalDate dateEmbauche, double primeAnciennete)
            throws SalaireInvalideException {

        super(nom, prenom, poste, salaireBase, dateEmbauche);

        validerSalaire(salaireBase);
        this.primeAnciennete = primeAnciennete;
    }

   
    private void validerSalaire(double salaireBase) throws SalaireInvalideException {
        if (salaireBase <= 0)
            throw new SalaireInvalideException("Le salaire doit être strictement positif.");

        if (salaireBase < 3000)
            throw new SalaireInvalideException("Le salaire doit être supérieur ou égal au SMIG.");
    }

   
    public void demanderConge(int jours) throws CongeRefuseException {
        if (jours > 20)
            throw new CongeRefuseException("Le congé demandé ne peut pas être accordé.");
    }

    @Override
    public double calculerSalaire() {
        return getSalaireBase() + primeAnciennete;
    }
    public double getPrimeAnciennete() {
        return primeAnciennete;
    }


    @Override
    public String toString() {
        return "EmployePermanent{" +
                "id=" + getId() +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", poste='" + getPoste() + '\'' +
                ", salaireBase=" + getSalaireBase() +
                ", dateEmbauche=" + getDateEmbauche() +
                ", primeAnciennete=" + primeAnciennete +
                '}';
    }

	
}
