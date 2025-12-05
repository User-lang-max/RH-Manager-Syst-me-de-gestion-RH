package ui;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import hrmanager.model.Conge;
import hrmanager.model.Employe;
import hrmanager.model.Evaluation;
import hrmanager.service.EmployeService;
import hrmanager.util.EvaluationManager;
import hrmanager.util.ExportCSV;
import hrmanager.io.PdfExporter;

public class MenuConsole {

    private final Scanner scanner = new Scanner(System.in);
    private final EmployeService employeService;

    public MenuConsole(EmployeService employeService) {
        this.employeService = employeService;
    }

    public void demarrer() {
        int choix = -1;
        while (choix != 16) {
            afficherMenu();
            try {
                System.out.print("Votre choix : ");
                choix = Integer.parseInt(scanner.nextLine());

                switch (choix) {
                    case 1 -> ajouterEmploye();
                    case 2 -> afficherTousEmployes();
                    case 3 -> menuEvaluation();
                    case 4 -> rechercheEmployeParId();
                    case 5 -> demandeConge();
                    case 6 -> validationRefusConge();
                    case 7 -> trierEmployesParSalaire();
                    case 8 -> exportFicheEmployeCSV();
                    case 9 -> afficherStatistiques();
                    case 10 -> exportFicheEmployePDF();
                    case 11 -> modifierEmploye();
                    case 12 -> supprimerEmploye();
                    case 13 -> afficherHistoriqueCongesEmploye();
                    case 14 -> afficherTousLesConges();
                    case 16 -> System.out.println(">>> Au revoir !");
                    default -> System.out.println("Choix invalide.");
                }
            } catch (Exception e) {
                System.out.println("Erreur : " + e.getMessage());
            }

            System.out.println();
        }
    }

    private void afficherMenu() {
        System.out.println("\n");
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║            MENU RH — HRManager       ║");
        System.out.println("╚══════════════════════════════════════╝");

        System.out.println(" 1️  Ajouter un employé");
        System.out.println(" 2️  Afficher tous les employés");
        System.out.println(" 3️  Gestion des évaluations");
        System.out.println(" 4️  Recherche employé par ID");
        System.out.println(" 5️  Demande de congé");
        System.out.println(" 6️  Validation / Refus congé");
        System.out.println(" 7️  Trier employés par salaire");
        System.out.println(" 8️  Export fiche employé (CSV)");
        System.out.println(" 9️  Statistiques (Streams)");
        System.out.println(" 10 Export fiche employé (PDF)");
        System.out.println(" 1️1️ Modifier un employé");
        System.out.println(" 1️2️ Supprimer un employé");
        System.out.println(" 1️3️ Historique des congés d’un employé");
        System.out.println(" 1️4️ Liste complète des congés (RH)");

        System.out.println("----------------------------------------");
        System.out.println(" 1️5  Quitter");
        System.out.println("----------------------------------------");
    }


