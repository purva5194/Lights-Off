package com.purvapatel.lightsoffapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by purvapatel on 4/12/17.
 */

public class Feedback extends Fragment {

    Button submit;
    EditText sunlight, floor, place;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.activity_feedback, container , false);

        submit = (Button) view.findViewById(R.id.btnSubmit);
        sunlight = (EditText) view.findViewById(R.id.feedback_sunlight);
        floor = (EditText) view.findViewById(R.id.feedback_floor);
        place = (EditText)  view.findViewById(R.id.feedback_place);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // save data into database
                //post query

                Fragment fragmentFeedback = new Home();
                FragmentTransaction ft =  getFragmentManager().beginTransaction();
                ft.replace(R.id.content_main,fragmentFeedback);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Survey");
    }
}
