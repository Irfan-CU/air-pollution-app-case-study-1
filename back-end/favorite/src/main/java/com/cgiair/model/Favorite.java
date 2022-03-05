package com.cgiair.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class Favorite {
    @Id
    private String email;
    private List<FavoriteItem> favoriteItems;

    public Favorite(String email) {
        this.email = email;
        this.favoriteItems = new ArrayList<>();
    }

    public Favorite() {
    }

    public String getEmail() {
        return email;
    }

    public void setId(String email) {
        this.email = email;
    }

    public List<FavoriteItem> getFavoriteItems() {
        return favoriteItems;
    }

    public void setFavoriteItems(List<FavoriteItem> favoriteItems) {
        this.favoriteItems = favoriteItems;
    }

    public void addFavoriteItem(FavoriteItem favoriteItem) {
        this.favoriteItems.add(favoriteItem);
    }

    public void deleteFavoriteItem(FavoriteItem favoriteItem) {
        this.favoriteItems.remove(favoriteItem);
    }
}
