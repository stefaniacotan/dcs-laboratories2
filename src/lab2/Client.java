package lab2;
import java.io.IOException;
import java.net.*;
import java.util.Scanner;

class chatConn {
    static DatagramSocket c1_Socket;
    static InetAddress ip;

    static {
        try {
            c1_Socket = new DatagramSocket(1081);
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

class clientSend extends Thread {
    public void run() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            byte[] sendMsg = new byte[500];
            sendMsg = sc.nextLine().getBytes();
            DatagramPacket sendPk = new DatagramPacket(sendMsg, sendMsg.length, chatConn.ip, 1080);
            try {
                chatConn.c1_Socket.send(sendPk);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String message = new String(sendMsg);
            System.out.println("Anna: " + message);

            if (message.equals("-1")) {
                break;
            }
        }
    }
}

class clientReceive extends Thread {
    public void run(){
        while (true) {
            synchronized (this){
                byte[] recMsg = new byte[500];

                DatagramPacket recPk = new DatagramPacket(recMsg, recMsg.length);
                try {
                    chatConn.c1_Socket.receive(recPk);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String message = (new String(recMsg)).trim();
                System.out.println("Sarah: " + message);

                if (message.equals("-1")) {
                    break;
                }

            }
        }
    }
}

public class Client {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Server - Sarah, Client - Anna");

        clientSend cSend = new clientSend();
        clientReceive cRec = new clientReceive();

        cSend.start();
        cRec.start();

        cSend.join();
        cRec.join();
    }
}
