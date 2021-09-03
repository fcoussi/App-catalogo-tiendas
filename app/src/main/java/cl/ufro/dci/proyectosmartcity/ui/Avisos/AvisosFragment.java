
package cl.ufro.dci.proyectosmartcity.ui.Avisos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;

import java.util.ArrayList;
import cl.ufro.dci.proyectosmartcity.ComunicadorBDFirebase;
import cl.ufro.dci.proyectosmartcity.FirebaseAsyncTask;
import cl.ufro.dci.proyectosmartcity.R;
import cl.ufro.dci.proyectosmartcity.model.Comercio;


public class AvisosFragment extends Fragment implements ComunicadorBDFirebase, AvisosViewModel.RecyclerItemClick{

    RecyclerView recycler;
    ArrayList<Hortaliza> listaComercio;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*
            Obteniendo vista
        */
        View vista = inflater.inflate(R.layout.fragment_avisos, container, false);

        recycler = (RecyclerView) vista.findViewById(R.id.RecyclerId);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        // Realizando consulta base de datos
        FirebaseAsyncTask bdAsyncTask = new FirebaseAsyncTask(this);
        bdAsyncTask.execute("getDatosComercios");

        // Retornando vista Layout fragment_avisos
        return vista;
    }

    // Método utilizado para llenar el arreglo desde FireBase
    public void enObtencionDeDatos(String resul) {
        Gson gson = new Gson();
        //Obtener array de Comercio, a partir del JSON Array resul
        Comercio[] comercios = gson.fromJson(resul, Comercio[].class);
        listaComercio = new ArrayList<>();

        //Agregando datos Array List
        for (int n = 0; n<= 9; n++) {
            listaComercio.add(new Hortaliza(comercios[n].getNombre(), comercios[n].getServicios(), comercios[n].getDireccion(),
                    comercios[n].getFono(),comercios[n].getUrl()));
        }

        // Enviando los datos al Adaptador
        AvisosViewModel adaptador = new AvisosViewModel(listaComercio,this);
        recycler.setAdapter(adaptador);
    }

    @Override
    public void itemClick(Hortaliza item, View view) {

        /*
            Iniciar Actividad DetailComercio cuando realizamos click en un item view
        */
        
        Intent intent = new Intent(getContext(), DetailComercios.class);
        intent.putExtra("itemDetail", item);
        startActivity(intent);
    }


}




