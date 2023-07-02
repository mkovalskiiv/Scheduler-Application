package com.example.midterm2.ui.slideshow;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midterm2.AlarmBroadcastReceiver;
import com.example.midterm2.Appointment;
import com.example.midterm2.AppointmentViewModel;
import com.example.midterm2.R;
import com.example.midterm2.databinding.FragmentSlideshowBinding;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private AppointmentViewModel mAppointmentViewModel;

    public static ArrayList<String> appointmentArray = new ArrayList<String>();     // initialize appointmentArray ArrayList

    private SlideshowViewModel slideshowViewModel;
    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView appointmentRecycler = (RecyclerView) root.findViewById(R.id.recyclerAppointmentList);
        appointmentRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        //EDITED AppointmentAdapter appointmentAdapter = new AppointmentAdapter(getActivity(), appointmentArray);
        AppointmentListAdapter appointmentAdapter = new AppointmentListAdapter(getActivity());

        appointmentRecycler.setAdapter(appointmentAdapter);
        appointmentRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAppointmentViewModel = new ViewModelProvider(getActivity()).get(AppointmentViewModel.class);

        mAppointmentViewModel.getAllAppointments().observe(getActivity(), new Observer<List<Appointment>>() {
            @Override
            public void onChanged(List<Appointment> appointmentList) {
                AppointmentListAdapter.setAppointments(appointmentList);
            }
        });

        Button buttonExample = (Button) root.findViewById(R.id.btnExample);         //
        buttonExample.setOnClickListener(new View.OnClickListener(){                //
            @Override                                                               // handle button press from
            public void onClick (View v) {                                          // inside fragment, add an
                appointmentArray.add("Doctor Appointment\n12/16/22 1:00PM");        // item for testing
                Appointment apt = new Appointment();
                apt.setAppointmentName("Doctor Appointment");
                apt.setAppointmentDate("12/16/22 1:00PM");
                mAppointmentViewModel.insert(apt);

                appointmentArray.add("PTA Meeting\n6/22/22 10:45AM");               //
                apt = new Appointment();
                apt.setAppointmentName("PTA Meeting");
                apt.setAppointmentDate("6/22/22 10:45AM");
                mAppointmentViewModel.insert(apt);

                appointmentArray.add("Orthodontist Appointment\n9/02/22 2:30PM");   //
                apt = new Appointment();
                apt.setAppointmentName("Final Exam");
                apt.setAppointmentDate("9/02/22 2:30PM");
                mAppointmentViewModel.insert(apt);

                appointmentAdapter.notifyDataSetChanged();                          //
            }//onClick                                                              //
        });//buttonExample                                                          //

        Button buttonClear = (Button) root.findViewById(R.id.btnClearAppointments);                         //
        buttonClear.setOnClickListener(new View.OnClickListener(){                                          //
            @Override                                                                                       // handle button press from
            public void onClick (View v) {                                                                  // inside fragment, create alert dialog
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();                  //
                alertDialog.setTitle("Warning");                                                            //
                alertDialog.setMessage("This will clear every Appointment entry. Do you wish to proceed?"); //
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",                              //
                        new DialogInterface.OnClickListener() {                                             //
                            public void onClick(DialogInterface dialog, int which) {                        //
                                appointmentArray.clear();  // clear all entries from list                   //
                                mAppointmentViewModel.deleteAll();  // clear all entries from sqlite database
                                appointmentAdapter.notifyDataSetChanged(); // update displayed items        //
                                dialog.dismiss();   // close alert dialog                                   //
                            }//onClick                                                                      //
                        });                                                                                 //
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",                               //
                        new DialogInterface.OnClickListener() {                                             //
                            public void onClick(DialogInterface dialog, int which) {                        //
                                dialog.dismiss();   // close alert dialog                                   //
                            }//onClick                                                                      //
                        });                                                                                 //
                alertDialog.show(); //show dialog                                                           //
            }//onClick                                                                                      //
        });//buttonClear                                                                                    //

        Button buttonNewAppointment = (Button) root.findViewById(R.id.fabAddNewAppointment);
        buttonNewAppointment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());   // initialize alertdialog
                builder.setTitle("Add New To-Do");  // set title for alert dialog
                builder.setView(LayoutInflater.from(getActivity()).inflate(R.layout.aptdialoglayout,null));
                //AlertDialog alert = builder.create();


                final EditText input = (EditText) new EditText(getActivity());//alert.findViewById(R.id.input);
                final DatePicker appointmentDatePicker2 = (DatePicker) new DatePicker(getActivity());//alert.findViewById(R.id.appointmentDatePicker);
                final TimePicker appointmentTimePicker2 = (TimePicker) new TimePicker(getActivity());//alert.findViewById(R.id.appointmentTimePicker);

                LinearLayout layout = new LinearLayout(getActivity());  // initialize layout for alert dialog
                layout.setOrientation(LinearLayout.VERTICAL);   // set orientation of layout to vertical

                layout.addView(input);
                layout.addView(appointmentDatePicker2);
                layout.addView(appointmentTimePicker2);
                builder.setView(layout);


                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Integer aptMonth = appointmentDatePicker2.getMonth();
                        Integer aptMonthNonZero = aptMonth+1;
                        Integer aptDay = appointmentDatePicker2.getDayOfMonth();
                        Integer aptYear = appointmentDatePicker2.getYear();
                        Integer aptHour = appointmentTimePicker2.getCurrentHour();
                        Integer aptMinute = appointmentTimePicker2.getCurrentMinute();

                        String appointmentToAdd =   input.getText().toString() +        //
                                                    "\n" +                              //
                                                    aptMonthNonZero.toString() +        //
                                                    "/" +                               // prepare string from input
                                                    aptDay.toString() +                 // values in views
                                                    "/" +                               //
                                                    aptYear.toString() +                //
                                                    " " +                               //
                                                    timeFormatter(aptHour, aptMinute);  //

                        //add to sqlite database
                        Appointment apt = new Appointment();
                        apt.setAppointmentName(input.getText().toString());
                        String aptDate = aptMonthNonZero.toString() + "/" + aptDay.toString() + "/" + aptYear.toString() + " " + timeFormatter(aptHour, aptMinute);
                        apt.setAppointmentDate(aptDate);
                        mAppointmentViewModel.insert(apt);


                        ////////////////////////////
                        // Start Notification Code
                        // now works even when app is closed :)
                        ////////////////////////////

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.clear();
                        calendar.set(aptYear,aptMonth,aptDay,aptHour,aptMinute);
                        //calendar.set(2022,3,25,14,27);  //DEBUG  (note: Months start at zero. Jan = 0, Feb = 1, etc.)
                        Toast.makeText(getActivity(), "Appointment has been set.", Toast.LENGTH_LONG).show();  //DEBUG


                        Intent intent = new Intent(getActivity(), AlarmBroadcastReceiver.class);
                        String intentName = appointmentToAdd;
                        intent.putExtra("intentName", intentName);
                        PendingIntent pendingint = PendingIntent.getBroadcast(getActivity(), 1253, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                        AlarmManager alarmmgr = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                        alarmmgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingint);

                        /////////////////////////////
                        // End of Notification Code
                        /////////////////////////////


                        appointmentArray.add(appointmentToAdd);  // add text field to array
                        appointmentAdapter.notifyDataSetChanged();     // update appointmentAdapter with new info
                    }//onClick
                });//setPositiveButton

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }//onClick
                });//setNegativeButton

                builder.show();
            }//onClick
        });//buttonNewAppointment



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public String timeFormatter(int hour,int minute) {
        Time time = new Time(hour, minute, 0);  // set seconds to 0
        Format timeFormat = new SimpleDateFormat("h:mma");
        return timeFormat.format(time);
    }
}