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

    public Usuari findOne(String email) {
        return jdbcOperations.queryForObject(
                  "Select * from usuari where email = ?"
                , new Object[]{email}
                , new UsuariMapper()
        );
    }

    public int save(Usuari userLab) {
        int userUpdate = jdbcOperations.update(
                "insert into usuari (email, contrasenya) values(?,?)"
                , userLab.getEmail()
                , userLab.getContrasenya());

        return userUpdate;
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
