package cl.ufro.dci.proyectosmartcity.model.fiware;

import cl.ufro.dci.proyectosmartcity.model.Coordenada;

public class Ubicacion {

    private String type = "geo:point";
    private String value;
    private MetadataUbicacion metadata = new MetadataUbicacion();

    public Ubicacion(Coordenada coordenada) {
        this.value = coordenada.getLatitud() + "," + coordenada.getLongitud();
    }

}
