/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package consolechat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class Server {

    protected Socket clientSocket;
    protected volatile static ServerSocket serverSocket;
    protected int port;
    protected volatile static boolean isListenConnection;
    protected volatile static ArrayList<Socket> socketList;
	protected volatile static ArrayList<Worker> workerList;

    public Server(int port) {
        this.port = port;
        Server.isListenConnection = true;
		socketList = new ArrayList<>();
		workerList = new ArrayList<>();
    }

    public void stopListening() {
        Server.isListenConnection = false;
        try {
            Server.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            Server.serverSocket = new ServerSocket(this.port);

            // listen for client to connect
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(Server.isListenConnection) {
                        try {
                            Socket client = Server.serverSocket.accept();
							BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
							String line = reader.readLine();
							System.out.println(line + " is connected. ip:" + client.getInetAddress().toString());
							for(Socket sock : socketList) {
								PrintWriter writer = new PrintWriter(sock.getOutputStream());
								writer.println(line + " is connected");
								writer.flush();
							}
							socketList.add(client);
							Worker worker = new Worker(client,socketList);
							worker.setIpAddress(client.getInetAddress().toString());
							worker.setClientName(line);
							worker.start();
							workerList.add(worker);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
			t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Worker extends Thread {
        private final Socket client;
        private final ArrayList<Socket> socketList;
		private String name;
		private String ipAddress;

		public Worker(Socket client, ArrayList<Socket> socketList) {
            this.socketList = socketList;
            this.client = client;
		}

		public void setClientName(String name) {
			this.name = name;
		}

		public void setIpAddress(String ip)
		{
			this.ipAddress = ip;
		}

        @Override
        public void run() {
            String line;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
                while((line = reader.readLine()) != null) {
					System.out.println("from " + this.name + ": " + line);
					for (Socket socketList1 : socketList) {
						// broadcast to all socket except the one who send the message
						if (socketList1.getInetAddress() != this.client.getInetAddress()) {
							PrintWriter writer = new PrintWriter(socketList1.getOutputStream());
							writer.println(line);
							writer.flush();
						}
					}
                }
            } catch (IOException e) {
//                e.printStackTrace();
				System.out.println(this.name + " is disconnected. ip:" + this.ipAddress);
				for(Socket socket : socketList) {
					try {
						PrintWriter writer = new PrintWriter(socket.getOutputStream());
						writer.println(this.name + " is disconnected");
						writer.flush();
					} catch (IOException ex) {
						Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
					}
				}
            }
        }
    }
}