package data;

import models.Genero;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedList;

public class GeneroRepository {
    private final Logger LOG = LoggerFactory.getLogger(GeneroRepository.class);

    public LinkedList<Genero> getAllGeneros() {
        String sql = "SELECT * FROM generos";
        LinkedList<Genero> generos = new LinkedList<>();
        try(Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);) {
            while (rs.next()) {
                Genero genero = new Genero();
                genero.setId(rs.getInt("id"));
                genero.setDescripcion(rs.getString("descripcion"));
                generos.add(genero);
            }

        }catch (SQLException e){
            LOG.error("Error al obtener la lista de generos", e);
        }
        return generos;
    }

    public Genero getGeneroById(int id) {
        String sql = "SELECT * FROM generos WHERE id = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Genero genero = new Genero();
                    genero.setId(rs.getInt("id"));
                    genero.setDescripcion(rs.getString("descripcion"));
                    return genero;
                }
            }
            return  null;

        }catch (SQLException e){
            LOG.error("Error al obtener genero con id: '{}' ", id,e);
            return null;
        }
    }
    public void insertGenero(Genero genero) {
        String sql = "INSERT INTO generos (descripcion) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, genero.getDescripcion());
            stmt.executeUpdate();
            try(ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    genero.setId(rs.getInt(1));
                }
            }

        }catch (SQLException e){
            LOG.error("Error al insertar genero",e);
        }
    }
    public boolean deleteGenero(int id) {
        String sql = "DELETE FROM generos WHERE id = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() >0 ;

        }catch (SQLException e){
            LOG.error("Error al eliminar genero con id: '{}'",id,e);
            return false;
        }
    }
    public boolean updateGenero(Genero genero) {
        String sql = "UPDATE generos SET descripcion = ? WHERE id = ?";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setString(1, genero.getDescripcion());
            stmt.setInt(2, genero.getId());
            return stmt.executeUpdate() > 0;
        }catch (SQLException e){
            LOG.error("Error al actualizar genero con id: '{}'",genero.getId(),e);
            return false;
        }
    }
}
