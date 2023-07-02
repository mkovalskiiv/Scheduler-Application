package com.example.midterm2;


import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AppointmentRepository {
    private AppointmentDao mAppointmentDao;
    private LiveData<List<Appointment>> mAllAppointments;

    AppointmentRepository(Application application) {
        AppointmentRoomDatabase db = AppointmentRoomDatabase.getDatabase(application);
        mAppointmentDao = db.appointmentDao();
        mAllAppointments = mAppointmentDao.getAllAppointments();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Appointment>> getAllAppointments() {
        return mAllAppointments;
    }

    public void deleteAll(){
        new deleteAsyncTask(mAppointmentDao).execute();
    }

    // You must call this on a non-UI thread or your app will crash.
    // Like this, Room ensures that you're not doing any long running operations on the main
    // thread, blocking the UI.
    public void insert (Appointment appointment) { new insertAsyncTask(mAppointmentDao).execute(appointment); }

    public void deleteSingleAppointment (Appointment appointment) {
        new deleteSingleAsyncTask(mAppointmentDao).execute(appointment);
    }

    private static class deleteSingleAsyncTask extends AsyncTask<Appointment, Void, Void> {

        private AppointmentDao mAsyncTaskDao;

        deleteSingleAsyncTask(AppointmentDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Appointment... params) {
            mAsyncTaskDao.deleteAppointment(params[0]);
            return null;
        }
    }

    public void deleteSingleAppointment2 (Appointment appointment) {
        new deleteSingleAsyncTask2(mAppointmentDao).execute(appointment);
    }

    private static class deleteSingleAsyncTask2 extends AsyncTask<Appointment, Void, Void> {

        private AppointmentDao mAsyncTaskDao;

        deleteSingleAsyncTask2(AppointmentDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Appointment... params) {
            mAsyncTaskDao.deleteAppointment2(1);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Void, Void, Void> {

        private AppointmentDao mAsyncTaskDao;

        deleteAsyncTask(AppointmentDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... params) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }


    private static class insertAsyncTask extends AsyncTask<Appointment, Void, Void> {

        private AppointmentDao mAsyncTaskDao;

        insertAsyncTask(AppointmentDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Appointment... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}//AppointmentRepository