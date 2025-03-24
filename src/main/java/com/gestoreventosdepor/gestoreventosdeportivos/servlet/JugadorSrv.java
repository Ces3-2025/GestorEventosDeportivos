package com.gestoreventosdepor.gestoreventosdeportivos.servlet;

import com.gestoreventosdepor.gestoreventosdeportivos.dao.EquipoDAO;
import com.gestoreventosdepor.gestoreventosdeportivos.dao.JugadorDAO;
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
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "jugadorSrv", value = "/jugadores")
public class JugadorSrv extends HttpServlet {

    private EquipoSrv equipoSrv = new EquipoSrv(); // Inicializar equipoSrv
    private Gson gson = new Gson();
    public Map<Integer, EquipoDAO> equipos = equipoSrv.enviarListaEquipos();
    public Map<Integer, JugadorDAO> jugadores = new HashMap<>();

    @Override
    public void init() throws ServletException {
        super.init();
        // Inicializar los equipos usando la clase DataInitializer
        JugadorSrv.DataInitializer.inicializarJugadores(jugadores);
    }

    private static class DataInitializer {
        public static void inicializarJugadores(Map<Integer, JugadorDAO> jugadores) {
            // Crear algunos jugadores de ejemplo (usando JugadorDAO)
            JugadorDAO jugador1 = new JugadorDAO();
            jugador1.setId(1);
            jugador1.setNombre("Juan");
            jugador1.setApellido("Pérez");
            jugador1.setFechaNacimiento("1990-01-01");
            jugador1.setNacionalidad("Colombiana");
            jugador1.setPosicion("Delantero");
            jugador1.setNumero(9);
            jugador1.setEquipoId(1);
            jugador1.setEstadoActivo(true);
            jugadores.put(jugador1.getId(), jugador1);

            JugadorDAO jugador2 = new JugadorDAO();
            jugador2.setId(2);
            jugador2.setNombre("Carlos");
            jugador2.setApellido("Gómez");
            jugador2.setFechaNacimiento("1992-02-02");
            jugador2.setNacionalidad("Colombiana");
            jugador2.setPosicion("Defensa");
            jugador2.setNumero(4);
            jugador2.setEquipoId(1);
            jugador2.setEstadoActivo(true);
            jugadores.put(jugador2.getId(), jugador2);

            JugadorDAO jugador3 = new JugadorDAO();
            jugador3.setId(3);
            jugador3.setNombre("Luis");
            jugador3.setApellido("Martínez");
            jugador3.setFechaNacimiento("1991-03-03");
            jugador3.setNacionalidad("Colombiana");
            jugador3.setPosicion("Mediocampista");
            jugador3.setNumero(8);
            jugador3.setEquipoId(1);
            jugador3.setEstadoActivo(true);
            jugadores.put(jugador3.getId(), jugador3);

            JugadorDAO jugador4 = new JugadorDAO();
            jugador4.setId(4);
            jugador4.setNombre("Pedro");
            jugador4.setApellido("López");
            jugador4.setFechaNacimiento("1993-04-04");
            jugador4.setNacionalidad("Colombiana");
            jugador4.setPosicion("Portero");
            jugador4.setNumero(1);
            jugador4.setEquipoId(1);
            jugador4.setEstadoActivo(true);
            jugadores.put(jugador4.getId(), jugador4);

            JugadorDAO jugador20 = new JugadorDAO();
            jugador20.setId(20);
            jugador20.setNombre("Hugo");
            jugador20.setApellido("Santos");
            jugador20.setFechaNacimiento("2009-08-20");
            jugador20.setNacionalidad("Colombiana");
            jugador20.setPosicion("Portero");
            jugador20.setNumero(20);
            jugador20.setEquipoId(5);
            jugador20.setEstadoActivo(true);
            jugadores.put(jugador20.getId(), jugador20);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtengo el parámetro id de la petición
        String id = request.getParameter("id");
        // Si el parámetro id es nulo
        if (id == null) {
            // Envío la lista de jugadores como un JSON array
            response.setContentType("application/json");
            response.getWriter().println(gson.toJson(jugadores.values()));
        } else {
            // Si el parámetro id no es nulo
            // Creo una variable de tipo int y le asigno el valor del parámetro id
            int idJugador = Integer.parseInt(id);
            // Si el id del jugador no existe
            if (!jugadores.containsKey(idJugador)) {
                // Envío un mensaje de error
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "No se encontró el jugador con el id: " + idJugador);
            } else {
                // Si el id del jugador existe
                // Envío el jugador con el id especificado
                response.setContentType("application/json");
                response.getWriter().println(gson.toJson(jugadores.get(idJugador)));
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // Muestra la cantidad de objetos en la lista
            System.out.println("Cantidad de equipos: " + equipos.size());
            for (EquipoDAO e : equipos.values()) {
                System.out.println("Equipo: " + e.getNombre());
            }

            // Creamos una variable que diga si se guardó
            boolean guardado = false;
            // Obtenemos los parámetros del cuerpo de la petición
            JsonObject params = getParamsFromBody(request);
            // Creamos un objeto de tipo JugadorDAO
            JugadorDAO jugador = new JugadorDAO();
            // Setteamos los valores del objeto con los valores del JSON
            jugador.setId(Integer.parseInt(params.get("id").getAsString()));
            jugador.setNombre(params.get("nombre").getAsString());
            jugador.setApellido(params.get("apellido").getAsString());
            jugador.setFechaNacimiento(params.get("fechaNacimiento").getAsString());
            jugador.setNacionalidad(params.get("nacionalidad").getAsString());
            jugador.setPosicion(params.get("posicion").getAsString());
            jugador.setNumero(Integer.parseInt(params.get("numero").getAsString()));
            jugador.setEquipoId(Integer.parseInt(params.get("equipoId").getAsString()));
            jugador.setEstadoActivo(Boolean.parseBoolean(params.get("estadoActivo").getAsString()));

            // Validar que el equipo sea existente
            for (EquipoDAO e : equipos.values()) {
                if (e.getId() == jugador.getEquipoId() && !e.jugadorExiste(jugador.getId())) {
                    // Agregamos el jugador a la lista de jugadores del equipo
                    e.getJugadores().add(jugador.getId());
                    // Enviamos un mensaje de éxito
                    response.getWriter().println("Jugador creado con éxito");
                    // Cambiamos la variable a true
                    guardado = true;
                }
            }

            // Si no se guardó
            if (!guardado) {
                // Enviamos un mensaje de error
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "No es posible almacenar jugador");
            }
        } catch (Exception e) {
            // Si hay un error, enviamos un mensaje de error
            e.printStackTrace();
        }
    }

    // Creando el método para manipular los JSON, con HttpServletRequest leo el cuerpo de la petición
    private JsonObject getParamsFromBody(HttpServletRequest request) throws IOException {
        // El objeto BufferedReader lee el cuerpo de la petición
        BufferedReader reader = request.getReader();
        // Creo un objeto de tipo StringBuilder
        StringBuilder stringB = new StringBuilder();
        // Creo una variable de tipo String para leer las líneas del cuerpo de la petición
        String line = reader.readLine();
        // Mientras la línea no sea nula
        while (line != null) {
            // Agrego la línea al objeto StringBuilder
            stringB.append(line).append('\n');
            // Leo la siguiente línea
            line = reader.readLine();
        }
        // Cierro el reader
        reader.close();
        // Retorno el objeto StringBuilder convertido a String y parseado a un objeto Json
        return JsonParser.parseString(stringB.toString()).getAsJsonObject();
    }
}