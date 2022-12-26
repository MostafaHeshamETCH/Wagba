package com.example.wagba.view_models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wagba.models.room.LocalUser;
import com.example.wagba.models.room.LocalUserRepository;

import java.util.List;

public class LocalUserViewModel extends AndroidViewModel {

    private LocalUserRepository mRepository;

    private LiveData<List<LocalUser>> mAllLocalUsers;

    public LocalUserViewModel (Application application) {
        super(application);
        mRepository = new LocalUserRepository(application);
        mAllLocalUsers = mRepository.getAllLocalUsers();
    }

    public LiveData<List<LocalUser>> getAllLocalUsers() { return mAllLocalUsers; }

    public void insert(LocalUser localUser) { mRepository.insert(localUser); }
    public void clear() { mRepository.delete(); }
}
