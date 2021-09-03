package cl.ufro.dci.proyectosmartcity;

import android.os.AsyncTask;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ControladorAPI extends AsyncTask<String, String, String> {

    private ComunicadorToken comunicadorToken;

    public ControladorAPI(ComunicadorToken comunicadorToken) {
        this.comunicadorToken = comunicadorToken;
    }

    @Override
    protected String doInBackground(String... strings) {
        return getToken();
    }

    @Override
    public void onPostExecute(String token) {
        comunicadorToken.enObtencionDeToken(token);
    }

    /**
     * Obtiene el token de acceso a través una petición POST
     *
     * @return el token de acceso
     */
    private String getToken() {

        String keyUser = "f8af8d78-a588-4e47-80ae-840f38e1c600";
        String keyPassword = "6ce68922-2b1e-4895-ad31-409dfcc79262";

        try {
            URL url = new URL("https://fw-idm.smartaraucania.org/oauth2/token");
            HttpsURLConnection conexionHttp = (HttpsURLConnection) url.openConnection();

            conexionHttp.setDoOutput(true);
            conexionHttp.setRequestMethod("POST");
            String baseAuth = keyUser + ":" + keyPassword;
            conexionHttp.setRequestProperty("Authorization", "Basic " +
                    Base64.encodeToString(baseAuth.getBytes(), Base64.NO_WRAP));
            conexionHttp.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            String data = "grant_type=password&username=m.acencio01@ufromail.cl&password=Quiero1Algo";
            conexionHttp.setRequestProperty("Body", data);

            conexionHttp.connect();

            OutputStream outputStream = conexionHttp.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(data);
            bufferedWriter.flush();

            int statusCode;
            statusCode = conexionHttp.getResponseCode();

            if (statusCode > 299) {
                //Si la petición no fue exitosa
                //Para realizar debugging
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(conexionHttp.getErrorStream()));
                String inputLine;
                StringBuffer respuesta = new StringBuffer();
                //Construye respuesta obtenida
                while ((inputLine = bufferedReader.readLine()) != null) {
                    respuesta.append(inputLine);
                }
                bufferedReader.close();

            } else if (statusCode == 200) {
                //Si la petición fue exitosa
                InputStream inputStream = conexionHttp.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int value = inputStreamReader.read();
                StringBuilder respuesta = new StringBuilder();

                //Construir la respuesta obtenida en formato JSON
                while (value != -1) {
                    char current = (char) value;
                    respuesta.append(current);
                    value = inputStreamReader.read();
                }
                inputStream.close();
                inputStreamReader.close();
                JSONObject respuestaJson = new JSONObject(respuesta.toString());

                //Retornar el token de acceso
                return respuestaJson.getString("access_token");
            }

        } catch (JSONException e) {
            //Si hubo conflicto al tratar con JSONObject
            e.printStackTrace();
        } catch (IOException e) {
            //Si la petición no fue exitosa
            e.printStackTrace();
        }
        return "";
    }
}
