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

import com.example.wagba.databinding.ActivityMainBinding;
import com.example.wagba.databinding.ActivitySignUpBinding;
import com.example.wagba.models.CartModel;
import com.example.wagba.models.OrderModel;
import com.example.wagba.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SignUp extends AppCompatActivity {
    private ActivitySignUpBinding binding;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        binding.signInTv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        );

        binding.signUpButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createUser();
                    }
                }
        );
    }

    boolean validateInputs(){
        String email = binding.emailTv.getText().toString();
        String password = binding.passwordTv.getText().toString();
        String confirmPassword = binding.confirmPasswordTv.getText().toString();
        String name = binding.nameTv.getText().toString();

        boolean valid = true;

        if (!password.equals(confirmPassword)) {
            binding.passwordTv.setError("Passwords not matching!");
            binding.confirmPasswordTv.setError("Passwords not matching!");
            valid = false;
        }
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
        if (TextUtils.isEmpty(name)) {
            binding.nameTv.setError("Please enter a password!");
            binding.nameTv.requestFocus();
            valid = false;
        }
        if(!email.matches("[0-9][0-9][Pp][0-9][0-9][0-9][0-9]@eng\\.asu\\.edu\\.eg")){
            binding.emailTv.setError("Only official ASU emails allowed!");
            valid = false;
        }

        return valid;
    }

    private void createUser() {
        String email = binding.emailTv.getText().toString();
        String password = binding.passwordTv.getText().toString();
        String name = binding.nameTv.getText().toString();

        if (validateInputs()) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Auth", "createUserWithEmail:success");

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("users");
                                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                myRef.child(uid).setValue(new UserModel(email, name, new ArrayList<CartModel>(), new ArrayList<OrderModel>()));

                                Intent i = new Intent(SignUp.this, Homepage.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Auth", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUp.this, "Authentication failed.",
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
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }
}