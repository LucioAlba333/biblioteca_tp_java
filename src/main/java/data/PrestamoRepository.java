package data;

import models.Prestamo;
import models.Persona;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Date;
import java.util.LinkedList;

public class PrestamoRepository {
    private static final Logger LOG = LoggerFactory.getLogger(PrestamoRepository.class);

    private Prestamo mapPrestamo(ResultSet rs) throws SQLException {
        Persona persona = new Persona();
        persona.setId(rs.getInt("id_persona"));
        Date fecha_inicio =  rs.getDate("fecha_inicio");
        Date fecha_limite = rs.getDate("fecha_limite");
        int id = rs.getInt("id");
        Prestamo prestamo = new Prestamo(fecha_inicio,fecha_limite,id,persona);
        Date fecha_devolucion = rs.getDate("fecha_devolucion");
        if(fecha_devolucion!=null){
            prestamo.setFechaDevolucion(fecha_devolucion);
        }
        return prestamo;
    }

    public LinkedList<Prestamo> getAllPrestamos() {
        String sql = "SELECT * FROM prestamos";
        LinkedList<Prestamo> prestamos = new LinkedList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                prestamos.add(mapPrestamo(rs));
            }
        } catch (SQLException e) {
            LOG.error("Error al obtener la lista de préstamos", e);
        }
        return prestamos;
    }

    public Prestamo getPrestamoById(int id) {
        String sql = "SELECT * FROM prestamos WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapPrestamo(rs);
            }
            return null;
        } catch (SQLException e) {
            LOG.error("Error al obtener el préstamo con id: '{}'", id, e);
            return null;
        }
    }

    public void insertPrestamo(Prestamo p) {
        String sql = "INSERT INTO prestamos (fecha_inicio, fecha_devolucion, fecha_limite, id_persona) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, new java.sql.Date(p.getFechaInicio().getTime()));
            stmt.setDate(2, p.getFechaDevolucion() != null ? new java.sql.Date(p.getFechaDevolucion().getTime()) : null);
            stmt.setDate(3, new java.sql.Date(p.getFechaLimite().getTime()));
            stmt.setInt(4, p.getPersona().getId());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    p.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOG.error("Error al insertar el préstamo", e);
        }
    }

    public boolean deletePrestamo(int id) {
        String sql = "DELETE FROM prestamos WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error("Error al eliminar el préstamo con id: '{}'", id, e);
            return false;
        }
    }

    public boolean updatePrestamo(Prestamo p) {
        String sql = "UPDATE prestamos SET fecha_inicio = ?, fecha_devolucion = ?, fecha_limite = ?, id_persona = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(p.getFechaInicio().getTime()));
            stmt.setDate(2, p.getFechaDevolucion() != null ? new java.sql.Date(p.getFechaDevolucion().getTime()) : null);
            stmt.setDate(3, new java.sql.Date(p.getFechaLimite().getTime()));
            stmt.setInt(4, p.getPersona().getId());
            stmt.setInt(5, p.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error("Error al actualizar el préstamo", e);
            return false;
        }
    }
}
