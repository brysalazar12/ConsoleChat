/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolechat;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class Client {
	protected String name;
	protected String host;
	protected volatile static boolean isConnected = false;
	protected final int port;
	
	public Client(String name,String host, int port) {
		this.name = name;
		this.host = host;
		this.port = port;
	}
	public void start() {
		try {
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					while (!Client.isConnected) {
						try {
							if(!Client.isConnected) {
								System.out.print("\rConnecting    ");
								Thread.sleep(500);
							}
							if(!Client.isConnected) {
								System.out.print("\rConnecting .  ");
								Thread.sleep(500);
							}
							if(!Client.isConnected) {
								System.out.print("\rConnecting .. ");
								Thread.sleep(500);
							}
							if(!Client.isConnected) {
								System.out.print("\rConnecting ...");
								Thread.sleep(500);
							}
						} catch (InterruptedException ex) {
							Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
						}
					}
				}
			});
			t.start();
			System.out.println("Port: " + this.port);
			Socket socket = new Socket(this.host, this.port);
			Client.isConnected = true;
			System.out.println("");
			System.out.println("Successfully connected.");
			Listener listener = new Listener(socket);
			Speaker speaker = new Speaker(socket, this.name);
			listener.start();
			speaker.start();
		} catch (IOException ex) {
			Client.isConnected = true;
//			Logger.getLogger(Client1.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
