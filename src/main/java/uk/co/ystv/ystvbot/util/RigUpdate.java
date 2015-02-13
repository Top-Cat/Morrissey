package uk.co.ystv.ystvbot.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import uk.co.ystv.ystvbot.Main;

public class RigUpdate extends Thread{
	
	public void run() {
		while (true) {
			try {
				String previousAmount = RigUpdate.getRigTotal();
				while (true) {
					String checkAmount = RigUpdate.getRigTotal();
					if(!checkAmount.equals(previousAmount)){
						Main.bot.sendIRC().message("#YSTV", "New Rig donation! New total is £" + checkAmount + ". Up from £" + previousAmount + ".");
						previousAmount = checkAmount;
					}
					Thread.sleep(300000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String getRigTotal() {

		BufferedReader bufReader;
		String line;
		Boolean found = false; 

		try {
			URL url = new URL("https://yustart.hubbub.net/p/ystv-rig/");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			bufReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			while ((line = bufReader.readLine()) != null) {
				System.out.println(line);
				if (found == false && line.toLowerCase().contains("<div class=\"project-amount-raised\">")) {
					found = true;
				}
				else if(found == true){
					bufReader.close();
					return line.replaceAll("\\s+£","");
				}
			}
			return null;
		} catch (IOException e) {
			return null;
		}
	}
}

