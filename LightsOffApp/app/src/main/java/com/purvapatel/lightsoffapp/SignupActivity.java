package com.purvapatel.lightsoffapp;

/**
 * Created by purvapatel on 4/14/17.
 */


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.purvapatel.lightsoffapp.Supporting_Files.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    private EditText _firstnameText;
    private EditText _lastnameText;
    private EditText _emailText;
    private EditText _passwordText;
    private EditText _address;
    private EditText _confirmpasswordText;
    private Button _signupButton;
    private TextView _loginLink;
    String BASE_URL = "https://packers-backend.herokuapp.com";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        _firstnameText = (EditText) findViewById(R.id.input_firstname);
        _lastnameText = (EditText) findViewById(R.id.input_lastname);
        _emailText = (EditText) findViewById(R.id.input_email);
        _passwordText = (EditText) findViewById(R.id.input_password);
        _address = (EditText) findViewById(R.id.input_address);
        _confirmpasswordText = (EditText) findViewById(R.id.input_confirmpassword);
        _signupButton = (Button) findViewById(R.id.btn_signup);
        _loginLink = (TextView) findViewById(R.id.link_login);

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "By Product failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String firstname = _firstnameText.getText().toString();
        String lastname = _lastnameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String confirmpassword = _confirmpasswordText.getText().toString();
        String address = _address.getText().toString();

        //validation for first name
        if (firstname.isEmpty() || firstname.length() < 3) {
            _firstnameText.setError("Please enter at least 3 characters");
            valid = false;
        } else {
            _firstnameText.setError(null);
        }

        //validation for last name
        if (lastname.isEmpty() || lastname.length() < 3) {
            _lastnameText.setError("Please enter at least 3 characters");
            valid = false;
        } else {
            _lastnameText.setError(null);
        }

        //validation for address
        if (address.isEmpty()) {
            _address.setError("Please enter an address");
            valid = false;
        } else {
            _address.setError(null);
        }

        //validation for email address
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Please enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        //validation for password
        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            if(password.length() < 4)
                _passwordText.setError("Length should be > 4");
            else if(password.length() > 10)
                _passwordText.setError("Length should be < 10");
            else
                _passwordText.setError("Between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        //validation for confirm password
        if (confirmpassword.isEmpty() || confirmpassword.length() < 4 || confirmpassword.length() > 10) {
            _confirmpasswordText.setError("Between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _confirmpasswordText.setError(null);
        }

        //validation for same password and confirm password
        if(!password.equals(confirmpassword))
        {
            _confirmpasswordText.setError("Password do not match");
            valid = false;
        } else {
            _confirmpasswordText.setError(null);
        }

        return valid;
    }


    //handles onclik event of Create Account Button
    public void onBuyProduct(View v){

        if (!validate()) {
            onSignupFailed();
            return;
        }

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL) //Setting the Root URL
                .build();

        AppConfig.signup api = adapter.create(AppConfig.signup.class);
        api.buyproduct(
                _firstnameText.getText().toString(),
                _lastnameText.getText().toString(),
                _address.getText().toString(),
                _emailText.getText().toString(),
                _passwordText.getText().toString(),
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {

                        try {

                            BufferedReader reader = new BufferedReader(new InputStreamReader(result.getBody().in()));
                            String resp;
                            resp = reader.readLine();
                            Log.d("success", "" + resp);

                            JSONObject jObj = new JSONObject(resp);
                            int success = jObj.getInt("success");

                            if(success == 1){
                                Toast.makeText(getApplicationContext(), "Buy Product Successful, Please Log in", Toast.LENGTH_SHORT).show();;
                                Intent intent = new Intent();
                                intent.setClass(SignupActivity.this, LoginActivity.class);
                                startActivity(intent);
                            } else{
                                Toast.makeText(getApplicationContext(), "Buy Product Fail", Toast.LENGTH_SHORT).show();
                            }


                        } catch (IOException e) {
                            Log.d("Exception", e.toString());
                        } catch (JSONException e) {
                            Log.d("JsonException", e.toString());
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(SignupActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}
