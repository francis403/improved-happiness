package com.example.gamethetown.Catalogs;

import com.example.gamethetown.item.User;

import java.util.List;

public class UserCatalog {

    private static User currentUser;

    //TODO
    public List<User> getUsersFromDatabase(){
        return null;
    }

    public void setCurrentUser(User user){this.currentUser = user;}
    public User getCurrentUser(){return currentUser;}

}
