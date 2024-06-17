package si.uni_lj.fe.tnuv.outfitpicker2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ClosetFragment extends Fragment {

    private FloatingActionButton fabAddClothes;
    private RecyclerView recyclerView;
    private ClothesAdapter clothesAdapter;
    private List<ClothingItem> clothingItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_closet, container, false);
        fabAddClothes = view.findViewById(R.id.fab_add_clothes);
        recyclerView = view.findViewById(R.id.recycler_view);

        fabAddClothes.setOnClickListener(v -> showAddClothesFragment());

        // Initialize clothing items
        initializeClothingItems();

        // Set up RecyclerView
        clothesAdapter = new ClothesAdapter(clothingItems);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // 2 columns in the grid
        recyclerView.setAdapter(clothesAdapter);

        return view;
    }

    private void initializeClothingItems() {
        clothingItems = new ArrayList<>();
        clothingItems.add(new ClothingItem(R.drawable.white_tshirt));
        clothingItems.add(new ClothingItem(R.drawable.black_tshirt));
        clothingItems.add(new ClothingItem(R.drawable.blue_jeans));
        clothingItems.add(new ClothingItem(R.drawable.blue_tshirt));
        clothingItems.add(new ClothingItem(R.drawable.black_jeans));
    }

    private void showAddClothesFragment() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        AddClothesFragment addClothesFragment = new AddClothesFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.add(android.R.id.content, addClothesFragment, "AddClothesFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }
}