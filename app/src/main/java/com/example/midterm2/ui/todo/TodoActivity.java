package com.example.midterm2.ui.todo;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.midterm2.R;
import com.example.midterm2.databinding.FragmentGalleryBinding;

import java.util.ArrayList;

public class TodoActivity extends Fragment {

    public static ArrayList<String> todoArray = new ArrayList<String>();     // initialize todoArray ArrayList

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;
    String[] inputPrirityString = {"Normal", "High"};

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //todoArray.add("Test");

        RecyclerView todoRecycler = (RecyclerView) root.findViewById(R.id.recyclerTodoList);
        todoRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        TodoListAdapter todoAdapter = new TodoListAdapter(getActivity(), todoArray);
        todoRecycler.setAdapter(todoAdapter);

        Button buttonTest = (Button) root.findViewById(R.id.btnExample);        //
        buttonTest.setOnClickListener(new View.OnClickListener(){               //
            @Override                                                           // handle button press from
            public void onClick (View v) {                                      // inside fragment, add an
                todoArray.add("Take out the trash");                            // item for testing
                todoArray.add("(!) Deposit check at bank");                     //
                todoArray.add("Finish homework");                               //
                todoAdapter.notifyDataSetChanged();                             //
            }//onClick                                                          //
        });                                                                     //

        Button buttonClear = (Button) root.findViewById(R.id.btnClearTodos);                                     //
        buttonClear.setOnClickListener(new View.OnClickListener(){                                          //
            @Override                                                                                       // handle button press from
            public void onClick (View v) {                                                                  // inside fragment, create alert dialog
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();                  //
                alertDialog.setTitle("Warning");                                                            //
                alertDialog.setMessage("This will clear every To-Do entry. Do you wish to proceed?");       //
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",                              //
                        new DialogInterface.OnClickListener() {                                             //
                            public void onClick(DialogInterface dialog, int which) {                        //
                                todoArray.clear();  // clear all entries from list                          //
                                todoAdapter.notifyDataSetChanged(); // update displayed items               //
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

        Button buttonNewTodo = (Button) root.findViewById(R.id.fabAddNewTodo);
        buttonNewTodo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());   // initialize alertdialog
                builder.setTitle("Add New To-Do");  // set title for alert dialog

                LinearLayout layout = new LinearLayout(getActivity());  // initialize layout for alert dialog
                layout.setOrientation(LinearLayout.VERTICAL);   // set orientation of layout to vertical

                final EditText input = new EditText(getActivity());     // initialize input text field              // initialize
                final TextView inputPriorityLabel = new TextView(getActivity());     // initialize input text field // all
                //final Spinner inputPriority = new Spinner(getActivity());   // initialize spinner                 // views
                final CheckBox inputPriorityCheck = new CheckBox(getActivity());   // initialize checkbox           //

                inputPriorityLabel.setText("Is this to-do a priority item?"); // initialize text
                input.setHint("Type your to-do here");              // in views

                //ArrayAdapter spinAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, inputPrirityString);   // initialize spinner adapter
                //spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // set adapter row layout
                //inputPriority.setAdapter(spinAdapter);  // set spinner adapter to spinner

                layout.addView(input);              //
                layout.addView(inputPriorityLabel); // set alert dialog
                //layout.addView(inputPriority);    // views
                layout.addView(inputPriorityCheck); //
                builder.setView(layout);            //

                builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String todoToAdd = input.getText().toString();      // set todoToAdd as value input by user
                        if (inputPriorityCheck.isChecked()) todoToAdd = "(!) " + input.getText().toString();  // add excl. mark if priority is high
                        todoArray.add(todoToAdd);  // add text field to array
                        todoAdapter.notifyDataSetChanged();     // update todoAdapter with new info
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
        });//buttonNewTodo



        return root;
    }//onCreateView

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }//onDestroyView


}//GalleryFragment