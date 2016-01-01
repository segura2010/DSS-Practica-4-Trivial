package com.segura.alberto.p4_dss_luisalbertoseguradelgado;

/**
 * Created by alberto on 1/1/16.
 */
public enum UserResource {

    INSTANCE;

    User user;

    DBHelper db;

    public void setDB(DBHelper db) {
        this.db = db;
    }

    public void loadUser()
    {
        user = db.getUser();
    }

    public void saveUser()
    {
        db.updateUser(user);
    }

    public User getUser() {
        return user;
    }
}
