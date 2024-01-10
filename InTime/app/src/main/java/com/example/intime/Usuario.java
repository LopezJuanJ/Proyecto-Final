package com.example.intime;

public class Usuario {
    private String nombre;
    private String apellidos;
    private String correoElectronico;
    private String contrasena;
    private String codGym;

    private int admin;

    // Constructor vacío requerido para Firebase
    public Usuario() {
    }

    // Constructor con parámetros
    public Usuario(String nombre, String apellidos, String correoElectronico, String contrasena, String codGym,int admin) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
        this.codGym = codGym;
        this.admin=admin;
    }

    // Métodos getters y setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCodGym() {
        return codGym;
    }

    public void setCodGym(String codGym) {
        this.codGym = codGym;
    }
    public int getadmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }
}

