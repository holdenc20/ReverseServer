
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * A simple Swing-based client for the capitalization server. It has a main
 * frame window with a text field for entering strings and a textarea to see the
 * results of capitalizing them.
 */
public class ReverserClient {

	private Scanner in;
	private PrintWriter out;
	Socket socket;
	Scanner kb = new Scanner(System.in);


	public boolean update() {
		out.println(kb.nextLine());
		String response;
			response = in.nextLine();
			if (response == null || response.equals("")) {
				return false;
			}
		System.out.print(response);

		return true;
	}

	/**
	 * Implements the connection logic by prompting the end user for the
	 * server's IP address, connecting, setting up streams, and consuming the
	 * welcome messages from the server. The Capitalizer protocol says that the
	 * server sends three lines of text to the client immediately after
	 * establishing a connection.
	 */
	public void connectToServer() throws IOException {

		// Get the server address from a dialog box.
		String serverAddress = JOptionPane.showInputDialog(null, "Enter IP Address of the Server:",
				"Welcome to the Capitalization Program", JOptionPane.QUESTION_MESSAGE);

		// Make connection and initialize streams
		socket = new Socket(serverAddress, 9898);
		in = new Scanner(socket.getInputStream());
		// in = new Scanner( socket.getInputStream());
		out = new PrintWriter(socket.getOutputStream(), true);

		// Consume the initial welcoming messages from the server
		for (int i = 0; i < 3; i++) {
			System.out.println(in.nextLine() + "\n");
		}
	}

	void close() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Runs the client application.
	 */
	public static void main(String[] args) throws Exception {
		ReverserClient client = new ReverserClient();

		/*
		 * client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 * client.frame.pack(); client.frame.setVisible(true);
		 */
		client.connectToServer();
		while (client.update()) {

		}
		client.close();
	}
}