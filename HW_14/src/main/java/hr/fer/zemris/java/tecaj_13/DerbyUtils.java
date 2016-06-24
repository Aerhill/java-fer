package hr.fer.zemris.java.tecaj_13;

import java.sql.SQLException;

public class DerbyUtils {

    public DerbyUtils() {
    }

    public static boolean tableAlreadyExists(SQLException e) {
        boolean exists;
        if(e.getSQLState().equals("X0Y32")) {
            exists = true;
        } else {
            exists = false;
        }
        return exists;
    }
}