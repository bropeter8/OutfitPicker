package si.uni_lj.fe.tnuv.outfitpicker2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PickerFragment extends Fragment {

    private static final String TAG = "PickerFragment";
    private static final String PREFS_NAME = "outfit_prefs";
    private static final String PREF_OUTFITS = "saved_outfits";

    private ViewPager2 viewPagerTop, viewPagerBottom;
    private Button saveOutfitButton;
    private FloatingActionButton arrowLeftTop, arrowRightTop, arrowLeftBottom, arrowRightBottom;
    private PickerClothesAdapter vpAdapterTop, vpAdapterBottom;
    private SharedPreferences sharedPreferences;
    private List<ClothingItem> tops, bottoms;

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
        arrowLeftTop = view.findViewById(R.id.arrow_left_top);
        arrowRightTop = view.findViewById(R.id.arrow_right_top);
        arrowLeftBottom = view.findViewById(R.id.arrow_left_bottom);
        arrowRightBottom = view.findViewById(R.id.arrow_right_bottom);

        loadClothingItems();

        // Check if either tops or bottoms are empty and show a message
        if (tops.isEmpty() || bottoms.isEmpty()) {
            Toast.makeText(getContext(), "Please add clothes to the Closet by clicking the + icon.", Toast.LENGTH_LONG).show();
        }

        // Set up adapters for tops and bottoms
        vpAdapterTop = new PickerClothesAdapter(tops);
        viewPagerTop.setAdapter(vpAdapterTop);

        vpAdapterBottom = new PickerClothesAdapter(bottoms);
        viewPagerBottom.setAdapter(vpAdapterBottom);

        // Set up arrow buttons to navigate through ViewPager2
        arrowLeftTop.setOnClickListener(v -> viewPagerTop.setCurrentItem(viewPagerTop.getCurrentItem() - 1, true));
        arrowRightTop.setOnClickListener(v -> viewPagerTop.setCurrentItem(viewPagerTop.getCurrentItem() + 1, true));
        arrowLeftBottom.setOnClickListener(v -> viewPagerBottom.setCurrentItem(viewPagerBottom.getCurrentItem() - 1, true));
        arrowRightBottom.setOnClickListener(v -> viewPagerBottom.setCurrentItem(viewPagerBottom.getCurrentItem() + 1, true));

        saveOutfitButton.setOnClickListener(v -> saveSelectedOutfit());

        loadSavedOutfits();

        return view;
    }

    private void loadClothingItems() {
        List<ClothingItem> allClothingItems = ImageStorageUtil.getClothingItems(requireContext());
        tops = new ArrayList<>();
        bottoms = new ArrayList<>();
        for (ClothingItem item : allClothingItems) {
            if (item.isTop()) {
                tops.add(item);
            } else {
                bottoms.add(item);
            }
        }
        Log.d(TAG, "Tops: " + tops.size() + ", Bottoms: " + bottoms.size());
    }

    private void saveSelectedOutfit() {
        int selectedTop = viewPagerTop.getCurrentItem();
        int selectedBottom = viewPagerBottom.getCurrentItem();

        ClothingItem selectedTopItem = tops.get(selectedTop);
        ClothingItem selectedBottomItem = bottoms.get(selectedBottom);

        String outfit = selectedTopItem.getImagePath() + "," + selectedBottomItem.getImagePath();

        Set<String> savedOutfits = sharedPreferences.getStringSet(PREF_OUTFITS, new HashSet<>());
        savedOutfits.add(outfit);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(PREF_OUTFITS, savedOutfits);
        editor.apply();

        Toast.makeText(requireContext(), "Outfit saved successfully", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Saved outfit: " + outfit);
    }

    private void loadSavedOutfits() {
        // Retrieve saved outfits from SharedPreferences
        Set<String> savedOutfits = sharedPreferences.getStringSet(PREF_OUTFITS, new HashSet<>());

        // For demonstration, you might parse and display them
        if (!savedOutfits.isEmpty()) {
            // Get the last saved outfit
            String lastSavedOutfit = savedOutfits.iterator().next();
            String[] outfitParts = lastSavedOutfit.split(",");
            String savedTopPath = outfitParts[0];
            String savedBottomPath = outfitParts[1];

            int savedTopPosition = getPositionByPath(tops, savedTopPath);
            int savedBottomPosition = getPositionByPath(bottoms, savedBottomPath);

            // Ensure viewpagers are set to the saved positions
            viewPagerTop.setCurrentItem(savedTopPosition);
            viewPagerBottom.setCurrentItem(savedBottomPosition);
            Log.d(TAG, "Loaded saved outfit: Top=" + savedTopPosition + ", Bottom=" + savedBottomPosition);
        }
    }

    private int getPositionByPath(List<ClothingItem> items, String path) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getImagePath().equals(path)) {
                return i;
            }
        }
        return 0; // Default to the first item if not found
    }
}
