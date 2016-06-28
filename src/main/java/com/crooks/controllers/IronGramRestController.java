/*
 * Copyright (c) 2016.
 */

package com.crooks.controllers;

import com.crooks.services.PhotoRepository;
import com.crooks.services.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by johncrooks on 6/28/16.
 */
@RestController
public class IronGramRestController {
    @Autowired
    UserRepository userRepo;

    @Autowired
    PhotoRepository photoRepo;
}
