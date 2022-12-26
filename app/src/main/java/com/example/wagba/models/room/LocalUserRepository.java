package com.example.wagba.models.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class LocalUserRepository {

    private LocalUserDao mLocalUserDao;
    private LiveData<List<LocalUser>> mAllLocalUsers;

    LocalUserRepository(Application application) {
        LocalUserDatabase db = LocalUserDatabase.getDatabase(application);
        mLocalUserDao = db.localUserDao();
        mAllLocalUsers = mLocalUserDao.getAllLocalUsers();
    }

    LiveData<List<LocalUser>> getAllWords() {
        return mAllLocalUsers;
    }

    public void insert (LocalUser localUser) {
        new insertAsyncTask(mLocalUserDao).execute(localUser); // insert in separate thread
    }

    private static class insertAsyncTask extends AsyncTask<LocalUser, Void, Void> {

        private LocalUserDao mAsyncTaskDao;

        insertAsyncTask(LocalUserDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final LocalUser... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}