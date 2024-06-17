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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ClosetFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_closet, container, false);
        FloatingActionButton fabAddClothes = view.findViewById(R.id.fab_add_clothes);

        fabAddClothes.setOnClickListener(v -> showAddClothesFragment());

        // TODO: Initialize and set up the RecyclerView with existing clothes

        return view;
    }

    private void showAddClothesFragment() {
        FragmentManager fragmentManager = getParentFragmentManager();
        AddClothesFragment addClothesFragment = new AddClothesFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        transaction.replace(R.id.frame_layout, addClothesFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}