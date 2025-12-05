package hrmanager.io;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import hrmanager.model.Conge;
import hrmanager.model.Employe;

import java.io.FileOutputStream;
import java.util.List;

public class PdfExporter {

    public static void exportFicheEmployePDF(Employe e,
                                             List<Conge> conges,
                                             double salaireMoyen,
                                             double salaireMin,
                                             double salaireMax,
                                             long nbCongesAcceptes,
                                             String cheminFichier)
            throws Exception {

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(cheminFichier));
        document.open();

        Font titreFont = new Font(Font.HELVETICA, 22, Font.BOLD);
        Paragraph titre = new Paragraph("FICHE EMPLOYÉ", titreFont);
        titre.setAlignment(Element.ALIGN_CENTER);
        document.add(titre);
        document.add(new Paragraph("\n"));

        Font section = new Font(Font.HELVETICA, 14, Font.BOLD);
        document.add(new Paragraph("Informations personnelles :", section));
        document.add(new Paragraph("Nom : " + e.getNom()));
        document.add(new Paragraph("Prénom : " + e.getPrenom()));
        document.add(new Paragraph("Poste : " + e.getPoste()));
        document.add(new Paragraph("Salaire : " + e.calculerSalaire() + " DH"));
        document.add(new Paragraph("Date d'embauche : " + e.getDateEmbauche()));
        document.add(new Paragraph("\n"));

        document.add(new Paragraph("Statistiques globales :", section));
        document.add(new Paragraph("Salaire moyen : " + salaireMoyen));
        document.add(new Paragraph("Salaire max : " + salaireMax));
        document.add(new Paragraph("Salaire min : " + salaireMin));
        document.add(new Paragraph("Nombre de congés acceptés : " + nbCongesAcceptes));
        document.add(new Paragraph("\n"));

        document.add(new Paragraph("Historique des congés :", section));
        document.add(new Paragraph("\n"));

        PdfPTable table = new PdfPTable(4);
        table.addCell("Début");
        table.addCell("Fin");
        table.addCell("Type");
        table.addCell("Statut");

        for (Conge c : conges) {
            table.addCell(c.getDateDebut().toString());
            table.addCell(c.getDateFin().toString());
            table.addCell(c.getType());
            table.addCell(c.getStatut());
        }

        document.add(table);

        document.close();
    }
}
