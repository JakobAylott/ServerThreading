package com.company;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;


public class ChatServer {


    public static void main(String[] args) throws IOException {
        int i;
        int port = 14001;
        for (i=0;i<args.length;i++){ //If there are no parameters passed in, will not execute.
            if (args[0].equals("-csp")){ //If the parameter passed in is valid, the proceeding value is set as new port
                port = Integer.parseInt(args[1]);
            }
        }
        System.out.println("Server listening");
        MessageDisplay messagedisplay = new MessageDisplay(); //Creating new observer object
        ServerSocket in = new ServerSocket(port); //Initialising socket with given or default port
        Thread sit = new ServerInputThread(in, messagedisplay); //Creating ServerInputThread
        sit.start();


        while (true) { //runs indefinitely
            Socket s = null;
            try {
                s = in.accept();
                System.out.println("Server accepted connection on " + in.getLocalPort() + " ; " + s.getPort());
                DataInputStream dis = new DataInputStream(s.getInputStream()); //Creating data output and input stream objects for communication through socket
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                Thread t = new ClientHandler(s, dis, dos, messagedisplay); //Creating ClientHandler thread
                t.start(); //Must be a thread because multiple clients need to connect


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

