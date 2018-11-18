package auth;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rubensousa.viewpagercards.MainActivity;
import com.github.rubensousa.viewpagercards.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class auth extends AppCompatActivity {

    Button buttonLogin;
    Button buttonSignup;
    EditText ETEmail;
    EditText ETPassword;
    private FirebaseAuth mAuth;
    String TAG = "Auth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        buttonLogin = (Button) findViewById(R.id.login);
        buttonSignup = (Button) findViewById(R.id.signup);
        ETEmail = (EditText) findViewById(R.id.email);
        ETPassword = (EditText) findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Do something in response to button click

                Log.v(TAG, "Login Clicked!");

                // input validation
                if(isValidEmail(ETEmail.getText()) && isValidPassword(ETPassword.getText())){
                    //create firebase account
                    createAccount(ETEmail.getText().toString(), ETPassword.getText().toString());
                }else{
                    Toast.makeText(auth.this, "bad email/password", Toast.LENGTH_SHORT).show();
                }

                //empties edittext fields
                resetUI(view);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Do something in response to button click

                Log.v(TAG, "Sign in Clicked!");

                // input validation
                if(isValidEmail(ETEmail.getText()) && isValidPassword(ETPassword.getText())){
                    //create firebase account
                    signIn(ETEmail.getText().toString(), ETPassword.getText().toString());
                }else{
                    Toast.makeText(auth.this, "bad email/password", Toast.LENGTH_SHORT).show();
                }

                //empties edittext fields
                resetUI(view);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        currentUser.getIdToken(true);

        if (currentUser != null){
            //automatically logs in if user already exists
            // goes to mainactivity
            Intent k = new Intent(auth.this, MainActivity.class);
            startActivity(k);
        }
        //updateUI(currentUser);
    }

    public void resetUI(View view){
        ETEmail.setText("");
        ETPassword.setText("");
    }


    public void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( @NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(auth.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                            // goes to mainactivity
                            Intent k = new Intent(auth.this, MainActivity.class);
                            startActivity(k);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(auth.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }

    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public boolean isValidPassword(CharSequence target) {
        return (!TextUtils.isEmpty(target) && target.length() >= 6);
    }
}

