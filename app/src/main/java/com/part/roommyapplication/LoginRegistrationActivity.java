package com.part.roommyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.part.roommyapplication.Login.LoGin;
import com.part.roommyapplication.Registration.UploadImage;
import com.part.roommyapplication.Registration.VerifyOTP;
import com.part.roommyapplication.Registration.Vitalinfo;
import com.part.roommyapplication.config.RequestSingletonVolley;
import com.part.roommyapplication.config.SharedPrefManager;
import com.part.roommyapplication.config.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginRegistrationActivity extends AppCompatActivity {

    private CallbackManager mCallbackManager;
    public static FragmentManager fragmentManager;
    private GoogleSignInClient mGoogleSignInClient;
    private String TAG = "MainActivity";
    private FirebaseAuth mAuth;
    private int RC_SIGN_IN = 1;
    int commingPage;
    String Fname, Lname, email, phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        Log.d("onCreate",this.getClass().toString());
        //Making the activity full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_registration);
        mAuth = FirebaseAuth.getInstance();
        //Call
        mCallbackManager = CallbackManager.Factory.create();
        // Configuring the google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("434284353810-6n8dlliojl3g0ifqu754vmeneblljcfq.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.fragment_container) != null) {
            Log.d("onCreate","Inside If");
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            LoGin loGin = new LoGin();
            Vitalinfo vitalinfo = new Vitalinfo();
            //SignUpFirstPage signUpFirstFragment=new SignUpFirstPage();
            //VerifyOTP verifyOTP=new VerifyOTP();
            UploadImage uploadImage = new UploadImage();
            fragmentTransaction.add(R.id.fragment_container, loGin, null);
            fragmentTransaction.commit();

        }
    }

    //function that sign In's an account using Gmail
    public void signIn(int page) {
        //Using Intent for google sign in client
        commingPage = page;
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            //Creating sign task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            //Function that handles the sign in
            handleSignInResult(task);
        }
    }

    //Defining the  function that handles the sign in using Gmail
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Fname = acc.getGivenName();
            Lname = acc.getFamilyName();
            email=acc.getEmail();
            //Log.d("phone",acc.get);
            Log.d("data",email+Fname+Lname+phone);
            Toast.makeText(LoginRegistrationActivity.this, "Signed In Successfully", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);
        } catch (ApiException e) {

            Toast.makeText(LoginRegistrationActivity.this, "Sign In failed", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }
    }

    //Hndling firebase google Authentication
    private void FirebaseGoogleAuth(GoogleSignInAccount acct) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        //Checking authentication credential
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginRegistrationActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    Log.d("Coming From 1", String.valueOf(commingPage));
                    if (commingPage == 1) {
                        Log.d("Coming From 1", String.valueOf(commingPage));
//                        email = user.getEmail();
                        phone = user.getPhoneNumber();

                        if (phone == null) {
                            phone = "";
                        }
                        SharedPrefManager.getInstance(getApplicationContext()).setUserPhone(phone);
                        loginUsingSocial();
//                       Log.d("Fname",user.getPhoneNumber());
//                       Log.d("lastName",user.getFamilyName());
//                       Log.d("Email",user.getEmail());

                    } else {

                        socialSignIn();
                    }

                } else {
                    Toast.makeText(LoginRegistrationActivity.this, "Fialed", Toast.LENGTH_SHORT).show();
                    // updateUI(null);
                }
            }
        });
    }

    public void loginUsingSocial() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.socialLogin,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            Log.d("Server Response", obj.toString());

                            //if no error in response
                            Log.d("Server Response", String.valueOf(obj.getInt("success")));
                            if (obj.getInt("success") == 1) {
                                Toast.makeText(getApplicationContext(), "Data Success", Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                int userId = obj.getInt("userID");
                                SharedPrefManager.getInstance(getApplicationContext()).setUserId(userId);
                                FragmentManager fragmentManager;
                                fragmentManager = getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                Log.d("registerState",String.valueOf((obj.getInt("registrationStage"))));
                                switch (obj.getInt("registrationStage")){
                                    case 1:
                                        VerifyOTP verifyOTP = new VerifyOTP();
                                        fragmentTransaction.replace(R.id.fragment_container, verifyOTP, null);
                                        fragmentTransaction.commit();
                                        break;
                                    case 2:
                                        Vitalinfo vitalinfo = new Vitalinfo();
                                        fragmentTransaction.replace(R.id.fragment_container, vitalinfo, null);
                                        fragmentTransaction.commit();
                                        break;
                                    case 4:
                                        UploadImage uploadImage = new UploadImage();
                                        fragmentTransaction.replace(R.id.fragment_container,uploadImage, null);
                                        fragmentTransaction.commit();
                                        break;
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("firstName", Fname);
                params.put("lastName", Lname);
                params.put("phone", phone);
                params.put("registerMode","GoogleAuth");

                return params;
            }
        };

        RequestSingletonVolley.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void socialSignIn(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.socialSignIn,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);
                            Log.d("Server Response", obj.toString());

                            //if no error in response
                            Log.d("Server Response", String.valueOf(obj.getInt("success")));
                            if (obj.getInt("success") == 1) {
                               Intent intent =new Intent(getApplicationContext(),Dashboard.class);
                               startActivity(intent);

                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("firstName", Fname);
                params.put("lastName", Lname);
                params.put("phone", phone);
                params.put("registerMode","GoogleAuth");
//                params.put("userName",);
                params.put("signInMode","GoogleAuth");

                return params;
            }
        };

        RequestSingletonVolley.getInstance(this).addToRequestQueue(stringRequest);
    }

}


