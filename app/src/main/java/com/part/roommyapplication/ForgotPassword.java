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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.part.roommyapplication.Login.LoGin;
import com.part.roommyapplication.Registration.VerifyOTP;


public class ForgotPassword extends Fragment {

    TextView tvBckToSignIN;
    Button btnForgotPwd;
    public static FragmentManager fragmentManager;

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
        btnForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager=getFragmentManager();
                VerifyOTP verifyOTP=new VerifyOTP();
                if(savedInstanceState != null){
                    return;
                }
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.fragment_container,verifyOTP,null);
                fragmentTransaction.commit();
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
}