package com.company;
import java.util.ArrayList;
import java.util.List;

public class MessageDisplay { //Observer to hold current message for broadcasting and a list of all the clients to broadcast to.

    private List<ClientHandler> clients = new ArrayList<ClientHandler>(); //Creating list to hold client handlers (individual clients)
    private String message;

    public String getMessage() { //returns most recent sent message
        return message;
    }

    public void setMessage(String message) { //Takes incoming message and sets as most recent one
        this.message = message;
        updateAllClients(); //When new message is received it needs to be broadcast to all clients
    }

    public void attach(ClientHandler clienthandler){ //Adds a new client handler to the arraylist
        clients.add(clienthandler);
    }

    public void disattach(ClientHandler clienthandler){ //Removes client from arraylist on exit command
        clients.remove(clienthandler);
    }

    public void updateAllClients(){ //Cycles through the list of client handlers and runs their update methods
        for (ClientHandler clienthandler : clients) {
            clienthandler.update(); //Sends the message to the client via client handler to be output on console
        }
    }
}
