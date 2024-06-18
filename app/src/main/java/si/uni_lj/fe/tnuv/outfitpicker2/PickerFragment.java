package si.uni_lj.fe.tnuv.outfitpicker2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PickerFragment extends Fragment {

    private static final String PREFS_NAME = "outfit_prefs";
    private static final String PREF_OUTFITS = "saved_outfits";

    private ViewPager2 viewPagerTop, viewPagerBottom;
    private Button saveOutfitButton;
    private VPAdapter vpAdapterTop, vpAdapterBottom;
    private SharedPreferences sharedPreferences;

    public PickerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picker, container, false);

        viewPagerTop = view.findViewById(R.id.viewpager_top);
        viewPagerBottom = view.findViewById(R.id.viewpager_bottom);
        saveOutfitButton = view.findViewById(R.id.save_outfit_button);

        // Assuming you have a list of drawable resources for tops and bottoms
        int[] tops = {R.drawable.blue_tshirt, R.drawable.black_tshirt};
        int[] bottoms = {R.drawable.black_jeans, R.drawable.blue_jeans};

        ArrayList<ViewPagerItem> viewPagerTopItems = new ArrayList<>();
        for (int top : tops) {
            viewPagerTopItems.add(new ViewPagerItem(top));
        }
        vpAdapterTop = new VPAdapter(viewPagerTopItems);
        viewPagerTop.setAdapter(vpAdapterTop);

        ArrayList<ViewPagerItem> viewPagerBottomItems = new ArrayList<>();
        for (int bottom : bottoms) {
            viewPagerBottomItems.add(new ViewPagerItem(bottom));
        }
        vpAdapterBottom = new VPAdapter(viewPagerBottomItems);
        viewPagerBottom.setAdapter(vpAdapterBottom);

        saveOutfitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSelectedOutfit();
            }
        });

        loadSavedOutfits();

        return view;
    }

    private void saveSelectedOutfit() {
        int selectedTop = viewPagerTop.getCurrentItem();
        int selectedBottom = viewPagerBottom.getCurrentItem();

        String outfit = selectedTop + "," + selectedBottom;

        Set<String> savedOutfits = sharedPreferences.getStringSet(PREF_OUTFITS, new HashSet<>());
        savedOutfits.add(outfit);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(PREF_OUTFITS, savedOutfits);
        editor.apply();

        Toast.makeText(requireContext(), "Outfit saved successfully", Toast.LENGTH_SHORT).show();
    }

    private void loadSavedOutfits() {
        // Retrieve saved outfits from SharedPreferences
        Set<String> savedOutfits = sharedPreferences.getStringSet(PREF_OUTFITS, new HashSet<>());

        // For demonstration, you might parse and display them
        if (!savedOutfits.isEmpty()) {
            // Get the last saved outfit
            String lastSavedOutfit = savedOutfits.iterator().next();
            String[] outfitParts = lastSavedOutfit.split(",");
            int savedTopPosition = Integer.parseInt(outfitParts[0]);
            int savedBottomPosition = Integer.parseInt(outfitParts[1]);

            // Ensure viewpagers are set to the saved positions
            viewPagerTop.setCurrentItem(savedTopPosition);
            viewPagerBottom.setCurrentItem(savedBottomPosition);
        }
    }
}
