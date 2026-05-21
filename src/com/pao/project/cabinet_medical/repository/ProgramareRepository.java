package com.pao.project.cabinet_medical.repository;

import com.pao.project.cabinet_medical.model.*;
import com.pao.project.cabinet_medical.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProgramareRepository implements Repository<Programare, String>
{
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    @Override
    public void save(Programare p)
    {
        String sql = "INSERT INTO cabinet_medical.programare(cod_p, client_id, medic_id, data_ora, pret_baza, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, p.getCod().getVal());
            ps.setInt(2, p.getClient().getId());
            ps.setInt(3, p.getMedic().getId());
            ps.setTimestamp(4, Timestamp.valueOf(p.getDataOra()));
            ps.setDouble(5, p.getPretBaza());
            ps.setString(6, p.getStatus());
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Eroare save programare: " + e.getMessage());
        }
    }

    @Override
    public Optional<Programare> findById(String cod)
    {
        String sql = "SELECT * FROM cabinet_medical.programare WHERE cod_p = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, cod);
            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next()) { return Optional.of(mapRow(rs));}
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Eroare findById programare: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Programare> findAll()
    {
        List<Programare> lista = new ArrayList<>();
        String sql = "SELECT * FROM cabinet_medical.programare";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) { lista.add(mapRow(rs));}
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Eroare findAll programari: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void update(Programare p)
    {
        String sql = "UPDATE cabinet_medical.programare SET client_id=?, medic_id=?, data_ora=?, pret_baza=?, status=? WHERE cod_p=?";
        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, p.getClient().getId());
            ps.setInt(2, p.getMedic().getId());
            ps.setTimestamp(3, Timestamp.valueOf(p.getDataOra()));
            ps.setDouble(4, p.getPretBaza());
            ps.setString(5, p.getStatus());
            ps.setString(6, p.getCod().getVal());
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Eroare update programare: " + e.getMessage());
        }
    }

    @Override
    public void delete(String cod)
    {
        String sql = "DELETE FROM cabinet_medical.programare WHERE cod_p=?";
        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, cod);
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Eroare delete programare: " + e.getMessage());
        }
    }

    //programare activa + detalii client/medic
    public List<String> findProgramariActive()
    {
        List<String> rez = new ArrayList<>();
        String sql = "SELECT p.cod_p, c.nume AS client, m.nume AS medic, p.data_ora, p.status " +
                     "FROM cabinet_medical.programare p " +
                     "JOIN cabinet_medical.client c ON p.client_id = c.id " +
                     "JOIN cabinet_medical.medic  m ON p.medic_id  = m.id " +
                     "WHERE p.status = 'PROGRAMATA'";
        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
                rez.add(String.format("Cod: %s | Client: %s | Medic: %s | Data: %s | Status: %s",
                        rs.getString("cod"), rs.getString("client"), rs.getString("medic"), rs.getTimestamp("data_ora").toLocalDateTime(), rs.getString("status")));
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Eroare join programari: "+ e.getMessage());
        }
        return  rez;
    }

    //nr programari/medic
    public List<String> findNrProgramariMedic()
    {
        List<String> rez = new ArrayList<>();
        String sql = "SELECT m.nume, COUNT(p.cod_p) AS nr_programari " +
                     "FROM cabinet_medical.medic m " +
                     "LEFT JOIN cabinet_medical.programare p ON m.id = p.medic_id " +
                     "GROUP BY m.id, m.nume " +
                     "ORDER BY nr_programari DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                rez.add(String.format("Medic: %s | Programari: %d", rs.getString("nume"), rs.getInt("nr_programari")));
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Eroare JOIN programari per medic: " + e.getMessage());
        }
        return rez;
    }

    //jdbc
    public void salveazaProgramareCuConsultatie(Programare programare, Consultatie consultatie)
    {
        String sqlProgramare = "INSERT INTO cabinet_medical.programare(cod_p, client_id, medic_id, data_ora, pret_baza, status) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlConsultatie = "INSERT INTO cabinet_medical.consultatie(cod_p, diagnostic, recomandari, cost_final, data_consultatie) VALUES (?, ?, ?, ?, ?)";
        try
        {
            connection.setAutoCommit(false);
            try (PreparedStatement ps1 = connection.prepareStatement(sqlProgramare))
            {
                ps1.setString(1, programare.getCod().getVal());
                ps1.setInt(2, programare.getClient().getId());
                ps1.setInt(3, programare.getMedic().getId());
                ps1.setTimestamp(4, Timestamp.valueOf(programare.getDataOra()));
                ps1.setDouble(5, programare.getPretBaza());
                ps1.setString(6, programare.getStatus());
                ps1.executeUpdate();
            }
            try (PreparedStatement ps2 = connection.prepareStatement(sqlConsultatie))
            {
                ps2.setString(1, consultatie.getProgramare().getCod().getVal());
                ps2.setString(2, consultatie.getDiagnostic());
                ps2.setString(3, consultatie.getRecomandari());
                ps2.setDouble(4, consultatie.getCostFinal());
                ps2.setTimestamp(5, Timestamp.valueOf(consultatie.getDataEfectuare()));
                ps2.executeUpdate();
            }
            connection.commit();
            System.out.println("Tranzactie reusita: programare + consultatie salvate.");
        }
        catch (SQLException e)
        {
            try
            {
                connection.rollback();
                System.out.println("Tranzactie esuata, rollback efectuat.");
            }
            catch (SQLException ex)
            {
                throw new RuntimeException("Eroare rollback: " + ex.getMessage());
            }
            throw new RuntimeException("Eroare tranzactie: " + e.getMessage());
        }
        finally
        {
            try { connection.setAutoCommit(true);}
            catch (SQLException e)
            {
                throw new RuntimeException("Eroare reset autocommit: " + e.getMessage());
            }
        }
    }

    private Programare mapRow(ResultSet rs) throws SQLException
    {
        Client clientMin = new Client(rs.getInt("id"), "", "", "");
        Medic medicMin = new Medic(rs.getInt("id"), "", "", "", null, new ArrayList<>());
        LocalDateTime dataOra = rs.getTimestamp("data_ora").toLocalDateTime();
        CodProgramare cod = new CodProgramare(rs.getString("cod"));
        Programare p = new Programare(clientMin, medicMin, dataOra, rs.getDouble("pret_baza"), cod);
        p.setStatus(rs.getString("status"));
        return p;
    }
}