package data;
import java.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBConnection {
    private static final Logger LOG = LoggerFactory.getLogger(DBConnection.class);

    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String user = "tp_java";
    private static final String password = "tp_java";
    private static final String db = "biblioteca_tp_java";
    private static final String url =
            "jdbc:mysql://localhost:3306/"+db
            + "?user="+user+"&password="+password;
    static {
        try {
            Class.forName(driver);
            LOG.info("Driver '{}' registrado correctamente", driver);
        } catch (ClassNotFoundException e) {
            LOG.error("Error al registrar el driver '{}'", driver, e);
        }
    }
    protected static Connection getConnection(){
        try {
            return DriverManager.getConnection(url);
        }catch (SQLException e){
            LOG.error(e.getMessage());
            return null;
        }
    }
    protected static void closeResources(Connection conn,
                                         Statement stmt,
                                         ResultSet rs){
        try {
            if(rs != null){
                rs.close();
            }
            if(stmt != null){
                stmt.close();
            }
            if(conn != null && !conn.isClosed()){
                conn.close();
            }
        }catch (SQLException e){
            LOG.error(e.getMessage());
        }
    }
}