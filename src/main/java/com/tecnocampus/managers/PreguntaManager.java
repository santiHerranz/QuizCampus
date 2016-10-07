package com.tecnocampus.managers;

import com.tecnocampus.domain.Enquesta;
import com.tecnocampus.domain.Pregunta;
import com.tecnocampus.domain.PreguntaNumerica;
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


/**
 * Created by ignasiargemipuig on 4/10/16.
 */
@Repository
public class PreguntaManager {

    private JdbcOperations jdbcOperations;

    private static final String SQL_SELECT_STATEMENT = "SELECT * FROM PREGUNTA ";
    private static final String SQL_INSERT_STATEMENT = "INSERT INTO PREGUNTA (ENQUESTAID, ENUNCIAT, MAXIM, MINIM) VALUES(?,?,?,?)";
    private static final String SQL_UPDATE_STATEMENT = "UPDATE PREGUNTA SET ENUNCIAT = ?, MAXIM = ?, MINIM = ? WHERE PREGUNTAID = ?";
    private static final String SQL_DELETE_STATEMENT = "DELETE PREGUNTA WHERE PREGUNTAID = ?";

    public PreguntaManager(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    public int crear(Pregunta pregunta) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int preguntaUpdate = this.jdbcOperations.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                        SQL_INSERT_STATEMENT,
                        new String[] { "preguntaId" });
                ps.setLong(1, pregunta.getEnquesta().getId());
                ps.setString(2, pregunta.getEnunciat());
                ps.setInt(3, ((PreguntaNumerica)pregunta).getMaxim());
                ps.setInt(4, ((PreguntaNumerica)pregunta).getMinim());
                return ps;
            }
        }, keyHolder);

        pregunta.setId(keyHolder.getKey().longValue());

        return preguntaUpdate;
    }

    public int eliminar(Pregunta pregunta) {
            int resultCount = jdbcOperations.update(
                    SQL_DELETE_STATEMENT
                    , pregunta.getId()
            );
            return resultCount;
        }

    public Pregunta obtenir(int id) {
        return jdbcOperations.queryForObject(
                SQL_SELECT_STATEMENT + "where preguntaId = ?"
                , new Object[]{id}
                , new PreguntaMapper()
        );
    }

    public void llistarRespostes(Pregunta pregunta) {

    }

    public Iterable<Pregunta> llistarTotes() {
        return jdbcOperations.query(
                SQL_SELECT_STATEMENT
                , new PreguntaMapper()
        );
    }


    private final class PreguntaMapper implements RowMapper<Pregunta> {
        @Override
        public Pregunta mapRow(ResultSet resultSet, int i) throws SQLException {

            Enquesta enquesta = new EnquestaManager(jdbcOperations).obtenirEnquesta(resultSet.getInt("enquestaId"));

            PreguntaNumerica pregunta = new PreguntaNumerica(enquesta, resultSet.getString("enunciat"));
            pregunta.setId(resultSet.getLong("preguntaid"));
            pregunta.setMaxim(resultSet.getInt("maxim"));
            pregunta.setMinim(resultSet.getInt("minim"));
            return pregunta;
        }
    }
}
