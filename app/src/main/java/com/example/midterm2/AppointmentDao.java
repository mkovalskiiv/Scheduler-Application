package com.example.midterm2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AppointmentDao {

    @Query("SELECT * from appointment_info ORDER BY appointmentid ASC")
    LiveData<List<Appointment>> getAllAppointments();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Appointment appointment);

    @Query("DELETE FROM appointment_info")
    void deleteAll();

    @Query("DELETE FROM appointment_info WHERE appointmentid = :appointmentid")
    abstract void deleteAppointment2(Integer appointmentid);

    @Delete
    void deleteAppointment(Appointment appointment);
}

