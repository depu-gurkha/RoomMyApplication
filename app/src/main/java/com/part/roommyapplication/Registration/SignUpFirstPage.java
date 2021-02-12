package com.part.roommyapplication.Registration;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputLayout;
import com.part.roommyapplication.Login.LoGin;
import com.part.roommyapplication.R;
import com.part.roommyapplication.config.RequestSingletonVolley;
import com.part.roommyapplication.config.SharedPrefManager;
import com.part.roommyapplication.config.URLs;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SignUpFirstPage extends Fragment {

    TextView tvSignin;
    TextInputLayout lFirstName, lLastName, lPhone, lEmail;
    EditText etEmail, etFstName, etLstName, etPhone;
    Button btnSignUp, btnGoogle, btnFacebook;
    public FragmentManager fragmentManager;

    public SignUpFirstPage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_sign_up_first_page, container, false);
        tvSignin = v.findViewById(R.id.tvsignin);
        lFirstName = v.findViewById(R.id.lFname);
        lLastName = v.findViewById(R.id.lLname);
        lPhone = v.findViewById(R.id.lPhone);
        lEmail = v.findViewById(R.id.lEmail);
        etEmail = v.findViewById(R.id.email);
        etFstName = v.findViewById(R.id.fName);
        etLstName = v.findViewById(R.id.lName);
        etPhone = v.findViewById(R.id.phone);
        btnSignUp = v.findViewById(R.id.btn_signUp);

        //For already registred along with on click to take back to the signin page
        String text = "Already Registered? Sign In";
        SpannableString sSignIn = new SpannableString(text);
        ForegroundColorSpan fcsBlue = new ForegroundColorSpan(Color.BLUE);

        //Clicking the blue colored with take to sign in Fragment
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                fragmentManager = getFragmentManager();
                if (savedInstanceState != null) {
                    Log.d("Inside first if", fragmentManager.toString());
                    return;
                }
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                LoGin loGin = new LoGin();
                fragmentTransaction.replace(R.id.fragment_container, loGin, null);
                fragmentTransaction.commit();

                Toast.makeText(getActivity(), "SignIn", Toast.LENGTH_SHORT).show();
            }
        };
        sSignIn.setSpan(fcsBlue, 20, 27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sSignIn.setSpan(clickableSpan, 20, 27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvSignin.setText(sSignIn);
        tvSignin.setMovementMethod(LinkMovementMethod.getInstance());

        //Onclick to navigate to the next signup page
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userRegistration();
            }
        });
        return v;
    }


    //Validate First name
    private boolean validateFName() {

        String strname = lFirstName.getEditText().getText().toString().trim();
        if (strname.isEmpty()) {
            lFirstName.setErrorIconDrawable(R.drawable.ic_baseline_error_outline_24);
            lFirstName.setError("Please enter a valid username");
            return false;
        } else {
            lFirstName.setError(null);
            return true;
        }
    }

    //Validate Last name
    private boolean validateLastName() {

        String strname = lLastName.getEditText().getText().toString().trim();
        if (strname.isEmpty()) {
            lFirstName.setErrorIconDrawable(R.drawable.ic_baseline_error_outline_24);
            lFirstName.setError("Please enter a valid username");
            return false;
        } else {
            lFirstName.setError(null);
            return true;
        }
    }
    //password Validate
//    private boolean validatePassword(){
//        String check="^[0-9]{8,}$";
//        String strpass=lPass.getEditText().getText().toString().trim();
//        if(strpass.isEmpty()){
//            lPass.setErrorIconDrawable(R.drawable.ic_baseline_error_outline_24);
//            lPass.setError("Field cannot be empty");
//            return false;
//        }
//        else if(!strpass.matches(check)){
//            lPass.setErrorIconDrawable(R.drawable.ic_baseline_error_outline_24);
//            lPass.setError("Please enter a validate Password");
//            return false;
//        }else
//        {
//            lPass.setError(null);
//            return true;
//        }
//    }

    //function For User Registration
    private void userRegistration() {

        final String firstName = etFstName.getText().toString().trim();
        final String lastName = etLstName.getText().toString().trim();
        final String email = etEmail.getText().toString().trim();
        final String phone = etPhone.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.userRegisterUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            Log.d("Server Response", obj.toString());
                            //if no error in response
                            if (obj.getInt("success") == 1) {
                                Toast.makeText(getActivity().getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                VerifyOTP verifyOTP = new VerifyOTP();
                                fragmentTransaction.replace(R.id.fragment_container, verifyOTP, null);
                                fragmentTransaction.commit();
                                Bundle bundle = new Bundle();
                                bundle.putString("Phone", phone);
                                verifyOTP.setArguments(bundle);
                                int userId=obj.getInt("userID");
                                Log.d("userId",String.valueOf(userId));
                                SharedPrefManager.getInstance(getActivity().getApplicationContext()).setUserId(userId);
                            } else {
                                Toast.makeText(getActivity().getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("firstName", firstName);
                params.put("lastName", lastName);
                params.put("email", email);
                params.put("phone", phone);
                params.put("password", "12345678");
                return params;
            }
        };

        RequestSingletonVolley.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}

