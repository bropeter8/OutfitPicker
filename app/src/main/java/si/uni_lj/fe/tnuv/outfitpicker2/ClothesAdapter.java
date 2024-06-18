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

public class ClothesAdapter extends RecyclerView.Adapter<ClothesAdapter.ImageViewHolder> {

    private List<ClothingItem> clothingItems;

    public ClothesAdapter(List<ClothingItem> clothingItems) {
        this.clothingItems = clothingItems;
    }

    public void setClothingItems(List<ClothingItem> clothingItems) {
        this.clothingItems = clothingItems;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_closet_clothing, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        ClothingItem clothingItem = clothingItems.get(position);
        String imagePath = clothingItem.getImagePath();
        Log.d("ClothesAdapter", "Loading image: " + imagePath + " isTop: " + clothingItem.isTop());

        // Check if the file path is valid
        File imgFile = new File(imagePath);
        if (imgFile.exists()) {
            Uri imageUri = Uri.fromFile(imgFile);

            // Use Glide to load the image
            Glide.with(holder.imageView.getContext())
                    .load(imageUri)
                    .into(holder.imageView);

            // Log to confirm the URI
            Log.d("ClothesAdapter", "Image URI: " + imageUri.toString());
        } else {
            Log.e("ClothesAdapter", "File does not exist: " + imagePath);
        }
    }

    @Override
    public int getItemCount() {
        return clothingItems.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }
}