package data;

import models.Autor;
import models.Editorial;
import models.Genero;
import models.Libro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.LinkedList;

public class LibroRepository {
    private final Logger LOG = LoggerFactory.getLogger(LibroRepository.class);


    protected static Libro mapLibro(ResultSet rs) throws SQLException {

        Autor autor = new Autor();
        autor.setId(rs.getInt("id_autor"));
        Editorial editorial = new Editorial();
        editorial.setId(rs.getInt("id_editorial"));
        Genero genero = new Genero();
        int id =  rs.getInt("id");
        genero.setId(rs.getInt("id_genero"));
        String titulo = rs.getString("titulo");
        return new Libro(autor,editorial,genero,id, titulo);
    }
    public LinkedList<Libro> getAllLibros() {
        String sql = "SELECT * FROM LIBROS";
        LinkedList<Libro> libros = new LinkedList<>();
        try(Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                libros.add(mapLibro(rs));
            }

        }catch (SQLException e){
            LOG.error("Error al obtener la lista de libros",e);
        }
        return  libros;
    }
    public Libro getLibroById(int id) {
        String sql = "SELECT * FROM LIBROS WHERE id = ? ";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return mapLibro(rs);
            }
            return null;
        }catch (SQLException e){
            LOG.error("Error al obtener el libro con id: '{}'",id,e);
            return null;
        }
    }
    public void insertLibro(Libro libro) {
        String sql =
            "INSERT INTO LIBROS (titulo, id_autor, id_genero, id_editorial) VALUES (?,?,?,?)";
            
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, libro.getTitulo());
            stmt.setInt(2, libro.getAutor().getId());
            stmt.setInt(3, libro.getEditorial().getId());
            stmt.setInt(4, libro.getGenero().getId());
            stmt.setInt(5, libro.getEditorial().getId());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    libro.setId(rs.getInt(1));
                }
            }
        }catch (SQLException e){
            LOG.error("Error al insertar el libro",e);
        }
    }
    public boolean deleteLibro(int id) {
        String sql = "delete from LIBROS where id = ?";
        try(Connection conn = DBConnection.getConnection();
        PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setInt(1, id);
            return stm.executeUpdate() > 0;
        }catch (SQLException e){
            LOG.error("Error al eliminar el libro con id: '{}'", id,e);
            return false;
        }
    }
    public boolean updateLibro(Libro libro) {
        String sql = """
            UPDATE libros SET
                titulo = ?,
                id_autor = ?,
                id_editorial = ?,
                id_genero = ?
            WHERE id = ?
            """;
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, libro.getTitulo());
                stmt.setInt(2, libro.getAutor().getId());
                stmt.setInt(3, libro.getEditorial().getId());
                stmt.setInt(4, libro.getGenero().getId());
                stmt.setInt(5, libro.getId());
                return stmt.executeUpdate() > 0;

        }catch (SQLException e){
            LOG.error("Error al actualizar el libro",e);
            return false;
        }
    }
}
