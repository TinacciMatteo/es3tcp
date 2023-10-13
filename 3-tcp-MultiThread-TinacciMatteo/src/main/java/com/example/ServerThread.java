package com.example;

import java.io.*;
import java.net.*;

public class ServerThread extends Thread {
    ServerSocket server = null;
    Socket socketClient = null;

    int porta = 6789;

    BufferedReader in;
    DataOutputStream out;

    String letto; 

    public ServerThread(Socket socket){
        this.socketClient = socket;
    }

    public void run(){
        try{
            comunica();
        } catch (Exception e ){
            e.printStackTrace(System.out);
        }
    }
     public Socket attendi(){

        try {

            System.out.println("Inizializzo il server...");

            server = new ServerSocket(porta);

            System.out.println("Server pronto in ascolto sulla porta: " + porta);

            socketClient = server.accept();

            System.out.println("Connessione stabilita con un client!");

            server.close();

           

        } catch (IOException e) {

            e.printStackTrace();
        }

        return socketClient;
    }

    public void comunica() throws Exception{

        in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
        out = new DataOutputStream(socketClient.getOutputStream());

        int numeroGenerato =  (int) (Math.random()*1000);
        System.out.println("Numero generato:" + numeroGenerato);

        for(;;){

            letto = in.readLine(); 
            int numeroRicevuto = 0;

            try {
                numeroRicevuto = Integer.parseInt(letto);
            } catch (Exception e) {
                out.writeBytes("Inserisci un numero valido" + '\n');
            }
            if(numeroRicevuto < 0 || numeroRicevuto > 999){
                out.writeBytes("Inserisci un numero valido " + '\n');
            }
            if(numeroRicevuto == numeroGenerato){
                out.writeBytes("Hai indovinato!" + '\n');
                break;
            }else if(numeroGenerato > numeroRicevuto){
                out.writeBytes("Il numero e' maggiore di quello inserito" + '\n');
                System.out.println("Il numero e' piu grande");
            }else {
                out.writeBytes("Il numero e' piu piccolo" + '\n');
                System.out.println("Il numero e' piu piccolo");
            }
        }
        out.close();
        in.close();
        System.out.println("Chiusura del socket" + socketClient);
        socketClient.close();
        
    }

        public static void main(String[] args) {
            MultiServer server = new MultiServer();
            server.startServer();
        }
    
}