/*
 * Copyright (c) 2016.
 */

package com.crooks.controllers;

import com.crooks.services.PhotoRepository;
import com.crooks.services.UserRepository;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.sql.SQLException;

/**
 * Created by johncrooks on 6/28/16.
 */
@Controller
public class IronGramController {
    @Autowired
    UserRepository userRepo;

    @Autowired
    PhotoRepository photoRepo;

    @PostConstruct
    public void init() throws SQLException{
        Server.createWebServer().start();
    }
}
