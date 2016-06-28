/*
 * Copyright (c) 2016.
 */

package com.crooks.controllers;

import com.crooks.entities.Photo;
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
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

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

    @RequestMapping(path="/photos", method = RequestMethod.GET)
    public Iterable<Photo> getPhotos(HttpSession session) throws InterruptedException {
        String username = (String) session.getAttribute("username");
        User user = userRepo.findFirstByName(username);

        //Checking to see the size of the PhotoRepo
        Iterable<Photo> tempIterable =  photoRepo.findByRecipientAndIsPublic(user, false);
        int itSize=0;
        Iterator it = tempIterable.iterator();
        while ( it.hasNext()){
            it.next();
            itSize++;
        }
        if (session.getAttribute("username")!=null){
            if(itSize >0 ) {
                for (Photo p : tempIterable) {
                    if (p.getViewLength() > p.getCounter()) {
                        p.setCounter(p.getCounter() + 1);
                        System.out.println("count =" + p.getCounter());
                        photoRepo.save(p);

                    } else if (p.getViewLength() <= p.getCounter()) {
                        System.out.println(p.getId() + "  is getting deleted...");

                        Photo fileToDelete = photoRepo.findOne(p.getId());  //creating an object into which we insert the item(file) to delete
                        File fileOnDisk = new File("public/photos/" + fileToDelete.getFilename());
                        fileOnDisk.delete(); //is highlighted bc we are not capturing the return value that that indicates the success of the deletion

                        photoRepo.delete(p);
                        System.out.println(p.getCounter() + " ----" + p.getViewLength());
                    }
                }
            }
        }
        return photoRepo.findByRecipient(user);
    }

    @RequestMapping(path="/public-photos", method = RequestMethod.GET)
    public Iterable<Photo> getPhotos(String username){
        User user = userRepo.findFirstByName(username);
        return photoRepo.findBySenderAndIsPublic(user, true);
    }


}
