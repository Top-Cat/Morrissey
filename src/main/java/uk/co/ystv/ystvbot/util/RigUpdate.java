package uk.co.ystv.ystvbot.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import lombok.Getter;
import uk.co.ystv.ystvbot.Main;

public class RigUpdate extends Thread {

	@Getter int rigTotal = 0;

	public void run() {
		while (true) {
			try {
				doUpdate();
				int previousAmount = rigTotal;
				while (true) {
					doUpdate();
					if (rigTotal > previousAmount) {
						Main.bot.sendIRC().message("#YSTV", "New Rig donation! New total is £" + rigTotal + ". Up from £" + previousAmount + ".");
						previousAmount = rigTotal;
					}
					Thread.sleep(300000);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public int doUpdate() {

		BufferedReader bufReader;
		String line;
		Boolean found = false;

		try {
			URL url = new URL("https://yustart.hubbub.net/p/ystv-rig/");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			bufReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			while ((line = bufReader.readLine()) != null) {
				if (found == false && line.toLowerCase().contains("<div class=\"project-amount-raised\">")) {
					found = true;
				} else if (found == true) {
					bufReader.close();
					rigTotal = Integer.parseInt(line.replaceAll("\\s+£", ""));
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rigTotal;
	}
}
