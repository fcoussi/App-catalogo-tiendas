package cl.ufro.dci.proyectosmartcity;


import android.os.AsyncTask;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;


public class FirebaseAsyncTask extends AsyncTask <String, String, String> implements ValueEventListener {

    private ComunicadorBDFirebase comunicadorBDFirebase;
    private String respuesta;

    public FirebaseAsyncTask(ComunicadorBDFirebase comunicator) {
        comunicadorBDFirebase = comunicator;
    }
  /*Funcion de AsyncTask en donde se aplican las acciones*/
    @Override
    protected String doInBackground(String... strings) {
        respuesta = "";
        if (strings[0].equals("getDatosComercios")) {
            FirebaseDatabase bd = FirebaseDatabase.getInstance();
            DatabaseReference referenciaBD = bd.getReference();
            referenciaBD.addValueEventListener(this);
            int segundosDeEsperaMaximo = 15;
            int intentos = 0;
            while (respuesta.equals("")) {
                if (intentos < segundosDeEsperaMaximo) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    intentos++;
                } else {
                    break;
                }

            }
        }
        return respuesta;
    }

    /* Resultado obtenido en doInBackground es entregado en este método*/
    @Override
    public void onPostExecute(String resul) {
        comunicadorBDFirebase.enObtencionDeDatos(resul);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        respuesta = new Gson().toJson((snapshot.getValue()));
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        //No se pudo obtener los datos
        //No se consideró utilizar éste método
    }

}
