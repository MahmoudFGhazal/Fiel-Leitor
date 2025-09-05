package com.mahas.facade;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.mahas.dao.IDAO;
import com.mahas.dao.UserDAO;

import ch.qos.logback.core.net.server.Client;
import jakarta.annotation.PostConstruct;

public abstract class FacadeAbstract {

    @Autowired
    private UserDAO userDAO;

    protected final Map<String, IDAO> daos = new HashMap<>();

    @PostConstruct
    public void initDaos() {
        daos.put(Client.class.getName(), userDAO);
    }
}