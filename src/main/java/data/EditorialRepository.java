package data;

import models.Editorial;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedList;

public class EditorialRepository {
    private static final Logger LOG = LoggerFactory.getLogger(EditorialRepository.class);

    public LinkedList<Editorial>  getAllEditoriales()
    {
        String sql = "SELECT * FROM EDITORIALES";
        LinkedList<Editorial> editoriales = new LinkedList<>();
        try (Connection conn = DBConnection.getConnection();
             Statement stmt =  conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            while (rs.next()) {
                Editorial editorial = new Editorial();
                editorial.setId(rs.getInt("ID"));
                editorial.setNombre(rs.getString("NOMBRE"));
                editoriales.add(editorial);
            }
        }catch (SQLException e) {
            LOG.error("error al obtener la lista de editoriales", e);
        }
        return editoriales;
    }
    public Editorial getEditorialById(int id)
    {
        String sql = "SELECT * FROM EDITORIALES WHERE ID = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()){
                if (rs.next()) {
                    Editorial editorial = new Editorial();
                    editorial.setId(rs.getInt("ID"));
                    editorial.setNombre(rs.getString("NOMBRE"));
                    return editorial;
                }
            }
            return null;

        }catch (SQLException e) {
            LOG.error("error al obtener el editorial con id: '{}' ", id, e);
            return null;
        }
    }
    public void insertEditorial(Editorial editorial)
    {
        String sql  ="INSERT INTO editoriales (nombre) values (?)";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, editorial.getNombre());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    editorial.setId(rs.getInt(1));
                }
            }
        }catch (SQLException e) {
            LOG.error("error al insertar el editorial", e);
        }
    }
    public boolean deleteEditorial(int id)
    {
        String sql = "DELETE FROM editoriales WHERE ID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        }catch (SQLException e) {
            LOG.error("error al eliminar el editorial con id {}", id,e);
            return false;
        }
    }
    public boolean updateEditorial(Editorial editorial)
    {
        String sql = "UPDATE editoriales SET nombre = ? WHERE ID = ?";
        try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, editorial.getNombre());
            stmt.setInt(2, editorial.getId());
            return stmt.executeUpdate() > 0;

        }catch (SQLException e) {
            LOG.error("error al actualizar el editorial con id {}", editorial.getId(), e);
            return false;
        }

    }
}
