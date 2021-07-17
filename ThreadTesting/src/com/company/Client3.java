package com.company;

import java.net.Socket;
import java.util.Scanner;
import java.io.*;


public class Client3{



    public static void main(String[] args){
        try {
            int i;
            int port = 14001;
            String host = "localhost";
            for (i=0;i<args.length;i++){
                if (args[i].equals("-cca")){
                    host = (args[i+1]);
                }
                else if (args[i].equals("-ccp")){
                    port = Integer.parseInt(args[i+1]);
                }
            }
            Socket s = new Socket(host, port);
            DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            Scanner scn = new Scanner(System.in);
            System.out.println("Type Exit at any time to disconnect the client.\nPlease enter username:");
            String username = scn.nextLine();
            System.out.println("Welcome to the chat, " + username);
            Thread r = new ReadThread(dis);
            Thread w = new WriteThread(s, dos, dis, username);
            w.start();
            r.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
