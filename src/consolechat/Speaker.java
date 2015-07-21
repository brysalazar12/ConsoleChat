/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolechat;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class Speaker extends Thread {
	protected Socket socket;
	protected String name;

	public Speaker(Socket socket, String name) {
		this.socket = socket;
		this.name = name;
	}

	@Override
	public void run() {
		try {
			PrintWriter writer = new PrintWriter(this.socket.getOutputStream());
			writer.println(this.name);
			writer.flush();
			while(true) {
				Scanner sc = new Scanner(System.in);
				writer.println("\r"+this.name + ": "+sc.nextLine());
				writer.flush();
			}
		} catch (IOException ex) {
			Logger.getLogger(Speaker.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
}
