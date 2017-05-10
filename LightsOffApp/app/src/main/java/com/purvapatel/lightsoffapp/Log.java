package com.purvapatel.lightsoffapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by purvapatel on 4/12/17.
 */

public class Log extends Fragment {
    EditText logs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.activity_log, container , false);

        logs = (EditText) view.findViewById(R.id.logs);

        logs.setText("Date : 4/12/2017 Light : off\nDate : 4/12/2017 Light : off\nDate : 4/12/2017 Light : off\n" +
                "Date : 4/12/2017 Light : off\nDate : 4/12/2017 Light : off\nDate : 4/12/2017 Light : off\n" +
                "Date : 4/12/2017 Light : off\nDate : 4/12/2017 Light : off\nDate : 4/12/2017 Light : off\n" +
                "Date : 4/12/2017 Light : off\nDate : 4/12/2017 Light : off\nDate : 4/12/2017 Light : off\n" +
                "Date : 4/12/2017 Light : off\nDate : 4/12/2017 Light : off\nDate : 4/12/2017 Light : off\n" +
                "Date : 4/12/2017 Light : off\nDate : 4/12/2017 Light : off\nDate : 4/12/2017 Light : off\n" +
                "Date : 4/12/2017 Light : off\nDate : 4/12/2017 Light : off\nDate : 4/12/2017 Light : off\n" +
                "Date : 4/12/2017 Light : off\nDate : 4/12/2017 Light : off\nDate : 4/12/2017 Light : off\n" +
                "Date : 4/12/2017 Light : off\nDate : 4/12/2017 Light : off\nDate : 4/12/2017 Light : off\n" +
                "Date : 4/12/2017 Light : off\nDate : 4/12/2017 Light : off\nDate : 4/12/2017 Light : off\n" +
                "Date : 4/12/2017 Light : off\nDate : 4/12/2017 Light : off\nDate : 4/12/2017 Light : off\n" +
                "Date : 4/12/2017 Light : off\nDate : 4/12/2017 Light : off\nDate : 4/12/2017 Light : off\n");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Log");
    }
}
