package Lecture_04_Socket_Source_Code;

import java.io.*; 
import java.net.*;
 
public class lec_03_prg_09_udp_echo_client_multithread {
 
    public static void main(String args[]) throws Exception {      
        int clientport = 65456;
        String host = "127.0.0.1";
        System.out.println("echo-client is activated");
        
        InetAddress ia = InetAddress.getByName(host);
 
        SenderThread sender = new SenderThread(ia, clientport);
        sender.start();
        ReceiverThread receiver = new ReceiverThread(sender.getSocket());
        receiver.start();
    }
}      
 
class SenderThread extends Thread {
 
    private InetAddress serverIPAddress;
    private DatagramSocket udpClientSocket;
    private boolean stopped = false;
    private int serverport;
 
    public SenderThread(InetAddress address, int serverport) throws SocketException {
        this.serverIPAddress = address;
        this.serverport = serverport;
        this.udpClientSocket = new DatagramSocket();
        this.udpClientSocket.connect(serverIPAddress, serverport);
    }
    public void halt() {
        this.stopped = true;
    }
    public DatagramSocket getSocket() {
        return this.udpClientSocket;
    }
 
    public void run() {       
        try {    
        	byte[] data = new byte[1024];
        	data = "".getBytes();
        	DatagramPacket blankPacket = new DatagramPacket(data,data.length , serverIPAddress, serverport);
            udpClientSocket.send(blankPacket);
            
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            while (true) 
            {
                if (stopped)
                    return;
                System.out.print("> ");
                String clientMessage = inFromUser.readLine();
 
                if (clientMessage.equals("quit"))
                    break;

                byte[] sendData = new byte[1024];
 
                sendData = clientMessage.getBytes();
 
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverIPAddress, serverport);
 
                udpClientSocket.send(sendPacket);
 
                Thread.yield();
            }
            System.out.println("echo-client is de-activated");
        }
        catch (IOException ex) {
            System.err.println(ex);
        }
    }
}   
 
class ReceiverThread extends Thread {
 
    private DatagramSocket udpClientSocket;
    private boolean stopped = false;
 
    public ReceiverThread(DatagramSocket ds) throws SocketException {
        this.udpClientSocket = ds;
    }
 
    public void halt() {
        this.stopped = true;
    }
 
    public void run() {
        byte[] receiveData = new byte[1024];
        while (true) {            
            if (stopped)
            return;
            
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                udpClientSocket.receive(receivePacket);     
                String serverReply =  new String(receivePacket.getData(), 0, receivePacket.getLength());
                if(serverReply.equals("#DEREG")) {
                	break;
                }
                System.out.println(">>receive: " + serverReply);
 

                Thread.yield();
            } 
            catch (IOException ex) {
            System.err.println(ex);
            }
        }
    }
}