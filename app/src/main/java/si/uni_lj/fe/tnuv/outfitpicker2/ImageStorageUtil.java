package si.uni_lj.fe.tnuv.outfitpicker2;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ImageStorageUtil {

    private static final String PREF_NAME = "MyAppPrefs";
    private static final String KEY_CLOTHING_ITEMS = "clothingItems";
    private static final Gson gson = new Gson();

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Call this method once to clear old data
    public static void clearOldData(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(KEY_CLOTHING_ITEMS);
        editor.apply();
        Log.d("ImageStorageUtil", "Cleared old SharedPreferences data");
    }

    public static void addClothingItem(Context context, ClothingItem clothingItem) {
        List<ClothingItem> clothingItems = getClothingItems(context);
        clothingItems.add(clothingItem);

        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        String json = gson.toJson(clothingItems);
        editor.putString(KEY_CLOTHING_ITEMS, json);
        editor.apply();

        Log.d("ImageStorageUtil", "Added clothing item: " + clothingItem.getImagePath() + " isTop: " + clothingItem.isTop());
        Log.d("ImageStorageUtil", "Current clothing items after addition: " + clothingItems);
    }

    public static List<ClothingItem> getClothingItems(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        String json = sharedPreferences.getString(KEY_CLOTHING_ITEMS, null);
        Type type = new TypeToken<ArrayList<ClothingItem>>() {}.getType();
        List<ClothingItem> clothingItems = gson.fromJson(json, type);
        if (clothingItems == null) {
            clothingItems = new ArrayList<>();
        }
        Log.d("ImageStorageUtil", "Retrieved image paths: " + clothingItems);
        return clothingItems;
    }
}
