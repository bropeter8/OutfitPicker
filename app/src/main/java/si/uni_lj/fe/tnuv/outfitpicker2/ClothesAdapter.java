package si.uni_lj.fe.tnuv.outfitpicker2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ClothesAdapter extends RecyclerView.Adapter<ClothesAdapter.ClothesViewHolder> {

    private List<ClothingItem> clothingItems;

    public ClothesAdapter(List<ClothingItem> clothingItems) {
        this.clothingItems = clothingItems;
    }

    @NonNull
    @Override
    public ClothesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clothing, parent, false);
        return new ClothesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClothesViewHolder holder, int position) {
        ClothingItem clothingItem = clothingItems.get(position);
        holder.imageView.setImageResource(clothingItem.getImageResourceId());
    }

    @Override
    public int getItemCount() {
        return clothingItems.size();
    }

    public static class ClothesViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ClothesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }
}