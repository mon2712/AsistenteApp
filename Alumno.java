package com.vane.montse.asistente;

public class Alumno {
    private int id;
    private String nombre;
    private String apellido;
    private String nivel;
    private String horaEntrada;
    private String tiempo;
    private String tiempoReducido;

    public Alumno(){

    }

    public Alumno(int id, String nombre, String apellido, String nivel, String horaEntrada, String tiempo, String tiempoReducido) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nivel = nivel;
        this.horaEntrada = horaEntrada;
        this.tiempo = tiempo;
        this.tiempoReducido = tiempoReducido;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNivel() {
        return nivel;
    }

    public String getHoraEntrada() {
        return horaEntrada;
    }

    public String getTiempo() {
        return tiempo;
    }

    public String getTiempoReducido() {
        return tiempoReducido;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public void setTiempoReducido(String tiempoReducido) {
        this.tiempoReducido = tiempoReducido;
    }
}
