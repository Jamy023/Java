/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

/**
 *
 * @author User
 */


import Modelo.MunicipioDAO;
import Modelo.SitioInteresDAO;
import Modelo.Municipio;
import Modelo.SitioInteres;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ControladorAdmin  {
    
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
    
    // Obtener sitios por municipio
    public List<SitioInteres> obtenerSitiosPorMunicipio(String nombreMunicipio) {
        try {
            return sitioDAO.obtenerPorNombreMunicipio(nombreMunicipio);
        } catch (SQLException e) {
            System.err.println("Error al obtener sitios del municipio " + nombreMunicipio + ": " + e.getMessage());
            return new ArrayList<>();
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
}