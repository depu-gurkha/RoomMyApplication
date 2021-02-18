package com.part.roommyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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
import com.part.roommyapplication.Registration.SignUpFirstPage;
import com.part.roommyapplication.Registration.UploadImage;
import com.part.roommyapplication.Registration.VerifyOTP;

public class LoginRegistrationActivity extends AppCompatActivity {

    private CallbackManager mCallbackManager;
    public static FragmentManager fragmentManager;
    private GoogleSignInClient mGoogleSignInClient;
    private String TAG="MainActivity";
    private FirebaseAuth mAuth;
    private int RC_SIGN_IN=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        //Making the activity full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login_registration);
        mAuth=FirebaseAuth.getInstance();
        //Call
        mCallbackManager = CallbackManager.Factory.create();
        // Configuring the google sign in
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("434284353810-8ppgehlnjav5elbkn7gvhtf6tupls3n3.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso);
        fragmentManager = getSupportFragmentManager();
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            // loGin = new LoGin();
            //Vitalinfo vitalinfo=new Vitalinfo();
//             SignUpFirstPage signUpFirstFragment=new SignUpFirstPage();
            VerifyOTP verifyOTP=new VerifyOTP();
            UploadImage uploadImage = new UploadImage();
            fragmentTransaction.add(R.id.fragment_container, verifyOTP, null);
            fragmentTransaction.commit();

        }
    }

    //function that sign In's an account using Gmail
    public void signIn(){
        //Using Intent for google sign in client
        Intent signInIntent=mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RC_SIGN_IN){
            //Creating sign task
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            //Function that handles the sign in
            handleSignInResult(task);
        }
    }

    //Defining the  function that handles the sign in using Gmail
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount acc=completedTask.getResult(ApiException.class);
            Toast.makeText(LoginRegistrationActivity.this,"Signed In Successfully",Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);
        }
        catch (ApiException e){

            Toast.makeText(LoginRegistrationActivity.this,"Sign In failed",Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);
        }
    }

    //Hndling firebase google Authentication
    private void FirebaseGoogleAuth(GoogleSignInAccount acct){
        AuthCredential authCredential= GoogleAuthProvider.getCredential(acct.getIdToken(),null);

        //Checking authentication credential
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(LoginRegistrationActivity.this,"Successful",Toast.LENGTH_SHORT).show();
                    FirebaseUser user =mAuth.getCurrentUser();
                    //  updateUI(user);
                }
                else{
                    Toast.makeText(LoginRegistrationActivity.this,"Fialed",Toast.LENGTH_SHORT).show();
                    // updateUI(null);
                }

            }
        });
    }
}
