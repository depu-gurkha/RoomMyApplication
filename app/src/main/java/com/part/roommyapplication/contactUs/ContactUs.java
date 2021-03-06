package com.part.roommyapplication.contactUs;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.google.android.material.textfield.TextInputLayout;
import com.part.roommyapplication.R;
import com.part.roommyapplication.config.RequestSingletonVolley;
import com.part.roommyapplication.config.URLs;
import com.part.roommyapplication.library.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ContactUs extends Fragment {

    //Elements
    TextInputLayout lContactName, lContactEmail, lContactSubject, lContactMessage;
    EditText etContactName, etContactEmail, etContactSubject, etContactMessage;
    Button btnSend;
//    LinearLayout lCall,lEmail,lLocation;
    ImageView ivEmail,ivCall,ivLocation;
    TextView tvEmail,tvCall,tvLocation;

    public FragmentManager fragmentManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ContactUs() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact_us, container, false);
        lContactName = v.findViewById(R.id.lCname);
        lContactEmail = v.findViewById(R.id.lCemail);
        lContactSubject = v.findViewById(R.id.lCsubject);
        lContactMessage = v.findViewById(R.id.lCmessage);

        etContactName = v.findViewById(R.id.cName);
        etContactEmail = v.findViewById(R.id.cEmail);
        etContactSubject = v.findViewById(R.id.cSubject);
        etContactMessage = v.findViewById(R.id.cMessage);

        btnSend = v.findViewById(R.id.btn_sendMessage);

//        lEmail = v.findViewById(R.id.l_Email);
//        lCall = v.findViewById(R.id.l_Call);
//        lLocation = v.findViewById(R.id.l_Email);

        ivCall = v.findViewById(R.id.iv_call);
        ivLocation = v.findViewById(R.id.iv_location);
        ivEmail = v.findViewById(R.id.iv_email);

        tvCall = v.findViewById(R.id.tv_call);
        tvLocation = v.findViewById(R.id.tv_location);
        tvEmail = v.findViewById(R.id.tv_email);

        
//        lEmail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getContext(), "EMAIL CLICKED", Toast.LENGTH_LONG).show();
//            }
//        });


        SpannableString sCall = new SpannableString("(03637) 235242");
        SpannableString sEmail = new SpannableString("rkmhikaionline@gmail.com");
        SpannableString sLocation = new SpannableString("Sohra, Pincode: 793108");

        ForegroundColorSpan fcsBlue = new ForegroundColorSpan(Color.BLUE);

        //Clicking the blue colored with take to sign in Fragment
        ClickableSpan clickableSpanCall = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                makeCall();
            }
        };

        //Clicking the blue colored with take to sign in Fragment
        ClickableSpan clickableSpanEmail = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                makeEmail();
            }
        };

        //Clicking the blue colored with take to sign in Fragment
        ClickableSpan clickableSpanLocation = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                makeLocation();
            }
        };

        sCall.setSpan(fcsBlue, 0, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sCall.setSpan(clickableSpanCall, 0, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        sEmail.setSpan(fcsBlue, 0, 23, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sEmail.setSpan(clickableSpanEmail, 0, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        sLocation.setSpan(fcsBlue, 0, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sLocation.setSpan(clickableSpanLocation, 0, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvCall.setText(sCall);
        tvCall.setMovementMethod(LinkMovementMethod.getInstance());

        tvEmail.setText(sEmail);
        tvEmail.setMovementMethod(LinkMovementMethod.getInstance());

        tvLocation.setText(sLocation);
        tvLocation.setMovementMethod(LinkMovementMethod.getInstance());
        
        ivEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
//                emailIntent.setData(Uri.parse("mailto:rkmhikaionline@gmail.com"));
//                startActivity(Intent.createChooser(emailIntent, "Send feedback"));
//                Toast.makeText(getContext(), "EMAIL CLICKED by ICON", Toast.LENGTH_SHORT).show();
                makeEmail();
            }
        });

        ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "03637235242", null));
//                startActivity(intent);
//                Toast.makeText(getContext(), "CALL CLICKED by ICON", Toast.LENGTH_SHORT).show();
                makeCall();
            }
        });

        ivLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
////                Uri.parse("geo:25.28627290117523, 91.71692318845199?z=zoom"));
////                Uri.parse("geo:25.28627290117523, 91.71692318845199?z=zoom?q=Ramakrishna+Mission+Ashrama,+Cherrapunji"));
//                Uri.parse("https://www.google.com/maps/place/Ramakrishna+Mission+Ashrama,+Cherrapunji/@25.286003,91.716937,16z/data=!4m12!1m6!3m5!1s0x0:0xc3a52c1a1c567f97!2sRamakrishna+Mission+Ashrama,+Cherrapunji!8m2!3d25.2860026!4d91.7169373!3m4!1s0x0:0xc3a52c1a1c567f97!8m2!3d25.2860026!4d91.7169373?hl=en"));
//                startActivity(intent);
//                Toast.makeText(getContext(), "LOCATION CLICKED by ICON", Toast.LENGTH_SHORT).show();
                makeLocation();
            }
        });


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_SHORT).show();
                sendMessage();
            }
        });

        return v;
    }

    private void makeCall() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "03637235242", null));
        startActivity(intent);
        Toast.makeText(getContext(), "CALLING", Toast.LENGTH_SHORT).show();
    }

    private void makeEmail(){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:rkmhikaionline@gmail.com"));
        startActivity(Intent.createChooser(emailIntent, "Send feedback"));
        Toast.makeText(getContext(), "EMAILING", Toast.LENGTH_SHORT).show();
    }

    private void makeLocation(){
        Intent intent = new Intent(Intent.ACTION_VIEW,
        Uri.parse("https://www.google.com/maps/place/Ramakrishna+Mission+Ashrama,+Cherrapunji/@25.286003,91.716937,16z/data=!4m12!1m6!3m5!1s0x0:0xc3a52c1a1c567f97!2sRamakrishna+Mission+Ashrama,+Cherrapunji!8m2!3d25.2860026!4d91.7169373!3m4!1s0x0:0xc3a52c1a1c567f97!8m2!3d25.2860026!4d91.7169373?hl=en"));
        startActivity(intent);
        Toast.makeText(getContext(), "LOCATING", Toast.LENGTH_SHORT).show();
    }

    private void sendMessage(){
        final String name = etContactName.getText().toString().trim();
        final String email = etContactEmail.getText().toString().trim();
        final String subject = etContactSubject.getText().toString().trim();
        final String message = etContactMessage.getText().toString().trim();

        if(Validation.isEmpty(lContactName,"Name cannot be empty") |
                Validation.isValidEmail(lContactEmail,"Not a valid email") |
                Validation.isEmpty(lContactSubject,"Subject cannot be empty") |
                Validation.isEmpty(lContactMessage,"Message cannot be empty") ) {


            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.contactUsUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if(jsonObject.getInt("success")==1){
                            Toast.makeText(getActivity(), "Successfully Added", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getActivity(), "Something Went Wrong, Try Again", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("name",name);
                    params.put("email",email);
                    params.put("subject",subject);
                    params.put("message",message);
                    Log.i("REQUEST", "getParams: " + params.toString());
                    return params;
                }
            };
            RequestSingletonVolley.getInstance(getContext()).addToRequestQueue(stringRequest);
        }
    }
}