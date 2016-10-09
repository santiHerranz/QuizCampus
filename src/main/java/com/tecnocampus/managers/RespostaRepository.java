package com.tecnocampus.managers;

import com.tecnocampus.domain.Pregunta;
import com.tecnocampus.domain.Resposta;
import com.tecnocampus.domain.RespostaNumerica;
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
import java.util.List;

/**
 * Created by ignasiargemipuig on 4/10/16.
 */
@Repository
public class RespostaRepository {


    private static final String SQL_SELECT_STATEMENT = "SELECT * FROM RESPOSTA ";
    private static final String SQL_INSERT_STATEMENT = "INSERT INTO RESPOSTA (PREGUNTAID, USUARIID, VALOR) VALUES(?,?,?)";
    private static final String SQL_UPDATE_STATEMENT = "UPDATE RESPOSTA SET VALOR = ? WHERE RESPOSTAID = ?";
    private static final String SQL_DELETE_STATEMENT = "DELETE FROM RESPOSTA WHERE RESPOSTAID = ?";

    private JdbcOperations jdbcOperations;
    private  UsuariRepository usuariRepository;

    public RespostaRepository(JdbcOperations jdbcOperations, UsuariRepository usuariRepository) {

        this.jdbcOperations = jdbcOperations;
        this.usuariRepository = usuariRepository;
    }

    public int save(Pregunta pregunta, RespostaNumerica resposta) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int respostaUpdate = this.jdbcOperations.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                        SQL_INSERT_STATEMENT,
                        new String[] { "respostaId" });
                ps.setLong(1, pregunta.getId());
                ps.setLong(2, resposta.getUsuari().getId());
                //TODO: Forçem el tipus perque de moment només hi ha una taula ,delete el forçat quan es separin amb 2 taules
                ps.setInt(3, ((RespostaNumerica)resposta).getValor());
                return ps;
            }
        }, keyHolder);

        resposta.setId(keyHolder.getKey().longValue());

        return respostaUpdate;  
    }

    public List<RespostaNumerica> findAll(Long preguntaId) {
        return jdbcOperations.query(
                SQL_SELECT_STATEMENT +" WHERE preguntaId = ?"
                , new Object[] { preguntaId}
                , new RespostaNumericaMapper());
    }

    public List<RespostaNumerica> findAll() {
        return jdbcOperations.query(
                SQL_SELECT_STATEMENT
                , new RespostaNumericaMapper());
    }

    public Iterable<RespostaNumerica> llistarRespostesNumeriques() {
        return jdbcOperations.query(SQL_SELECT_STATEMENT, new RespostaNumericaMapper());
    }

    public int delete(Resposta resposta) {
        int resultDelete = jdbcOperations.update(
                SQL_DELETE_STATEMENT
                , resposta.getId()
        );
        return resultDelete;

    }

    public Resposta findOne(int respostaId) {
        return jdbcOperations.queryForObject(
                SQL_SELECT_STATEMENT + " where respostaId = ?"
                , new Object[]{respostaId}
                , new RespostaNumericaMapper()
        );

    }



    public List<RespostaNumerica> findAllFromUser(Long usuariId) {
        return jdbcOperations.query(
                SQL_SELECT_STATEMENT + " where usuariId = ?"
                , new Object[]{usuariId}
                , new RespostaNumericaMapper()
        );
    }




    private final class RespostaNumericaMapper implements RowMapper<RespostaNumerica> {
        @Override
        public RespostaNumerica mapRow(ResultSet resultSet, int i) throws SQLException {


            RespostaNumerica resposta = new RespostaNumerica();
            resposta.setUsuariId(resultSet.getLong("usuariid"));
            resposta.setPreguntaId(resultSet.getLong("preguntaid"));
            resposta.setId(resultSet.getLong("respostaid"));
            resposta.setValor(resultSet.getInt("valor"));

            Usuari usuari = usuariRepository.findOne(resposta.getUsuariId());
            resposta.setUsuari(usuari);
            usuari.afegirResposta(resposta);

            return resposta;
        }
    }

}
