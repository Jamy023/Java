package Controlador;

import Modelo.MunicipioDAO;
import Modelo.SitioInteresDAO;
import Modelo.TipoSitioInteres;
import Modelo.Municipio;
import Modelo.SitioInteres;
import Vista.EditarSitioView;
import Vista.PanelAdminMunicipio;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControladorTurismo {
    
    private final MunicipioDAO municipioDAO = new MunicipioDAO();
    private final SitioInteresDAO sitioDAO = new SitioInteresDAO();
    
    // Obtener todos los municipios
    public List<Municipio> obtenerMunicipios() {
        try {
            return municipioDAO.obtenerTodos();
        } catch (SQLException e) {
            System.err.println("Error al obtener municipios: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    // Obtener todos los sitios
    public List<SitioInteres> obtenerTodosSitios() {
        try {
            return sitioDAO.obtenerTodos();
        } catch (SQLException e) {
            System.err.println("Error al obtener sitios: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    // Obtener un sitio por su ID
    public SitioInteres obtenerSitioPorId(int id) {
        try {
            return sitioDAO.obtenerPorId(id);
        } catch (SQLException e) {
            System.err.println("Error al obtener sitio con ID " + id + ": " + e.getMessage());
            return null;
        }
    }
    
    // Obtener sitios por municipio
    public List<SitioInteres> obtenerSitiosPorMunicipio(String nombreMunicipio) {
        try {
            return sitioDAO.obtenerPorNombreMunicipio(nombreMunicipio);
        } catch (SQLException e) {
            System.err.println("Error al obtener sitios del municipio " + nombreMunicipio + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }
    

    
    // Actualizar sitio existente
    public boolean actualizarSitio(SitioInteres sitio) {
        try {
            return sitioDAO.actualizar(sitio);
          
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar sitio: " + e.getMessage());
            return false;
        }
    }
    
  
    
    // Obtener todos los tipos de sitio
    public List<TipoSitioInteres> obtenerTiposSitioInteres() {
        try {
            return TipoSitioInteres.obtenerTodos();
        } catch (SQLException e) {
            System.err.println("Error al obtener tipos de sitio: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    // Obtener tipo de sitio por ID
    public TipoSitioInteres obtenerTipoSitioPorId(int id) {
        try {
            return TipoSitioInteres.obtenerPorId(id);
        } catch (SQLException e) {
            System.err.println("Error al obtener tipo de sitio con ID " + id + ": " + e.getMessage());
            return null;
        }
    }
    
    // Obtener estadísticas de un municipio
    public int[] obtenerEstadisticasMunicipio(String nombreMunicipio) {
        int[] estadisticas = new int[3]; // [sinServicios, conAlojamiento, conTodosServicios]
        
        try {
            estadisticas[0] = sitioDAO.contarSitiosSinServicios(nombreMunicipio);
            estadisticas[1] = sitioDAO.contarSitiosConAlojamiento(nombreMunicipio);
            estadisticas[2] = sitioDAO.contarSitiosConTodosServicios(nombreMunicipio);
        } catch (SQLException e) {
            System.err.println("Error al obtener estadísticas del municipio " + nombreMunicipio + ": " + e.getMessage());
        }
        
        return estadisticas;
    }
    // Método para abrir ventana de edición de sitio

}