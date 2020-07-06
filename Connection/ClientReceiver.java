package Connection;

import Controller.HumanCollection;
import Human.HumanBeing;
import Utilites.Deserializator;

import javax.swing.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;


public class ClientReceiver {
    HumanCollection collection = new HumanCollection();
    Deserializator deserializator = new Deserializator();
    static DatagramSocket clientSocket;

    byte[] data = new byte[4096];
    private int clientPort;

    public ClientReceiver(int clientPort) throws SocketException {
        this.clientSocket = new DatagramSocket(clientPort);
        this.clientPort = clientPort;
    }


    public int getClientPort() {
        return clientPort;
    }

    public String receive() throws IOException {
        DatagramPacket packet = new DatagramPacket(data, data.length);
        clientSocket.setSoTimeout(2000);
        clientSocket.receive(packet);
        clientSocket.setSoTimeout(0);
        String received = new String(packet.getData(), 0, packet.getLength());
        return received;
    }

    public ArrayList receiveCollection() throws IOException, ClassNotFoundException {
        DatagramPacket packet = new DatagramPacket(data, data.length);
        clientSocket.setSoTimeout(2000);
        clientSocket.receive(packet);
        clientSocket.setSoTimeout(0);
        return ((ArrayList<HumanBeing>) Deserializator.toDeserialize(packet.getData()));
    }

    public Object receiveObject() throws IOException, ClassNotFoundException {
        DatagramPacket packet = new DatagramPacket(data, data.length);
        clientSocket.setSoTimeout(2000);
        clientSocket.receive(packet);
        clientSocket.setSoTimeout(0);
        return Deserializator.toDeserialize(packet.getData());
    }

    private static class Message extends JOptionPane {
        //public
    }
}



