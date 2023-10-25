package socket;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        String serverHost = "localhost";
        int serverPort = 1950;

        try {
            Socket clientSocket = new Socket(serverHost, serverPort);

            DataInputStream in = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter the value of x: ");
            int x = Integer.parseInt(reader.readLine());
            System.out.print("Enter the value of y: ");
            int y = Integer.parseInt(reader.readLine());
            out.writeInt(x);
            out.writeInt(y);
            double result = in.readDouble();

            System.out.println("Percentage of " + x + " to " + y + " is " + result + "%");

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
