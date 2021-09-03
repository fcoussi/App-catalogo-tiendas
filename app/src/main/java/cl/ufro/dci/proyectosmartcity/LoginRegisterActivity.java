package cl.ufro.dci.proyectosmartcity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Arrays;
import android.widget.Toast;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseUser;
import java.util.List;


public class LoginRegisterActivity extends AppCompatActivity {

    List<AuthUI.IdpConfig> providers;
    private static final String TAG = "LoginRegisterActivity";
    private static final int RC_SIGN_IN = 712;
     FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // cambio de la autentificacion funciona de la misma manera pero para poder cerrar la sesion tuve que
        //dejarla en una clase distinta al Main
        super.onCreate(savedInstanceState);
        fAuth=FirebaseAuth.getInstance();

        // Formas de autenticación en la App
        providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build()
        );

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.comercio)
                        .setTheme(R.style.LoginTheme)
                        .build(),
                RC_SIGN_IN);


    }
    /*RESULTADO GENERADO DE LA ACTIVIDAD*/

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fAuth=FirebaseAuth.getInstance();

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
                startActivity(new Intent(this, MainActivity.class));
                this.finish();


            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = fAuth.getInstance().getCurrentUser();

                //Show Email in Toast
                Toast.makeText(this, "" + user.getEmail(), Toast.LENGTH_SHORT).show();

                // btn_sign_out.setEnabled(true);

            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...

                Toast.makeText(this, "" + response.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

    }

    //Aunque cerremos la app la sesion del user no cierra hasta que el la cierra asi no se hay que volver a autentificar
    // a cada momento
    @Override
    protected void onStart(){
        super.onStart();
        if(fAuth.getCurrentUser() !=null){
            startActivity(new Intent(LoginRegisterActivity.this,MainActivity.class));
        }
    }

}
