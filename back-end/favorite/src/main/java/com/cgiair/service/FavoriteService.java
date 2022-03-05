package com.cgiair.service;

import com.cgiair.model.Favorite;
import com.cgiair.model.FavoriteItem;
import com.cgiair.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {
    @Autowired
    private FavoriteRepository favoriteRepository;

    public Favorite newUser(String userEmail) {
        Favorite favorite =  new Favorite(userEmail);
        favoriteRepository.save(favorite);
        return favoriteRepository.findById(favorite.getEmail()).get();
    }

    public List<FavoriteItem> getAllFavs(String userEmail) {
        if (favoriteRepository.findById(userEmail).isPresent()) {
            return ((Favorite) favoriteRepository.findById(userEmail).get()).getFavoriteItems();
        }
        return null;
    }

    public void addFav(String userEmail, FavoriteItem favoriteItem) {
        Favorite favorite = (Favorite) favoriteRepository.findById(userEmail).get();
        List<FavoriteItem> favList = favorite.getFavoriteItems();
        favList.add(favoriteItem);
        favorite.setFavoriteItems(favList);

        favoriteRepository.save(favorite);
    }

    public boolean deleteFav(String userEmail, FavoriteItem favoriteItem) {
        if (favoriteRepository.findById(userEmail).isPresent()) {
            System.out.println("The user email is in the service layer of delete"+userEmail);
            Favorite favorite = (Favorite) favoriteRepository.findById(userEmail).get();
            List<FavoriteItem> favList = favorite.getFavoriteItems();

            List<FavoriteItem> tempfavList =  favList.stream().filter(e->!(e.getCity().equals(favoriteItem.getCity()))).collect(Collectors.toList());

            if (favList.size() != tempfavList.size()) {
                System.out.println("The removed item in the service layer of delete"+favoriteItem);
                favorite.setFavoriteItems(tempfavList);
                favoriteRepository.save(favorite);
                return true;
            }
        }
        return false;
    }
}
