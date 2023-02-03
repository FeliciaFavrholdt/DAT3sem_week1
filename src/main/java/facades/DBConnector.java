package facades;

import dtos.DBUserDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {
    private static String URL = "jdbc:mysql://localhost:3306/";


    private static String USER = "dev";
    private static String PW = "ax2";

    private static Connection singleton;

    public static void setConnection( Connection con ) {
        singleton = con;
    }

    public static Connection connection(String db) throws ClassNotFoundException, SQLException {
        if ( singleton == null ) {
            Class.forName( "com.mysql.cj.jdbc.Driver" );
            DBUserDTO dbUserDTO = getCredentails();
            singleton = DriverManager.getConnection( dbUserDTO.getConnectionStr()+db+"?enabledTLSProtocols=TLSv1.2", dbUserDTO.getUserName(), dbUserDTO.getPassword() );
        }
        return singleton;
    }
    private static DBUserDTO getCredentails() {
        boolean isDeployed = (System.getenv("DEPLOYED") != null);
        if (isDeployed) {
            String user = System.getenv("USER");
            String pw = System.getenv("PW");
            String connectionUrl = System.getenv("CONNECTION_STR");
            //System.out.println("USING ENVIRONMENT VARIABLES: User: "+user+" PW: "+pw);
            return new DBUserDTO(user, pw, connectionUrl);
        }
        return new DBUserDTO(USER, PW, URL);
        }

}

