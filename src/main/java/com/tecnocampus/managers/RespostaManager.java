package com.tecnocampus.managers;

import com.tecnocampus.domain.*;
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
public class RespostaManager {


    private static final String SQL_SELECT_STATEMENT = "SELECT * FROM RESPOSTA ";
    private static final String SQL_INSERT_STATEMENT = "INSERT INTO RESPOSTA (PREGUNTAID, USUARIID, VALOR) VALUES(?,?,?)";
    private static final String SQL_UPDATE_STATEMENT = "UPDATE RESPOSTA SET VALOR = ? WHERE RESPOSTAID = ?";
    private static final String SQL_DELETE_STATEMENT = "DELETE FROM RESPOSTA WHERE RESPOSTAID = ?";

    private JdbcOperations jdbcOperations;

    public RespostaManager(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }


    public int crear(Resposta resposta) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        int respostaUpdate = this.jdbcOperations.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(
                        SQL_INSERT_STATEMENT,
                        new String[] { "respostaId" });
                ps.setLong(1, resposta.getPregunta().getId());
                ps.setLong(2, resposta.getUsuari().getId());
                //TODO: Forçem el tipus perque de moment només hi ha una taula ,eliminar el forçat quan es separin amb 2 taules
                ps.setInt(3, ((RespostaNumerica)resposta).getValor());
                return ps;
            }
        }, keyHolder);

        resposta.setId(keyHolder.getKey().longValue());

        return respostaUpdate;  
    }



    public int eliminarResposta(Resposta resposta) {
        int resultDelete = jdbcOperations.update(
                SQL_DELETE_STATEMENT
                , resposta.getId()
        );
        return resultDelete;

    }

    public Resposta obtenirResposta(int respostaId) {
        return jdbcOperations.queryForObject(
                SQL_SELECT_STATEMENT + " where respostaId = ?"
                , new Object[]{respostaId}
                , new RespostaMapper()
        );

    }

    private final class RespostaMapper implements RowMapper<Resposta> {
        @Override
        public Resposta mapRow(ResultSet resultSet, int i) throws SQLException {

            Pregunta pregunta = new PreguntaManager(jdbcOperations).obtenir(resultSet.getInt("preguntaId"));
            Usuari usuari = new UsuariManager(jdbcOperations).obtenir(resultSet.getInt("usuariId"));

            Resposta resposta = new RespostaNumerica(pregunta, usuari, resultSet.getInt("valor"));
            resposta.setId(resultSet.getLong("respostaid"));
            return resposta;
        }
    }
}
