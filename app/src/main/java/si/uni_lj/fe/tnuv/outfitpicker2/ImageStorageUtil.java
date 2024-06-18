package si.uni_lj.fe.tnuv.outfitpicker2;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashSet;
import java.util.Set;

public class ImageStorageUtil {

    private static final String PREF_NAME = "MyAppPrefs";
    private static final String KEY_IMAGE_PATHS = "imagePaths";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static void addImagePath(Context context, String imagePath) {
        Set<String> imagePaths = getImagePaths(context);
        imagePaths.add(imagePath);

        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putStringSet(KEY_IMAGE_PATHS, imagePaths);
        editor.apply();
    }

    public static Set<String> getImagePaths(Context context) {
        return getSharedPreferences(context).getStringSet(KEY_IMAGE_PATHS, new HashSet<>());
    }
}