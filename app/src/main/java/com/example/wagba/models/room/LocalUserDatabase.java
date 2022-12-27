package com.example.wagba.models.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {LocalUser.class}, version = 2, exportSchema = false)
public abstract class LocalUserDatabase extends RoomDatabase {
    public abstract LocalUserDao localUserDao();
    private static LocalUserDatabase INSTANCE;

    static LocalUserDatabase getDatabase(final Context context) {
        if(INSTANCE == null){
            synchronized (LocalUserDatabase.class) {
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    LocalUserDatabase.class, "local_user_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
