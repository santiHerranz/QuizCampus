package com.tecnocampus;

import com.tecnocampus.domain.Resposta;
import com.tecnocampus.useCases.RespostaCasosUs;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Created by santi on 11/10/16.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RespostaCasosUsTest {

    @Rule // Aplica a tota la classe
    public ExpectedException expectedEx = ExpectedException.none();

    @Autowired
    RespostaCasosUs respostaCasosUs;

    @Test
    public void llistarRespostesTest() {

        List<Resposta> list = respostaCasosUs.llistarRespostes();
        Assert.assertTrue(list.size()>0);
    }
}