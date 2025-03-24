package com.gestoreventosdepor.gestoreventosdeportivos.dao;

import java.util.ArrayList;

public class EventoDAO {
    private int id;
    private String nombre;
    private String fecha;
    private String lugar;
    private String deporte;
    private ArrayList<Integer> equiposParticipantes;
    private String eqipo1;
    private String eqipo2;
    private int capacidad;
    private int entradasVendidas;
    private EstadoEvento estado;

    // Constructor
    public EventoDAO(int id, String nombre, String fecha, String lugar, String deporte, int capacidad) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.lugar = lugar;
        this.deporte = deporte;
        this.capacidad = capacidad;
        this.equiposParticipantes = new ArrayList<>();
        this.entradasVendidas = 0;
        this.estado = EstadoEvento.PROGRAMADO;
    }

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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public ArrayList<Integer> getEquiposParticipantes() {
        return equiposParticipantes;
    }

    public void setEquiposParticipantes(ArrayList<Integer> equiposParticipantes) {
        this.equiposParticipantes = equiposParticipantes;
    }

    public void addEquipoParticipante(int equipoId) {
        this.equiposParticipantes.add(equipoId);
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public int getEntradasVendidas() {
        return entradasVendidas;
    }

    public void setEntradasVendidas(int entradasVendidas) {
        this.entradasVendidas = entradasVendidas;
    }

    public EstadoEvento getEstado() {
        return estado;
    }

    public void setEstado(EstadoEvento estado) {
        this.estado = estado;
    }
}