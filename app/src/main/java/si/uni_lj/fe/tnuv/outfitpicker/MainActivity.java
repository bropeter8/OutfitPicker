package si.uni_lj.fe.tnuv.outfitpicker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up Navigation Controller
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        // Set up AppBarConfiguration with top-level destinations
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_color_picker, R.id.navigation_closet, R.id.navigation_profile)
                .build();

        // Set up ActionBar with Navigation Controller
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        // Set up Bottom Navigation View with Navigation Controller
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        NavigationUI.setupWithNavController(bottomNav, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
