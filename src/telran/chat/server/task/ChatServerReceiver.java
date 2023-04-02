package telran.chat.server.task;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import telran.chat.model.Message;
import telran.mediation.IBlkQueue;

public class ChatServerReceiver implements Runnable {
	Socket socket;
	IBlkQueue<Message> messageBox;

	public ChatServerReceiver(Socket socket, IBlkQueue<Message> messageBox) {
		this.socket = socket;
		this.messageBox = messageBox;
	}

	@Override
	public void run() {
		try (Socket socket = this.socket) {
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			while (true) {
				Message message = (Message) ois.readObject();
				messageBox.push(message);
			}
		} catch (IOException e) {
			System.out.println("Connection to client: " + socket.getInetAddress() + ":" + socket.getPort() + " closed");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
