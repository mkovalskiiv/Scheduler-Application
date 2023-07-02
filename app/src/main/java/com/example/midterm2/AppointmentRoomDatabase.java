package com.example.midterm2;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Appointment.class}, version = 2,  exportSchema = false)
public abstract class AppointmentRoomDatabase extends RoomDatabase {
    public abstract AppointmentDao appointmentDao();

    private static AppointmentRoomDatabase INSTANCE;

    static AppointmentRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppointmentRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppointmentRoomDatabase.class, "appointment_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(appointmentRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     */
    private static RoomDatabase.Callback appointmentRoomDatabaseCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);

            //Enable the line beflow if we would like to auto generate some students
            //when launching the program
            //new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
     * Populate the database in the background.
     * If you want to start with more words, just add them.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppointmentDao mDao;
        int numberofAppointments = 3;

        PopulateDbAsync(AppointmentRoomDatabase db) {
            mDao = db.appointmentDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            // mDao.deleteAll();

            for( int i = 0; i < numberofAppointments; i++) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentName("Appointment Name"+i);
                appointment.setAppointmentDate("Appointment Date"+i);
                mDao.insert(appointment);
            }
            return null;
        }
    }
}//AppointmentRoomDatabase
