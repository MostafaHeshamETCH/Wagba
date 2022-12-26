package com.example.wagba.models.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LocalUserDao {
    @Insert
    void insert(LocalUser localUser);

    @Query("DELETE FROM local_user_table")
    void deleteAll();

    @Query("SELECT * FROM local_user_table ORDER BY address ASC")
    LiveData<List<LocalUser>> getAllLocalUsers();
}
