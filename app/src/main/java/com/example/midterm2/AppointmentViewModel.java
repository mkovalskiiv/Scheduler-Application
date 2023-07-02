package com.example.midterm2;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AppointmentViewModel extends AndroidViewModel {
    private AppointmentRepository mRepository;

    private LiveData<List<Appointment>> mAllAppointments;

    public AppointmentViewModel (Application application) {
        super(application);
        mRepository = new AppointmentRepository(application);
        mAllAppointments = mRepository.getAllAppointments();
    }

    public LiveData<List<Appointment>> getAllAppointments() { return mAllAppointments; }

    public void deleteAll(){
        mRepository.deleteAll();
    }

    public void deleteSingleAppointment(Appointment appointment){ mRepository.deleteSingleAppointment(appointment); }

    public void insert(Appointment appointment) { mRepository.insert(appointment); }

}
