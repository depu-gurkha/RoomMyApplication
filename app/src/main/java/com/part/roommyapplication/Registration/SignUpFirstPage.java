package com.part.roommyapplication.Registration;
import android.content.Intent;
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
import android.widget.ImageButton;
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
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.part.roommyapplication.Dashboard;
import com.part.roommyapplication.Login.LoGin;
import com.part.roommyapplication.LoginRegistrationActivity;
import com.part.roommyapplication.R;
import com.part.roommyapplication.config.RequestSingletonVolley;
import com.part.roommyapplication.config.SharedPrefManager;
import com.part.roommyapplication.config.URLs;
import com.part.roommyapplication.library.Validation;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class

SignUpFirstPage extends Fragment {
    TextView tvSignin;
    TextInputLayout lFirstName, lLastName, lPhone, lEmail,lPwd,lConfirmPwd;
    EditText etEmail, etFstName, etLstName, etPhone,etPwd,etConfirmPwd;
    Button btnSignUp, btnGoogle;
    LoginButton btnFacebook;

    public FragmentManager fragmentManager;
    FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;
    private static final String EMAIL = "email";
    private static final String TAG="FACELOG";

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
        lPwd=v.findViewById(R.id.lPassword);
        lConfirmPwd=v.findViewById(R.id.lConfirmPwd);
        etEmail = v.findViewById(R.id.email);

        etFstName = v.findViewById(R.id.fName);
        etLstName = v.findViewById(R.id.lName);
        etPhone = v.findViewById(R.id.phone);
        etPwd=v.findViewById(R.id.password);
        etConfirmPwd=v.findViewById(R.id.confirmPwd);
        btnSignUp = v.findViewById(R.id.btn_signUp);
        btnGoogle =v.findViewById(R.id.btn_googleRegis);
        btnFacebook=v.findViewById(R.id.btn_facebookRes);
        mCallbackManager = CallbackManager.Factory.create();
        btnFacebook.setReadPermissions("email", "public_profile");
        etFstName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etFstName.setError(null);

            }
        });
        btnFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginRegistrationActivity activity = (LoginRegistrationActivity) getActivity();
                if (activity instanceof LoginRegistrationActivity) {
                    activity.signIn();
                }
            }
        });
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
    //function For User Registration
    private void userRegistration() {
        final String firstName = etFstName.getText().toString().trim();
        final String lastName = etLstName.getText().toString().trim();
        final String email = etEmail.getText().toString().trim();
        final String phone = etPhone.getText().toString().trim();
        final String password = etPwd.getText().toString().trim();
        final String conFirmPassword=etConfirmPwd.getText().toString().trim();

            if (Validation.isEmpty(lFirstName, "Please Enter Valid First Name") | Validation.isEmpty(lLastName, "Please Enter Valid Last Name") | Validation.isValidPassword(lPwd, "Password must contain at least one Capital letter, one small letter, a special character, and a number") |
                    Validation.isValidPhone(lPhone, "Enter a valid Phone") | Validation.isValidEmail(lEmail, "Please enter a valid email") | Validation.isValidText(lLastName, "Invalid First Name") |
                    Validation.isValidText(lFirstName, "Invalid Last Name")) {
                if (conFirmPassword.isEmpty() | conFirmPassword.equals(password)) {
                    lConfirmPwd.setErrorIconDrawable(R.drawable.ic_baseline_error_outline_24);
                    lConfirmPwd.setError("Password did not match");


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
                                            int userId = obj.getInt("userID");
                                            Log.d("userId", String.valueOf(userId));
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
                            params.put("password", password);
                            return params;
                        }
                    };

                    RequestSingletonVolley.getInstance(getContext()).addToRequestQueue(stringRequest);
                }
            }
        }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();

//        if(currentUser !=null){
//            updateUI();
//        }
//        updateUI(currentUser);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("yoho", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("yoho", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI();
                        }

                        // ...
                    }
                });
    }

    private void updateUI(){

        Toast.makeText(getContext(),"You are Logged in",Toast.LENGTH_SHORT).show();
        Intent accountIntent= new Intent(getContext(), Dashboard.class);
        startActivity(accountIntent);

    }

}

