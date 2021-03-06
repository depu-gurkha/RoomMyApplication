package com.part.roommyapplication.schooldetail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.google.android.material.textfield.TextInputLayout;
import com.part.roommyapplication.R;
import com.part.roommyapplication.Registration.VerifyOTP;
import com.part.roommyapplication.config.RequestSingletonVolley;
import com.part.roommyapplication.config.URLs;
import com.part.roommyapplication.library.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SchoolDetails extends Fragment {

    //Elements
    TextInputLayout lSchoolName, lSchoolAddress, lSchoolPincode, lSchoolEmail, lSchoolPhone, lSchoolWebsite;
    EditText etSchoolName, etSchoolAddress, etSchoolPincode, etSchoolEmail, etSchoolPhone, etSchoolWebsite;;
    Spinner spDistrict;
    Spinner spType;
    String district;
    String type;
    Button btnRegister;
    public static TextView tvType;
    public static ImageView ivType;
    public static TextView tvDistrict;
    public static ImageView ivDistrict;
    public FragmentManager fragmentManager;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SchoolDetails() {
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
        View v= inflater.inflate(R.layout.fragment_schooldetails, container, false);

        lSchoolName = v.findViewById(R.id.lSname);
        lSchoolAddress = v.findViewById(R.id.lSaddress);
        lSchoolPincode = v.findViewById(R.id.lSpincode);
        lSchoolEmail = v.findViewById(R.id.lSemail);
        lSchoolPhone = v.findViewById(R.id.lSphone);
        lSchoolWebsite = v.findViewById(R.id.lSwebsite);

        etSchoolName = v.findViewById(R.id.sName);
        etSchoolAddress = v.findViewById(R.id.sAddress);
        etSchoolPincode = v.findViewById(R.id.sPincode);
        etSchoolEmail = v.findViewById(R.id.sEmail);
        etSchoolPhone = v.findViewById(R.id.sPhone);
        etSchoolWebsite = v.findViewById(R.id.sWebsite);

        tvType = v.findViewById(R.id.tv_type);
        ivType = v.findViewById(R.id.iv_type);

        tvDistrict = v.findViewById(R.id.tv_district);
        ivDistrict = v.findViewById(R.id.iv_district);

        spDistrict = v.findViewById(R.id.spDistrict);
        spType = v.findViewById(R.id.spType);

        btnRegister = v.findViewById(R.id.btn_signUp);


        //Create an GenderAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> districtAdapter = ArrayAdapter.createFromResource(getContext(),
            R.array.District, R.layout.custom_spinner);
        // Specify the layout to use when the list of choices appears
        districtAdapter.setDropDownViewResource(R.layout.custom_drop_down);
        // Apply the adapter to the spinner
        spDistrict.setAdapter(districtAdapter);

        spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Select District")){
                    Toast.makeText(parent.getContext(), "Please Select District", Toast.LENGTH_SHORT).show();
                    district="";
                    ((TextView)parent.getChildAt(0)).setTextColor(view.getResources().getColor(R.color.blue_gray));
                }else{
                    district=parent.getItemAtPosition(position).toString();
                    Toast.makeText(parent.getContext(), district, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });



        //Create an TypeAdapter using the string Array and a default spinner layout
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.Type,R.layout.custom_spinner);
        //specify the layput to use when the list of choices appears
        typeAdapter.setDropDownViewResource(R.layout.custom_drop_down);
        //apply the adapter to the spinner
        spType.setAdapter(typeAdapter);

        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if(adapterView.getItemAtPosition(position).equals("Select Type")){
                    type="";
                    ((TextView)adapterView.getChildAt(0)).setTextColor(view.getResources().getColor(R.color.blue_gray));
                }else{
                    type=adapterView.getItemAtPosition(position).toString();
                    Toast.makeText(adapterView.getContext(), type, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //Onclick to navigate to the next signup page
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                schoolRegistration();
            }
        });
        return v;
    }

    //function For User Registration
    private void schoolRegistration() {

        final String schoolName = etSchoolName.getText().toString().trim();
        final String schoolAddress = etSchoolAddress.getText().toString().trim();
        final String schoolDistrict = district;
        final String schoolPincode = etSchoolPincode.getText().toString().trim();
        final String schoolEmail = etSchoolEmail.getText().toString().trim();
        final String schoolPhone = etSchoolPhone.getText().toString().trim();
        final String schoolWebsite = etSchoolWebsite.getText().toString().trim();
        final String schoolType = type;

        if (Validation.isEmpty(lSchoolName, "School Name Cannot be empty") |
            Validation.isEmpty(lSchoolAddress, "School Addresss Cannot be Empty") |
            Validation.isValidPincode(lSchoolPincode,"Not a Valid Pincode") |
            Validation.isValidEmail(lSchoolEmail, "Not a Valid Email") |
            Validation.isValidPhone(lSchoolPhone, "Not a Vaid Phone") |
            Validation.isEmpty(lSchoolWebsite, "Website Cannot be empty") |
            isEmpty(type,"Select School Type",tvType,ivType) |
            isEmpty(district,"Select District",tvDistrict,ivDistrict) ) {


            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.schoolRegisterUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", "onResponse: " + response);
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

//                                int userId=obj.getInt("userID");
//                                Log.d("userId",String.valueOf(userId));
//                                SharedPrefManager.getInstance(getActivity().getApplicationContext()).setUserId(userId);
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
                            Log.i("VOLLEY", "onErrorResponse: " + error.getMessage());
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("schoolName", schoolName);
                    params.put("schoolAddress", schoolAddress);
                    params.put("schoolDistrict", schoolDistrict);
                    params.put("schoolPincode", schoolPincode);
                    params.put("schoolEmail", schoolEmail);
                    params.put("schoolPhone", schoolPhone);
                    params.put("schoolWebsite", schoolWebsite);
                    params.put("schoolType", schoolType);
                    params.put("schoolEstablish", "2010");
//                params.put("password", "12345678");
                    Log.i("REQUEST", "getParams: " + params.toString());
                    return params;
                }
            };

            RequestSingletonVolley.getInstance(getContext()).addToRequestQueue(stringRequest);
        }
    }

    public static boolean isEmpty(String value, String message, TextView textView, ImageView imageView){
//        String value = textInputLayout.getEditText().getText().toString().trim();
        if (value.isEmpty()) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(message);
            imageView.setVisibility(View.VISIBLE);
//            tvType.setError("HELLO");
//            textInputLayout.setErrorIconDrawable(R.drawable.ic_baseline_error_outline_24);
//            textInputLayout.setError(message);
            return false;
        } else {
//            textInputLayout.setError(null);
            textView.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.INVISIBLE);
            return true;
        }
    }


}