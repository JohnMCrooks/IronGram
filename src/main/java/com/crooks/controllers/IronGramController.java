/*
 * Copyright (c) 2016.
 */

package com.crooks.controllers;

import com.crooks.entities.Photo;
import com.crooks.entities.User;
import com.crooks.services.PhotoRepository;
import com.crooks.services.UserRepository;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
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

    @RequestMapping(path="/upload", method = RequestMethod.POST)
    public String upload(MultipartFile file, String receiver, int viewLength, HttpSession session, Boolean isPublic) throws Exception {
        String username = (String) session.getAttribute("username");
        User sender = userRepo.findFirstByName(username);
        User rec = userRepo.findFirstByName(receiver);

        if(sender==null || rec ==null){
            throw new Exception("Can't find sender or receiver");
        }
        if(isPublic==null){ isPublic=false;}

        if(file.getContentType().contains("image")){        //Checking to make sure the photo is actually a photo before Uploading it to the server
            File dir = new File("public/photos");
            dir.mkdirs();

            File photoFile = File.createTempFile("photo", file.getOriginalFilename(), dir);  //adding a prefix and a unique number to the filename so there won't be any duplicates on the server
            FileOutputStream fos = new FileOutputStream(photoFile);
            fos.write(file.getBytes());                                 //Writing file to Disk

            Photo photo = new Photo(sender, rec, photoFile.getName(),viewLength, isPublic, 0);
            photoRepo.save(photo);                                      //adding photo info to the Database
        }else{
            throw new Exception("Wrong file type! We only take Images");
        }

        return "redirect:/";
    }

    @RequestMapping(path = "/logout",method = RequestMethod.POST)
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }

}
