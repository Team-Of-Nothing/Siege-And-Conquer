package com.mygdx.game;
import java.net.*;
import java.io.*;
import java.util.Scanner;


//TODO OGOLNIE CALOSC
public class Client {

    static final int PORT = 8080;

    public static void main(String[] args) {

        Socket clientSocket;
        PrintWriter out;
        BufferedReader in;
        Scanner keyboard;

        //setup
        try {
            clientSocket = new Socket("10.0.0.4", PORT);
            out = new PrintWriter(clientSocket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            keyboard = new Scanner(System.in);
        } catch (IOException e) {
            return;
        }
        new ClientWaiterForServer(clientSocket, in).start();
        System.out.println("Send Notifications, Format is [notification|time] \n");

        while (clientSocket.isConnected()) {
            String[] buffer = keyboard.nextLine().split("\\|");

            try {
                if (buffer.length != 2 || buffer[0].equals("")) {
                    throw new EmptyStringException("Empty");
                }
                long temp = Long.parseLong(buffer[1]);
                if(temp < 0){
                    System.out.println("Incorrect time input. Time cannot be less than 0s");
                }
            } catch (NumberFormatException e) {
                System.out.println("Incorrect input format");
                continue;
            } catch(EmptyStringException exc) {
                System.out.println("You missed something! Try [notification|time]");
                continue;
            }
            out.println(buffer[0] + "|" + buffer[1]);
            System.out.println("Notification Send");
        }

    }

}
class EmptyStringException extends Exception{
    public EmptyStringException(String s){
        super(s);
    }
}
