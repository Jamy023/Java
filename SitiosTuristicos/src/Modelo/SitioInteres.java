package Modelo;

/**
 * Clase que representa un sitio de interés turístico
 */
public class SitioInteres {
    private int id;
    private String nombreSitio;
    private String descripcion;
    private double precio;
    private int idMunicipioFk;
    private float distancia;
    private int idTipoSitioFk;
    private String nombreSitio2;
    private Municipio municipio;
    
    // Campos adicionales para información relacionada
    private String nombreMunicipio;
    private String tipoSitio;
    
    // Variables para los servicios (obtenidas de la tabla servicios)
    private boolean tieneAlojamiento;
    private boolean tieneAlimentacion;
    private boolean tieneTransporte;
    
    /**
     * Constructor sin parámetros
     */
    public SitioInteres() {
    }
    
    /**
     * Constructor con parámetros básicos
     */
    
    public SitioInteres(String nombreSitio, String descripcion, double precio, 
                        int idMunicipioFk, float distancia, int idTipoSitioFk) {
        this.nombreSitio = nombreSitio;
        this.descripcion = descripcion;
        this.precio = precio;
        this.idMunicipioFk = idMunicipioFk;
        this.distancia = distancia;
        this.idTipoSitioFk = idTipoSitioFk;
        this.tieneAlojamiento = false;
        this.tieneAlimentacion = false;
        this.tieneTransporte = false;
    }
    
    /**
     * Constructor completo con ID
     */
    public SitioInteres(int id, String nombreSitio, String descripcion, double precio, 
                        int idMunicipioFk, float distancia, int idTipoSitioFk) {
        this.id = id;
        this.nombreSitio = nombreSitio;
        this.descripcion = descripcion;
        this.precio = precio;
        this.idMunicipioFk = idMunicipioFk;
        this.distancia = distancia;
        this.idTipoSitioFk = idTipoSitioFk;
        this.tieneAlojamiento = false;
        this.tieneAlimentacion = false;
        this.tieneTransporte = false;
    }
    public SitioInteres(int id, String nombreSitio, String descripcion, double precio, int idMunicipioFk, float distancia, int idTipoSitioFk, String nombreSitio2, Municipio municipio, String nombreMunicipio, String tipoSitio, boolean tieneAlojamiento, boolean tieneAlimentacion, boolean tieneTransporte) {
        this.id = id;
        this.nombreSitio = nombreSitio;
        this.descripcion = descripcion;
        this.precio = precio;
        this.idMunicipioFk = idMunicipioFk;
        this.distancia = distancia;
        this.idTipoSitioFk = idTipoSitioFk;
        this.nombreSitio2 = nombreSitio2;
        this.municipio = municipio;
        this.nombreMunicipio = nombreMunicipio;
        this.tipoSitio = tipoSitio;
        this.tieneAlojamiento = tieneAlojamiento;
        this.tieneAlimentacion = tieneAlimentacion;
        this.tieneTransporte = tieneTransporte;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreSitio() {
        return nombreSitio;
    }

    public void setNombreSitio(String nombreSitio) {
        this.nombreSitio = nombreSitio;
    }
    
    // Para mantener compatibilidad con código existente
    public String getNombre() {
        return nombreSitio;
    }
    
    public void setNombre(String nombre) {
        this.nombreSitio = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getIdMunicipioFk() {
        return idMunicipioFk;
    }

    public void setIdMunicipioFk(int idMunicipioFk) {
        this.idMunicipioFk = idMunicipioFk;
    }
    
    // Para mantener compatibilidad con código existente
    public int getIdMunicipio() {
        return idMunicipioFk;
    }
    
    public void setIdMunicipio(int idMunicipio) {
        this.idMunicipioFk = idMunicipio;
    }

    public float getDistancia() {
        return distancia;
    }

    public void setDistancia(float distancia) {
        this.distancia = distancia;
    }

    public int getIdTipoSitioFk() {
        return idTipoSitioFk;
    }

    public void setIdTipoSitioFk(int idTipoSitioFk) {
        this.idTipoSitioFk = idTipoSitioFk;
    }
    
    // Para mantener compatibilidad con código existente
    public int getIdTipo() {
        return idTipoSitioFk;
    }
    
    public void setIdTipo(int idTipo) {
        this.idTipoSitioFk = idTipo;
    }

    public boolean isTieneAlojamiento() {
        return tieneAlojamiento;
    }

    public void setTieneAlojamiento(boolean tieneAlojamiento) {
        this.tieneAlojamiento = tieneAlojamiento;
    }

    public boolean isTieneAlimentacion() {
        return tieneAlimentacion;
    }

    public void setTieneAlimentacion(boolean tieneAlimentacion) {
        this.tieneAlimentacion = tieneAlimentacion;
    }

    public boolean isTieneTransporte() {
        return tieneTransporte;
    }

    public void setTieneTransporte(boolean tieneTransporte) {
        this.tieneTransporte = tieneTransporte;
    }

    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }

    public String getTipoSitio() {
        return tipoSitio;
    }

    public void setTipoSitio(String tipoSitio) {
        this.tipoSitio = tipoSitio;
    }
    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    // Getter y Setter para nombreSitio
    public String getNombre2() {
        return nombreSitio2;
    }

    public void setNombre2(String nombreSitio) {
        this.nombreSitio = nombreSitio;
    }
    
    /**
     * Determina si el sitio ofrece algún servicio
     * @return true si ofrece al menos un servicio, false en caso contrario
     */
    public boolean tieneAlgunServicio() {
        return tieneAlojamiento || tieneAlimentacion || tieneTransporte;
    }
    
    /**
     * Determina si el sitio ofrece todos los servicios
     * @return true si ofrece todos los servicios, false en caso contrario
     */
    public boolean tieneTodosServicios() {
        return tieneAlojamiento && tieneAlimentacion && tieneTransporte;
    }
    
    /**
     * Devuelve un resumen de los servicios que ofrece el sitio
     * @return Cadena descriptiva de los servicios
     */
    public String getResumenServicios() {
        StringBuilder resumen = new StringBuilder();
        
        if (tieneAlojamiento) {
            resumen.append("Alojamiento");
        }
        
        if (tieneAlimentacion) {
            if (resumen.length() > 0) {
                resumen.append(", ");
            }
            resumen.append("Alimentación");
        }
        
        if (tieneTransporte) {
            if (resumen.length() > 0) {
                resumen.append(", ");
            }
            resumen.append("Transporte");
        }
        
        if (resumen.length() == 0) {
            resumen.append("Ningún servicio disponible");
        }
        
        return resumen.toString();
    }

    @Override
    public String toString() {
        return nombreSitio;
    }
}