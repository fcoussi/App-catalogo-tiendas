package cl.ufro.dci.proyectosmartcity.model.fiware;

public class Nombre {

    private String type = "Text";
    private String value;
    private MetadataNombre metadata=new MetadataNombre();

    public Nombre(String nombre) {
        value = nombre;
    }

}
