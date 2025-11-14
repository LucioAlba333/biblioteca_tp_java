package data;

import models.Ejemplar;

import models.Libro;
import models.Prestamo;
import models.enums.EstadoEjemplar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedList;

public class EjemplarRepository {
    private final Logger LOG = LoggerFactory.getLogger(EjemplarRepository.class);

    private static Ejemplar mapEjemplar(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        boolean disponible = rs.getBoolean("disponible");
        EstadoEjemplar estado = EstadoEjemplar.valueOf(rs.getString("estado"));

        Libro libro = new Libro();
        libro.setId(rs.getInt("id_libro"));
        Prestamo prestamo = null;
        int idPrestamo = rs.getInt("id_prestamo");
        if(!rs.wasNull()) {
            prestamo = new Prestamo();
            prestamo.setId(idPrestamo);
        }
        return new Ejemplar(disponible,estado,id,libro,prestamo);
    }




    public LinkedList<Ejemplar> getAllEjemplares() {
        String sql = "SELECT * FROM ejemplares";
        LinkedList<Ejemplar> ejemplares = new LinkedList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ejemplares.add(mapEjemplar(rs));
            }
        } catch (SQLException e) {
            LOG.error("Error al obtener la lista de ejemplares", e);
        }
        return ejemplares;
    }
    public LinkedList<Ejemplar> getAllEjemplaresByLibro(int idLibro) {
        String sql = "SELECT * FROM ejemplares where id_libro = ?";
        LinkedList<models.Ejemplar> ejemplares = new LinkedList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idLibro);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ejemplares.add(mapEjemplar(rs));
            }
        } catch (SQLException e) {
            LOG.error("Error al obtener la lista de ejemplares", e);
        }
        return ejemplares;
    }
    public LinkedList<Ejemplar> getAllEjemplaresPrestados() {
        String sql = "SELECT * FROM ejemplares WHERE id_prestamo IS NOT NULL";
        LinkedList<Ejemplar> ejemplares = new LinkedList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ejemplares.add(mapEjemplar(rs));
            }
        } catch (SQLException e) {
            LOG.error("Error al obtener la lista de ejemplares", e);
        }
        return ejemplares;

    }

    public Ejemplar getEjemplarById(int id) {
        String sql = "SELECT *FROM ejemplares WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapEjemplar(rs);
            }
            return null;
        } catch (SQLException e) {
            LOG.error("Error al obtener el ejemplar con id: '{}'", id, e);
            return null;
        }
    }

    public void insertEjemplar(Ejemplar ejemplar) {
        String sql = "INSERT INTO ejemplares (estado, disponible, id_libro) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, ejemplar.getEstado().name());
            stmt.setBoolean(2, ejemplar.isDisponible());
            stmt.setInt(3, ejemplar.getLibro().getId());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    ejemplar.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOG.error("Error al insertar el ejemplar", e);
        }
    }

    public boolean deleteEjemplar(int id) {
        String sql = "DELETE FROM ejemplares WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error("Error al eliminar el ejemplar con id: '{}'", id, e);
            return false;
        }
    }

    public boolean updateEjemplar(Ejemplar ejemplar) {
        String sql =
                "UPDATE ejemplares SET estado = ?, disponible = ?, id_libro = ?, id_prestamo = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ejemplar.getEstado().name());
            stmt.setBoolean(2, ejemplar.isDisponible());
            stmt.setInt(3, ejemplar.getLibro().getId());
            if (ejemplar.getPrestamo() != null) {
                stmt.setInt(4, ejemplar.getPrestamo().getId());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }
            stmt.setInt(5, ejemplar.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error("Error al actualizar el ejemplar", e);
            return false;
        }
    }

}
