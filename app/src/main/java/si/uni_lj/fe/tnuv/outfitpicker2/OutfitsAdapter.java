package si.uni_lj.fe.tnuv.outfitpicker2;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.List;

public class OutfitsAdapter extends RecyclerView.Adapter<OutfitsAdapter.OutfitViewHolder> {

    private List<String[]> savedOutfits;

    public OutfitsAdapter(List<String[]> savedOutfits) {
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
        String[] outfit = savedOutfits.get(position);

        String topImagePath = outfit[0];
        String bottomImagePath = outfit[1];

        // Load images using Glide
        Glide.with(holder.topImageView.getContext())
                .load(topImagePath)
                .into(holder.topImageView);

        Glide.with(holder.bottomImageView.getContext())
                .load(bottomImagePath)
                .into(holder.bottomImageView);
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
