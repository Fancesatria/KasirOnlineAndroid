package com.example.authapp.Database.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.authapp.Database.Dao.DetailJualDao;
import com.example.authapp.Database.KasirDatabase;
import com.example.authapp.Model.ModelDetailJual;
import com.example.authapp.Model.ModelJual;

import java.util.ArrayList;
import java.util.List;

public class DetailJualRepository {
    public DetailJualDao detailJualDao;
    private LiveData<List<ModelDetailJual>> allDetailJual;
    private KasirDatabase kasirDatabase;

    public DetailJualRepository(Application application){
        kasirDatabase = KasirDatabase.getInstance(application);
        detailJualDao = kasirDatabase.detailJualDao();
        allDetailJual = detailJualDao.getAllJual();
    }

    public LiveData<List<ModelDetailJual>> getAllDetailJual() {
        return allDetailJual;
    }

    public void insertAll(List<ModelDetailJual> data, boolean truncate){
        new InsertDetailJualAll(detailJualDao,truncate).execute(data);
    }
    public void insertAll(List<ModelDetailJual> data, Long modelJual){
        new InsertDetailJualAll(detailJualDao,false,modelJual).execute(data);
    }

    public void insert(ModelDetailJual detailJual){
        new InsertDetailJual(detailJualDao).execute(detailJual);
    }

    public void delete(ModelDetailJual detailJual){
        new DeleteDetailJual(detailJualDao).execute(detailJual);
    }

    private static class DeleteDetailJual extends AsyncTask<ModelDetailJual,Void,Void> {
        private DetailJualDao detailJualDao;

        public DeleteDetailJual(DetailJualDao detailJualDao) {
            this.detailJualDao = detailJualDao;
        }

        @Override
        protected Void doInBackground(ModelDetailJual... lists) {

            detailJualDao.delete(lists[0]);
            return null;
        }
    }

    private static class InsertDetailJual extends AsyncTask<ModelDetailJual,Void,Void> {
        private DetailJualDao detailJualDao;

        public InsertDetailJual(DetailJualDao detailJualDao) {
            this.detailJualDao = detailJualDao;
        }

        @Override
        protected Void doInBackground(ModelDetailJual... lists) {

            detailJualDao.insert(lists[0]);
            return null;
        }
    }

    private static class InsertDetailJualAll extends AsyncTask<List<ModelDetailJual>,Void,Void> {
        private DetailJualDao detailJualDao;
        private boolean truncate;

        private Long modelJual=null;

        public InsertDetailJualAll(DetailJualDao detailJualDao, boolean truncate, Long modelJual) {
            this.detailJualDao = detailJualDao;
            this.truncate = truncate;
            this.modelJual = modelJual;
        }

        public InsertDetailJualAll(DetailJualDao detailJualDao, boolean truncate) {
            this.detailJualDao = detailJualDao;
            this.truncate = truncate;
        }

        @Override
        protected Void doInBackground(List<ModelDetailJual>... lists) {
            if(truncate){
                detailJualDao.deleteAll();
            }
            List<ModelDetailJual> data = new ArrayList<>();
            for (
                    ModelDetailJual detail : lists[0]
            ){
                if (modelJual != null){
                    detail.setIdjual(modelJual.intValue());
                }
                data.add(detail);
            }
            detailJualDao.insertAll(data);
            return null;
        }
    }


    public void update(ModelDetailJual detailJual){
        new UpdateDetailJual(detailJualDao).execute(detailJual);
    }

    private static class UpdateDetailJual extends AsyncTask<ModelDetailJual,Void,Void>
    {
        private DetailJualDao detailJualDao;

        public UpdateDetailJual(DetailJualDao detailJualDao) {
            this.detailJualDao = detailJualDao;
        }

        @Override
        protected Void doInBackground(ModelDetailJual... modelDetailJuals) {
            detailJualDao.update(modelDetailJuals[0]);
            return null;
        }
    }


}