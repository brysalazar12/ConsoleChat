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
public class Client3 {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("localhost", 3000);
			Listener listener = new Listener(socket);
			Speaker speaker = new Speaker(socket, "client3");
			listener.start();
			speaker.start();
		} catch (IOException ex) {
			Logger.getLogger(Client1.class.getName()).log(Level.SEVERE, null, ex);
		}
	}	
}
