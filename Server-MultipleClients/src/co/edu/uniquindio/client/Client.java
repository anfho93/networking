package co.edu.uniquindio.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Client {

	public static final int PORT = 3400;
	public static final String SERVER_LOCATION = "localhost";

	private Socket clientSocket;
	private DataOutputStream salidaDatos;
	private BufferedReader entradaDatos;

	public static void main(String[] args) {
		new Client();
	}

	public Client() {
		System.out.println("TCP Client");
		try {
			clientSocket = new Socket(SERVER_LOCATION, PORT);
			salidaDatos = new DataOutputStream(clientSocket.getOutputStream());
			entradaDatos = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			Thread escuchador = new Thread(new Runnable() {				
				@Override
				public void run() {
					while(true){
						try {
							if(!clientSocket.isClosed() && entradaDatos.ready())
								System.out.println("Mensaje entrante> "+entradaDatos.readLine()+"\n");
							
						} catch (IOException e) {

							e.printStackTrace();
							System.out.println("Comunicación finalizada!!!!");
							break;
						
						}
					}
						
				}
			});
			escuchador.start();
			
				Scanner lector =  new Scanner(System.in);
					while(lector.hasNextLine() ){
						try {
							if(!clientSocket.isClosed())
								salidaDatos.writeBytes(lector.nextLine()+"\n");							
							
						} catch (IOException e) {						
							e.printStackTrace();
							break;							
						}
					}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {
				if (entradaDatos != null)
					entradaDatos.close();
				if (salidaDatos != null)
					salidaDatos.close();
				if (clientSocket != null)
					clientSocket.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	
	
	

}
