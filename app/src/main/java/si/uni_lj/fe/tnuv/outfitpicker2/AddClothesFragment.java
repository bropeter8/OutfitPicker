package si.uni_lj.fe.tnuv.outfitpicker2;

// Import ImageStorageUtil at the top
import si.uni_lj.fe.tnuv.outfitpicker2.ImageStorageUtil;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import si.uni_lj.fe.tnuv.outfitpicker2.R;

public class AddClothesFragment extends Fragment {

    private static final String TAG = "AddClothesFragment";
    private static final int REQUEST_CAMERA_PERMISSION = 100;
    private String currentPhotoPath;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Log.d(TAG, "Camera permission granted");
                    dispatchTakePictureIntent();
                } else {
                    Log.d(TAG, "Camera permission denied");
                    Toast.makeText(getContext(), "Camera permission denied", Toast.LENGTH_SHORT).show();
                }
            });

    private final ActivityResultLauncher<Intent> takePictureLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == getActivity().RESULT_OK) {
                    // The photo was taken and saved to the currentPhotoPath
                    Log.d(TAG, "Photo saved at: " + currentPhotoPath);
                    ImageStorageUtil.addImagePath(requireContext(), currentPhotoPath);
                    Toast.makeText(getContext(), "Photo saved: " + currentPhotoPath, Toast.LENGTH_SHORT).show();
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_clothes, container, false);

        // Find buttons by their IDs
        MaterialButton buttonPhotoTop = view.findViewById(R.id.button_photo_top);
        MaterialButton buttonPickTop = view.findViewById(R.id.button_pick_top);
        MaterialButton buttonPhotoBottoms = view.findViewById(R.id.button_photo_bottoms);
        MaterialButton buttonPickBottoms = view.findViewById(R.id.button_pick_bottoms);
        ImageButton backButton = view.findViewById(R.id.back_button);

        // Set click listeners for buttons
        buttonPhotoTop.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Requesting camera permission");
                requestPermissionLauncher.launch(Manifest.permission.CAMERA);
            } else {
                Log.d(TAG, "Camera permission already granted");
                dispatchTakePictureIntent();
            }
        });

        buttonPickTop.setOnClickListener(v -> {
            // TODO: Implement action for picking an existing top
            Toast.makeText(getContext(), "Pick a Top", Toast.LENGTH_SHORT).show();
        });

        buttonPhotoBottoms.setOnClickListener(v -> {
            // TODO: Implement action for taking photo of bottoms
            Toast.makeText(getContext(), "Take Photo of Bottoms", Toast.LENGTH_SHORT).show();
        });

        buttonPickBottoms.setOnClickListener(v -> {
            // TODO: Implement action for picking existing bottoms
            Toast.makeText(getContext(), "Pick Bottoms", Toast.LENGTH_SHORT).show();
        });

        backButton.setOnClickListener(v -> {
            // Navigate back to the previous fragment
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.e(TAG, "Error creating file: " + ex.getMessage());
                Toast.makeText(getContext(), "Error creating file", Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(requireContext(),
                        "si.uni_lj.fe.tnuv.outfitpicker2.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                Log.d(TAG, "Starting camera intent");
                takePictureLauncher.launch(takePictureIntent);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.GERMANY).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}