package dao;

import db.DatabaseConnection;
import hrmanager.model.Employe;
import hrmanager.model.EmployePermanent;
import hrmanager.model.EmployeContractuel;
import hrmanager.exceptions.CongeRefuseException;
import hrmanager.exceptions.ContratInvalideException;
import hrmanager.exceptions.EvaluationInvalideException;
import hrmanager.exceptions.SalaireInvalideException;

import hrmanager.exceptions.EmployeNonTrouveException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeDAO {

   
    public void ajouter(Employe e) throws Exception {

        String sql = """
            INSERT INTO employe
            (nom, prenom, poste, salaire_base, date_embauche, type_employe, prime_anciennete, date_fin_contrat)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, e.getNom());
            stmt.setString(2, e.getPrenom());
            stmt.setString(3, e.getPoste());
            stmt.setDouble(4, e.getSalaireBase());
            stmt.setDate(5, Date.valueOf(e.getDateEmbauche()));

            if (e instanceof EmployePermanent p) {
                stmt.setString(6, "PERMANENT");
                stmt.setDouble(7, p.getPrimeAnciennete());
                stmt.setNull(8, Types.DATE);
            }
            else if (e instanceof EmployeContractuel c) {
                stmt.setString(6, "CONTRACTUEL");
                stmt.setNull(7, Types.DOUBLE);
                stmt.setDate(8, Date.valueOf(c.getDateFinContrat()));
            }

            stmt.executeUpdate();
        }
    }

   
    public void supprimer(int id) {
        String sql = "DELETE FROM employe WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   
    public void modifier(Employe e) {
        String sql = """
            UPDATE employe
            SET nom=?, prenom=?, poste=?, salaire_base=?, date_embauche=?,
                prime_anciennete=?, date_fin_contrat=?
            WHERE id=?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, e.getNom());
            stmt.setString(2, e.getPrenom());
            stmt.setString(3, e.getPoste());
            stmt.setDouble(4, e.getSalaireBase());
            stmt.setDate(5, Date.valueOf(e.getDateEmbauche()));

            if (e instanceof EmployePermanent p) {
                stmt.setDouble(6, p.getPrimeAnciennete());
                stmt.setNull(7, Types.DATE);
            }
            else if (e instanceof EmployeContractuel c) {
                stmt.setNull(6, Types.DOUBLE);
                stmt.setDate(7, Date.valueOf(c.getDateFinContrat()));
            }

            stmt.setInt(8, e.getId());
            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

   
    public Employe getById(int id) throws Exception {

        String sql = "SELECT * FROM employe WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                throw new Exception("Employé non trouvé");
            }

            return mapEmploye(rs);
        }
    }

 
   
    public List<Employe> getAll() {
        List<Employe> liste = new ArrayList<>();

        String sql = "SELECT * FROM employe ORDER BY id DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                liste.add(mapEmploye(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return liste;
    }

    // ---------------------------------------------------------
    //  MAPPER SQL → OBJET EMPLOYE
    // ---------------------------------------------------------
    private Employe mapEmploye(ResultSet rs) throws SQLException {

        int id = rs.getInt("id");
        String nom = rs.getString("nom");
        String prenom = rs.getString("prenom");
        String poste = rs.getString("poste");
        double salaireBase = rs.getDouble("salaire_base");
        LocalDate dateEmbauche = rs.getDate("date_embauche").toLocalDate();

        String type = rs.getString("type_employe");

        if ("PERMANENT".equalsIgnoreCase(type)) {
            double prime = rs.getDouble("prime_anciennete");

            try {
				return new EmployePermanent(
				        id, nom, prenom, poste,
				        salaireBase, dateEmbauche, prime
				);
			} catch (SalaireInvalideException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        else {
            LocalDate dateFin = null;
            Date df = rs.getDate("date_fin_contrat");
            if (df != null) dateFin = df.toLocalDate();

            try {
				return new EmployeContractuel(
				        id, nom, prenom, poste,
				        salaireBase, dateEmbauche, dateFin
				);
			} catch (SalaireInvalideException | ContratInvalideException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		return null;
    }
}
