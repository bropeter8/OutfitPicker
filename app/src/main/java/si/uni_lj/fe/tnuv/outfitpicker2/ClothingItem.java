package si.uni_lj.fe.tnuv.outfitpicker2;

public class ClothingItem {
    private int imageResourceId;

    public ClothingItem(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }
}