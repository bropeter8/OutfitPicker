package si.uni_lj.fe.tnuv.outfitpicker2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClosetFragment extends Fragment {

    private RecyclerView recyclerView;
    private ClothesAdapter adapter;
    private List<ClothingItem> clothingItems;
    private FloatingActionButton fabAddClothes;

    private Button buttonAll, buttonTops, buttonBottoms;
    private Button selectedButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_closet, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        fabAddClothes = view.findViewById(R.id.fab_add_clothes);
        buttonAll = view.findViewById(R.id.button_all);
        buttonTops = view.findViewById(R.id.button_tops);
        buttonBottoms = view.findViewById(R.id.button_bottoms);

        // Set default selected button
        selectedButton = buttonAll;
        updateButtonStyles();

        // Clear old SharedPreferences data (only call this once, you can comment it out after the first run)
        ImageStorageUtil.clearOldData(requireContext());

        // Retrieve stored image paths using ImageStorageUtil
        clothingItems = ImageStorageUtil.getClothingItems(requireContext());

        // Setup RecyclerView adapter and display images
        adapter = new ClothesAdapter(clothingItems);
        recyclerView.setAdapter(adapter);

        // Use GridLayoutManager with 2 columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        fabAddClothes.setOnClickListener(v -> showAddClothesFragment());

        // Set up button click listeners for filtering
        buttonAll.setOnClickListener(v -> {
            selectedButton = buttonAll;
            updateButtonStyles();
            showAllClothes();
        });
        buttonTops.setOnClickListener(v -> {
            selectedButton = buttonTops;
            updateButtonStyles();
            showTops();
        });
        buttonBottoms.setOnClickListener(v -> {
            selectedButton = buttonBottoms;
            updateButtonStyles();
            showBottoms();
        });

        return view;
    }

    private void showAddClothesFragment() {
        AddClothesFragment addClothesFragment = new AddClothesFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, addClothesFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void showAllClothes() {
        adapter.setClothingItems(clothingItems);
        adapter.notifyDataSetChanged();
    }

    private void showTops() {
        List<ClothingItem> tops = new ArrayList<>();
        for (ClothingItem item : clothingItems) {
            if (item.isTop()) {
                tops.add(item);
            }
        }
        adapter.setClothingItems(tops);
        adapter.notifyDataSetChanged();
    }

    private void showBottoms() {
        List<ClothingItem> bottoms = new ArrayList<>();
        for (ClothingItem item : clothingItems) {
            if (!item.isTop()) {
                bottoms.add(item);
            }
        }
        adapter.setClothingItems(bottoms);
        adapter.notifyDataSetChanged();
    }

    private void updateButtonStyles() {
        Button[] buttons = {buttonAll, buttonTops, buttonBottoms};
        for (Button button : buttons) {
            if (button == selectedButton) {
                button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.purple_500));
                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
                button.setAlpha(1f); // Fully opaque
            } else {
                button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.purple_200));
                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
                button.setAlpha(0.8f); // Lower opacity
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Reload clothing items in case new images were added
        clothingItems = ImageStorageUtil.getClothingItems(requireContext());
        adapter.setClothingItems(clothingItems);
        adapter.notifyDataSetChanged();

        // Log the loaded clothing items
        for (ClothingItem item : clothingItems) {
            Log.d("ClosetFragment", "Loaded item: " + item.getImagePath() + " isTop: " + item.isTop());
        }

        Log.d("ClosetFragment", "Loaded clothing items on resume: " + clothingItems);
    }
}
