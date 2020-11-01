package finalProjectDB;

import java.sql.*;

public class conDB {
    public static Connection connection = null;
    public static Statement statement = null;
    public static ResultSet result = null;
                                // jdbc:mysql://localhost:3306/database name >> next is the fix when you try to launch the first place mine is time not being the same
    public static String url = "jdbc:mysql://localhost:3306/Supermarket?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    public static String user = "root";
    public static String password = "root"; // Depends on own SQL
    public static PreparedStatement pst = null;

    public static void main(String[] args){

    }
}
