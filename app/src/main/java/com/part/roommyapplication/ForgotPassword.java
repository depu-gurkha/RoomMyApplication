package com.part.roommyapplication;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.part.roommyapplication.Login.LoGin;
import com.part.roommyapplication.Registration.VerifyOTP;
import com.part.roommyapplication.config.RequestSingletonVolley;
import com.part.roommyapplication.config.SharedPrefManager;
import com.part.roommyapplication.config.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ForgotPassword extends Fragment {

    TextView tvBckToSignIN;
    Button btnForgotPwd;
    EditText etEmail;
    String email;
    public static FragmentManager fragmentManager;
    TextView txtMailSent,txtSuccess;
    ProgressBar progressBar;

    public ForgotPassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_forgot_password, container, false);
        tvBckToSignIN=v.findViewById(R.id.tvBckToSignIn);
        btnForgotPwd=v.findViewById(R.id.pwdReset);
        etEmail=v.findViewById(R.id.email);
        txtMailSent=v.findViewById(R.id.txtmailSent);
        progressBar=v.findViewById(R.id.progress_bar);
        txtSuccess=v.findViewById(R.id.success);
        btnForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email=etEmail.getText().toString().trim();
                String check="^([a-zA-Z0-9.]+)@([a-zA-Z]+)\\.([a-zA-Z]+)$";

                if (email.isEmpty() | ! email.matches(check)){
                    Log.d("email",email);
                    etEmail.setError("Invalid Email");
                }
                else{
                    btnForgotPwd.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    forgotPwd();


                }


            }
        });
        String text = "Back to Sign In Click Here";
        SpannableString sBck = new SpannableString(text);
        ForegroundColorSpan fcsBlue = new ForegroundColorSpan(Color.BLUE);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                fragmentManager=getFragmentManager();
                if(savedInstanceState!=null){
                    Log.d("Inside first if",fragmentManager.toString());
                    return;
                }
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                LoGin loGin=new LoGin();
                fragmentTransaction.replace(R.id.fragment_container,loGin,null);
                fragmentTransaction.commit();

                Toast.makeText(getActivity(), "SignUp", Toast.LENGTH_SHORT).show();
            }
        };
        sBck.setSpan(fcsBlue, 16, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sBck.setSpan(clickableSpan, 16, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvBckToSignIN.setText(sBck);
        tvBckToSignIN.setMovementMethod(LinkMovementMethod.getInstance());
        return v;
    }

    public void forgotPwd(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.forgotPassword,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            Log.d("Server Response", obj.toString());
//                            if no error in response
                            if (obj.getInt("success") == 1) {
                                progressBar.setVisibility(View.GONE);
                                btnForgotPwd.setVisibility(View.VISIBLE);
                                txtSuccess.setVisibility(View.VISIBLE);

                                // sendVerificationCodeToUser(phoneNo);
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
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("PhoneNo", "");
                return params;
            }
        };

        RequestSingletonVolley.getInstance(getContext()).addToRequestQueue(stringRequest);
    }
}