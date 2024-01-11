package com.example.intime;

public class Sesion {
    private static Sesion instanciaUnica;

    private String NombreUsuario;
    private String apellidos;  // Nuevo atributo
    private String codGym;
    private int admin;
    private String Nombre;  // Nuevo atributo

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

    // Getter y Setter para apellidos
    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    // Getter y Setter para codGym
    public String getCodGym() {
        return codGym;
    }

    public void setCodGym(String codGym) {
        this.codGym = codGym;
    }

    // Getter y Setter para admin
    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    // Getter y Setter para correoElectronico
    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
}
