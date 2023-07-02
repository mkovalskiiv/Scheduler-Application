package com.example.midterm2.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.midterm2.MainActivity;
import com.example.midterm2.R;
import com.example.midterm2.databinding.FragmentHomeBinding;
import com.example.midterm2.ui.slideshow.SlideshowFragment;
import com.example.midterm2.ui.todo.TodoActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        TextView numTodos = (TextView) root.findViewById(R.id.txtHomeNumTODO);  // find number of todos and
        setNumTODOs(getActivity());
        numTodos.setText(getNumTODOs(getActivity()));                           // display it (from file)

        TextView numApt = (TextView) root.findViewById(R.id.txtHomeNumAppointment);     // find number of appointments and
        setNumApts(getActivity());
        numApt.setText(getNumApts(getActivity()));                                      // display it (from file)

        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
        //    @Override
        //    public void onChanged(@Nullable String s) {
        //        textView.setText(s);
        //    }
        //});
        return root;
    }//onCreateView

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private String getNumTODOs(Context context) {
        String numTODOs = "";    //get num todos

        try {
            File file = new File(getActivity().getFilesDir(), "text");
            File file2read = new File(file, "todos.txt");
            InputStream inputStream = new FileInputStream(file2read);
            if ( inputStream != null ) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                numTODOs = bufferedReader.readLine();
                inputStream.close();
            }//if
        } catch (FileNotFoundException e) {
            Log.e("home fragment", "File does not exist: " + e.toString());
        } catch (IOException e) {
            Log.e("home fragment", "Cannot read file: " + e.toString());
        }//try-catch

        return numTODOs;
    }//getNumTODOs

    private String setNumTODOs(Context context) {
        String numTODOs = String.valueOf(TodoActivity.todoArray.size());

        File file = new File(getActivity().getFilesDir(), "text");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            File file2write = new File(file, "todos.txt");
            file2write.delete();
            file2write.createNewFile();
            FileWriter writer = new FileWriter(file2write);
            writer.append(numTODOs);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            Log.e("home fragment", "File does not exist: " + e.toString());
        } catch (IOException e) {
            Log.e("home fragment", "Cannot read file: " + e.toString());
        }//try-catch

        return numTODOs;
    }//getNumTODOs

    private String getNumApts(Context context) {
        String numApts = "";    //get num apts

        try {
            File file = new File(getActivity().getFilesDir(), "text");
            File file2read = new File(file, "apts.txt");
            InputStream inputStream = new FileInputStream(file2read);
            if ( inputStream != null ) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                numApts = bufferedReader.readLine();
                inputStream.close();
            }//if
        } catch (FileNotFoundException e) {
            Log.e("home fragment", "File does not exist: " + e.toString());
        } catch (IOException e) {
            Log.e("home fragment", "Cannot read file: " + e.toString());
        }//try-catch

        return numApts;
    }//getNumApts

    private String setNumApts(Context context) {
        String numApts = String.valueOf(SlideshowFragment.appointmentArray.size());

        File file = new File(getActivity().getFilesDir(), "text");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            File file2write = new File(file, "apts.txt");
            file2write.delete();
            file2write.createNewFile();
            FileWriter writer = new FileWriter(file2write);
            writer.append(numApts);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            Log.e("home fragment", "File does not exist: " + e.toString());
        } catch (IOException e) {
            Log.e("home fragment", "Cannot read file: " + e.toString());
        }//try-catch

        return numApts;
    }//setNumApts

}//HomeFragment