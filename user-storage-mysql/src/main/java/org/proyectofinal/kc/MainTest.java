package org.proyectofinal.kc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MainTest {

    public static void main(String[] args) throws Exception {

        // === ①  Parametros ============
        String jdbcUrl  = "jdbc:mysql://localhost:3307/IngenieriaWeb"; // <-- ajusta puerto/schema
        String dbUser   = "root";
        String dbPass   = "root";

        String sqlLoad      = "SELECT id_empleado AS username, correo FROM IngenieriaWeb.empleado WHERE id_empleado = ?";
        String sqlValidate  = "SELECT password FROM IngenieriaWeb.empleado WHERE id_empleado = ?";

        String username     = "1234567892";   // id_empleado
        String rawPassword  = "lopez";        // contraseña en texto claro
        // =================================

        // DS
        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(jdbcUrl);
        cfg.setUsername(dbUser);
        cfg.setPassword(dbPass);
        try (HikariDataSource ds = new HikariDataSource(cfg)) {

            // ② Carga user
            try (Connection c = ds.getConnection();
                 PreparedStatement ps = c.prepareStatement(sqlLoad)) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    System.out.println("✓ Usuario existe → correo = " + rs.getString("correo"));
                } else {
                    System.out.println("✗ Usuario NO encontrado");
                    return;
                }
            }

            // ③ Valida pass
            try (Connection c = ds.getConnection();
                 PreparedStatement ps = c.prepareStatement(sqlValidate)) {
                ps.setString(1, username);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    System.out.println("✗ No devolvió contraseña");
                    return;
                }
                String hash = rs.getString(1);
                if (hash.startsWith("{bcrypt}")) hash = hash.substring("{bcrypt}".length());

                boolean ok = BCrypt.checkpw(rawPassword, hash);
                System.out.println(ok ? "✓ Contraseña CORRECTA" : "✗ Contraseña INCORRECTA");
            }
        }
    }
}
