package com.tecnocampus.databaseRepositories;

import com.tecnocampus.domain.Enquesta;
import com.tecnocampus.domain.Pregunta;
import com.tecnocampus.domain.PreguntaNumerica;
import com.tecnocampus.domain.Resposta;
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
 * Created by ignasiargemipuig on 4/10/16.
 */
@Repository
public class PreguntaRepository {

    private JdbcOperations jdbcOperations;

    @Autowired
    RespostaRepository respostaRepository;

    private static final String SQL_SELECT_STATEMENT = "SELECT * FROM PREGUNTA ";
    private static final String SQL_INSERT_STATEMENT = "INSERT INTO PREGUNTA (ENQUESTAID, ENUNCIAT, MAXIM, MINIM) VALUES(?,?,?,?)";
    private static final String SQL_UPDATE_STATEMENT = "UPDATE PREGUNTA SET ENUNCIAT = ?, MAXIM = ?, MINIM = ? WHERE PREGUNTAID = ?";
    private static final String SQL_DELETE_STATEMENT = "DELETE PREGUNTA WHERE PREGUNTAID = ?";

    public PreguntaRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }


    public Pregunta save(Enquesta enquesta, Pregunta pregunta) {
        if(pregunta.getId() == null) {
            insert(enquesta, pregunta);
        } else {
            update(pregunta);
        }

        return findOne(pregunta.getId());
    }
    public void insert(Enquesta enquesta, Pregunta pregunta) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int preguntaUpdate = this.jdbcOperations.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                        SQL_INSERT_STATEMENT,
                        new String[] { "preguntaId" });
                ps.setLong(1, enquesta.getId());
                ps.setString(2, pregunta.getEnunciat());
                ps.setInt(3, ((PreguntaNumerica)pregunta).getMaxim());
                ps.setInt(4, ((PreguntaNumerica)pregunta).getMinim());
                return ps;
            }
        }, keyHolder);
        pregunta.setId(keyHolder.getKey().longValue());

        // Enllaçar objectes
        pregunta.setEnquesta(enquesta);
        enquesta.getPreguntes().add(pregunta);
    }

    private void update(Pregunta pregunta) {
        PreguntaNumerica preguntaNumerica = ((PreguntaNumerica)pregunta);

        int updateResult = this.jdbcOperations.update(
                SQL_UPDATE_STATEMENT,
                new Object[] { pregunta.getEnunciat()
                        , preguntaNumerica.getMaxim()
                        , preguntaNumerica.getMinim()
                        , pregunta.getId().toString()
                }
        );
    }


    public int delete(Pregunta pregunta) {
            int resultCount = jdbcOperations.update(
                    SQL_DELETE_STATEMENT
                    , pregunta.getId()
            );
            return resultCount;
        }

    public Pregunta findOne(Long preguntaId) {
        try {
            Pregunta pregunta = jdbcOperations.queryForObject(
                    SQL_SELECT_STATEMENT + "where preguntaId = ?"
                    , new Object[]{preguntaId}
                    , new PreguntaMapper()
            );
            return pregunta;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Pregunta findOneLazy(Long preguntaId) {
        try {
            Pregunta pregunta = jdbcOperations.queryForObject(
                    SQL_SELECT_STATEMENT + "where preguntaId = ?"
                    , new Object[]{preguntaId}
                    , new PreguntaMapperLazy()
            );
            return pregunta;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Pregunta> findAll() {
        List<Pregunta> list = jdbcOperations.query(
                SQL_SELECT_STATEMENT
                , new PreguntaMapper()
        );
        return  list;
    }

    /***
     * Aquesta funció llista totes les preguntes que pertanyin a l'esquesta que passem per parametre
     * @param enquestaId
     */
    public Iterable<Pregunta> findAllFromQuiz(Long enquestaId) {
        return jdbcOperations.query(
                SQL_SELECT_STATEMENT +" WHERE enquestaId = ?"
                , new Object[]{ enquestaId}
                , new PreguntaMapper());
    }



    private final class PreguntaMapper implements RowMapper<Pregunta> {
        @Override
        public Pregunta mapRow(ResultSet resultSet, int i) throws SQLException {

            PreguntaNumerica pregunta = new PreguntaNumerica();

            pregunta.setId(resultSet.getLong("preguntaid"));
            //pregunta.setEnquestaId(resultSet.getLong("enquestaid"));
            pregunta.setEnunciat(resultSet.getString("enunciat"));
            pregunta.setMaxim(resultSet.getInt("maxim"));
            pregunta.setMinim(resultSet.getInt("minim"));

            Enquesta enquesta = new EnquestaRepository(jdbcOperations).findOneLazy(resultSet.getLong("enquestaid"));
            pregunta.setEnquesta(enquesta);

            List<Resposta> list = respostaRepository.findAll(pregunta.getId());
            for (Resposta r: list) {
                r.setPregunta(pregunta);
                pregunta.afegirResposta(r);
            }

            return pregunta;
        }
    }

    private final class PreguntaMapperLazy implements RowMapper<Pregunta> {
        @Override
        public Pregunta mapRow(ResultSet resultSet, int i) throws SQLException {

            PreguntaNumerica pregunta = new PreguntaNumerica();

            pregunta.setId(resultSet.getLong("preguntaid"));
            //pregunta.setEnquestaId(resultSet.getLong("enquestaid"));
            pregunta.setEnunciat(resultSet.getString("enunciat"));
            pregunta.setMaxim(resultSet.getInt("maxim"));
            pregunta.setMinim(resultSet.getInt("minim"));

            Enquesta enquesta = new EnquestaRepository(jdbcOperations).findOneLazy(resultSet.getLong("enquestaid"));
            pregunta.setEnquesta(enquesta);

            // No enllaçar amb les respostes per evitar referencies circulars

            return pregunta;
        }
    }

}
