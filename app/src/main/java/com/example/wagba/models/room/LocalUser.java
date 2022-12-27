package com.example.wagba.models.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "local_user_table")
public class LocalUser {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "year")
    private String uniYear;

    public LocalUser(@NonNull String uniYear) {this.uniYear = uniYear;}

    public String getUniYear(){return this.uniYear;}
}
