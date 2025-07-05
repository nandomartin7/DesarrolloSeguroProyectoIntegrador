package org.proyectofinal.kc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.ws.rs.NotFoundException;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.*;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.UserStorageProvider;
import org.mindrot.jbcrypt.BCrypt;
import javax.sql.DataSource;
import java.sql.*;
import org.jboss.logging.Logger;

public class MySqlUserStorageProvider
        implements UserStorageProvider,
        UserLookupProvider,
        CredentialInputValidator {

    private final KeycloakSession session;
    private final ComponentModel model;
    private final DataSource ds;
    private final String roleName;
    private final String sqlLoad;
    private final String sqlValidate;

    private static final Logger LOG = Logger.getLogger(MySqlUserStorageProvider.class);

    public MySqlUserStorageProvider(KeycloakSession session, ComponentModel model) {
        this.session = session;
        this.model   = model;

        HikariConfig cfg = new HikariConfig();
        cfg.setJdbcUrl(model.getConfig().getFirst("jdbcUrl"));
        cfg.setUsername(model.getConfig().getFirst("dbUser"));
        cfg.setPassword(model.getConfig().getFirst("dbPass"));
        this.ds = new HikariDataSource(cfg);

        this.roleName    = model.getConfig().getFirst("realmRole");
        this.sqlLoad     = model.getConfig().getFirst("sql.load");
        this.sqlValidate = model.getConfig().getFirst("sql.validate");
    }

    @Override
    public UserModel getUserByUsername(RealmModel realm, String username) {
        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sqlLoad)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return null;

            String id = "f:" + model.getId() + ":" + username;
            UserModel user = session.users().addUser(realm, id);
            user.setUsername(username);
            user.setEnabled(true);
            String mail = rs.getString("correo");

            if (mail != null) user.setEmail(mail);

            RoleModel r = realm.getRole(roleName);
            if (r == null) throw new NotFoundException("Role " + roleName + " missing in realm");
            user.grantRole(r);
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserModel getUserByEmail(RealmModel realmModel, String s) {
        return null;
    }

    @Override
    public UserModel getUserById(RealmModel realm, String id) {
        String prefix = "f:" + model.getId() + ":";
        if (!id.startsWith(prefix)) return null;
        String username = id.substring(prefix.length());
        return getUserByUsername(realm, username);
    }

    @Override
    public boolean isValid(RealmModel realm, UserModel user, CredentialInput input) {
        if (!supportsCredentialType(input.getType())) return false;

        String raw = input.getChallengeResponse();
        LOG.infov("→ isValid: user={0}, raw={1}", user.getUsername(), raw);   // ①

        try (Connection c = ds.getConnection();
             PreparedStatement ps = c.prepareStatement(sqlValidate)) {

            ps.setString(1, user.getUsername());
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return false;
                String hash = rs.getString(1);
                if (hash.startsWith("{bcrypt}")) hash = hash.substring("{bcrypt}".length());

                LOG.infov("→ hash DB = {0}", hash.substring(0, 20) + "…");    // ②

                return BCrypt.checkpw(raw, hash);
            }
        } catch (SQLException e) {
            LOG.error("SQL error", e);
            return false;
        }
    }



    @Override
    public boolean supportsCredentialType(String credentialType) {
        return PasswordCredentialModel.TYPE.equals(credentialType);
    }

    @Override
    public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
        return supportsCredentialType(credentialType);
    }

    @Override
    public void close() {
        ((HikariDataSource) ds).close();
    }
}