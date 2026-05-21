package com.pao.project.cabinet_medical.repository;

import com.pao.project.cabinet_medical.model.Abonament;
import com.pao.project.cabinet_medical.model.Client;
import com.pao.project.cabinet_medical.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepository implements Repository<Client, Integer>{
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    @Override
    public void save(Client c)
    {
        String sql = "INSERT INTO cabinet_medical.client(id, nume, email, telefon, tip_abonament, reducere_procent, pret_lunar) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, c.getId());
            ps.setString(2, c.getNume());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getTelefon());
            if (c.getAbonament() != null) {
                ps.setString(5, c.getAbonament().getTip());
                ps.setDouble(6, c.getAbonament().getReducereProcent());
                ps.setDouble(7, c.getAbonament().getPretLunar());
            } else {
                ps.setNull(5, Types.VARCHAR);
                ps.setNull(6, Types.DOUBLE);
                ps.setNull(7, Types.DOUBLE);
            }
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Eroare save client: "+ e.getMessage());
        }
    }

    @Override
    public Optional<Client> findById(Integer id)
    {
        String sql = "SELECT * FROM cabinet_medical.client WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare findById client: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Client> findAll() {
        List<Client> lista = new ArrayList<>();
        String sql = "SELECT * FROM cabinet_medical.client";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare findAll clienti: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void update(Client c) {
        String sql = "UPDATE cabinet_medical.client SET nume=?, email=?, telefon=?, tip_abonament=?, reducere_procent=?, pret_lunar=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, c.getNume());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getTelefon());
            if (c.getAbonament() != null)
            {
                ps.setString(4, c.getAbonament().getTip());
                ps.setDouble(5, c.getAbonament().getReducereProcent());
                ps.setDouble(6, c.getAbonament().getPretLunar());
            }
            else
            {
                ps.setNull(4, Types.VARCHAR);
                ps.setNull(5, Types.DOUBLE);
                ps.setNull(6, Types.DOUBLE);
            }
            ps.setInt(7, c.getId());
            ps.executeUpdate();
        } catch (SQLException e)
        {
            throw new RuntimeException("Eroare update client: " + e.getMessage());
        }
    }

    @Override
    public void delete(Integer id)
    {
        String sql = "DELETE FROM cabinet_medical.client WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Eroare delete client: " + e.getMessage());
        }
    }

    private Client mapRow(ResultSet rs) throws SQLException {
        Client c = new Client(
                rs.getInt("id"),
                rs.getString("nume"),
                rs.getString("email"),
                rs.getString("telefon")
        );
        String tipAbon = rs.getString("tip_abonament");
        if (tipAbon != null) {
            c.setAbonament(new Abonament(
                    tipAbon,
                    rs.getDouble("reducere_procent"),
                    rs.getDouble("pret_lunar")
            ));
        }
        return c;
    }
}
