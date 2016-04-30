package database;

import global.Vars;

import java.sql.DriverManager;
import java.util.Locale;

public class Connect {
    public void go() {
        Locale.setDefault(Locale.ENGLISH);
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            Vars.con = DriverManager.getConnection(url, "steiner", "81284642");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
