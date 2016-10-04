package com.tecnocampus.managers;

import com.tecnocampus.domain.Usuari;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by santi on 03/10/2016.
 */
@Repository
public class UsuariManager {

    private JdbcOperations jdbcOperations;

    public UsuariManager(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public Iterable<Usuari> findAll() {
        return jdbcOperations.query("Select * from usuari", new UsuariMapper());
    }

    public Usuari obtenir(String email) {
        return jdbcOperations.queryForObject(
                  "Select * from usuari where email = ?"
                , new Object[]{email}
                , new UsuariMapper()
        );
    }

    public int crear(Usuari usuari) {
        int userUpdate = jdbcOperations.update(
                "insert into usuari (email, contrasenya) values(?,?)"
                , usuari.getEmail()
                , usuari.getContrasenya());

        return userUpdate;
    }
    public boolean comprobar(Usuari usuari) {
        //TODO: implementar
        return true;
    }

    public void ferAdmin(Usuari usuari) {
        //TODO: implementar
    }

    public void eliminar(Usuari usuari) {
        //TODO: implementar
    }


        private final class UsuariMapper implements RowMapper<Usuari> {
        @Override
        public Usuari mapRow(ResultSet resultSet, int i) throws SQLException {
            Usuari usuari = new Usuari();
            usuari.setEmail(resultSet.getString("email"));
            usuari.setContrasenya(resultSet.getString("contrasenya"));
            return usuari;
        }
    }
}
