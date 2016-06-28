/*
 * Copyright (c) 2016.
 */

package com.crooks.services;

import com.crooks.entities.Photo;
import com.crooks.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by johncrooks on 6/28/16.
 */
public interface PhotoRepository extends CrudRepository<Photo, Integer> {
    public Iterable<Photo> findByRecipient(User recipient);
}
