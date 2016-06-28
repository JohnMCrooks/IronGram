/*
 * Copyright (c) 2016.
 */

package com.crooks.controllers;

import com.crooks.entities.User;
import com.crooks.services.PhotoRepository;
import com.crooks.services.UserRepository;
import com.crooks.utils.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * Created by johncrooks on 6/28/16.
 */
@RestController
public class IronGramRestController {
    @Autowired
    UserRepository userRepo;

    @Autowired
    PhotoRepository photoRepo;


    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public User login(@RequestBody User user, HttpSession session) throws Exception {        //In a REST controller we want to send something other than a String back so the front end can use it
        User userFromDB = userRepo.findFirstByName(user.getName());     //Check to see if the user is in the DB
        if (userFromDB==null){                                          //if it isn't, add it
            user.setPassword(PasswordStorage.createHash(user.getPassword()));
            userRepo.save(user);
        }else if(!PasswordStorage.verifyPassword(user.getPassword(), userFromDB.getPassword())){  //otherwise check the password
            throw new Exception("Wrong password!");
        }

        session.setAttribute("username", user.getName());
        return user;         //When using JSON routes we don't redirect the page, that's handled by the front end
    }

    @RequestMapping(path = "/logout",method = RequestMethod.POST)
    public void logout(HttpSession session){
        session.invalidate();
    }


}
