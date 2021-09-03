package cl.ufro.dci.proyectosmartcity;

import android.os.AsyncTask;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class EnvioEntidadAsyncTask extends AsyncTask<String, String, Integer> {

    private ComunicadorEnvioEntidad comunicadorEnvioEntidad;
    private String tokenAcceso;

    public EnvioEntidadAsyncTask(ComunicadorEnvioEntidad comunicadorEnvioEntidad, String tokenAcceso) {
        this.comunicadorEnvioEntidad = comunicadorEnvioEntidad;
        this.tokenAcceso = tokenAcceso;

    }

    @Override
    protected Integer doInBackground(String... entidadJSON) {
        return enviarEntidad(entidadJSON[0]);
    }

    @Override
    public void onPostExecute(Integer codigoDeEstado) {
        comunicadorEnvioEntidad.enEnvioRealizado(codigoDeEstado);
    }

    private int enviarEntidad(String entidadJSON) {
        try {
            URL url = new URL("https://fw-producer.smartaraucania.org/orion-north/v2/entities");
            HttpsURLConnection conexionHttps = (HttpsURLConnection) url.openConnection();

            //Establecer método POST
            conexionHttps.setDoOutput(true);

            conexionHttps.setRequestProperty("Content-Type", "application/json");
            conexionHttps.setRequestProperty("fiware-service", "openapps");
            conexionHttps.setRequestProperty("fiware-servicepath", "/");
            conexionHttps.setRequestProperty("X-Auth-Token", tokenAcceso);
            conexionHttps.setRequestProperty("data", entidadJSON);

            conexionHttps.connect();

            OutputStream outputStream = conexionHttps.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(entidadJSON);
            bufferedWriter.flush();

            int codigoDeEstado = conexionHttps.getResponseCode();
            conexionHttps.disconnect();
            return codigoDeEstado;

        } catch (MalformedURLException e) {
            //Excepción arrojada por objeto URL
            e.printStackTrace();
        } catch (IOException e) {
            //Excepción arrojada por objeto HttpURLConnection
            e.printStackTrace();
        }
        //Si no pudo realizarse la petición
        return -1;
    }
}
