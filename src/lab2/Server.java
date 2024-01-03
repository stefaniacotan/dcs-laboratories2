package lab2;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

class chatServerConn {
    static DatagramSocket c1_Socket;
    static InetAddress ip;

    static {
        try {
            c1_Socket = new DatagramSocket(1080);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    static {
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}

class serverSend extends Thread {
    public void run() {
        Scanner sc = new Scanner(System.in);
        while (true){
            synchronized (this) {
                byte[] sendMsg = new byte[500];
                sendMsg = sc.nextLine().getBytes();
                DatagramPacket sendPk = new DatagramPacket(sendMsg, sendMsg.length, chatServerConn.ip, 1081);
                try {
                    chatServerConn.c1_Socket.send(sendPk);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String message = new String(sendMsg);
                System.out.println("Sarah: " + message);

                if (message.equals("-1")) {
                    break;
                }
            }
        }
    }
}

class serverRec extends Thread {
    public void run() {
        while (true) {
            synchronized (this) {
                byte[] recMsg = new byte[500];
                DatagramPacket recPk = new DatagramPacket(recMsg, recMsg.length);

                try {
                    chatServerConn.c1_Socket.receive(recPk);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String message = new String(recMsg).trim();
                System.out.println("Anna: " + message);

                if (message.equals("-1")) {
                    break;
                }

            }
        }
    }
}

public class Server {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Server - Sarah, Client - Anna");

        serverSend sSend = new serverSend();
        serverRec sRec = new serverRec();

        sSend.start();
        sRec.start();

        sSend.join();
        sRec.join();
    }
}
