package com.company;

import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {//Thread to deal with incoming client messages etc.
    final Socket s; //Initialising necessary data streams and observer
    private DataInputStream dis;
    private DataOutputStream dos;
    protected MessageDisplay messagedisplay;


    // Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos, MessageDisplay messagedisplay) { //Constructor for ClientHandler
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.messagedisplay = messagedisplay;
        this.messagedisplay.attach(this); //This attaches this clienthandler object to the arraylist in the observer
    }

    @Override
    public void run() {
        String received;
        try {
            while (true) {
                received = dis.readUTF(); //Reads incoming data for input from its client
                if (!received.equals("Exit")) { //If any message that isn't exit
                    messagedisplay.setMessage(received); //Sets state of observer's message to what the client sent
                } else{ //If the client typed exit
                    messagedisplay.setMessage("Another client has disconnected");
                    messagedisplay.disattach(this); //Removes it from observer's arraylist so it no longer tries to send this client messages
                }

            }
            } catch(IOException e){
                System.out.println("Client issue... Server still running");
            }
        }

    public void update() {
        try {
            dos.writeUTF(messagedisplay.getMessage()); //Sends the current message held by the observer to this handlers specific client
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    }
