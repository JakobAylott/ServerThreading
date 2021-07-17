package com.company;
import java.io.*;


public class ReadThread extends Thread { //Thread to read incoming communications to client
    private DataInputStream dis;



    public ReadThread(DataInputStream dis) {//Constructor
        this.dis = dis;
    }


    public void run() {
        while (true) { //Runs indefinitely
            try {
                String received = dis.readUTF(); //Reads incoming data stream in UTF (Unicode Transformation Format)
                System.out.println(received); //prints any messages received to the clients console
                if (received.equals("Server Has Closed, terminating connection")){ //If message is the termination message from the server
                    System.exit(0); //End the client program
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}