import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TV {
	

	
	public static void main(String[] args) {
		
		try {
            Socket socket = new Socket("127.0.0.1", 12345);
            new PrintStream(socket.getOutputStream()).println("TV");
            Scanner entrada = new Scanner(socket.getInputStream());
            while (entrada.hasNextLine()) {
                System.out.printf("SENHA: %s\n", entrada.nextLine());
            }
        } catch (IOException io) {
            System.out.println(io.getMessage());
        }
		
	}

}
