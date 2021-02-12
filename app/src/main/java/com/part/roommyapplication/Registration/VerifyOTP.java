package com.part.roommyapplication.Registration;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
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
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.part.roommyapplication.R;


public class VerifyOTP extends Fragment {
    TextView txtResend;
    Button btnVerify;
    public FirebaseAuth mAuth = FirebaseAuth.getInstance();
    CardView cardOTP, cardWaiting;
    String  verificationID;
    String otp="";

    public VerifyOTP() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_verify_o_t_p, container, false);
        // Inflate the layout for this fragment

        final EditText code1 = v.findViewById(R.id.firstNumber);
        final EditText code2 = v.findViewById(R.id.secondNumber);
        final EditText code3 = v.findViewById(R.id.thirdNumber);
        final EditText code4 = v.findViewById(R.id.fourthNumber);
        final EditText code5 = v.findViewById(R.id.fifthNumber);
        final EditText code6 = v.findViewById(R.id.sixthNumber);
        btnVerify = v.findViewById(R.id.btn_verify);
        cardOTP = v.findViewById(R.id.cardOTP);
        cardWaiting = v.findViewById(R.id.cardWaiting);

        EditText[] otpTextViews = {code1, code2, code3, code4, code5, code6};
        txtResend = v.findViewById(R.id.tvResend);
        final EditText txtPhone = v.findViewById(R.id.etPhoneEdit);
        Bundle bundle = this.getArguments();
        String phoneNo = bundle.getString("Phone");
        Toast.makeText(getContext(), phoneNo, Toast.LENGTH_SHORT).show();
        Log.d("Phone Number", phoneNo);
        //txtPhone.setText(phoneNo);
       // sendVerificationCodeToUser(phoneNo);
        String text = "You can generate new code Resend";
        SpannableString sResend = new SpannableString(text);
        ForegroundColorSpan fcsBlue = new ForegroundColorSpan(Color.BLUE);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Toast.makeText(getActivity(), "Resend", Toast.LENGTH_SHORT).show();
            }
        };
        sResend.setSpan(fcsBlue, 26, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sResend.setSpan(clickableSpan, 26, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtResend.setText(sResend);
        txtResend.setMovementMethod(LinkMovementMethod.getInstance());

        /* Assign the TextViews in the array in the order in which you want to shift focus */
        for (EditText currTextView : otpTextViews) {
            currTextView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    nextTextView().requestFocus();
                }

                @Override
                public void afterTextChanged(Editable s) {
                }

                public TextView nextTextView() {
                    int i;
                    for (i = 0; i < otpTextViews.length - 1; i++) {
                        if (otpTextViews[i] == currTextView)
                            return otpTextViews[i + 1];
                    }
                    return otpTextViews[i];
                }
            });
        }

        btnVerify.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
//                for (EditText currText : otpTextViews) {
//                    otp += currText.getText().toString();
//                }
//
//                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, otp);
//                Log.d("verification Code 1 ",credential.toString());
//                verifyUser(credential);
//                //verfication
                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Vitalinfo vitalinfo = new Vitalinfo();
                fragmentTransaction.replace(R.id.fragment_container, vitalinfo, null);
                fragmentTransaction.commit();

            }
        });

        return v;
    }

    //Phone Number Verification
//    private void sendVerificationCodeToUser(String phoneNo) {
//        PhoneAuthOptions options =
//                PhoneAuthOptions.newBuilder(mAuth)
//                        .setPhoneNumber("+91"+phoneNo)       // Phone number to verify
//                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//                        .setActivity(getActivity())                 // Activity (for callback binding)
//                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
//                        .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
//    }
//
//    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//        @Override
//        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//
//        }
//
//        @Override
//        public void onVerificationFailed(@NonNull FirebaseException e) {
//
//        }
//
//        @Override
//        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//            super.onCodeSent(s, forceResendingToken);
//            cardWaiting.setVisibility(View.GONE);
//            cardOTP.setVisibility(View.VISIBLE);
//             verificationID=s;
//            Log.d("verification Code",verificationID);
//        }
//    };
//
//
//    //VerifyingOtp
//
//    private void verifyUser(PhoneAuthCredential credential  ) {
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener((Executor) this, task -> {
//                    if (task.isSuccessful()) {
//                        // Sign in success, update UI with the signed-in user's information
//                        Log.d("SignIn Successfull", "signInWithCredential:success");
//
//                    } else {
//                        // Sign in failed, display a message and update the UI
//                        Log.d("Sign In FAil" ,"signInWithCredential:failure", task.getException());
//                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                            // The verification code entered was invalid
//                        }
//                    }
//                });
//    }

}
