package com.tecnocampus.databaseRepositories;

import com.tecnocampus.BeansManager;
import com.tecnocampus.domain.Enquesta;
import com.tecnocampus.domain.Pregunta;
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
 * Created by ignasiargemipuig on 4/10/16.
 */
@Repository
public class EnquestaRepository {

    private static final String SQL_SELECT_STATEMENT = "SELECT * FROM ENQUESTA ";
    private static final String SQL_INSERT_STATEMENT = "INSERT INTO ENQUESTA (TITOL) VALUES(?) ";
    private static final String SQL_UPDATE_STATEMENT = "UPDATE ENQUESTA SET TITOL = ? WHERE ENQUESTAID = ? ";
    private static final String SQL_DELETE_STATEMENT = "DELETE FROM ENQUESTA WHERE ENQUESTAID = ?";


    private JdbcOperations jdbcOperations;

    @Autowired
    BeansManager beansManager;

    public EnquestaRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    /***
     * Mètode public per a save noves enquestes
     * @param enquesta
     * @return
     */
    public Enquesta save(Enquesta enquesta) {
        if(enquesta.getId() == null) {
            insert(enquesta);
        } else {
            update(enquesta);
        }
        return findOne(enquesta.getId());
    }


    private void update(Enquesta enquesta) {
        int updateResult = this.jdbcOperations.update(
                SQL_UPDATE_STATEMENT,
                enquesta.getTitol(),
                enquesta.getId().toString()
        );
    }

    private void insert(Enquesta enquesta) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int insertResult = this.jdbcOperations.update(new PreparedStatementCreator() {
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
    }

    /***
     * Aquesta funció retorna totes les enquestes
     * @return
     */
    public List<Enquesta> findAll() {
        return jdbcOperations.query(SQL_SELECT_STATEMENT, new EnquestaMapper());
    }

    /***
     * Aquesta funció llista totes les enquestes que hagi respost l'usuari que li passem per parametre
     * @param usuari
     * @return
     */
    public List<Enquesta> findAllFromUser(Usuari usuari) {
        String CercaAmbJoin = "select distinct(e.enquestaId) enquestaId, e.titol titol from enquesta as e join pregunta as p on e.enquestaId = p.enquestaId join resposta as r on p.preguntaId = r.preguntaId\n";

        List<Enquesta> llista = jdbcOperations.query(CercaAmbJoin + " where r.usuariId = ?"
                , new Object[]{ usuari.getId()}
                , new EnquestaMapperLazy());
        return llista;
    }


    /***
     * Aquesta funció retorna l'enquesta amb el títol que li passem per paràmetre
     * @param titol
     * @return Enquesta o null
     */
    public Enquesta findOne(String titol) {
        try {
            Enquesta enquesta = jdbcOperations.queryForObject(
                    SQL_SELECT_STATEMENT + " where titol like ?"
                    , new Object[]{'%'+ titol +'%'}
                    , new EnquestaMapper()
            );
            return enquesta;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /***
     * Aquesta funció retorna l'enquesta amb l'identificador que li passem per paràmetre
     * @param enquestaId
     * @return Enquesta o null
     */
    public Enquesta findOne(Long enquestaId) {
        try {
            return jdbcOperations.queryForObject(
                    SQL_SELECT_STATEMENT + " where enquestaId = ?"
                    , new Object[]{enquestaId}
                    , new EnquestaMapper()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public Enquesta findOneLazy(Long enquestaId) {
        try {
            return jdbcOperations.queryForObject(
                    SQL_SELECT_STATEMENT + " where enquestaId = ?"
                    , new Object[]{enquestaId}
                    , new EnquestaMapperLazy()
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /***
     * Aquest mètode elimina l'enquesta passada per parametres.
     * @param enquesta
     */
    public void eliminarEnquesta(Enquesta enquesta) {
        int result = jdbcOperations.update(
                SQL_DELETE_STATEMENT
                , enquesta.getId()
        );
    }


    private final class EnquestaMapper implements RowMapper<Enquesta> {
        @Override
        public Enquesta mapRow(ResultSet resultSet, int i) throws SQLException {
            Enquesta enquesta = new Enquesta(resultSet.getString("titol"));
            enquesta.setId(resultSet.getLong("enquestaId"));
            enquesta.setDataCreacio(resultSet.getDate("data_creacio"));

            Iterable<Pregunta> list = beansManager.preguntaRepository.findAllFromQuiz(enquesta.getId());
            for (Pregunta p: list) {
                p.setEnquesta(enquesta);
                enquesta.afegirPregunta(p);
            }

            return enquesta;
        }
    }

    private final class EnquestaMapperLazy implements RowMapper<Enquesta> {
        @Override
        public Enquesta mapRow(ResultSet resultSet, int i) throws SQLException {
            Enquesta enquesta = new Enquesta(resultSet.getString("titol"));
            enquesta.setId(resultSet.getLong("enquestaId"));
            enquesta.setDataCreacio(resultSet.getDate("data_creacio"));

            return enquesta;
        }
    }

}
