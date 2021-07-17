package com.company;
import java.io.*;
import java.util.Scanner;
import java.net.ServerSocket;


public class ServerInputThread extends Thread { //Deals with user input into the console, must be thread to allow server to continue other jobs
    Scanner scn = new Scanner(System.in); //Initialising
    private ServerSocket in;
    private MessageDisplay messagedisplay;


    public ServerInputThread(ServerSocket in, MessageDisplay messagedisplay) { //Constructor for SIT
        this.in = in;
        this.messagedisplay = messagedisplay;
    }


    public void run() {
        while (true) { //Runs indefinitely
            try {
                String isExit = scn.nextLine(); //For console input
                if(isExit.equals("Exit")){
                    System.out.println("Server Closing");
                    messagedisplay.setMessage("Server Has Closed, terminating connection"); //Notifies users to close connection
                    this.in.close(); //closes ServerSocket
                    System.exit(0); //terminate program
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}