    //  ÉVALUATIONS
    private void menuEvaluation() {

        int choix = -1;

        while (choix != 0) {
            System.out.println("\n===== Gestion des Évaluations =====");
            System.out.println("1. Ajouter une évaluation");
            System.out.println("2. Afficher les évaluations d'un employé");
            System.out.println("0. Retour au menu principal");
            System.out.print("Votre choix : ");

            try {
                choix = Integer.parseInt(scanner.nextLine());

                switch (choix) {
                    case 1 -> ajouterEvaluation();
                    case 2 -> afficherEvaluationsEmploye();
                    case 0 -> System.out.println("Retour au menu principal...");
                    default -> System.out.println("Choix invalide.");
                }

            } catch (Exception e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        }
    }

    private void ajouterEvaluation() {
        try {
            System.out.print("ID Employé : ");
            int id = Integer.parseInt(scanner.nextLine());

            System.out.print("Note (1 à 5) : ");
            int note = Integer.parseInt(scanner.nextLine());

            System.out.print("Commentaire : ");
            String commentaire = scanner.nextLine();

            Evaluation eval = EvaluationManager.ajouterEvaluation(id, note, commentaire);

            System.out.println("Évaluation ajoutée : " + eval);

        } catch (Exception ex) {
            System.out.println("Erreur : " + ex.getMessage());
        }
    }

    private void afficherEvaluationsEmploye() {
        System.out.print("ID Employé : ");
        int id = Integer.parseInt(scanner.nextLine());

        List<Evaluation> list = EvaluationManager.getEvaluationsEmploye(id);

        if (list.isEmpty()) {
            System.out.println("Aucune évaluation trouvée pour cet employé.");
        } else {
            System.out.println("\n===== Évaluations de l’employé " + id + " =====");
            list.forEach(System.out::println);
        }
    }

    // EMPLOYÉS
    private void ajouterEmploye() {
        try {
            System.out.println("Type d'employé : ");
            System.out.println("1. Permanent");
            System.out.println("2. Contractuel");
            int type = Integer.parseInt(scanner.nextLine());

            System.out.print("Nom : ");
            String nom = scanner.nextLine();

            System.out.print("Prénom : ");
            String prenom = scanner.nextLine();

            System.out.print("Poste : ");
            String poste = scanner.nextLine();

            System.out.print("Salaire de base : ");
            double salaireBase = Double.parseDouble(scanner.nextLine());

            System.out.print("Date d'embauche (AAAA-MM-JJ) : ");
            LocalDate dateEmbauche = LocalDate.parse(scanner.nextLine());

            Employe e = employeService.creerEmploye(type, nom, prenom, poste, salaireBase, dateEmbauche);

            employeService.ajouterEmploye(e);
            System.out.println("Employé ajouté avec succès : " + e);

        } catch (Exception ex) {
            System.out.println("Erreur lors de l'ajout : " + ex.getMessage());
        }
    }

    private void afficherTousEmployes() {
        List<Employe> liste = employeService.getTousEmployes();
        if (liste.isEmpty()) {
            System.out.println("Aucun employé.");
        } else {
            liste.forEach(System.out::println);
        }
    }

    private void rechercheEmployeParId() {
        System.out.print("ID employé : ");
        int id = Integer.parseInt(scanner.nextLine());

        Optional<Employe> opt = employeService.trouverEmployeParId(id);
        if (opt.isPresent()) {
            System.out.println(opt.get());
        } else {
            System.out.println("Aucun employé trouvé avec cet ID.");
        }
    }

    // MODIFIER EMPLOYÉ
    private void modifierEmploye() {
        System.out.print("ID de l’employé à modifier : ");
        int id = Integer.parseInt(scanner.nextLine());

        Optional<Employe> opt = employeService.trouverEmployeParId(id);
        if (opt.isEmpty()) {
            System.out.println("Aucun employé trouvé avec cet ID.");
            return;
        }

        Employe e = opt.get();
        System.out.println("Employé actuel : " + e);
        System.out.println("Laisser vide pour conserver la valeur actuelle.");

        System.out.print("Nouveau nom (" + e.getNom() + ") : ");
        String nom = scanner.nextLine();
        if (!nom.isBlank()) e.setNom(nom);

        System.out.print("Nouveau prénom (" + e.getPrenom() + ") : ");
        String prenom = scanner.nextLine();
        if (!prenom.isBlank()) e.setPrenom(prenom);

        System.out.print("Nouveau poste (" + e.getPoste() + ") : ");
        String poste = scanner.nextLine();
        if (!poste.isBlank()) e.setPoste(poste);

        System.out.print("Nouveau salaire (" + e.getSalaireBase() + ") : ");
        String salaireStr = scanner.nextLine();
        if (!salaireStr.isBlank()) {
            try {
                e.setSalaireBase(Double.parseDouble(salaireStr));
            } catch (NumberFormatException ex) {
                System.out.println("Salaire invalide ! Ancienne valeur conservée.");
            }
        }

        System.out.print("Nouvelle date embauche (" + e.getDateEmbauche() + ") : ");
        String dateStr = scanner.nextLine();
        if (!dateStr.isBlank()) {
            try {
                e.setDateEmbauche(LocalDate.parse(dateStr));
            } catch (Exception ex) {
                System.out.println("Date invalide. Ancienne valeur conservée.");
            }
        }

        try {
            employeService.modifierEmploye(e);
            System.out.println("Employé modifié avec succès !");
        } catch (Exception ex) {
            System.out.println("Erreur modification : " + ex.getMessage());
        }
    }

    // SUPPRIMER EMPLOYÉ 
    private void supprimerEmploye() {
        System.out.print("ID employé à supprimer : ");
        int id = Integer.parseInt(scanner.nextLine());

        Optional<Employe> opt = employeService.trouverEmployeParId(id);
        if (opt.isEmpty()) {
            System.out.println("Aucun employé trouvé.");
            return;
        }

        System.out.println("Employé : " + opt.get());
        System.out.print("Confirmer suppression ? (o/n) : ");
        String rep = scanner.nextLine();

        if (rep.equalsIgnoreCase("o")) {
            try {
                employeService.supprimerEmploye(id);
                System.out.println("Employé supprimé !");
            } catch (Exception ex) {
                System.out.println("Erreur suppression : " + ex.getMessage());
            }
        } else {
            System.out.println("Suppression annulée.");
        }
    }

    //  CONGÉS
    private void demandeConge() {
        System.out.print("ID employé : ");
        int idEmploye = Integer.parseInt(scanner.nextLine());

        System.out.print("Date début (AAAA-MM-JJ) : ");
        LocalDate debut = LocalDate.parse(scanner.nextLine());

        System.out.print("Date fin (AAAA-MM-JJ) : ");
        LocalDate fin = LocalDate.parse(scanner.nextLine());

        System.out.print("Type de congé : ");
        String type = scanner.nextLine();

        Conge c = new Conge(idEmploye, debut, fin, type);
        employeService.demanderConge(c);
        System.out.println("Demande enregistrée : " + c);
    }

    private void validationRefusConge() {
        System.out.print("ID du congé : ");
        int idConge = Integer.parseInt(scanner.nextLine());

        Optional<Conge> opt = employeService.trouverCongeParId(idConge);
        if (opt.isEmpty()) {
            System.out.println("Aucun congé avec cet ID.");
            return;
        }

        Conge c = opt.get();
        System.out.println("Conge : " + c);

        System.out.println("1. Accepter");
        System.out.println("2. Refuser");

        int choix = Integer.parseInt(scanner.nextLine());
        if (choix == 1) c.setStatut("ACCEPTE");
        else if (choix == 2) c.setStatut("REFUSE");

        employeService.updateConge(c);
        System.out.println("Statut mis à jour !");
    }

    //  HISTORIQUE CONGÉS EMPLOYÉ 
    private void afficherHistoriqueCongesEmploye() {
        System.out.print("ID employé : ");
        int id = Integer.parseInt(scanner.nextLine());

        List<Conge> conges = employeService.getTousConges().stream()
                .filter(c -> c.getIdEmploye() == id)
                .toList();

        if (conges.isEmpty()) {
            System.out.println("Aucun congé trouvé pour cet employé.");
        } else {
            System.out.println("\n===== HISTORIQUE DES CONGÉS DE L’EMPLOYÉ " + id + " =====");
            conges.forEach(System.out::println);
        }
    }

    //  LISTE COMPLÈTE DES CONGÉS (RH) 
    private void afficherTousLesConges() {
        List<Conge> conges = employeService.getTousConges();

        if (conges.isEmpty()) {
            System.out.println("Aucune demande de congé.");
        } else {
            System.out.println("\n===== LISTE COMPLÈTE DES CONGÉS =====");
            conges.forEach(System.out::println);
        }
    }

    // STATISTIQUES
    private void trierEmployesParSalaire() {
        List<Employe> tries = employeService.trierParSalaire();
        tries.forEach(e -> System.out.println(
                e.getId() + " - " + e.getNom() + " " + e.getPrenom() +
                        " | Salaire : " + e.calculerSalaire()
        ));
    }

    private void afficherStatistiques() {
        double moy = employeService.salaireMoyen();
        double max = employeService.salaireMax().orElse(0);
        double min = employeService.salaireMin().orElse(0);
        long nbCongesAcceptes = employeService.compterCongesAcceptes();

        System.out.println("Salaire moyen : " + moy);
        System.out.println("Salaire max : " + max);
        System.out.println("Salaire min : " + min);
        System.out.println("Congés acceptés : " + nbCongesAcceptes);
    }

    private void exportFicheEmployeCSV() {
        System.out.print("ID employé : ");
        int id = Integer.parseInt(scanner.nextLine());

        Optional<Employe> opt = employeService.trouverEmployeParId(id);
        if (opt.isEmpty()) {
            System.out.println("Employé introuvable.");
            return;
        }

        try {
            ExportCSV.exportFicheEmploye(opt.get());
            System.out.println("CSV exporté !");
        } catch (Exception e) {
            System.out.println("Erreur CSV : " + e.getMessage());
        }
    }

    private void exportFicheEmployePDF() {
        System.out.print("ID employé : ");
        int id = Integer.parseInt(scanner.nextLine());

        Optional<Employe> optEmp = employeService.trouverEmployeParId(id);
        if (optEmp.isEmpty()) {
            System.out.println("Employé introuvable.");
            return;
        }

        Employe e = optEmp.get();

        List<Conge> conges = employeService.getTousConges().stream()
                .filter(c -> c.getIdEmploye() == id)
                .toList();

        double salaireMoyen = employeService.salaireMoyen();
        double salaireMin = employeService.salaireMin().orElse(0);
        double salaireMax = employeService.salaireMax().orElse(0);
        long nbCongesAcceptes = employeService.compterCongesAcceptes();

        String path = "exports/fiche_employe_" + e.getId() + ".pdf";

        try {
            PdfExporter.exportFicheEmployePDF(
                    e, conges, salaireMoyen, salaireMin, salaireMax, nbCongesAcceptes, path
            );
            System.out.println("PDF généré : " + path);

        } catch (Exception ex) {
            System.out.println("Erreur PDF : " + ex.getMessage());
        }
    }
}
