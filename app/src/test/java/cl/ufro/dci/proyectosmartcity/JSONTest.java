package cl.ufro.dci.proyectosmartcity;

import com.google.gson.Gson;

import org.junit.Test;

import cl.ufro.dci.proyectosmartcity.model.Comercio;
import cl.ufro.dci.proyectosmartcity.model.Coordenada;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class JSONTest {

    private Gson gson = new Gson();

    @Test
    public void extraerDatoDesdeJSON() {
        Coordenada c = gson.fromJson("{\"latitud\":12.3456789, \"longitud\":-9.87654321}", Coordenada.class);

        double resulEsperado = c.getLongitud();
        double resulActual = -9.87654321;
        assertEquals(resulEsperado, resulActual, 0);
    }

    @Test
    public void deserializarJSONArray() {
        Comercio[] comercio = gson.fromJson("[{servicios=Delivery}, {servicios=\"\"}, {\"servicios\"=\"Delivery\"}]", Comercio[].class);

        String resulEsperado="Delivery";
        String resulActual=comercio[0].getServicios();

        assertEquals(resulEsperado,resulActual);
    }

}