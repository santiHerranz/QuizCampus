package com.tecnocampus.domain;

import com.tecnocampus.BeansManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * Created by santi on 10/10/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
public class UsuariCasosUsTest {

    @Autowired
    BeansManager beansManager;

    @Test
    public void eliminarUsuari(){
        Usuari usuari = beansManager.usuariRepository.findOne(1L);
        beansManager.usuariRepository.delete(usuari);
        Assert.isNull(usuari,"");
    }

}
