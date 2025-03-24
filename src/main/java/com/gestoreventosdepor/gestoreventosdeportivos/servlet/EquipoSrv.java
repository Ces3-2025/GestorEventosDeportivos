package com.gestoreventosdepor.gestoreventosdeportivos.servlet;


import com.gestoreventosdepor.gestoreventosdeportivos.dao.EquipoDAO;
import com.gestoreventosdepor.gestoreventosdeportivos.dao.JugadorDAO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
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

@WebServlet(name = "equipoSrv", value = "/equipos")
public class EquipoSrv extends HttpServlet {
    private Gson gson = new Gson();
    Map<Integer, EquipoDAO> equipos = new HashMap<>();
    // Clase interna para inicializar los datos de los equipos
    private static class DataInitializer {
        public static void inicializarEquipos(Map<Integer, EquipoDAO> equipos) {
            // Crear algunos equipos de ejemplo (usando EquipoDAO)
            EquipoDAO equipo1 = new EquipoDAO();
            equipo1.setId(1);
            equipo1.setNombre("Los Leones");
            equipo1.setDeporte("Fútbol");
            equipo1.setCiudad("Medellín");
            equipo1.setFechaFundacion("1990-05-15");
            equipo1.setLogo("https://www.losleones.com/logo.png");
            equipo1.addJugador(1);
            equipo1.addJugador(2);
            equipo1.addJugador(3);
            equipo1.addJugador(4);
            equipos.put(equipo1.getId(), equipo1);

            EquipoDAO equipo2 = new EquipoDAO();
            equipo2.setId(2);
            equipo2.setNombre("Las Panteras");
            equipo2.setDeporte("Baloncesto");
            equipo2.setCiudad("Cali");
            equipo2.setFechaFundacion("1995-10-20");
            equipo2.setLogo("https://www.laspanteras.com/logo.png");
            equipo2.addJugador(5);
            equipo2.addJugador(6);
            equipo2.addJugador(7);
            equipo2.addJugador(8);
            equipos.put(equipo2.getId(), equipo2);

            EquipoDAO equipo3 = new EquipoDAO();
            equipo3.setId(3);
            equipo3.setNombre("Los Tigres");
            equipo3.setDeporte("Béisbol");
            equipo3.setCiudad("Barranquilla");
            equipo3.setFechaFundacion("2000-03-10");
            equipo3.setLogo("https://www.lostigres.com/logo.png");
            equipo3.addJugador(9);
            equipo3.addJugador(10);
            equipo3.addJugador(11);
            equipo3.addJugador(12);
            equipos.put(equipo3.getId(), equipo3);

            EquipoDAO equipo4 = new EquipoDAO();
            equipo4.setId(4);
            equipo4.setNombre("Los Caimanes");
            equipo4.setDeporte("Fútbol");
            equipo4.setCiudad("Cartagena");
            equipo4.setFechaFundacion("1985-07-22");
            equipo4.setLogo("https://www.loscaimanes.com/logo.png");
            equipo4.addJugador(13);
            equipo4.addJugador(14);
            equipo4.addJugador(15);
            equipo4.addJugador(16);
            equipos.put(equipo4.getId(), equipo4);

            EquipoDAO equipo5 = new EquipoDAO();
            equipo5.setId(5);
            equipo5.setNombre("Las Águilas");
            equipo5.setDeporte("Voleibol");
            equipo5.setCiudad("Bogotá");
            equipo5.setFechaFundacion("1988-08-01");
            equipo5.setLogo("https://www.lasaguilas.com/logo.png");
            equipo5.addJugador(17);
            equipo5.addJugador(18);
            equipo5.addJugador(19);
            equipo5.addJugador(20);
            equipos.put(equipo5.getId(), equipo5);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        // Inicializar los equipos usando la clase DataInitializer
        DataInitializer.inicializarEquipos(equipos);
    }


    public Map enviarListaEquipos() {
        System.out.println("hay equipos" + equipos.size());
        return equipos;
    }

    //mostrar los equipos por id o todos los equipos
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //obtengo el id del equipo
        String id = request.getParameter("id");
        //si el id es nulo
        if (id == null) {
            //envio la lista de equipos
            response.getWriter().println(gson.toJson(equipos.values()));
        } else {
            //si no, obtengo el equipo por id
            EquipoDAO equipo = equipos.get(Integer.parseInt(id));
            //si el equipo no es nulo
            if (equipo != null) {
                //envio el equipo
                response.getWriter().println(gson.toJson(equipo));
            } else {
                //si no, envio un mensaje de error
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "No se encontro el equipo con el id: " + id);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            //obtengo los parametros del cuerpo de la peticion
            JsonObject params = getParamsFromBody(request);
            //creo un objeto de tipo EquipoDAO
            EquipoDAO equipo = new EquipoDAO();
            //seteo los valores del objeto con los valores del json
            equipo.setId(Integer.parseInt(params.get("id").getAsString()));
            equipo.setNombre(params.get("nombre").getAsString());
            equipo.setDeporte(params.get("deporte").getAsString());
            equipo.setCiudad(params.get("ciudad").getAsString());
            equipo.setFechaFundacion(params.get("fechaFundacion").getAsString());
            equipo.setLogo(params.get("logo").getAsString());
            JsonArray jugadoresJsonArray = params.get("jugadores").getAsJsonArray();
            for (int i = 0; i < jugadoresJsonArray.size(); i++) {
                int jugadorId = jugadoresJsonArray.get(i).getAsInt();
                equipo.addJugador(jugadorId);
            }

            //si no existe un equipo con el mismo nombre y deporte
            if(existeEquipoConMismoNombreYDeporte(equipo))
            {
                //envio un mensaje de error
                response.sendError(HttpServletResponse.SC_CONFLICT, "Ya existe un equipo con el mismo nombre y deporte");
            }
            else
            {
                //si no existe, agrego el equipo al mapa de equipos
                equipos.put(equipo.getId(), equipo);
                //envio un mensaje de exito
                response.setStatus(HttpServletResponse.SC_CREATED);
                // **Imprimir el equipo en la consola**
                System.out.println("Equipo creado: " + equipo);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean existeEquipoConMismoNombreYDeporte(EquipoDAO equipo) {
        for (EquipoDAO e : equipos.values()) {
            if (e.getNombre().equals(equipo.getNombre()) && e.getDeporte().equals(equipo.getDeporte())) {
                return true;
            }
        }
        return false;
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
