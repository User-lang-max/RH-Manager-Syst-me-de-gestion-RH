package hrmanager.util;

import hrmanager.model.Conge;
import hrmanager.model.Employe;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ExportCSV {

    private static final String SEPARATOR = ";";

   
    public static void exportFicheEmploye(Employe e) throws IOException {

        
        Files.createDirectories(Path.of("exports"));

        String fileName = "exports/fiche_employe_" + e.getId() + ".csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            bw.write("ID" + SEPARATOR + e.getId() + "\n");
            bw.write("Nom" + SEPARATOR + e.getNom() + "\n");
            bw.write("Prenom" + SEPARATOR + e.getPrenom() + "\n");
            bw.write("Poste" + SEPARATOR + e.getPoste() + "\n");
            bw.write("Salaire" + SEPARATOR + e.calculerSalaire() + "\n");
            bw.write("Date Embauche" + SEPARATOR + e.getDateEmbauche() + "\n");

            System.out.println("Fiche employé exportée : " + fileName);

        } catch (IOException ex) {
            throw new IOException("Erreur lors de l'export CSV : " + ex.getMessage());
        }
    }

    
    public static void exportListeSalaires(List<Employe> employes) throws IOException {

        Files.createDirectories(Path.of("exports"));

        String fileName = "exports/liste_salaires.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            bw.write("ID;Nom;Prenom;Poste;Salaire\n");

            for (Employe e : employes) {
                bw.write(
                        e.getId() + SEPARATOR +
                        e.getNom() + SEPARATOR +
                        e.getPrenom() + SEPARATOR +
                        e.getPoste() + SEPARATOR +
                        e.calculerSalaire() + "\n"
                );
            }

            System.out.println("Liste des salaires exportée : " + fileName);

        } catch (IOException ex) {
            throw new IOException("Erreur export salaires : " + ex.getMessage());
        }
    }

    public static void exportListeConges(List<Conge> conges) throws IOException {

        Files.createDirectories(Path.of("exports"));

        String fileName = "exports/liste_conges.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {

            bw.write("ID Conge;ID Employe;Date Debut;Date Fin;Type;Statut\n");

            for (Conge c : conges) {
                bw.write(
                        c.getIdConge() + SEPARATOR +
                        c.getIdEmploye() + SEPARATOR +
                        c.getDateDebut() + SEPARATOR +
                        c.getDateFin() + SEPARATOR +
                        c.getType() + SEPARATOR +
                        c.getStatut() + "\n"
                );
            }

            System.out.println("Liste des congés exportée : " + fileName);

        } catch (IOException ex) {
            throw new IOException("Erreur export congés : " + ex.getMessage());
        }
    }
}
