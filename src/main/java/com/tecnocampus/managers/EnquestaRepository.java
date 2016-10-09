package com.tecnocampus.managers;

import com.tecnocampus.domain.Enquesta;
import com.tecnocampus.domain.Pregunta;
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
 * Created by ignasiargemipuig on 4/10/16.
 */
@Repository
public class EnquestaRepository {

    private static final String SQL_SELECT_STATEMENT = "SELECT * FROM ENQUESTA ";
    private static final String SQL_INSERT_STATEMENT = "INSERT INTO ENQUESTA (TITOL) VALUES(?) ";


    private JdbcOperations jdbcOperations;
    private PreguntaRepository preguntaRepository;

    public EnquestaRepository(JdbcOperations jdbcOperations, PreguntaRepository preguntaRepository) {
        this.jdbcOperations = jdbcOperations;
        this.preguntaRepository = preguntaRepository;
    }

    /***
     * Mètode public per a save noves enquestes
     * @param enquesta
     * @return
     */
    public int save(Enquesta enquesta) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int userUpdate = this.jdbcOperations.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                        SQL_INSERT_STATEMENT,
                        new String[] { "enquestaId" });
                ps.setString(1, enquesta.getTitol());
                return ps;
            }
        }, keyHolder);

        enquesta.setId(keyHolder.getKey().longValue());

        return userUpdate;
    }

    public List<Enquesta> findAll() {
        return jdbcOperations.query(SQL_SELECT_STATEMENT, new EnquestaMapper());
    }

    /***
     * Aquesta funció llista totes les enquestes que hagi respost l'usuari que li passem per parametre
     * @param usuari
     * @return
     */
    public List<Enquesta> findAllFromUser(Usuari usuari) {
        List<Enquesta> llista = new ArrayList<Enquesta>();
        //TODO recuperar llista d'enquestes amb respostes de l'usuari
        //Fer consulta a enquestes, llavors a preguntes i llavors a respostes
        return jdbcOperations.query(SQL_SELECT_STATEMENT, new EnquestaMapper());
    }


    /***
     * Aquesta funció retorna l'enquesta amb el títol que li passem per paràmetre
     * @param titol
     * @return Enquesta o null
     */
    public Enquesta findOne(String titol) {
        Enquesta enquesta = jdbcOperations.queryForObject(
                SQL_SELECT_STATEMENT + " where titol like ?"
                , new Object[]{'%'+ titol +'%'}
                , new EnquestaMapper()
        );
        return enquesta;
    }

    /***
     * Aquesta funció retorna l'enquesta amb l'identificador que li passem per paràmetre
     * @param enquestaId
     * @return
     */
    public Enquesta findOne(Long enquestaId) {

        Enquesta enquesta = jdbcOperations.queryForObject(
                SQL_SELECT_STATEMENT + " where enquestaId = ?"
                , new Object[]{enquestaId}
                , new EnquestaMapper()
        );

        return enquesta;
    }


    private final class EnquestaMapper implements RowMapper<Enquesta> {
        @Override
        public Enquesta mapRow(ResultSet resultSet, int i) throws SQLException {
            Enquesta enquesta = new Enquesta(resultSet.getString("titol"));
            enquesta.setId(resultSet.getLong("enquestaId"));

            Iterable<Pregunta> list = preguntaRepository.findAllFromQuiz(enquesta.getId());
            for (Pregunta p: list) {
                p.setEnquesta(enquesta);
                enquesta.afegirPregunta(p);
            }

            return enquesta;
        }
    }
}
