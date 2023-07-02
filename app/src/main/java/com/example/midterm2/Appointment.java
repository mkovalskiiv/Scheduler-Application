package com.example.midterm2;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName ="appointment_info")
public class Appointment {
    @PrimaryKey(autoGenerate=true)
    @NonNull
    private int appointmentid;

    @ColumnInfo(name = "appointment_name")
    private String appointmentName;

    @ColumnInfo(name = "appointment_date")
    private String appointmentDate;

    public int getAppointmentid() {
        return appointmentid;
    }
    public void setAppointmentid(int appointmentid) {
        this.appointmentid = appointmentid;
    }

    public String getAppointmentName() { return appointmentName; }
    public void setAppointmentName(String appointmentName) { this.appointmentName = appointmentName; }

    public String getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(String appointmentDate) { this.appointmentDate = appointmentDate; }

}//Appointment
