package cl.ufro.dci.proyectosmartcity.model.fiware;

import cl.ufro.dci.proyectosmartcity.model.Comercio;

public class EntidadComercio {

    private String id;
    private String type;
    private Nombre name;
    private Servicios services;
    private Ubicacion location;

    public EntidadComercio(Comercio comercio) {
        id = comercio.getIdUbicacion();
        type = "comercio-rural";
        name = new Nombre(comercio.getNombre());
        services = new Servicios(comercio.getServicios());
        location = new Ubicacion(comercio.getCoordenada());
    }

}
