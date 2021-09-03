package cl.ufro.dci.proyectosmartcity.ui.mapa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cl.ufro.dci.proyectosmartcity.ComunicadorEnvioEntidad;
import cl.ufro.dci.proyectosmartcity.ComunicadorToken;
import cl.ufro.dci.proyectosmartcity.ControladorAPI;
import cl.ufro.dci.proyectosmartcity.EnvioEntidadAsyncTask;
import cl.ufro.dci.proyectosmartcity.FirebaseAsyncTask;
import cl.ufro.dci.proyectosmartcity.ComunicadorBDFirebase;
import cl.ufro.dci.proyectosmartcity.R;
import cl.ufro.dci.proyectosmartcity.model.Comercio;
import cl.ufro.dci.proyectosmartcity.model.Coordenada;
import cl.ufro.dci.proyectosmartcity.model.fiware.EntidadComercio;

public class MapaFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, ComunicadorBDFirebase, Runnable, ComunicadorToken,
        ComunicadorEnvioEntidad {

    private GoogleMap mapa;
    private final int CODIGO_ACCESO_UBICACION = 100;
    private ArrayList<Marker> ubicacionesComerciales;
    private final double LATITUD_TEMUCO = -38.736429;
    private final double LONGITUD_TEMUCO = -72.590973;
    private Comercio[] listaComercios;
    private Marker marcadorComercio;
    private LatLng latLngComercio;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ubicacionesComerciales = new ArrayList<>();
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mapa = googleMap;

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        //Comprobar que existe el permiso para obtener la ubicación
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //Hay permiso para acceder a la ubicación

            //Mover la cámara del mapa a Temuco
            LatLng posicionActual = new LatLng(LATITUD_TEMUCO, LONGITUD_TEMUCO);
            mapa.moveCamera(CameraUpdateFactory.newLatLng(posicionActual));
            //Dar zoom al mapa (los dispositivos soportan un valor máximo,
            // tener en consideración aquello)
            mapa.moveCamera(CameraUpdateFactory.zoomTo(12f));

            mapa.setOnMarkerClickListener(this);

            //Solicitar los datos a la Base de Datos
            FirebaseAsyncTask bdAsyncTask = new FirebaseAsyncTask(this);
            bdAsyncTask.execute("getDatosComercios");

        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
            //Mostrar al usuario (en una UI, aunque de momento se utiliza un Toast)
            // del porque debería permitir el acceso a su ubicación
            Toast.makeText(getActivity(),
                    "Es necesario conocer su ubicación para mostrar los puntos comerciales",
                    Toast.LENGTH_SHORT).show();

        } else {
            //Mostrar un mensaje solicitando accesso
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    CODIGO_ACCESO_UBICACION);

        }
    }

    /**
     * Calcula la latitud y longitud a partir de una direccion física dada
     *
     * @param direccionFisica dirección física, la cual se le añade un sufijo de ", Ciudad, País"
     * @return un objeto Address con la latitud y longitud
     */
    private Address obtenerLatLng(String direccionFisica) {

        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        List<Address> addresses;
        try {
            //Obtener una lista de direcciones encontradas por la dirección proporcionada
            addresses = geocoder.getFromLocationName(
                    direccionFisica + ", Temuco, Chile", 1);

            //Retornar la primera dirección de las encontradas
            if (addresses != null && addresses.size() > 0) {
                Address direccion = addresses.get(0);
                return direccion;
            }
        } catch (IOException e) {
            //Si la red no está disponible, o si ocurre cualquier otro problema de I/O
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            //Si el nombre de la ubicación dada es nulo
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        TextView tvNombreComercio;
        TextView tvFonoComercio;
        TextView tvDireccionComercio;
        TextView tvServiciosComercio;

        for (Marker marcador : ubicacionesComerciales) {
            if (marker.getId().equals(marcador.getId())) {
                BottomSheetDialog hojaDialogo = new BottomSheetDialog(getActivity(),
                        R.style.BottomSheetDialogTheme);
                View vistaBottomSheet = LayoutInflater.from(
                        getActivity().getApplicationContext()).inflate(R.layout.bottom_sheet_mapa,
                        (LinearLayout) getActivity().findViewById(R.id.linear_layout_bottom_sheet));

                tvNombreComercio = vistaBottomSheet.findViewById(R.id.text_view_nombre_comercio);
                tvFonoComercio = vistaBottomSheet.findViewById(R.id.text_view_fono_comercio);
                tvDireccionComercio = vistaBottomSheet.findViewById(R.id.text_view_direccion_comercio);
                tvServiciosComercio = vistaBottomSheet.findViewById(R.id.text_view_servicio_comercio);

                for (Comercio comercio : listaComercios) {
                    if (comercio.getIdUbicacion().equals(marcador.getId())) {
                        tvDireccionComercio.setText(comercio.getDireccion());
                        tvNombreComercio.setText(comercio.getNombre());
                        tvFonoComercio.setText(comercio.getFono());
                        tvServiciosComercio.setText(comercio.getServicios());
                    }
                }
                hojaDialogo.setContentView(vistaBottomSheet);
                hojaDialogo.show();
            }
        }

        return false;
    }

    /**
     * Interfaz que permite la comunicación con la base de datos Firebase y el resultado que
     * este arroja. Se ejecuta una vez que se obtiene una respuesta por firebase
     *
     * @param resul resultado obtenido de la consulta, en formato JSON Array
     */
    @Override
    public void enObtencionDeDatos(String resul) {
        Gson gson = new Gson();

        //Obtener array de tipo Comercio, a partir del JSON Array recibido por parámetro
        listaComercios = gson.fromJson(resul, Comercio[].class);

        Toast.makeText(getActivity(), "Cargando Ubicaciones...", Toast.LENGTH_SHORT).show();
        new Thread(this).start();

    }

    /**
     * Método que marca las posiciones en el mapa a través de un Thread
     */
    @Override
    public void run() {
        Address direccion;
        double latitude;
        double longitude;

        for (Comercio comercio : listaComercios) {
            direccion = obtenerLatLng(comercio.getDireccion());
            if (direccion != null) {
                latitude = direccion.getLatitude();
                longitude = direccion.getLongitude();
                latLngComercio = new LatLng(latitude, longitude);
                comercio.setCoordenada(new Coordenada(latitude, longitude));

                Runnable cargaDeUbicaciones = new Runnable() {
                    @Override
                    public void run() {
                        //Hilo UI sincronizado con el Hilo Secundario
                        synchronized (this) {
                            marcadorComercio = mapa.addMarker(new MarkerOptions().position(latLngComercio));
                            this.notify();
                        }
                    }
                };

                //Hilo Secundario, o actual, sincronizado con Hilo UI
                synchronized (cargaDeUbicaciones) {

                    //Si el método getActivity() entrega un valor null, terminar el ciclo for
                    //Esto ocurre cuando se cambia de vista mientras se muestran las
                    // ubicaciones en el mapa
                    if (getActivity() == null) {
                        break;
                    }

                    //Añadir marcador al mapa, a través del Hilo UI
                    getActivity().runOnUiThread(cargaDeUbicaciones);

                    try {
                        cargaDeUbicaciones.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                comercio.setIdUbicacion(marcadorComercio.getId());
                ubicacionesComerciales.add(marcadorComercio);
            }
        }


        //Si el método getActivity() no entrega un valor null, mostrar Toast
        //Esto ocurre cuando se cambia de vista mientras se muestran las
        // ubicaciones en el mapa
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "Listo!", Toast.LENGTH_SHORT).show();
                }
            });
            ControladorAPI controladorToken = new ControladorAPI(this);
            controladorToken.execute();
        }
    }

    /**
     * Método de la interfaz ComunicadorToken
     * Ejecuta el envio de la entidad
     *
     * @param token el token de acceso
     */
    @Override
    public void enObtencionDeToken(String token) {
        //Comprobar si el token fue obtenido correctamente
        if (!token.equals("")) {
            EnvioEntidadAsyncTask envioEntidad =
                    new EnvioEntidadAsyncTask(this, token);
            envioEntidad.execute(new Gson().toJson(new EntidadComercio(listaComercios[8])));
        }
    }

    /**
     * Método de la interfaz ComunicadorEnvioEntidad
     * Muestra un Toast si el envío fue realizado exitosamente
     *
     * @param codigoDeEstado el código obtenido al realizar POST de la entidad
     */
    @Override
    public void enEnvioRealizado(int codigoDeEstado) {
        if (codigoDeEstado == 201) {
            Toast.makeText(getActivity(), "Entidad enviada!", Toast.LENGTH_SHORT).show();
        } else {
            if(codigoDeEstado==422) {
                Toast.makeText(getActivity(), "Entidad existente", Toast.LENGTH_SHORT).show();
            }
        }
    }
}