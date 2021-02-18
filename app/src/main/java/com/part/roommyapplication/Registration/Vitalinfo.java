package com.part.roommyapplication.Registration;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.part.roommyapplication.R;
import com.part.roommyapplication.config.RequestSingletonVolley;
import com.part.roommyapplication.config.SharedPrefManager;
import com.part.roommyapplication.config.URLs;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
public class Vitalinfo extends Fragment {

    Spinner sGender,sStream,sClass;
    TextView txtdte;
    private DatePickerDialog datePicker;
    private int year, month, day;
    String gender,standard,stream,DOB;
    Button btnSignUp;
    public FragmentManager fragmentManager;

    public Vitalinfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_vitalinfo, container, false);
        sGender=v.findViewById(R.id.sGender);
        sStream=v.findViewById(R.id.sStream);
        sClass=v.findViewById(R.id.sClass);
        txtdte=v.findViewById(R.id.dtepicker);
        btnSignUp=v.findViewById(R.id.btn_signUp);
        txtdte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Calendar cldr = Calendar.getInstance();
                 day = cldr.get(Calendar.DAY_OF_MONTH);
                 month = cldr.get(Calendar.MONTH);
                 year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datePicker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                DOB=dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                txtdte.setText(DOB);
                            }
                        }, year, month, day);
                datePicker.show();
            }
        });
// Create an GenderAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.Gender, R.layout.custom_spinner);
// Specify the layout to use when the list of choices appears
        genderAdapter.setDropDownViewResource(R.layout.custom_drop_down);
// Apply the adapter to the spinner
        sGender.setAdapter(genderAdapter);

        sGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Select gender")){

                }else{
                     gender=parent.getItemAtPosition(position).toString();

                    Toast.makeText(parent.getContext(), gender, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // Create an ClassAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> classAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.classStudied, R.layout.custom_spinner);
// Specify the layout to use when the list of choices appears
        genderAdapter.setDropDownViewResource(R.layout.custom_drop_down);
// Apply the adapter to the spinner
        sClass.setAdapter(classAdapter);

        sClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Select class")){

                }else{

                    standard=parent.getSelectedItem().toString();
                    if(standard.equals("Class I")|standard.equals("Class II")|standard.equals("Class III")|standard.equals("Class IV")|standard.equals("Class V")|standard.equals("Class VI")
                    |standard.equals("Class VII")|standard.equals("Class VIII")|standard.equals("Class IX")|standard.equals("Class X")){
                        sStream.setEnabled(false);
                        stream="N/A";
                    }
                    else{
                        sStream.setEnabled(true);
                    }

                    Toast.makeText(parent.getContext(), standard, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Create an ClassAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> streamAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.stream, R.layout.custom_spinner);
// Specify the layout to use when the list of choices appears
        genderAdapter.setDropDownViewResource(R.layout.custom_drop_down);
// Apply the adapter to the spinner
        sStream.setAdapter(streamAdapter);

        sStream.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Select Stream")){

                }else{
                     stream=parent.getItemAtPosition(position).toString();

                    Toast.makeText(parent.getContext(), stream, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               userVitalInfo();
            }
        });


        return v;
    }

    //User VitalInfo

    private void userVitalInfo() {
        String userId=String.valueOf(SharedPrefManager.getInstance(getActivity().getApplicationContext()).getUserId());
        Log.d("USER ID",userId);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.vitalInfoUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            Log.d("Server Response", obj.toString());
//                            if no error in response
                               if (obj.getInt("success") == 1) {
                                Toast.makeText(getActivity().getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                   Toast.makeText(getActivity(), gender+standard+stream+DOB, Toast.LENGTH_SHORT).show();
                                fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                UploadImage uploadImage = new UploadImage();
                                fragmentTransaction.replace(R.id.fragment_container, uploadImage, null);
                                fragmentTransaction.commit();
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
                params.put("dob", DOB);
                params.put("gender", gender);
                params.put("class", standard);
                params.put("stream", stream);
                params.put("school","RKM");
                params.put("address", "sohra");
                params.put("district", "East kahsi hills");
                params.put("pincode","793004");
                params.put("userID",userId);
                return params;
            }
        };

        RequestSingletonVolley.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

}