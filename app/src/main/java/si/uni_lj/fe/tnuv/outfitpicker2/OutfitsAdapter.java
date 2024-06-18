package si.uni_lj.fe.tnuv.outfitpicker2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OutfitsAdapter extends RecyclerView.Adapter<OutfitsAdapter.OutfitViewHolder> {

    private List<int[]> savedOutfits;
    private int[] tops = {R.drawable.blue_tshirt, R.drawable.black_tshirt}; // Update these as per your drawable resources
    private int[] bottoms = {R.drawable.black_jeans, R.drawable.blue_jeans}; // Update these as per your drawable resources

    public OutfitsAdapter(List<int[]> savedOutfits) {
        this.savedOutfits = savedOutfits;
    }

    @NonNull
    @Override
    public OutfitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_outfit, parent, false);
        return new OutfitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OutfitViewHolder holder, int position) {
        int[] outfit = savedOutfits.get(position);
        holder.topImageView.setImageResource(tops[outfit[0]]);
        holder.bottomImageView.setImageResource(bottoms[outfit[1]]);
    }

    @Override
    public int getItemCount() {
        return savedOutfits.size();
    }

    static class OutfitViewHolder extends RecyclerView.ViewHolder {
        ImageView topImageView, bottomImageView;

        public OutfitViewHolder(@NonNull View itemView) {
            super(itemView);
            topImageView = itemView.findViewById(R.id.top_image_view);
            bottomImageView = itemView.findViewById(R.id.bottom_image_view);
        }
    }
}
