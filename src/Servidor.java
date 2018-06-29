import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Servidor{
	public static List<String> senhas = new LinkedList<>();
	public static void main(String[] args) throws IOException{
		try {
			//INSTANCIA DO SERVIDOR SOCKET
			ServerSocket servidor = new ServerSocket(12345);
			System.out.println("Servidor Iniciado!");
			
			while (true) {
				//SERVIDOR NA ESCUTADA DA PORTA 12345
				Socket socket = servidor.accept();
				new Thread(new ServidorCliente(socket)).start();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class ServidorCliente implements Runnable{
	
	Socket socket;
	String senha;
	String tv;
	
	public ServidorCliente(Socket socket) {
		// TODO Auto-generated constructor stub
		this.socket = socket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Scanner entrada = new Scanner(socket.getInputStream());
			
			String string = entrada.nextLine();
			
			if(string.contains("SIM")) {
				if(Servidor.senhas.isEmpty()) {
					PrintStream saida = new PrintStream(socket.getOutputStream());
					saida.println("NOSENHAS");
					saida.close();
				}else {
					PrintStream saida = new PrintStream(socket.getOutputStream());
					senha = Servidor.senhas.get(0);
					Servidor.senhas.remove(0);
					
					saida.println(senha);
					mostrarTV(socket, senha);
					saida.close();
					
				}
			}else {
				if(string.contains("N")) {
					Servidor.senhas.add(string);
					System.out.println("Senha recebida com sucesso!");
				}else if(string.contains("P")) {
					Servidor.senhas.add(0, string);
					System.out.println("Senha recebida com sucesso!");
				}
			}
				entrada.close();
				socket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public synchronized void mostrarTV(Socket socket, String senha) {
		
		String tv;
		
		try {
			Scanner entrada = new Scanner(socket.getInputStream());
			tv = entrada.nextLine();
			
			if(tv.contains("TV")) {
				PrintStream saida = new PrintStream(socket.getOutputStream());
				saida.println(senha);
				saida.close();
			}
			
			entrada.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
