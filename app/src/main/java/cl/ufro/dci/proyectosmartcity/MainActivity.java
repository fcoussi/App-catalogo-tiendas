
package cl.ufro.dci.proyectosmartcity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements FirebaseAuth.AuthStateListener,
        View.OnClickListener{

    private AppBarConfiguration mAppBarConfiguration;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        /*setSupportActionBar establece la barra de herramientas como la
          barra de la app de la actividad.*/
        setSupportActionBar(toolbar);

        /*Se verifica si el usuario es null, si es correcto se llama al metodo
        * */
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startLoginActivity();
        }

        /*
        Se crea componente Drawer y se asignan los layouts a mostrar al momento
        de acer click en un item Drawer.
        */
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_perfil, R.id.nav_avisos, R.id.nav_mapa, R.id.nav_mensajes)
                .setDrawerLayout(drawer)
                .build();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
            switch (menuItem.getItemId()){
                case R.id.nav_cierre_sesion:
                    AuthUI.getInstance().signOut(getApplication())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        startLoginActivity();
                                    } else {
                                        Log.e(TAG, "onComplete:", task.getException());
                                    }
                                }
                            });
                    break;
            }
            return true;
        }
        });

        /*NavController gestiona la nevegación de la aplicación dentro un NavHost*/
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        /*NavigationUI conecta barra de navegación con su NavController*/
        /*se configura ActionBar devuelto por AppCompatActivity para uso con NavController*/
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        /*Se configura NavigationView para usar con NavController*/
        NavigationUI.setupWithNavController(navigationView, navController);


    }

    /*Nos dirije a la activity LoginRegisterActivity para visualizar la vista autenticación*/
    private void startLoginActivity() {
        Intent intent = new Intent(this, LoginRegisterActivity.class);
        startActivity(intent);
        finish();
    }

    /*Inflando el menuItem*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.aviso, menu);
        getMenuInflater().inflate(R.menu.cerrar_sesion, menu);
        return true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //Aca se cierra sesion desde el menu de la app esto es opcional en realidad
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_cerrarSesion:
                Toast.makeText(this, "Logout", Toast.LENGTH_LONG).show();
                AuthUI.getInstance().signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    startLoginActivity();
                                } else {
                                    Log.e(TAG, "onComplete:", task.getException());
                                }
                            }
                        });


                return true;
            case R.id.nav_perfil:
                Toast.makeText(this, "Profile", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    // Se cierra la sesión desde el toolbar, pero haciendo click con un Button
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button_sesion:
                Toast.makeText(this, "Logout", Toast.LENGTH_LONG).show();
                AuthUI.getInstance().signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    startLoginActivity();
                                } else {
                                    Log.e(TAG, "onComplete:", task.getException());
                                }
                            }
                        });
                break;
            case R.id.button_sesion2:
                Toast.makeText(this, "Profile", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startLoginActivity();
            return;
        }

    }
}