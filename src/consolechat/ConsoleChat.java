/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolechat;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class ConsoleChat {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Runtime obj = null;

		
//		ConsoleChat consoleChat = new ConsoleChat();
//		consoleChat.run();
		if(args.length >= 1) {
			if(args[0].equals("client")) {
				System.out.print("Please Enter your name:");
				Scanner sc = new Scanner(System.in);
				String name = sc.nextLine();
				System.out.print("Please Enter host:");
				sc = new Scanner(System.in);
				String host = sc.nextLine();
				Client client = new Client(name,host);
				client.start();
			} else if(args[0].equals("server")) {
				Server server = new Server(3000);
				server.run();
			}
		} else {
			try {
				Runtime.getRuntime().exec("cmd.exe /c start java -jar D:\\netbeans_project\\ConsoleChat\\dist\\ConsoleChat.jar client");
			} catch (IOException ex) {
				Logger.getLogger(ConsoleChat.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public void run() {
		clientOrServer();
	}
	
	public String clientOrServer() {
		boolean isRun = true;
		while(isRun) {
			System.out.println("Press 1 for server press 2 for client.");
			Scanner sc = new Scanner(System.in);
			String ans = sc.next();
			System.out.println("You choose: " + ans);
			if(ans.equals("1") || ans.equals("2"))
				return ans;
			System.out.println("Invalid input");
		}
		return "";
	}
}
