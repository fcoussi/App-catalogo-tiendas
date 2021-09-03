package cl.ufro.dci.proyectosmartcity.model;

public class Comercio {

    private String nombre;
    private String servicios;
    private String direccion;
    private String Fono;
    private String idUbicacion = "";
    private String url;
    private Coordenada coordenada;

    public String getNombre() {
        return nombre;
    }

    public String getServicios() {
        return servicios;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getFono() {
        return Fono;
    }

    public void setIdUbicacion(String id) {
        idUbicacion = id;
    }

    public String getIdUbicacion() {
        return idUbicacion;
    }

    public String getUrl() {
        return url;
    }

    public void setCoordenada(Coordenada coordenada) {
        this.coordenada = coordenada;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }
}