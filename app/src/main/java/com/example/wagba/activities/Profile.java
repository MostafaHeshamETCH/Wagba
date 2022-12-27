package com.example.wagba.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.wagba.R;
import com.example.wagba.databinding.ActivityProfileBinding;
import com.example.wagba.models.room.LocalUser;
import com.example.wagba.view_models.LocalUserViewModel;

import java.util.List;

public class Profile extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private LocalUserViewModel localUserViewModel;
    boolean isClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        localUserViewModel = new ViewModelProvider(this).get(LocalUserViewModel.class);

        localUserViewModel.getAllLocalUsers().observe(this, new Observer<List<LocalUser>>() {
            @Override
            public void onChanged(List<LocalUser> localUsers) {
                if(!localUsers.isEmpty()){
                    binding.address.setText(localUsers.get(0).getUniYear());
                } else {
                    binding.address.setText("No data entered yet");
                }
            }
        });

        binding.backButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }
        );

        binding.editIcon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!isClicked){
                            binding.address.setEnabled(true);
                            binding.address.setCursorVisible(true);
                            binding.editIcon.setImageResource(R.drawable.save_svgrepo_com);
                            binding.address.requestFocus();
                            isClicked = true;
                        } else {
                            binding.address.setEnabled(false);
                            binding.address.setCursorVisible(false);
                            binding.editIcon.setImageResource(R.drawable.edit_detailed_svgrepo_com);
                            hideKeyboard(Profile.this);
                            isClicked = false;
                            // TODO: Save
                            localUserViewModel.clear();
                            localUserViewModel.insert(new LocalUser(binding.address.getText().toString()));
                            Toast.makeText(Profile.this, "Saved", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}