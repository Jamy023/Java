/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author User
 */
public class Municipio {
    private int id;
    private String nombreMunicipio;
    private String descripcion;
    private String bandera;
    private String  nombre2;
    
    public Municipio() {
    }

    public Municipio(int id, String nombreMunicipio, String descripcion, String bandera, String nombre2) {
        this.id = id;
        this.nombreMunicipio = nombreMunicipio;
        this.descripcion = descripcion;
        this.bandera = bandera;
        this.nombre2 = nombre2;
    }

    public String getNombre2() {
        return nombre2;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreMunicipio() {
        return nombreMunicipio;
    }

    public void setNombreMunicipio(String nombreMunicipio) {
        this.nombreMunicipio = nombreMunicipio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getBandera() {
        return bandera;
    }

    public void setBandera(String bandera) {
        this.bandera = bandera;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }
    

    @Override
    public String toString() {
        return nombreMunicipio;
    }
}
