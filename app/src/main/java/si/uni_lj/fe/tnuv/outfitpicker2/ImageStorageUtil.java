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
    private static final String KEY_IMAGE_PATHS = "imagePaths";
    private static final Gson gson = new Gson();

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    // Call this method once to clear old data
    public static void clearOldData(Context context) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.remove(KEY_IMAGE_PATHS);
        editor.apply();
        Log.d("ImageStorageUtil", "Cleared old SharedPreferences data");
    }

    public static void addImagePath(Context context, String imagePath) {
        List<String> imagePaths = getImagePaths(context);
        imagePaths.add(imagePath);

        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        String json = gson.toJson(imagePaths);
        editor.putString(KEY_IMAGE_PATHS, json);
        editor.apply();

        Log.d("ImageStorageUtil", "Added image path: " + imagePath);
        Log.d("ImageStorageUtil", "Current image paths: " + imagePaths);
    }

    public static List<String> getImagePaths(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        String json = sharedPreferences.getString(KEY_IMAGE_PATHS, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        List<String> imagePaths = gson.fromJson(json, type);
        if (imagePaths == null) {
            imagePaths = new ArrayList<>();
        }
        Log.d("ImageStorageUtil", "Retrieved image paths: " + imagePaths);
        return imagePaths;
    }
}
