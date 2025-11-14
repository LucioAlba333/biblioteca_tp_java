package data;

import models.Persona;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedList;

public class PersonaRepository {
    private static final Logger LOG = LoggerFactory.getLogger(PersonaRepository.class);

    protected static Persona mapPersona(ResultSet rs) throws SQLException {
        Persona p = new Persona();
        p.setId(rs.getInt("ID"));
        p.setNombre(rs.getString("NOMBRE"));
        p.setApellido(rs.getString("APELLIDO"));
        p.setEmail(rs.getString("EMAIL"));
        p.setDireccion(rs.getString("DIRECCION"));
        p.setTelefono(rs.getString("TELEFONO"));
        return p;

    }
    public LinkedList<Persona> getAllPersonas() {
        String sql = "SELECT * FROM PERSONAS";
        LinkedList<Persona> personas = new LinkedList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                personas.add(mapPersona(rs));
            }

        } catch (SQLException e) {
            LOG.error("Error al obtener la lista de Personas", e);
        }
        return personas;
    }

    public Persona getPersonaById(int id) {
        String sql = "SELECT * FROM PERSONAS WHERE ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapPersona(rs);
                }
            }
            return null;

        } catch (SQLException e) {
            LOG.error("Error al obtener la Persona con id: '{}'", id, e);
            return null;
        }
    }

    public void insertPersona(Persona p) {
        String sql = "INSERT INTO PERSONAS (NOMBRE, APELLIDO, EMAIL, DIRECCION, TELEFONO) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getApellido());
            stmt.setString(3, p.getEmail());
            stmt.setString(4, p.getDireccion());
            stmt.setString(5, p.getTelefono());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setId(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            LOG.error("Error al crear la Persona", e);
        }
    }

    public boolean deletePersona(int id) {
        String sql = "DELETE FROM PERSONAS WHERE ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            LOG.error("Error al eliminar la Persona", e);
            return false;
        }
    }

    public boolean updatePersona(Persona p) {
        String sql = "UPDATE PERSONAS SET NOMBRE = ?, APELLIDO = ?, EMAIL = ?, DIRECCION = ?, TELEFONO = ? WHERE ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getApellido());
            stmt.setString(3, p.getEmail());
            stmt.setString(4, p.getDireccion());
            stmt.setString(5, p.getTelefono());
            stmt.setInt(6, p.getId());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            LOG.error("Error al actualizar la Persona", e);
            return false;
        }
    }
}

