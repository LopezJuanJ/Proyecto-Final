package com.example.intime;

public class Gimnasio {
    private String nombre;
    private String direccion;
    private String telefono;
    private String correoElectronico;
    private String codGym;

    public Gimnasio() {
    }

    public Gimnasio(String nombre, String direccion, String telefono, String correoElectronico, String codGym) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correoElectronico = correoElectronico;
        this.codGym = codGym;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getCodGym() {
        return codGym;
    }

    public void setCodGym(String codGym) {
        this.codGym = codGym;
    }
}

