package cl.ufro.dci.proyectosmartcity.model.fiware;

public class Servicios {

    private String type = "Text";
    private String value;
    private MetadataServicios metadata = new MetadataServicios();

    public Servicios(String servicios) {
        value = servicios;
    }

}
