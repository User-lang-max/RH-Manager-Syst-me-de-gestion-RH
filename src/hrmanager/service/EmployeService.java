package hrmanager.service;

import dao.EmployeDAO;
import dao.CongeDAO;

import hrmanager.model.Employe;
import hrmanager.model.EmployePermanent;
import hrmanager.model.EmployeContractuel;
import hrmanager.model.Conge;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class EmployeService {

    private final EmployeDAO employeDAO;
    private final CongeDAO congeDAO;

    public EmployeService(EmployeDAO employeDAO, CongeDAO congeDAO) {
        this.employeDAO = employeDAO;
        this.congeDAO = congeDAO;
    }

   
    //  CRÉATION EMPLOYÉ
   
    public Employe creerEmploye(int type, String nom, String prenom,
                                String poste, double salaireBase,
                                LocalDate dateEmbauche) throws Exception {

        if (type == 1) {  // Permanent
            System.out.print("Prime d'ancienneté : ");
            double prime = new java.util.Scanner(System.in).nextDouble();

            return new EmployePermanent(
                    0, nom, prenom, poste, salaireBase, dateEmbauche, prime
            );
        }
        else if (type == 2) { // Contractuel
            System.out.print("Date fin contrat (AAAA-MM-JJ) : ");
            String dfc = new java.util.Scanner(System.in).nextLine();

            return new EmployeContractuel(
                    0, nom, prenom, poste, salaireBase, dateEmbauche,
                    LocalDate.parse(dfc)
            );
        }
        else {
            throw new Exception("Type employé invalide !");
        }
    }

  
    // AJOUT EMPLOYÉ
  
    public void ajouterEmploye(Employe e) throws Exception {
        employeDAO.ajouter(e);
    }

    public List<Employe> getTousEmployes() {
        return employeDAO.getAll();
    }

    public Optional<Employe> trouverEmployeParId(int id) {
        try {
            return Optional.ofNullable(employeDAO.getById(id));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }


    //  NOUVEAU : MODIFIER EMPLOYÉ
   
    public void modifierEmploye(Employe e) {
        employeDAO.modifier(e);
    }

  
    // NOUVEAU : SUPPRIMER EMPLOYÉ

    public void supprimerEmploye(int id) {
        employeDAO.supprimer(id);
    }


    // GESTION DES CONGÉS
 
    public Conge demanderConge(Conge c) {
        congeDAO.ajouter(c);
        return c;
    }

    public Optional<Conge> trouverCongeParId(int idConge) {
        return congeDAO.getToutesLesDemandes()
                .stream()
                .filter(c -> c.getIdConge() == idConge)
                .findFirst();
    }

    public void updateConge(Conge c) {
        congeDAO.changerStatut(c.getIdConge(), c.getStatut());
    }

    public List<Conge> getTousConges() {
        return congeDAO.getToutesLesDemandes();
    }

 
    // STATISTIQUES

    public List<Employe> trierParSalaire() {
        return employeDAO.getAll().stream()
                .sorted(Comparator.comparingDouble(Employe::calculerSalaire))
                .collect(Collectors.toList());
    }

    public double salaireMoyen() {
        return employeDAO.getAll().stream()
                .mapToDouble(Employe::calculerSalaire)
                .average()
                .orElse(0.0);
    }

    public OptionalDouble salaireMax() {
        return employeDAO.getAll().stream()
                .mapToDouble(Employe::calculerSalaire)
                .max();
    }

    public OptionalDouble salaireMin() {
        return employeDAO.getAll().stream()
                .mapToDouble(Employe::calculerSalaire)
                .min();
    }

    public long compterCongesAcceptes() {
        return congeDAO.getToutesLesDemandes().stream()
                .filter(c -> "ACCEPTE".equalsIgnoreCase(c.getStatut()))
                .count();
    }
}
