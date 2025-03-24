package com.gestoreventosdepor.gestoreventosdeportivos.dao;

import java.util.ArrayList;

public class EquipoDAO {
    private int id;
    private String nombre;
    private String deporte;
    private String ciudad;
    private String fechaFundacion;
    private String logo;
    private ArrayList<Integer> jugadores = new ArrayList<>();



    // Constructor
//    public EquipoDAO(int id, String nombre, String deporte, String ciudad, String fechaFundacion, String logo) {
//        this.id = id;
//        this.nombre = nombre;
//        this.deporte = deporte;
//        this.ciudad = ciudad;
//        this.fechaFundacion = fechaFundacion;
//        this.logo = logo;
//        this.jugadores = new ArrayList<>();
//    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getFechaFundacion() {
        return fechaFundacion;
    }

    public void setFechaFundacion(String fechaFundacion) {
        this.fechaFundacion = fechaFundacion;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public ArrayList<Integer> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<Integer> jugadores) {
        this.jugadores = jugadores;
    }

    public void addJugador(Integer jugador) {
        this.jugadores.add(jugador);
    }

    public boolean jugadorExiste(Integer jugador) {
        return this.jugadores.contains(jugador);
    }
}
