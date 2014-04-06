package uk.co.ystv.ystvbot.util;

import java.io.*;
import java.net.*;

import uk.co.ystv.ystvbot.Main;
 
public class Streamwatch implements Runnable{
    public void run(){
    	try {
			start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void start() throws Exception{
        String fromClient;
        String toClient;
 
        ServerSocket server = new ServerSocket(19997);
        System.out.println("wait for connection on port 19997");
 
        boolean run = true;
        while(run) {
            Socket client = server.accept();
            System.out.println("got connection on port 19997");
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            PrintWriter out = new PrintWriter(client.getOutputStream(),true);
 
            fromClient = in.readLine();
            System.out.println("received: " + fromClient);
            
            Main.bot.sendIRC().message("#YSTVfiringrange", fromClient);
        }
        System.exit(0);
    }
}