package com.example.authapp.Database.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.authapp.Database.Dao.JualDao;
import com.example.authapp.Database.KasirDatabase;
import com.example.authapp.Model.ModelJual;

import java.util.List;

public class JualRepository {
    public JualDao jualDao;
    private LiveData<List<ModelJual>> allJual;
    private KasirDatabase kasirDatabase;

    public JualRepository(Application application){
        kasirDatabase = KasirDatabase.getInstance(application);
        jualDao = kasirDatabase.jualDao();
        allJual = jualDao.getJual("");
    }



    public LiveData<List<ModelJual>> getAllJual(String keyword){
        allJual = jualDao.getJual(keyword);
        return allJual;
    }

    public LiveData<List<ModelJual>> getAllJual() {
        return allJual;
    }

    public void insertAll(List<ModelJual> data, boolean truncate){
        new InsertJualAll(jualDao,truncate).execute(data);
    }

    public void insert(ModelJual jual){
        new InsertJual(jualDao).execute(jual);
    }

    public void delete(ModelJual jual){
        new DeleteJual(jualDao).execute(jual);
    }

    private static class DeleteJual extends AsyncTask<ModelJual,Void,Void> {
        private JualDao jualDao;

        public DeleteJual(JualDao jualDao) {
            this.jualDao = jualDao;
        }

        @Override
        protected Void doInBackground(ModelJual... lists) {

            jualDao.delete(lists[0]);
            return null;
        }
    }

    private static class InsertJual extends AsyncTask<ModelJual,Void,Void> {
        private JualDao jualDao;

        public InsertJual(JualDao jualDao) {
            this.jualDao = jualDao;
        }

        @Override
        protected Void doInBackground(ModelJual... lists) {

            jualDao.insert(lists[0]);
            return null;
        }
    }

    private static class InsertJualAll extends AsyncTask<List<ModelJual>,Void,Void> {
        private JualDao jualDao;
        private boolean truncate;

        public InsertJualAll(JualDao jualDao, boolean truncate) {
            this.jualDao = jualDao;
            this.truncate = truncate;
        }

        @Override
        protected Void doInBackground(List<ModelJual>... lists) {
            if(truncate){
                jualDao.deleteAll();
            }
            jualDao.insertAll(lists[0]);
            return null;
        }
    }


    public void update(ModelJual modelJual){
        new UpdateJual(jualDao).execute(modelJual);
    }


    private static class UpdateJual extends AsyncTask<ModelJual,Void,Void>{

        private JualDao jualDao;

        public UpdateJual(JualDao jualDao) {
            this.jualDao = jualDao;
        }

        @Override
        protected Void doInBackground(ModelJual... modelJuals) {
            jualDao.update(modelJuals[0]);
            return null;
        }
    }


}