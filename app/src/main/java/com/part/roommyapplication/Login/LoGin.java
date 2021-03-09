package com.part.roommyapplication.Login;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.part.roommyapplication.dashboardpackage.Dashboard;
import com.part.roommyapplication.ForgotPassword;
import com.part.roommyapplication.LoginRegistrationActivity;
import com.part.roommyapplication.R;
import com.part.roommyapplication.Registration.SignUpFirstPage;
import com.part.roommyapplication.config.RequestSingletonVolley;
import com.part.roommyapplication.config.SharedPrefManager;
import com.part.roommyapplication.config.URLs;
import com.part.roommyapplication.library.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;


public class LoGin extends Fragment {
    TextView tvSignup, tvForgotPwd;
    TextInputLayout lPass, lUserName;
    EditText email, pwd;
    Button btnLogin, btnGoogle;
    LoginButton btnFacebook;
    public static FragmentManager fragmentManager;
    FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;
    private static final String EMAIL = "email";
    private static final String TAG = "FACELOG";
    ImageView icon1;

    private TextView textViewError;
    private CardView view8Char, viewUppercase, viewNumber, viewSymbol;
    private RelativeLayout lytVerify;
    private boolean isAtLeast8 = false, hasUppercase = false, hasNumber = false, hasSymbol = false, isRegistrationClickable = false;
    LinearProgressIndicator linearProgressIndicator;
    private int strength ;
    int progress;
    RelativeLayout relativeLayout;

    public LoGin() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("onCreate",this.getClass().toString());
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        mAuth = FirebaseAuth.getInstance();
        tvSignup = v.findViewById(R.id.tvsignup);
        tvForgotPwd = v.findViewById(R.id.tvForgotPwd);
        lUserName = v.findViewById(R.id.lUsername);
        lPass = v.findViewById(R.id.lPass);
        email = v.findViewById(R.id.uname);
        pwd = v.findViewById(R.id.pwd);
        btnLogin = v.findViewById(R.id.btn_login);
        btnFacebook = v.findViewById(R.id.btn_facebookRes);
        icon1=v.findViewById(R.id.ivicon1);
       // textViewError = v.findViewById(R.id.txt_password_error);
        relativeLayout=v.findViewById(R.id.lyt_password);
        view8Char = v.findViewById(R.id.lyt_verify_4_icon);
        viewUppercase = v.findViewById(R.id.lyt_verify_1_icon);
        viewNumber = v.findViewById(R.id.lyt_verify_2_icon);
        viewSymbol = v.findViewById(R.id.lyt_verify_3_icon);
        lytVerify = v.findViewById(R.id.btn_verify);
        textViewError=v.findViewById(R.id.txt_password);
        pwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View arg0, boolean hasfocus) {
                if (hasfocus) {
                    relativeLayout.setVisibility(View.VISIBLE);
                    inputChange();
                } else {
                    Log.e("TAG", "e1 not focused");
                    relativeLayout.setVisibility(View.GONE);
                }
            }
        });
        linearProgressIndicator = v.findViewById(R.id.progressIndicator);
        //Call
        mCallbackManager = CallbackManager.Factory.create();

        btnFacebook.setReadPermissions("email", "public_profile");
        btnFacebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
