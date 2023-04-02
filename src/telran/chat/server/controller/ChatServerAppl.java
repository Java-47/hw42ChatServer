package telran.chat.server.controller;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import telran.chat.model.Message;
import telran.chat.server.task.ChatServerReceiver;
import telran.chat.server.task.ChatServerSender;
import telran.mediation.BlkQueue;
import telran.mediation.IBlkQueue;

public class ChatServerAppl {

	public static void main(String[] args) {
		int port = 9000;
		ExecutorService executorService = Executors.newFixedThreadPool(40);
		IBlkQueue<Message> messageBox = new BlkQueue<>(10);
		ChatServerSender sender = new ChatServerSender(messageBox);
		Thread senderThread = new Thread(sender);
		senderThread.setDaemon(true);
		senderThread.start();
		try (ServerSocket serverSocket = new ServerSocket(port);) {
			try {
				while (true) {
					System.out.println("server wait...");
					Socket socket = serverSocket.accept();
					System.out.println("Connection established");
					System.out.println("Client host: " + socket.getInetAddress() + ":" + socket.getPort());
					sender.addClient(socket);
					ChatServerReceiver receiver = new ChatServerReceiver(socket, messageBox);
					executorService.execute(receiver);
				} 
			} finally {
				executorService.shutdown();
				executorService.awaitTermination(1, TimeUnit.MINUTES);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
