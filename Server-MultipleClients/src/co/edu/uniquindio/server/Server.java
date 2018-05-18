package co.edu.uniquindio.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class Server {

    public final static int PORT = 3400;

    private ServerSocket socketBienvenida;
    private ArrayList<ManejadorComunicacion> manejadorClientes;

    public static void main(String[] args) {
        new Server();
    }

    public Server() {
        System.out.println("Servidor iniciado en el puerto 3400");
        manejadorClientes = new ArrayList<ManejadorComunicacion>();

        try {
            socketBienvenida = new ServerSocket(3400);

            while (true) {
                Socket cliente = socketBienvenida.accept();
                System.out.println("Conexion entrante");
                manejadorClientes.add(new ManejadorComunicacion(cliente, this));
                manejadorClientes.get(manejadorClientes.size() - 1).start();

                for (int i = 0; i < manejadorClientes.size(); i++) {
                    if (!manejadorClientes.get(i).isAlive()) {
                        manejadorClientes.remove(i);
                        i--;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socketBienvenida != null) {
                    socketBienvenida.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void enviarMensajeATodos(String mensaje) {
        for (Iterator<ManejadorComunicacion> it = manejadorClientes.iterator(); it.hasNext();) {
            ManejadorComunicacion comunicacion = it.next();
            if (comunicacion.isAlive()) {
                boolean enviarMensaje = comunicacion.enviarMensaje(mensaje);
                if (!enviarMensaje) {
                    it.remove();
                }
            }
        }
    }

}
