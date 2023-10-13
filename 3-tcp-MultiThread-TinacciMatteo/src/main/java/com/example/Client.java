package com.example;

import java.io.*;
import java.net.*;

public class Client {

    Socket mioSocket = null;

    int porta = 6789;

    BufferedReader in;
    DataOutputStream out;

    BufferedReader tastiera;

    String messaggio; 
    
    public Socket connetti()
    {
        try {

            System.out.println("Pronto a connettermi al server...");

            Socket mioSocket = new Socket(InetAddress.getLocalHost(), porta);

            System.out.println("Connesso!");

            in = new BufferedReader(new InputStreamReader(mioSocket.getInputStream()));

            out = new DataOutputStream(mioSocket.getOutputStream());

        } catch (UnknownHostException e) {

            System.err.println("Host sconosciuto");

        } catch (Exception e) {

            e.printStackTrace();

            System.err.println("Impossibile stabilire connessione");
        }

        return mioSocket;
    }

    public void comunica(){

        try {
            for(;;){

            tastiera = new BufferedReader( new InputStreamReader(System.in));
            System.out.println("Messaggio da inviare al server:");
            messaggio = tastiera.readLine();
            System.out.println("Invio:" + messaggio);
            out.writeBytes(messaggio + "\n");
            System.out.println("In attesa di una risposta...");
            String ricevuta = in.readLine();
            System.out.println("Risposta del server:" + ricevuta);

                if(ricevuta.equals("Hai indovinato!")){
                    System.out.println("elaborazione terminata, chiudo connessione");
                    mioSocket.close();
                    break;
                }
                
            }
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.connetti();
        client.comunica();
    }
}
