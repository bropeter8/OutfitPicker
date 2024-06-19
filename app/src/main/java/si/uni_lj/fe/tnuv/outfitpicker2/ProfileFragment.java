package si.uni_lj.fe.tnuv.outfitpicker2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProfileFragment extends Fragment {

    private static final int PERMISSION_REQUEST_CODE = 100;

    private ImageView profilePhoto;
    private TextView username;
    private EditText usernameEditText;
    private Button updateButton, changePhotoButton, settingsButton;

    private static final String PREFS_NAME = "outfit_prefs";
    private static final String PREF_OUTFITS = "saved_outfits";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PROFILE_PHOTO = "profile_photo";

    private SharedPreferences sharedPreferences;
    private RecyclerView outfitsRecyclerView;
    private OutfitsAdapter outfitsAdapter;
    private List<String[]> savedOutfits;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    pickImage();
                } else {
                    // Handle permission denial
                }
            });

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null) {
                        profilePhoto.setImageURI(selectedImageUri);
                        saveProfilePhoto(selectedImageUri); // Save profile photo URI to SharedPreferences
                    }
                }
            });

    private boolean isEditingUsername = false;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profilePhoto = view.findViewById(R.id.profile_photo);
        username = view.findViewById(R.id.username);
        usernameEditText = view.findViewById(R.id.username_edit_text);
        updateButton = view.findViewById(R.id.update_button);
        changePhotoButton = view.findViewById(R.id.photo_button);
        settingsButton = view.findViewById(R.id.settings_button);

        updateButton.setOnClickListener(v -> {
            if (isEditingUsername) {
                // Save the new username
                String newName = usernameEditText.getText().toString();
                username.setText(newName);
                username.setVisibility(View.VISIBLE);
                usernameEditText.setVisibility(View.GONE);
                saveUsername(newName); // Save username to SharedPreferences
                hideKeyboard(usernameEditText);
                isEditingUsername = false;
                updateButton.setText("Update Username");
            } else {
                // Make the TextView editable
                usernameEditText.setText(username.getText());
                username.setVisibility(View.GONE);
                usernameEditText.setVisibility(View.VISIBLE);
                usernameEditText.requestFocus();
                usernameEditText.setSelection(usernameEditText.getText().length());
                showKeyboard(usernameEditText);
                isEditingUsername = true;
                updateButton.setText("Save Username");
            }
        });

        changePhotoButton.setOnClickListener(v -> {
            if (checkPermissions()) {
                pickImage();
            } else {
                requestPermissions();
            }
        });

        settingsButton.setOnClickListener(v -> openSettingsActivity());

        outfitsRecyclerView = view.findViewById(R.id.outfits_recycler_view);
        outfitsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        loadSavedOutfits();
        loadUsername(); // Load username from SharedPreferences
        loadProfilePhoto(); // Load profile photo from SharedPreferences

        outfitsAdapter = new OutfitsAdapter(savedOutfits);
        outfitsRecyclerView.setAdapter(outfitsAdapter);

        // Add Clear Favorites Button
        Button clearFavoritesButton = view.findViewById(R.id.clear_favorites_button);
        clearFavoritesButton.setOnClickListener(v -> clearSavedOutfits());

        return view;
    }



    private void loadSavedOutfits() {
        Set<String> savedOutfitsSet = sharedPreferences.getStringSet(PREF_OUTFITS, new HashSet<>());
        savedOutfits = new ArrayList<>();

        for (String outfit : savedOutfitsSet) {
            String[] parts = outfit.split(",");
            savedOutfits.add(parts);
        }
    }

    private void loadUsername() {
        String savedUsername = sharedPreferences.getString(PREF_USERNAME, "");
        username.setText(savedUsername);
    }

    private void saveUsername(String username) {
        sharedPreferences.edit().putString(PREF_USERNAME, username).apply();
    }

    private void loadProfilePhoto() {
        String savedPhotoUri = sharedPreferences.getString(PREF_PROFILE_PHOTO, null);
        if (savedPhotoUri != null) {
            Uri photoUri = Uri.parse(savedPhotoUri);
            profilePhoto.setImageURI(photoUri);
        }
    }

    private void saveProfilePhoto(Uri photoUri) {
        sharedPreferences.edit().putString(PREF_PROFILE_PHOTO, photoUri.toString()).apply();
    }

    private boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED;
        } else {
            return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    private void openSettingsActivity() {
        Intent intent = new Intent(requireActivity(), SettingsActivity.class);
        startActivity(intent);
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    private void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void clearSavedOutfits() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(PREF_OUTFITS);
        editor.apply();
        savedOutfits.clear();
        outfitsAdapter.notifyDataSetChanged();
        Toast.makeText(getContext(), "All favorites have been cleared", Toast.LENGTH_SHORT).show();
    }
}
