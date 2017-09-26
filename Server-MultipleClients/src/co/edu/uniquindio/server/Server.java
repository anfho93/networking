package co.edu.uniquindio.server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Server{

	public final static int PORT = 3400;

	private ServerSocket socketBienvenida;
	private ArrayList<ManejadorComunicacion> manejadorClientes;


	
	public static void main(String[] args) {
		new Server();
	}
		
	
	public Server() {
		System.out.println("Servidor iniciado en el puerto 3400");
		manejadorClientes = new  ArrayList<ManejadorComunicacion>();
		
		try {
			socketBienvenida = new ServerSocket(3400);
		
			while(true) {
				
				Socket cliente = socketBienvenida.accept();
				System.out.println("Conexion entrante");
				manejadorClientes.add(new ManejadorComunicacion(cliente,this));
				manejadorClientes.get(manejadorClientes.size()-1).start();
				
				for (int i = 0; i < manejadorClientes.size(); i++) {
					if(!manejadorClientes.get(i).isAlive()) {
						manejadorClientes.remove(i);
						i--;
					}					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {		
			
				if(socketBienvenida!=null)socketBienvenida.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	public void enviarMensajeATodos(String mensaje) {
		for (int i = 0; i < manejadorClientes.size(); i++) {
			if(manejadorClientes.get(i).isAlive() ) {
				manejadorClientes.get(i).enviarMensaje(mensaje);
			}					
		}
		
	}
		
}