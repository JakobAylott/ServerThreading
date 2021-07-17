package com.company;

import java.net.Socket;
import java.util.Scanner;
import java.io.*;


public class ChatClient{ //The program run by the client



    public static void main(String[] args){
        try {
            int i;
            int port = 14001; //default values for port and host
            String host = "localhost";
            for (i=0;i<args.length;i++){ //If parameters have been passed in
                if (args[i].equals("-cca")){ //if '-cca' then the following argument is used as host
                    host = (args[i+1]);
                }
                else if (args[i].equals("-ccp")){ //if '-ccp' then the following argument is used as port
                    port = Integer.parseInt(args[i+1]);
                }
            }
            Socket s = new Socket(host, port); //Creating objects for socket and data streams and scanner
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            Scanner scn = new Scanner(System.in);
            System.out.println("Type Exit at any time to disconnect the client.\nPlease enter username:");
            String username = scn.nextLine(); //Username must be entered before any other actions occur so doesn't need to be thread
            System.out.println("Welcome to the chat, " + username);
            Thread r = new ReadThread(dis); //Creating read and write threads so that the client can send and receive messages concurrently
            Thread w = new WriteThread(s, dos, dis, username);
            w.start();
            r.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
