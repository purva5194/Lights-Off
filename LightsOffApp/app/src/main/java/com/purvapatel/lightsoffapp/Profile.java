package com.purvapatel.lightsoffapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by purvapatel on 4/12/17.
 */

public class Profile extends Fragment {

    private Button update;
    private EditText _firstnameText;
    private EditText _lastnameText;
    private EditText _emailText;
    private EditText _passwordText;
    private EditText _confirmpasswordText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.activity_profile, container , false);

        update = (Button) view.findViewById(R.id.button);
        _firstnameText = (EditText) view.findViewById(R.id.profile_firstname);
        _lastnameText = (EditText) view.findViewById(R.id.profile_lastname);
        _emailText = (EditText) view.findViewById(R.id.profile_email);
        _passwordText = (EditText) view.findViewById(R.id.profile_password);
        _confirmpasswordText = (EditText) view.findViewById(R.id.profile_confirmpassword);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // update query

                getActivity().finish();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Profile");
    }
}
