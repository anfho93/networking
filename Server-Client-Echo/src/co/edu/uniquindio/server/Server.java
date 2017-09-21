package co.edu.uniquindio.server;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public final static int PORT = 3400;

	private ServerSocket socketBienvenida;
	private Socket socketClienteEntrante;
	private DataOutputStream salidaACliente;
	private BufferedReader entradaDeCliente;

	
	public static void main(String[] args) {
		new Server();
	}
	
	public Server() {
		System.out.println("Servidor iniciado en el puerto 3400");
		
		
		try {
			socketBienvenida = new ServerSocket(3400);
			
			while(true) {
				
				socketClienteEntrante = socketBienvenida.accept();
				
				System.out.println("Conexion entrante....");
				
				entradaDeCliente = new BufferedReader(new InputStreamReader(socketClienteEntrante.getInputStream()));
				salidaACliente = new DataOutputStream(socketClienteEntrante.getOutputStream());
				String mensajeDelCliente = entradaDeCliente.readLine();
				
				String respuesta =  mensajeDelCliente;
				System.out.println(respuesta);
				salidaACliente.writeBytes(respuesta+"\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {		
			
				if(entradaDeCliente!=null) entradaDeCliente.close();
				if(salidaACliente !=null) salidaACliente.close();
				if(socketClienteEntrante!=null) socketClienteEntrante.close();
				if(socketBienvenida!=null)socketBienvenida.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		

	}

}
