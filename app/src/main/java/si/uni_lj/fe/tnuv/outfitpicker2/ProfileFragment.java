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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
    private EditText usernameInput;
    private Button updateButton, changePhotoButton, settingsButton;

    private static final String PREFS_NAME = "outfit_prefs";
    private static final String PREF_OUTFITS = "saved_outfits";

    private SharedPreferences sharedPreferences;
    private RecyclerView outfitsRecyclerView;
    private OutfitsAdapter outfitsAdapter;
    private List<int[]> savedOutfits;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profilePhoto = view.findViewById(R.id.profile_photo);
        username = view.findViewById(R.id.username);
        usernameInput = view.findViewById(R.id.username_input);
        updateButton = view.findViewById(R.id.update_button);
        changePhotoButton = view.findViewById(R.id.photo_button);
        settingsButton = view.findViewById(R.id.settings_button);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText(usernameInput.getText().toString());
            }
        });

        changePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkPermissions()) {
                    pickImage();
                } else {
                    requestPermissions();
                }
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsActivity();
            }
        });

        outfitsRecyclerView = view.findViewById(R.id.outfits_recycler_view);
        outfitsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        loadSavedOutfits();

        outfitsAdapter = new OutfitsAdapter(savedOutfits);
        outfitsRecyclerView.setAdapter(outfitsAdapter);

        return view;
    }

    private void loadSavedOutfits() {
        Set<String> savedOutfitsSet = sharedPreferences.getStringSet(PREF_OUTFITS, new HashSet<>());
        savedOutfits = new ArrayList<>();

        for (String outfit : savedOutfitsSet) {
            String[] parts = outfit.split(",");
            int top = Integer.parseInt(parts[0]);
            int bottom = Integer.parseInt(parts[1]);
            savedOutfits.add(new int[]{top, bottom});
        }
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

    private final ActivityResultLauncher<Intent> imagePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                    Uri selectedImageUri = result.getData().getData();
                    if (selectedImageUri != null) {
                        profilePhoto.setImageURI(selectedImageUri);
                    }
                }
            });

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        imagePickerLauncher.launch(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage();
            } else {
                // Add Later
            }
        }
    }

    private void openSettingsActivity() {
        Intent intent = new Intent(requireActivity(), SettingsActivity.class);
        startActivity(intent);
    }
}
