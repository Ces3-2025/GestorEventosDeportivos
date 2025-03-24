<%@ page import="java.util.ArrayList, com.gestoreventosdepor.gestoreventosdeportivos.dao.EventoDAO" %>
<!DOCTYPE html>
<html>
<head>
    <title>Eventos Deportivos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1nqEd00nK8kFUkk9U1aa5f85vKxWvQ" crossorigin="anonymous"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }

        h1 {
            text-align: center;
            margin-bottom: 20px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
            background-color: #fff;
        }

        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f0f0f0;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Eventos Deportivos</h1>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Nombre del Evento</th>
            <th>Fecha</th>
            <th>Lugar</th>
            <th>Deporte</th>
            <th>Capacidad</th>
            <th>Entradas Vendidas</th>
            <th>Estado</th>
            <th>Equipos Participantes</th>
        </tr>
        </thead>
        <tbody id="eventosTabla">
        <%
            //  Obtiene la lista de eventos desde el objeto 'request'.
            //  El Servlet 'EventoSrv' es quien coloca esta lista en el request.
            ArrayList<EventoDAO> eventos = (ArrayList<EventoDAO>) request.getAttribute("eventos");
            // Verifica si la lista de eventos no es nula.
            if (eventos != null) {
                // Itera sobre cada objeto 'EventoDAO' en la lista 'eventos'.
                for (EventoDAO evento : eventos) {
        %>
        <tr>
            // Muestra el nombre del evento.
            <td><%= evento.getNombre() %></td>
            // Muestra la fecha del evento.
            <td><%= evento.getFecha() %></td>
            // Muestra el lugar del evento.
            <td><%= evento.getLugar() %></td>
            // Muestra el deporte del evento.
            <td><%= evento.getDeporte() %></td>
            // Muestra la capacidad del evento.
            <td><%= evento.getCapacidad() %></td>
            // Muestra las entradas vendidas del evento.
            <td><%= evento.getEntradasVendidas() %></td>
            // Muestra el estado del evento.
            <td><%= evento.getEstado() %></td>
            <td>
                <%
                    // Verifica si el evento tiene equipos participantes.
                    if (evento.getEquiposParticipantes() != null && !evento.getEquiposParticipantes().isEmpty()) {
                        // Itera sobre la lista de IDs de equipos participantes.
                        for (Integer equipoId : evento.getEquiposParticipantes()) {
                            out.print("Equipo " + equipoId + ", ");
                        }
                    } else {
                        // Si no hay equipos participantes, muestra el mensaje "No hay equipos".
                        out.print("No hay equipos");
                    }
                %>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="8">No hay eventos disponibles.</td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
</div>
</body>
</html>
