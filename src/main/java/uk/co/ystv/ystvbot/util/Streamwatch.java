package uk.co.ystv.ystvbot.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import uk.co.ystv.ystvbot.Main;

public class Streamwatch extends Thread {

	public void run() {
		while (true) {
			try {
				String fromClient;

				ServerSocket server = new ServerSocket(19997);
				System.out.println("wait for connection on port 19997");

				boolean run = true;
				while (run) {
					Socket client = server.accept();
					System.out.println("got connection on port 19997");
					BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

					fromClient = in.readLine();
					System.out.println("received: " + fromClient);

					try {
						Main.bot.sendIRC().message("#YSTV", fromClient);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
