package si.uni_lj.fe.tnuv.outfitpicker2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PickerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PickerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PickerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PickerFragment newInstance(String param1, String param2) {
        PickerFragment fragment = new PickerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picker, container, false);
        ViewPager2 viewPagerTop = view.findViewById(R.id.viewpager_top);
        // Assuming you have a list of drawable resources for tops
        int[] tops = {R.drawable.blue_tshirt, R.drawable.black_tshirt};
        ArrayList<ViewPagerItem> viewPagerTopItems = new ArrayList<>();
        for (int top : tops) {
            ViewPagerItem item = new ViewPagerItem(top);
            viewPagerTopItems.add(item);
        }
        VPAdapter vpAdapterTop = new VPAdapter(viewPagerTopItems);
        viewPagerTop.setAdapter(vpAdapterTop);

        // Initialize and set up the ViewPager for bottoms
        ViewPager2 viewPagerBottom = view.findViewById(R.id.viewpager_bottom);
        // Assuming you have a list of drawable resources for bottoms
        int[] bottoms = {R.drawable.black_jeans, R.drawable.blue_jeans};
        ArrayList<ViewPagerItem> viewPagerBottomItems = new ArrayList<>();
        for (int bottom : bottoms) {
            ViewPagerItem item = new ViewPagerItem(bottom);
            viewPagerBottomItems.add(item);
        }
        VPAdapter vpAdapterBottom = new VPAdapter(viewPagerBottomItems);
        viewPagerBottom.setAdapter(vpAdapterBottom);

        // Set additional properties for both ViewPagers if needed
        // For example:
        viewPagerTop.setClipToPadding(false);
        viewPagerTop.setClipChildren(false);
        viewPagerTop.setOffscreenPageLimit(3);
        viewPagerTop.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        viewPagerBottom.setClipToPadding(false);
        viewPagerBottom.setClipChildren(false);
        viewPagerBottom.setOffscreenPageLimit(3);
        viewPagerBottom.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);

        return view;
    }
}