package si.uni_lj.fe.tnuv.outfitpicker.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import si.uni_lj.fe.tnuv.outfitpicker.R;

public class ColorPickerFragment extends Fragment {
    public ColorPickerFragment() {
        // Required empty public constructor
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Example of triggering navigation programmatically
        navigateToClosetFragment();
    }

    private void navigateToClosetFragment() {
        // Navigate to ClosetFragment
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.navigation_closet);
    }
}

