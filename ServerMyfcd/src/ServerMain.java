import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

	private int clientCount = 0;

	public static void main(String[] args) {  
		ServerMain server = new ServerMain();  
		server.init();  
	}  

	public void init() {  
		try {  
			ServerSocket serverSocket = new ServerSocket(8899);  
			System.out.println("�����������ɹ����ȴ��ͻ�������...\n");  
			while (true) {  
				Socket client = serverSocket.accept();  
				clientCount++;
				System.out.println("client" + clientCount + "���ӳɹ�, ���İ�");  
				new HandlerReceiverThread(client, clientCount);  
				new HandlerSendThread(client);
			}  
		} catch (Exception e) {  
			System.out.println("serverException: " + e.getMessage());  
		}  
	}  

	private class HandlerReceiverThread implements Runnable {  
		private Socket socket;  
		public HandlerReceiverThread(Socket client, int clientCount) {  
			socket = client;  
			new Thread(this).start();  
		}  

		public void run() {  
			try {  
				// ��ȡ�ͻ�������  
				DataInputStream input = new DataInputStream(socket.getInputStream());

				while (true) {
					String clientInputStr = input.readUTF();//����Ҫע��Ϳͻ����������д������Ӧ,������� EOFException
					// ����ͻ�������  
					System.out.println("\nclient" + clientCount + "��" + clientInputStr); 
					if (clientInputStr.equals("[byebyeծ��]")) {
						break;
					}
				}

				input.close();
			} catch (Exception e) {  
				System.out.println("������ run �쳣fromReceiver: " + e.getMessage());  
			} finally {  
				if (socket != null) {  
					try {  
						socket.close();  
					} catch (Exception e) {  
						socket = null;  
						System.out.println("����� finally �쳣:" + e.getMessage());  
					}  
				}  
			} 
		}  
	}
	
	private class HandlerSendThread implements Runnable {  
		private Socket socket;  
		public HandlerSendThread(Socket client) {  
			socket = client;  
			new Thread(this).start();  
		}  

		public void run() {  
			try {  
				// ��ͻ��˻ظ���Ϣ  
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());  

				while (true) {
					System.out.print("me:\t");  
					// ���ͼ��������һ��  
					String s = new BufferedReader(new InputStreamReader(System.in)).readLine();  
					
					if (s.equals("[byebyeծ��]")) {
						out.writeUTF("[byebyeծ��]");
						break;
					}
					out.writeUTF(s);
				}

				out.close();
			} catch (Exception e) {  
				System.out.println("������ run �쳣fromSender: " + e.getMessage());  
			} finally {  
				if (socket != null) {  
					try {  
						socket.close();  
					} catch (Exception e) {  
						socket = null;  
						System.out.println("����� finally �쳣:" + e.getMessage());  
					}  
				}  
			} 
		}  
	}
	
}  
