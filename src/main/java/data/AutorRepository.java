package data;
import models.Autor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.*;
import java.util.LinkedList;

public class AutorRepository {
    private static final Logger LOG = LoggerFactory.getLogger(AutorRepository.class);
    private final DBConnection dbConnection = new DBConnection();
    public LinkedList<Autor> getAllAutores(){
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            LinkedList<Autor> autores = new LinkedList<>();
            if(conn != null){
                stmt = conn.createStatement();
                rs = stmt.executeQuery("SELECT * FROM AUTORES");
            }
            while (rs != null && rs.next()){
                Autor a = new Autor(
                        rs.getInt("id"),
                        rs.getString("nombre"));
                autores.add(a);
            }
            return autores;
        }catch (SQLException e){
            LOG.error("error al obtener la lista de Autores", e);
            return null;
        }finally {
            DBConnection.closeResources(conn,stmt,rs);
        }
    }

    public Autor getAutorById(int id){
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            Autor a = null;
            if(conn != null){
                stmt = conn.prepareStatement("SELECT * FROM AUTORES WHERE ID = ?");
                stmt.setInt(1, id);
                rs = stmt.executeQuery();
            }
            if (rs!=null && rs.next()){
                a = new Autor(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );
            }
            return a;
        }catch (SQLException e){
            LOG.error("error al obtener el Autor con id: '{}' ", id ,e);
            return null;
        }finally {
            DBConnection.closeResources(conn,stmt,rs);
        }
    }
    public void insertAutor(Autor a){
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DBConnection.getConnection();
            if(conn != null){
                stmt = conn.prepareStatement(
                        "INSERT INTO AUTORES (NOMBRE) VALUES (?)",
                        PreparedStatement.RETURN_GENERATED_KEYS
                );
                stmt.setString(1,a.getNombre());
                stmt.executeUpdate();
                rs = stmt.getGeneratedKeys();
            }
            if(rs != null && rs.next()){
                a.setId(rs.getInt(1));
            }
        }catch (SQLException e){
            LOG.error("error al crear el Autor", e);
        }finally {
            DBConnection.closeResources(conn,stmt,rs);
        }
    }
    public boolean deleteAutor(int id){
        PreparedStatement stmt = null;
        Connection conn = null;
        if(getAutorById(id) == null){
            LOG.warn("La id: '{}' no pertenece a ningun autor", id);
            return false;
        }
        try {
            conn = DBConnection.getConnection();
            if(conn != null){

                stmt = conn.prepareStatement("DELETE FROM AUTORES WHERE ID = ?");
                stmt.setInt(1, id);
                stmt.executeUpdate();
                return true;
            }
            return false;
        }catch (SQLException e){
            LOG.error("error al eliminar el Autor", e);
            return false;
        }finally {
            DBConnection.closeResources(conn,stmt,null);
        }
    }
    public boolean updateAutor(Autor a){
        PreparedStatement stmt = null;
        Connection conn = null;
        if(getAutorById(a.getId()) == null){
            LOG.warn("la id: '{}' no pertenece a ningun autor", a.getId());
            return false;
        }
        try {
            conn = DBConnection.getConnection();
            if(conn != null){
                stmt = conn.prepareStatement(
                        "UPDATE AUTORES SET NOMBRE = ?"
                );
                stmt.setString(1, a.getNombre());
                stmt.executeUpdate();
                return true;
            }
            return false;
        }catch (SQLException e){
            LOG.error("error al actualizar el Autor", e);
            return false;
        }finally {
            DBConnection.closeResources(conn,stmt,null);
        }
    }

}
