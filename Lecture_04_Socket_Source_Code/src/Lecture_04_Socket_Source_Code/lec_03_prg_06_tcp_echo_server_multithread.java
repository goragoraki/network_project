package Lecture_04_Socket_Source_Code;

import java.net.*;               
import java.io.*;      

public class lec_03_prg_06_tcp_echo_server_multithread {
    public static void main(String[] args) {           
        try{
            ServerSocket server = new ServerSocket(65456);   
            System.out.println("echo-server is activated");       
            while(true){   
                Socket sock = server.accept();
                EchoThread echothread = new EchoThread(sock);
                echothread.setDaemon(true);
                echothread.start();
                System.out.println("> server loop running in thread (main thread)" +
                echothread.getName());
            }
        }catch(Exception e){       
            System.out.println(e);   
        }   
    }      
}            
           
class EchoThread extends Thread{           
    private Socket sock;       
    public EchoThread(Socket sock){       
        this.sock = sock;   
    }       
    public void run(){       
        try{   
            InetAddress inetaddr = sock.getInetAddress();
            System.out.println("> cilent connected by " + inetaddr.getHostAddress() + 
            		" with Port number " + sock.getPort());
            OutputStream out = sock.getOutputStream();
            InputStream in = sock.getInputStream();
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
            BufferedReader br = new BufferedReader(new InputStreamReader(in));   
            String line = null;   
            while((line = br.readLine()) != null){   
                System.out.println("> echoed : " + line + " by " + currentThread());
                pw.println(line);
                pw.flush();
                if (line.equals("quit")) {
                	break;
                }
            }   
            pw.close();   
            br.close();   
            sock.close();   
        }catch(Exception e){       
            System.out.println(e);   
        }       
    }           
}    

