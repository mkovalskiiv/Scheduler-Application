package com.example.midterm2.ui.todo;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midterm2.R;

import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.ViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater;

    TodoListAdapter(Context context, List<String> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }//TodoListAdapter

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.todorecycler_row, parent, false);
        return new ViewHolder(view);
    }//onCreateViewHolder

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String todo = mData.get(position);
        holder.myTextView.setText(todo);
        holder.myTextView.setTextColor(Color.parseColor("#FFFFFFFF"));
    }//onBindViewHolder

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }//getItemCount


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.todoName);
            itemView.setOnClickListener(this);
        }//ViewHolder

        @Override
        public void onClick(View view) {
            //TodoActivity.deleteRowConfirmation();
            AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();              //
            alertDialog.setTitle("Warning");                                                            //
            alertDialog.setMessage("Do you wish to delete the selected entry?");                        //
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",                              //
                    new DialogInterface.OnClickListener() {                                             //
                        public void onClick(DialogInterface dialog, int which) {                        //
                            TodoActivity.todoArray.remove(getAdapterPosition()); // remove list tiem    //
                            notifyDataSetChanged(); // update data set                                  //
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
        }//onClick
    }//ViewHolder

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id);
    }//getItem

}//TodoListAdapter
