package com.pao.project.cabinet_medical.repository;

import com.pao.project.cabinet_medical.model.Medic;
import com.pao.project.cabinet_medical.model.MedicSpecialist;
import com.pao.project.cabinet_medical.model.TipMedic;
import com.pao.project.cabinet_medical.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MedicRepository implements Repository<Medic, Integer>
{
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    @Override
    public void save(Medic m)
    {
        String sql = "INSERT INTO cabinet_medical.medic(id, nume, email, telefon, tip, specializare) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, m.getId());
            ps.setString(2, m.getNume());
            ps.setString(3, m.getEmail());
            ps.setString(4, m.getTelefon());
            ps.setString(5, m.getTip().name());
            if (m instanceof MedicSpecialist) { ps.setString(6, ((MedicSpecialist) m).getSpecializare());}
            else { ps.setNull(6, Types.VARCHAR);}
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Eroare save medic: " + e.getMessage());
        }
    }

    @Override
    public Optional<Medic> findById(Integer id)
    {
        String sql = "SELECT * FROM cabinet_medical.medic WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next()) { return Optional.of(mapRow(rs));}
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Eroare findById medic: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Medic> findAll()
    {
        List<Medic> lista = new ArrayList<>();
        String sql = "SELECT * FROM cabinet_medical.medic";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) { lista.add(mapRow(rs));}
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Eroare findAll medici: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void update(Medic m)
    {
        String sql = "UPDATE cabinet_medical.medic SET nume=?, email=?, telefon=?, tip=?, specializare=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1, m.getNume());
            ps.setString(2, m.getEmail());
            ps.setString(3, m.getTelefon());
            ps.setString(4, m.getTip().name());
            if (m instanceof MedicSpecialist) { ps.setString(5, ((MedicSpecialist) m).getSpecializare());}
            else { ps.setNull(5, Types.VARCHAR);}
            ps.setInt(6, m.getId());
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Eroare update medic: " + e.getMessage());
        }
    }

    @Override
    public void delete(Integer id)
    {
        String sql = "DELETE FROM cabinet_medical.medic WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new RuntimeException("Eroare delete medic: " + e.getMessage());
        }
    }

    private Medic mapRow(ResultSet rs) throws SQLException
    {
        TipMedic tip = TipMedic.valueOf(rs.getString("tip"));
        //orar nu e in DB
        List<String> orar = new ArrayList<>();
        String specializare = rs.getString("specializare");
        if (specializare != null)
        {
            return new MedicSpecialist( rs.getInt("id"), rs.getString("nume"), rs.getString("email"), rs.getString("telefon"), tip, orar, specializare);
        }
        return new Medic(rs.getInt("id"), rs.getString("nume"), rs.getString("email"), rs.getString("telefon"), tip, orar);
    }
}
