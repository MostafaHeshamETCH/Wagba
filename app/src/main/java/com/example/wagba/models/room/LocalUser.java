package com.example.wagba.models.room;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "local_user_table")
public class LocalUser {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "address")
    private String mAddress;

    public LocalUser(@NonNull String address) {this.mAddress = address;}

    public String getAddress(){return this.mAddress;}
}
