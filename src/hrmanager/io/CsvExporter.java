package hrmanager.io;

import hrmanager.model.Employe;
import hrmanager.model.Conge;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public class CsvExporter {

    private static final String SEPARATOR = ";";

    public static void exportFicheEmploye(Employe e, Path cheminFichier) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(cheminFichier)) {
            writer.write("id;nom;prenom;poste;salaire;dateEmbauche");
            writer.newLine();
            writer.write(
                    e.getId() + SEPARATOR +
                    e.getNom() + SEPARATOR +
                    e.getPrenom() + SEPARATOR +
                    e.getPoste() + SEPARATOR +
                    e.calculerSalaire() + SEPARATOR +
                    e.getDateEmbauche()
            );
            writer.newLine();
        }
    }

    public static void exportListeSalaires(Collection<Employe> employes, Path cheminFichier) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(cheminFichier)) {
            writer.write("id;nom;prenom;salaire");
            writer.newLine();
            for (Employe e : employes) {
                writer.write(
                        e.getId() + SEPARATOR +
                        e.getNom() + SEPARATOR +
                        e.getPrenom() + SEPARATOR +
                        e.calculerSalaire()
                );
                writer.newLine();
            }
        }
    }

    public static void exportListeConges(List<Conge> conges, Path cheminFichier) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(cheminFichier)) {
            writer.write("idConge;idEmploye;dateDebut;dateFin;type;statut");
            writer.newLine();
            for (Conge c : conges) {
                writer.write(
                        c.getIdConge() + SEPARATOR +
                        c.getIdEmploye() + SEPARATOR +
                        c.getDateDebut() + SEPARATOR +
                        c.getDateFin() + SEPARATOR +
                        c.getType() + SEPARATOR +
                        c.getStatut()
                );
                writer.newLine();
            }
        }
    }
}
