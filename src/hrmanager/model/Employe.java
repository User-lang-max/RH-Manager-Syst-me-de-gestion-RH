package hrmanager.model;
import hrmanager.exceptions.SalaireInvalideException;
import java.time.LocalDate;

public abstract class Employe {

    protected int id;
    private String nom;
    private String prenom;
    private String poste;
    protected double salaireBase;
    private LocalDate dateEmbauche;

    
    public Employe(int id, String nom, String prenom, String poste, double salaireBase, LocalDate dateEmbauche) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.poste = poste;
        this.salaireBase = salaireBase;
        this.dateEmbauche = dateEmbauche;
    }

 
    public Employe(String nom, String prenom, String poste, double salaireBase, LocalDate dateEmbauche) {
        this(0, nom, prenom, poste, salaireBase, dateEmbauche);
    }
    


  
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public double getSalaireBase() {
        return salaireBase;
    }

    public void setSalaireBase(double salaireBase) {
        this.salaireBase = salaireBase;
    }

    public LocalDate getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(LocalDate dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }
    

    public abstract double calculerSalaire();
    
    
    public void validerSalaire() throws SalaireInvalideException {
        double SMIG = 3000.0; 

        if (salaireBase <= 0) {
            throw new SalaireInvalideException("Le salaire doit être strictement positif.");
        }
        if (salaireBase < SMIG) {
            throw new SalaireInvalideException("Le salaire est inférieur au SMIG.");
        }
        if (poste != null && poste.toLowerCase().contains("ingénieur") && salaireBase < 6000) {
            throw new SalaireInvalideException("Le salaire est incohérent avec le poste d'ingénieur (trop bas).");
        }}
    @Override
    public String toString() {
        return "Employe{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", poste='" + poste + '\'' +
                ", salaireBase=" + salaireBase +
                ", dateEmbauche=" + dateEmbauche +
                '}';
    }
}
