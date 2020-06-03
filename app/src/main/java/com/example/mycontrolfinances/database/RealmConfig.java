package com.example.mycontrolfinances.database;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmConfig extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        RealmConfiguration configuration = new RealmConfiguration
                .Builder()
                .name("base.realm")
                .schemaVersion(2)
                //.schemaVersion(2) // Must be bumped when the schema changes
                //.migration(new MigrationData()) // Migration to run instead of throwing an exception
                .deleteRealmIfMigrationNeeded()
                .build();

        Realm.setDefaultConfiguration(configuration);

    }
}
