package com.example.intime;

public class Cuerpo {

    private String correo;
    private String kilos;
    private String altura;
    private double imc;

    // Constructor
    public Cuerpo(String correo, String kilos, String altura, double imc) {
        this.correo = correo;
        this.kilos = kilos;
        this.altura = altura;
        this.imc = imc;
    }

    // Métodos getter
    public String getCorreo() {
        return correo;
    }

    public String getKilos() {
        return kilos;
    }

    public String getAltura() {
        return altura;
    }

    public double getImc() {
        return imc;
    }

    // Métodos setter
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setKilos(String kilos) {
        this.kilos = kilos;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }

}

