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


    private Libro MapLibro(ResultSet rs) throws SQLException {
        Libro libro = new Libro();
        libro.setId(rs.getInt("id"));
        libro.setTitulo(rs.getString("titulo"));
        Autor autor = new Autor();
        autor.setId(rs.getInt("autor_id"));
        autor.setNombre(rs.getString("autor_nombre"));
        Editorial editorial = new Editorial();
        editorial.setId(rs.getInt("editorial_id"));
        editorial.setNombre(rs.getString("editorial_nombre"));
        Genero genero = new Genero();
        genero.setId(rs.getInt("genero_id"));
        genero.setDescripcion(rs.getString("genero_descripcion"));
        libro.setAutor(autor);
        libro.setEditorial(editorial);
        libro.setGenero(genero);
        return libro;
    }
    public LinkedList<Libro> getAllLibros() {
        String sql = """
               SELECT l.id, l.titulo, a.id AS autor_id, a.nombre as autor_nombre,
                   e.id as editorial_id, e.nombre as editorial_nombre,
                   g.id as genero_id, g.descripcion as genero_descripcion
            FROM LIBROS l
            INNER JOIN autores a ON a.id = l.id_autor
            INNER JOIN editoriales e ON e.id = l.id_editoriales
            INNER JOIN generos  g ON g.id = l.id_genero
            """;
        LinkedList<Libro> libros = new LinkedList<>();
        try(Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                libros.add(MapLibro(rs));
            }

        }catch (SQLException e){
            LOG.error("Error al obtener la lista de libros",e);
        }
        return  libros;
    }
    public Libro getLibroById(int id) {
        String sql = """
            SELECT l.id, l.titulo, a.id AS autor_id, a.nombre as autor_nombre,
                   e.id as editorial_id, e.nombre as editorial_nombre,
                   g.id as genero_id, g.descripcion as genero_descripcion
            FROM LIBROS l
            INNER JOIN autores a ON a.id = l.id_autor
            INNER JOIN editoriales e ON e.id = l.id_editoriales
            INNER JOIN generos  g ON g.id = l.id_genero
            WHERE l.id = ?
            """;
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return MapLibro(rs);
            }
            return null;
        }catch (SQLException e){
            LOG.error("Error al obtener el libro con id: '{}'",id,e);
            return null;
        }
    }
    public void insertLibro(Libro libro) {
        String sql =
            "INSERT INTO LIBROS (titulo, id_autor, id_genero, id_editoriales) VALUES (?,?,?,?)";
            
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
                id_editoriales = ?,
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
