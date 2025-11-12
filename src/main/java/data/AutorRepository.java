package data;
import models.Autor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.LinkedList;

public class AutorRepository {
    private static final Logger LOG = LoggerFactory.getLogger(AutorRepository.class);

    public LinkedList<Autor> getAllAutores() {
        String sql = "SELECT * FROM AUTORES";
        LinkedList<Autor> autores = new LinkedList<>();

        try (Connection conn = DBConnection.getConnection();
             Statement stmt =  conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Autor a = new Autor();
                a.setId(rs.getInt("ID"));
                a.setNombre(rs.getString("NOMBRE"));
                autores.add(a);
            }


        } catch (SQLException e) {
            LOG.error("error al obtener la lista de Autores", e);
        }
        return autores;
    }

    public Autor getAutorById(int id) {
        String sql = "SELECT * FROM AUTORES WHERE ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql) ) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Autor a = new Autor();
                    a.setId(rs.getInt("id"));
                    a.setNombre(rs.getString("nombre"));
                    return a;
                }
            }
            return null;

        } catch (SQLException e) {
            LOG.error("error al obtener el Autor con id: '{}' ", id, e);
            return null;
        }
    }

    public void insertAutor(Autor a) {
        String sql = "INSERT INTO AUTORES (NOMBRE) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt =
                     conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, a.getNombre());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    a.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            LOG.error("error al crear el Autor", e);
        }
    }

    public boolean deleteAutor(int id) {
        String sql = "DELETE FROM AUTORES WHERE ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            LOG.error("error al eliminar el Autor", e);
            return false;
        }
    }

    public boolean updateAutor(Autor a) {
        String sql = "UPDATE AUTORES SET NOMBRE = ? WHERE ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt =  conn.prepareStatement(sql)) {

            stmt.setString(1, a.getNombre());
            stmt.setInt(2, a.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.error("error al actualizar el Autor", e);
            return false;
        }
    }
}
