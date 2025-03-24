package com.gestoreventosdepor.gestoreventosdeportivos.servlet;

import com.gestoreventosdepor.gestoreventosdeportivos.dao.EventoDAO;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "eventoSrv", value = "/eventos")
public class EventoSrv extends HttpServlet {
    private Gson gson = new Gson();
    // Usamos un Map para almacenar los eventos, la clave es el ID del evento.
    private Map<Integer, EventoDAO> eventos = new HashMap<>();
    private int nextEventoId = 1; // Para generar IDs únicos

    @Override
    public void init() throws ServletException {
        super.init();
        // Inicializar algunos eventos de ejemplo
        inicializarEventos();
    }

    private void inicializarEventos() {
        if (eventos.isEmpty()) {
            EventoDAO evento1 = new EventoDAO(nextEventoId++, "Partido de Fútbol", "2024-03-10", "Estadio Metropolitano", "Fútbol", 50000);
            eventos.put(evento1.getId(), evento1);
            EventoDAO evento2 = new EventoDAO(nextEventoId++, "Final de Baloncesto", "2024-03-15", "Coliseo El Salitre", "Baloncesto", 15000);
            eventos.put(evento2.getId(), evento2);
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        // Crear un array JSON para almacenar los eventos
        String eventosJson = gson.toJson(eventos.values());
        out.print(eventosJson);
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            // Obtener los parámetros del cuerpo de la petición
            JsonObject eventoJson = getParamsFromBody(request);

            // Crear un nuevo objeto EventoDAO con los datos del JSON
            // Validar que los campos obligatorios estén presentes
            if (eventoJson.has("nombre") && eventoJson.has("fecha") && eventoJson.has("lugar") && eventoJson.has("deporte") && eventoJson.has("capacidad")) {
                String nombre = eventoJson.get("nombre").getAsString();
                String fecha = eventoJson.get("fecha").getAsString();
                String lugar = eventoJson.get("lugar").getAsString();
                String deporte = eventoJson.get("deporte").getAsString();
                int capacidad = eventoJson.get("capacidad").getAsInt();

                EventoDAO nuevoEvento = new EventoDAO(nextEventoId++, nombre, fecha, lugar, deporte, capacidad);
                // Agregar el nuevo evento al mapa
                eventos.put(nuevoEvento.getId(), nuevoEvento);

                // Enviar una respuesta JSON con el evento creado
                String nuevoEventoJson = gson.toJson(nuevoEvento);
                PrintWriter out = response.getWriter();
                out.print(nuevoEventoJson);
                out.flush();
                response.setStatus(HttpServletResponse.SC_CREATED); // Establecer el código de estado a 201 (Created)

            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan campos obligatorios en la solicitud.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al procesar la solicitud: " + e.getMessage());
        }
    }

    //creando el metodo para manipular los json, con httpServletRequest leo el cuerpo de la peticion
    private JsonObject getParamsFromBody(HttpServletRequest request) throws IOException {
        //el objeto BufferedReader lee el cuerpo de la peticion
        BufferedReader reader = request.getReader();
        //creo un objeto de tipo StringBuilder
        StringBuilder stringB = new StringBuilder();
        //creo una variable de tipo String para leer las lineas del cuerpo de la peticion
        String line = reader.readLine();
        //mientras la linea no sea nula
        while (line != null) {
            //agrego la linea al objeto StringBuilder
            stringB.append(line).append('\n');
            //leo la siguiente linea
            line = reader.readLine();
        }
        // cierro el reader
        reader.close();
        //retorno el objeto StringBuilder convertido a String y parseado a un objeto Json
        return JsonParser.parseString(stringB.toString()).getAsJsonObject();
    }
}

