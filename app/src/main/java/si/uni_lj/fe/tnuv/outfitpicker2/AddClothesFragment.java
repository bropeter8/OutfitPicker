package si.uni_lj.fe.tnuv.outfitpicker2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
public class AddClothesFragment extends Fragment {

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
            // TODO: Implement action for taking photo of top
            Toast.makeText(getContext(), "Take Photo of Top", Toast.LENGTH_SHORT).show();
        });

        buttonPickTop.setOnClickListener(v -> {
            // TODO: Implement action for taking photo of bottoms
            Toast.makeText(getContext(), "Pick a Top", Toast.LENGTH_SHORT).show();
        });

        buttonPhotoBottoms.setOnClickListener(v -> {
            // TODO: Implement action for picking existing top
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
}