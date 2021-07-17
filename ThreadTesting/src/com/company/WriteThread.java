package com.company;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class WriteThread extends Thread { //Deals with scanning and sending any messages typed in to the clients console by user
    private DataOutputStream dos;
    private DataInputStream dis;
    private Socket socket;
    private String username;
    private int permission = 0;


    public WriteThread(Socket s, DataOutputStream dos, DataInputStream dis, String username) {
        this.socket = s;
        this.dos = dos;
        this.dis = dis;
        this.username = username;
    }

    public void run()
    {
        Scanner scn = new Scanner(System.in);
        while (true) {
            try {
                String toSend = scn.nextLine(); //Read console input
                if(toSend.equals("Server Has Closed, terminating connection")){ //Prevents user from sending termination message
                    System.out.println("Sorry, you do not have permission to send that message");
                    permission = 1; //Sets permission to 1 to stop message from sending in next line
                }
                else if ((!toSend.equals("Exit")) && (permission == 0)) { //If a normal message
                    dos.writeUTF(username + ": " + toSend); //Writes to the clients handler with the client specific username attached
                }
                else {
                    if(permission == 0) { //If message is Exit
                        System.out.println("Exiting");
                        dos.writeUTF(toSend); //Notifies handler of exiting so it can disattach from observer
                        this.dos.close(); //Closes Data streams and socket
                        this.dis.close();
                        this.socket.close();
                        System.exit(0); //Terminates program
                    }
                }
                permission = 0; //Resets permission to 0 for next message
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}