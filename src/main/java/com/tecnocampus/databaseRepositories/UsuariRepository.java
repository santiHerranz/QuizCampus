package com.tecnocampus.databaseRepositories;

import com.tecnocampus.BeansManager;
import com.tecnocampus.domain.Resposta;
import com.tecnocampus.domain.Usuari;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
import java.util.List;

/**
 * Created by santi on 03/10/2016.
 */
@Repository
public class UsuariRepository {

    private JdbcOperations jdbcOperations;

    @Autowired
    BeansManager beansManager;

    private static final String SQL_SELECT_STATEMENT = "SELECT * FROM USUARI ";
    private static final String SQL_INSERT_STATEMENT = "INSERT INTO USUARI (EMAIL, CONTRASENYA) VALUES(?,?)";
    private static final String SQL_UPDATE_STATEMENT = "UPDATE USUARI SET EMAIL = ?, CONTRASENYA = ?, ADMIN = ? WHERE USUARIID = ?";
    private static final String SQL_DELETE_STATEMENT = "DELETE FROM USUARI WHERE USUARIID = ?";

    public UsuariRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    /***
     * Obtenir llistat d'usuaris
     * @return
     */
    public List findAll() {
        return jdbcOperations.query(SQL_SELECT_STATEMENT, new UsuariMapper());
    }



    /***
     * guarda l'usuari a bbdd i assigna el nou identificador autonuméric que obté
     * @param usuari
     * @return 0 o 1 segons el numero de files insertades
     */
    public int save(Usuari usuari) {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        int userUpdate = this.jdbcOperations.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                         SQL_INSERT_STATEMENT ,
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
     * Obtenir l'usuari a partir de l'Identificador
     * @param usuariId
     * @return l'usuari trobat o null
     */
    public Usuari findOne(Long usuariId) {
        try {
            return jdbcOperations.queryForObject(
                    SQL_SELECT_STATEMENT + "where usuariId = ?"
                    , new Object[]{usuariId}
                    , new UsuariMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public Usuari findOneLazy(Long usuariId) {
        try {
            return jdbcOperations.queryForObject(
                    SQL_SELECT_STATEMENT + "where usuariId = ?"
                    , new Object[]{usuariId}
                    , new UsuariMapperLazy()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /***
     * Obtenir l'usuari a partir de l'email
     * @param email
     * @return l'usuari trobat o null
     */
    public Usuari findOne(String email) {
        try {
            return jdbcOperations.queryForObject(
                    SQL_SELECT_STATEMENT + "where email = ?"
                    , new Object[]{email}
                    , new UsuariMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


    /***
     * Elimina l'usuari
     * @param usuari
     * @return 0 o 1 segons el numero de files eliminades
     */
    public void delete(Usuari usuari) {
        int result = jdbcOperations.update(
                SQL_DELETE_STATEMENT
                , usuari.getId()
                );
    }

    /***
     * Actualitza els atributs guardats a la bbdd
     * @param usuari
     */
    public void update(Usuari usuari) {
        jdbcOperations.update(
                 SQL_UPDATE_STATEMENT
                , usuari.getEmail()
                , usuari.getContrasenya()
                , usuari.isAdmin()
                , usuari.getId()
        );
    }



    private final class UsuariMapper implements RowMapper<Usuari> {
        @Override
        public Usuari mapRow(ResultSet resultSet, int i) throws SQLException {
            Usuari usuari = new Usuari(resultSet.getString("email"), resultSet.getString("contrasenya"));
            usuari.setId(resultSet.getLong("usuariid"));
            usuari.setAdmin(resultSet.getBoolean("admin"));

            Iterable<Resposta> list = beansManager.respostaRepository.findAllFromUser(usuari.getId());
            for (Resposta r: list) {
                r.setUsuari(usuari);
                usuari.afegirResposta(r);
            }

            return usuari;
        }
    }

    private final class UsuariMapperLazy implements RowMapper<Usuari> {
        @Override
        public Usuari mapRow(ResultSet resultSet, int i) throws SQLException {
            Usuari usuari = new Usuari(resultSet.getString("email"), resultSet.getString("contrasenya"));
            usuari.setId(resultSet.getLong("usuariid"));
            usuari.setAdmin(resultSet.getBoolean("admin"));

            return usuari;
        }
    }
}
