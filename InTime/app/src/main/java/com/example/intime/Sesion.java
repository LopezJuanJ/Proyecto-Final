package com.example.intime;

public class Sesion {
    private static Sesion instanciaUnica;

    private String NombreUsuario;
    private String codGym;
    private boolean admin;

    // Constructor privado para evitar la creación de instancias directas
    private Sesion() {
    }

    // Método estático para obtener la instancia única (singleton)
    public static synchronized Sesion getInstancia() {
        if (instanciaUnica == null) {
            instanciaUnica = new Sesion();
        }
        return instanciaUnica;
    }

    // Getter y Setter para NombreUsuario
    public String getNombreUsuario() {
        return NombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.NombreUsuario = nombreUsuario;
    }

    // Getter y Setter para codGym
    public String getCodGym() {
        return codGym;
    }

    public void setCodGym(String codGym) {
        this.codGym = codGym;
    }

    // Getter y Setter para admin
    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}

