
import java.sql.Connection;
import java.sql.DriverManager;

public class connector {

    public static Connection getConnector() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection cn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "123");
        return cn;
    }
}
