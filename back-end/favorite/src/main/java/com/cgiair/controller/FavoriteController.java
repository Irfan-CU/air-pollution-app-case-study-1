package com.cgiair.controller;

import com.cgiair.model.Favorite;
import com.cgiair.model.FavoriteItem;
import com.cgiair.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fav")
public class FavoriteController {

    @Autowired
    FavoriteService favoriteService;

    @PostMapping("/newuser/{userEmail}")
    public ResponseEntity <?> newUser(@PathVariable("userEmail") String userEmail) {
        Favorite createdFav = favoriteService.newUser(userEmail);
        return new ResponseEntity<>(createdFav, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllFavs(@RequestAttribute("userEmail") String userEmail) {
        System.out.println("userEmail: " + userEmail);
        if (userEmail == null) {
            System.out.println("unauthorized" + HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        List<FavoriteItem> favoriteItemList = favoriteService.getAllFavs(userEmail);
        if (favoriteItemList != null) {
            return new ResponseEntity<>(favoriteItemList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/")
    public ResponseEntity<?> addFav(@RequestBody FavoriteItem favoriteItem, @RequestAttribute("userEmail") String userEmail) {
        if (userEmail == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        System.out.println("adding favorite: " + favoriteItem.toString());

        favoriteService.addFav(userEmail, favoriteItem);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteFav(@RequestBody FavoriteItem favoriteItem, @RequestAttribute("userEmail") String userEmail) {
        if (userEmail == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        System.out.println(" I am deletign the favorite item");
        if (favoriteService.deleteFav(userEmail, favoriteItem)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
