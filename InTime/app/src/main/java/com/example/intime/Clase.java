package com.example.intime;

public class Clase {
    private String horario;
    private String maxPersonas;
    private String asistentes;

    // Constructor vacío requerido para Firebase
    public Clase() {
    }

    public Clase(String horario, String maxPersonas, String asistentes) {
        this.horario = horario;
        this.maxPersonas = maxPersonas;
        this.asistentes = asistentes;
    }

    // Métodos Get
    public String getHorario() {
        return horario;
    }

    public String getMaxPersonas() {
        return maxPersonas;
    }

    public String getAsistentes() {
        return asistentes;
    }

    // Métodos Set
    public void setHorario(String horario) {
        this.horario = horario;
    }

    public void setMaxPersonas(String maxPersonas) {
        this.maxPersonas = maxPersonas;
    }

    public void setAsistentes(String asistentes) {
        this.asistentes = asistentes;
    }
}
