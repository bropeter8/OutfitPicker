package si.uni_lj.fe.tnuv.outfitpicker2;

public class ClothingItem {
    private String imagePath;
    private boolean isTop;

    public ClothingItem(String imagePath, boolean isTop) {
        this.imagePath = imagePath;
        this.isTop = isTop;
    }

    public String getImagePath() {
        return imagePath;
    }

    public boolean isTop() {
        return isTop;
    }
}