package co.edu.uniquindio.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

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
			String mensajeSalida = JOptionPane.showInputDialog(null, "Ingrese un mensaje!!!!");

			salidaDatos.writeBytes(mensajeSalida+"\n");
			String mensajeRecibido = entradaDatos.readLine();
			System.out.println(mensajeRecibido);

		} catch (IOException e) {
			// TODO Auto-generated catch block
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
