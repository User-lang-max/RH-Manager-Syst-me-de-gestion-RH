package dao;

import db.DatabaseConnection;
import hrmanager.model.Conge;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CongeDAO {

    
    public void ajouter(Conge c) {
        String sql = """
            INSERT INTO conge(id_employe, date_debut, date_fin, type, statut)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, c.getIdEmploye());
            stmt.setDate(2, Date.valueOf(c.getDateDebut()));
            stmt.setDate(3, Date.valueOf(c.getDateFin()));
            stmt.setString(4, c.getType());
            stmt.setString(5, c.getStatut());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  
    public void changerStatut(int idConge, String statut) {
        String sql = "UPDATE conge SET statut=? WHERE id_conge=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, statut);
            stmt.setInt(2, idConge);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   
    public List<Conge> getHistoriqueEmploye(int idEmploye) {
        List<Conge> liste = new ArrayList<>();

        String sql = "SELECT * FROM conge WHERE id_employe=? ORDER BY date_debut DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idEmploye);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                liste.add(mapConge(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    
    public List<Conge> getToutesLesDemandes() {
        List<Conge> liste = new ArrayList<>();
        String sql = "SELECT * FROM conge ORDER BY id_conge DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                liste.add(mapConge(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    
    private Conge mapConge(ResultSet rs) throws SQLException {
        return new Conge(
                rs.getInt("id_conge"),
                rs.getInt("id_employe"),
                rs.getDate("date_debut").toLocalDate(),
                rs.getDate("date_fin").toLocalDate(),
                rs.getString("type"),
                rs.getString("statut")
        );
    }

}
