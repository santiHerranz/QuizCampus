package com.tecnocampus.managers;

import com.tecnocampus.domain.Usuari;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by santi on 03/10/2016.
 */
@Repository
public class UsuariManager {

    private JdbcOperations jdbcOperations;

    public UsuariManager(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    /***
     * Obtenir llistat d'usuaris
     * @return
     */
    public List<Usuari> llistar() {
        Iterable<Usuari> usuaris = jdbcOperations.query("Select * from usuari", new UsuariMapper());
        List<Usuari> target = new ArrayList<Usuari>();
        usuaris.forEach(target::add);
        return target;
    }

    /***
     * Obtenir l'usuari a partir de l'email
     * @param email
     * @return l'usuari trobat o null
     */
    public Usuari obtenir(String email) {
        return jdbcOperations.queryForObject(
                  "Select * from usuari where email = ?"
                , new Object[]{email}
                , new UsuariMapper()
        );
    }

    /***
     * Obtenir l'usuari a partir de l'Identificador
     * @param id
     * @return l'usuari trobat o null
     */
    public Usuari obtenir(int id) {
        return jdbcOperations.queryForObject(
                "Select * from usuari where usuariId = ?"
                , new Object[]{id}
                , new UsuariMapper()
        );
    }

    /***
     * guarda l'usuari a bbdd i obté el nou identificador autonuméric
     * @param usuari
     * @return 0 o 1 segons el numero de files insertades
     */
    public int crear(Usuari usuari) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int userUpdate = this.jdbcOperations.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                        "insert into usuari (email, contrasenya) values(?,?)",
                        new String[] { "usuariId" });
                ps.setString(1, usuari.getEmail());
                ps.setString(2, usuari.getContrasenya());
                return ps;
            }
        }, keyHolder);

        usuari.setId(keyHolder.getKey().longValue());

        return userUpdate;
    }

    /***
     * Elimina l'usuari
     * @param usuari
     * @return 0 o 1 segons el numero de files eliminades
     */
    public int eliminar(Usuari usuari) {
        int userDelete = jdbcOperations.update(
                "delete from usuari where usuariId = ?"
                , usuari.getId()
                );
        return userDelete;
    }

    /***
     * Actualitza els atributs guardats a la bbdd
     * @param usuari
     */
    public void guardar(Usuari usuari) {
        jdbcOperations.update(
                "update usuari set email = ?, contrasenya = ?, admin = ? WHERE usuariId = ?"
                , usuari.getEmail()
                , usuari.getContrasenya()
                , usuari.isAdmin()
                , usuari.getId()
        );
    }


    private final class UsuariMapper implements RowMapper<Usuari> {
        @Override
        public Usuari mapRow(ResultSet resultSet, int i) throws SQLException {
            Usuari usuari = new Usuari();
            usuari.setId(resultSet.getLong("usuariid"));
            usuari.setEmail(resultSet.getString("email"));
            usuari.setContrasenya(resultSet.getString("contrasenya"));
            usuari.setAdmin(resultSet.getBoolean("admin"));
            return usuari;
        }
    }
}
