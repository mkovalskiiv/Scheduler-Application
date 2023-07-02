package com.example.midterm2.ui.slideshow;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.midterm2.Appointment;
import com.example.midterm2.AppointmentViewModel;
import com.example.midterm2.R;

import java.util.List;

public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.AppointmentViewHolder>{

    private static List<Appointment> mAppointmentList;
    private final LayoutInflater mInflater;
    private AppointmentViewModel mAppointmentViewModel;



    class AppointmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView appointmentIdTextView;
        public final TextView appointmentNameTextView;
        public final TextView appointmentDateTextView;
        public final RecyclerView appointmentListRecyclerView2;

        final AppointmentListAdapter mAdapter;


        public AppointmentViewHolder(View itemView, AppointmentListAdapter adapter) {
            super(itemView);
            appointmentIdTextView = itemView.findViewById(R.id.id);
            appointmentNameTextView = itemView.findViewById(R.id.title);
            appointmentDateTextView = itemView.findViewById(R.id.date);
            appointmentListRecyclerView2 = itemView.findViewById(R.id.recyclerAppointmentList);

            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Get the position of the item that was clicked.
            int mPosition = getLayoutPosition();

            // Use that to access the affected item in mWordList.
            Appointment element = mAppointmentList.get(mPosition);
            // Change the word in the mWordList.


        }

    }

    public static void setAppointments(List<Appointment> appointmentList){
        mAppointmentList = appointmentList;
        //notifyDataSetChanged();
    }

    public AppointmentListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public AppointmentListAdapter.AppointmentViewHolder onCreateViewHolder(ViewGroup parent,
                                                               int viewType) {
        // Inflate an item view.
        View mItemView = mInflater.inflate(
                R.layout.appointmentrecycler_card, parent, false);
        return new AppointmentViewHolder(mItemView, this);
    }


    @Override
    public void onBindViewHolder(AppointmentListAdapter.AppointmentViewHolder holder,
                                 int position) {
        // Retrieve the data for that position.
        Appointment mCurrent = mAppointmentList.get(position);
        // Add the data to the view holder.
        holder.appointmentIdTextView.setText(mCurrent.getAppointmentid()+"");
        holder.appointmentNameTextView.setText(mCurrent.getAppointmentName());
        holder.appointmentDateTextView.setText(mCurrent.getAppointmentDate());

    }


    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        if(mAppointmentList ==null){
            return 0;
        }
        return mAppointmentList.size();
    }
}//AppointmentListAdapter