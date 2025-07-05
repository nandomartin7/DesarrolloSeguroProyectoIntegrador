package org.proyectofinal.kc;

import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.storage.UserStorageProviderFactory;
import java.util.List;

public class MySqlUserStorageProviderFactory
        implements UserStorageProviderFactory<MySqlUserStorageProvider> {

    @Override
    public String getId() {
        return "mysql-user-storage";
    }

    @Override
    public MySqlUserStorageProvider create(KeycloakSession session, ComponentModel model) {
        return new MySqlUserStorageProvider(session, model);
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return org.keycloak.provider.ProviderConfigurationBuilder.create()
                .property().name("jdbcUrl").label("JDBC URL").type(ProviderConfigProperty.STRING_TYPE).add()
                .property().name("dbUser").label("DB User").type(ProviderConfigProperty.STRING_TYPE).add()
                .property().name("dbPass").label("DB Pass").type(ProviderConfigProperty.PASSWORD).add()
                .property().name("sql.load").label("SQL Load").type(ProviderConfigProperty.STRING_TYPE).add()
                .property().name("sql.validate").label("SQL Validate").type(ProviderConfigProperty.STRING_TYPE).add()
                .property().name("realmRole").label("Realm Role").type(ProviderConfigProperty.STRING_TYPE).add()
                .build();
    }
}