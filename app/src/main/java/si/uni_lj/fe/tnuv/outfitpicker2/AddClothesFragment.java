package si.uni_lj.fe.tnuv.outfitpicker2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddClothesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_clothes, container, false);

        // Find buttons by their IDs
        LinearLayout buttonPhotoTop = view.findViewById(R.id.button_photo_top);
        LinearLayout buttonPickTop = view.findViewById(R.id.button_pick_top);
        LinearLayout buttonPhotoBottoms = view.findViewById(R.id.button_photo_bottoms);
        LinearLayout buttonPickBottoms = view.findViewById(R.id.button_pick_bottoms);

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

        return view;
    }
}