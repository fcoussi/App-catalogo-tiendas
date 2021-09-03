package cl.ufro.dci.proyectosmartcity.ui.Avisos;

import java.io.Serializable;

public class Hortaliza implements Serializable {

    private String nombre;
    private String info;
    private String servicio;
    private String precio;
    private String imagenId;

    public Hortaliza(String nombre, String info, String servicio, String precio, String imagenId) {

        this.nombre = nombre;
        this.info = info;
        this.servicio = servicio;
        this.precio = precio;
        this.imagenId = imagenId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.info = servicio;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getImagenId() {
        return imagenId;
    }

    public void setImagenId(String imagenId) {
        this.imagenId = imagenId;
    }



}
