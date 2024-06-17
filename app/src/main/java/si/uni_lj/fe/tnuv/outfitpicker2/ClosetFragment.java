package si.uni_lj.fe.tnuv.outfitpicker2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClosetFragment extends Fragment {

    private RecyclerView recyclerView;
    private ClothesAdapter adapter;
    private List<String> imagePaths;
    private FloatingActionButton fabAddClothes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_closet, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        fabAddClothes = view.findViewById(R.id.fab_add_clothes);

        // Retrieve stored image paths using ImageStorageUtil
        Set<String> imagePathsSet = ImageStorageUtil.getImagePaths(requireContext());
        imagePaths = new ArrayList<>(imagePathsSet);

        // Setup RecyclerView adapter and display images
        adapter = new ClothesAdapter(imagePaths);
        recyclerView.setAdapter(adapter);

        // Use GridLayoutManager with 2 columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        fabAddClothes.setOnClickListener(v -> showAddClothesFragment());

        return view;
    }

    private void showAddClothesFragment() {
        AddClothesFragment addClothesFragment = new AddClothesFragment();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, addClothesFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}