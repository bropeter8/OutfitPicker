package si.uni_lj.fe.tnuv.outfitpicker2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public class SettingsActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private CheckBox darkModeToggle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);

        // Initialize UI elements
        darkModeToggle = findViewById(R.id.dark_mode);
        Button backButton = findViewById(R.id.back_button);

        // Load current settings
        boolean isDarkModeOn = sharedPreferences.getBoolean("dark_mode", false);
        darkModeToggle.setChecked(isDarkModeOn);

        // Dark Mode Toggle listener
        darkModeToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Save the state in SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("dark_mode", isChecked);
            editor.apply();

            // Apply the selected theme
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });

        // Back Button click listener
        backButton.setOnClickListener(v -> {
            finish(); // Go back to the previous activity
        });

        // About Button click listener
        TextView aboutButton = findViewById(R.id.about_button);
        aboutButton.setOnClickListener(v -> openAboutActivity());

        // Handle back button press
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        });
    }

    private void openAboutActivity() {
        Intent intent = new Intent(SettingsActivity.this, AboutActivity.class);
        startActivity(intent);
    }
}