//                handleFacebookAccessToken(loginResult.getAccessToken());
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

        btnGoogle = v.findViewById(R.id.btn_googleRegis);
        String text = "Create a new Account SIGN UP";
        SpannableString sSignUp = new SpannableString(text);
        ForegroundColorSpan fcsBlue = new ForegroundColorSpan(Color.BLUE);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                fragmentManager = getFragmentManager();
                if (savedInstanceState != null) {
                    Log.d("Inside first if", fragmentManager.toString());
                    return;
                }
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SignUpFirstPage signUpFirstPage = new SignUpFirstPage();
                fragmentTransaction.replace(R.id.fragment_container, signUpFirstPage, null);
                fragmentTransaction.commit();

                Toast.makeText(getActivity(), "SignUp", Toast.LENGTH_SHORT).show();
            }
        };
        sSignUp.setSpan(fcsBlue, 21, 28, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sSignUp.setSpan(clickableSpan, 21, 28, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvSignup.setText(sSignUp);
        tvSignup.setMovementMethod(LinkMovementMethod.getInstance());
        tvForgotPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getFragmentManager();
                if (savedInstanceState != null) {
                    Log.d("Inside first if", fragmentManager.toString());
                    return;
                }
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ForgotPassword forgotPassword = new ForgotPassword();
                fragmentTransaction.replace(R.id.fragment_container, forgotPassword, null);
                fragmentTransaction.commit();
                Toast.makeText(getActivity(), "ForgotPassword", Toast.LENGTH_SHORT).show();
            }
        });

        //LOgin Buttton
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(!validateName() | !validatePassword()){
//                    userLogin();
//                    return;
//                }
//                else{
//
//                    Toast.makeText(getActivity(), "Login", Toast.LENGTH_SHORT).show();
//                }
                userLogin();
            }
        });

        //Sigining in with Google

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginRegistrationActivity activity = (LoginRegistrationActivity) getActivity();
                if (activity instanceof LoginRegistrationActivity) {
                    activity.signIn(2);
                }
            }
        });
        return v;
    }


    private boolean validateName() {

        String strname = lUserName.getEditText().getText().toString().trim();
        if (strname.isEmpty()) {
            lUserName.setErrorIconDrawable(R.drawable.ic_baseline_error_outline_24);
            lUserName.setError("Please enter a valid username");
            return false;
        } else {
            lUserName.setError(null);
            return true;
        }
    }


    private void userLogin() {
        //first getting the values
        //first getting the values
        final String username = email.getText().toString();
        final String password = pwd.getText().toString();
        if (Validation.isEmpty(lUserName, "Enter a valid Username") | Validation.isValidPassword(lPass, "Please enter a valid Passsword")) {

            //if everything is fine
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.userloginUrl,
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
                                    Toast.makeText(getActivity().getApplicationContext(), "Data Success", Toast.LENGTH_SHORT).show();

                                    //getting the user from the response
                                    JSONObject userJson = obj.getJSONObject("data");
                                    Log.d("data", userJson.toString());
                                    //creating a new user object
                                    User user = new User(
                                            userJson.getString("email"),
                                            userJson.getString("session_id"),
                                            userJson.getString("firstName") + userJson.getString("lastName"),
                                            userJson.getString("userType"),
                                            userJson.getString("stream"),
                                            userJson.getString("gender"),
                                            userJson.getString("dateOfBirth"),
                                            userJson.getString("profilepicture"),
                                            userJson.getString("accesstoken"),
                                            userJson.getString("refreshtoken"),
                                            userJson.getString("access_token_expires_in"),
                                            userJson.getString("class")

                                    );
                                    Log.d("ouruser", user.getName().toString());

                                    //storing the user in shared preferences
                                    SharedPrefManager.getInstance(getActivity().getApplicationContext()).userLogin(user);

                                    //starting the profile activity
                                    getActivity().finish();
                                    startActivity(new Intent(getActivity().getApplicationContext(), Dashboard.class));
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
                    params.put("userName", username);
                    params.put("password", password);
                    return params;
                }
            };

            RequestSingletonVolley.getInstance(getContext()).addToRequestQueue(stringRequest);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        if (currentUser != null) {
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
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(getContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        updateUI();

                        // ...
                    }
                });
    }

    private void updateUI() {

        Toast.makeText(getContext(), "You are Logged in", Toast.LENGTH_SHORT).show();
        Intent accountIntent = new Intent(getContext(), Dashboard.class);
        startActivity(accountIntent);

    }

    private void inputChange() {

        pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {

                registrationDataCheck();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }




    @SuppressLint("ResourceType")

    private void registrationDataCheck() {
        String password=pwd.getText().toString().trim();
        strength=0;
        progress =0;
        if (password.length() >= 8) {
            isAtLeast8 = true;
            icon1.setImageResource(R.drawable.com_facebook_button_icon);
//            view8Char.setCardBackgroundColor(R.color.white);
            strength++;

        } else {
            isAtLeast8 = false;
            icon1.setImageResource(R.drawable.ic_baseline_check_24);
//            view8Char.setCardBackgroundColor(R.color.com_facebook_blue);
            //icon1.setImageResource(R.drawable.ic_baseline_check_24);

        }
        if (password.matches("(.*[a-z].*[A-Z])|([A-Z].*[a-z].*)")) {
            hasUppercase = true;
//            viewUppercase.setCardBackgroundColor(Color.GREEN);
            strength++;

        } else {
            hasUppercase = false;
            viewUppercase.setCardBackgroundColor(R.color.light_gray);

        }
        if (password.matches("(.*[0-9].*)")) {
            hasNumber = true;
//            viewNumber.setCardBackgroundColor(Color.GREEN);

            strength++;

        } else {
            hasNumber = false;
//            viewNumber.setCardBackgroundColor(R.color.light_gray);

        }
        if (password.matches(".*[\\^`~<,>\"'}{\\]\\[|)(;&*$%#@!:./?\\\\+=\\-_ ].*")) {
            hasSymbol = true;
//            viewSymbol.setCardBackgroundColor(Color.GREEN);
            strength++;

        } else {
            hasSymbol = false;
//            viewSymbol.setCardBackgroundColor(R.color.light_gray);

        }
        checkStatus();
    }

    @SuppressLint("SetTextI18n")
    private void checkStatus() {
        if (strength < 2) {
            textViewError.setText("Password Strength: Very Weak");
            progress+=25;
            linearProgressIndicator.setProgress(progress);
        } else if (strength == 2) {
            textViewError.setText("Password Strength: Weak");
            progress+=50;
            linearProgressIndicator.setProgress(progress);

        } else if(strength ==3) {
            textViewError.setText("Password Strength: Strong");
            progress+=75;
            linearProgressIndicator.setProgress(progress);

        }else{
            textViewError.setText("Password Strength: Very Strong");
            progress+=100;
            linearProgressIndicator.setProgress(progress);
        }
    }
}

