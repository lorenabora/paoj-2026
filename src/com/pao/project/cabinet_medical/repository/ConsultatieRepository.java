package com.pao.project.cabinet_medical.repository;

import com.pao.project.cabinet_medical.model.Consultatie;
import com.pao.project.cabinet_medical.model.Programare;
import com.pao.project.cabinet_medical.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsultatieRepository implements Repository<Consultatie, Integer>
{
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    @Override
    public void save(Consultatie c)
    {
        String sql = "INSERT INTO cabinet_medical.consultatie(cod_p, diagnostic, recomandari, cost_final, data_consultatie) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, c.getProgramare().getCod().getVal());
            ps.setString(2, c.getDiagnostic());
            ps.setString(3, c.getRecomandari());
            ps.setDouble(4, c.getCostFinal());
            ps.setTimestamp(5, Timestamp.valueOf(c.getDataEfectuare()));
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Eroare save consultatie: " + e.getMessage());
        }
    }

    @Override
    public Optional<Consultatie> findById(Integer id)
    {
        String sql = "SELECT * FROM cabinet_medical.consultatie WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) { return Optional.of(mapRow(rs));}
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Eroare findById consultatie: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Consultatie> findAll()
    {
        List<Consultatie> lista = new ArrayList<>();
        String sql = "SELECT * FROM cabinet_medical.consultatie";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) { lista.add(mapRow(rs));}
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Eroare findAll consultatii: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void update(Consultatie c)
    {
        String sql = "UPDATE cabinet_medical.consultatie SET diagnostic=?, recomandari=?, cost_final=?, data_consultatie=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, c.getDiagnostic());
            ps.setString(2, c.getRecomandari());
            ps.setDouble(3, c.getCostFinal());
            ps.setTimestamp(4, Timestamp.valueOf(c.getDataEfectuare()));
            ps.setInt(5, c.getId());
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Eroare update consultatie: " + e.getMessage());
        }
    }

    @Override
    public void delete(Integer id)
    {
        String sql = "DELETE FROM cabinet_medical.consultatie WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Eroare delete consultatie: " + e.getMessage());
        }
    }

    //consultatiile unui client cu diagnostic si cost
    public List<String> findConsultatiiPerClient(int clientId)
    {
        List<String> rezultate = new ArrayList<>();
        String sql =
                "SELECT c.nume AS client, co.diagnostic, co.cost_final, " +
                        "       co.data_consultatie, p.cod_p AS cod_programare " +
                        "FROM cabinet_medical.consultatie co " +
                        "JOIN cabinet_medical.programare p  ON co.cod_p = p.cod_p " +
                        "JOIN cabinet_medical.client c      ON p.client_id = c.id " +
                        "WHERE c.id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, clientId);
            try (ResultSet rs = ps.executeQuery())
            {
                while (rs.next())
                {
                    rezultate.add(String.format("Client: %s | Programare: %s | Diagnostic: %s | Cost: %.2f | Data: %s", rs.getString("client"), rs.getString("cod_programare"), rs.getString("diagnostic"), rs.getDouble("cost_final"), rs.getTimestamp("data_consultatie").toLocalDateTime()));
                }
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Eroare JOIN consultatii client: " + e.getMessage());
        }
        return rezultate;
    }

    private Consultatie mapRow(ResultSet rs) throws SQLException
    {
        // Programare minimala cu doar codul
        Programare programareMin = new Programare(null, null, null, 0, new com.pao.project.cabinet_medical.model.CodProgramare(rs.getString("cod_programare")));
        return new Consultatie(rs.getInt("id"), programareMin, rs.getString("diagnostic"), rs.getString("recomandari"), rs.getDouble("cost_final"), rs.getTimestamp("data_consultatie").toLocalDateTime());
    }
}
