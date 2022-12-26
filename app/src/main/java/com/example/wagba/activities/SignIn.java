package com.example.wagba.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wagba.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity {
    private ActivitySignInBinding binding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        binding.signUpTv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            Intent k = new Intent(SignIn.this, SignUp.class);
                            startActivity(k);
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        binding.signInBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        signInUser();
                    }
                }
        );
    }

    boolean validateInputs(){
        String email = binding.emailTv.getText().toString();
        String password = binding.passwordTv.getText().toString();
        boolean valid = true;

        if (TextUtils.isEmpty(email)) {
            binding.emailTv.setError("Please enter an email!");
            binding.emailTv.requestFocus();
            valid = false;

        }
        if (TextUtils.isEmpty(password)) {
            binding.passwordTv.setError("Please enter a password!");
            binding.passwordTv.requestFocus();
            valid = false;
        }

        return valid;
    }

    private void signInUser() {
        String email = binding.emailTv.getText().toString();
        String password = binding.passwordTv.getText().toString();

        if (validateInputs()) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Auth", "signInWithEmailAndPassword:success");

                                Intent i = new Intent(SignIn.this, Homepage.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Auth", "signInWithEmailAndPassword:failure", task.getException());
                                Toast.makeText(SignIn.this, "Authentication failed. " + task.getException().toString(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }


    // dismiss keyboard when user clicks outside any input field
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }
}