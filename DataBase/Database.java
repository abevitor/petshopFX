package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/db_petshop";
    private static final String USER = "root";
    private static final String PASSWORD = "Vitriz3003!";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Carregado com sucesso");
        } catch(ClassNotFoundException e){
            System.out.println("Não encontrado");
            e.printStackTrace();
        }
    }

    public static Connection getConeConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    
}
