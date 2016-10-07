package com.tecnocampus.managers;

import com.tecnocampus.domain.*;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by ignasiargemipuig on 4/10/16.
 */
public class RespostaManager {

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
                        "insert into resposta (preguntaId, usuariId, valor) values(?,?,?)",
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



    public void eliminarResposta(Resposta respostaID) {

    }

    public void obtenirResposta(int respostaID) {

    }
}
