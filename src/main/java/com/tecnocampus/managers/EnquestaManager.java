package com.tecnocampus.managers;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import com.tecnocampus.domain.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ignasiargemipuig on 4/10/16.
 */
public class EnquestaManager {

    private JdbcOperations jdbcOperations;

    public EnquestaManager(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    /***
     * Mètode public per a crear noves enquestes
     * @param enquesta
     * @return
     */
    public int crear(Enquesta enquesta) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int userUpdate = this.jdbcOperations.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                        "insert into enquesta (email, contrasenya) values(?,?)",
                        new String[] { "enquestaId" });
                ps.setString(1, enquesta.getTitol());
                return ps;
            }
        }, keyHolder);

        enquesta.setId(keyHolder.getKey().longValue());

        return userUpdate;
    }

    /***
     * Aquesta funció llista totes les enquestes que hagi respost l'usuari que li passem per parametre
     * @param usuari
     * @return
     */
    public List<Enquesta> llistarEnquestes(Usuari usuari) {
        List<Enquesta> llista = new ArrayList<Enquesta>();
        //TODO recuperar llista d'enquestes amb respostes de l'usuari
        //Fer consulta a enquestes, llavors a preguntes i llavors a respostes
        return null;
    }

    /***
     * Aquesta funció llista totes les preguntes que pertanyin a l'esquesta que passem per parametre
     * @param enquesta
     */
    public List<Pregunta> llistarPreguntes(Enquesta enquesta) {
        List<Enquesta> llista = new ArrayList<Enquesta>();
        Iterable<Enquesta> enq = jdbcOperations.query("Select * from pregunta", new EnquestaMapper());
        return null;
    }

    /***
     * Aquesta funció retorna l'enquesta amb el títol que li passem per paràmetre
     * @param titol
     * @return Enquesta o null
     */
    public Enquesta obtenirEnquesta(String titol) {
        return null;
    }

    /***
     * Aquesta funció retorna l'enquesta amb l'identificador que li passem per paràmetre
     * @param enquestaID
     * @return
     */
    public Enquesta obtenirEnquesta(int enquestaID) {
        return null;
    }

    /***
     * Aquesta funció retorna un booleà en funció de si l'enquesta existeix
     * @param titol
     * @return
     */
    private boolean existeix(String titol) {
        return false;
    }

    private final class EnquestaMapper implements RowMapper<Enquesta> {
        @Override
        public Enquesta mapRow(ResultSet resultSet, int i) throws SQLException {
            Enquesta enquesta = new Enquesta(resultSet.getString("titol"));
            enquesta.setId(resultSet.getLong("enquestaID"));
            return enquesta;
        }
    }
}