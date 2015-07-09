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
			System.out.println("服务器启动成功，等待客户端连接...\n");  
			while (true) {  
				Socket client = serverSocket.accept();  
				clientCount++;
				System.out.println("client" + clientCount + "连接成功, 开聊吧");  
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
				// 读取客户端数据  
				DataInputStream input = new DataInputStream(socket.getInputStream());

				while (true) {
					String clientInputStr = input.readUTF();//这里要注意和客户端输出流的写方法对应,否则会抛 EOFException
					// 处理客户端数据  
					System.out.println("\nclient" + clientCount + "：" + clientInputStr); 
					if (clientInputStr.equals("[byebye债见]")) {
						break;
					}
				}

				input.close();
			} catch (Exception e) {  
				System.out.println("服务器 run 异常fromReceiver: " + e.getMessage());  
			} finally {  
				if (socket != null) {  
					try {  
						socket.close();  
					} catch (Exception e) {  
						socket = null;  
						System.out.println("服务端 finally 异常:" + e.getMessage());  
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
				// 向客户端回复信息  
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());  

				while (true) {
					System.out.print("me:\t");  
					// 发送键盘输入的一行  
					String s = new BufferedReader(new InputStreamReader(System.in)).readLine();  
					
					if (s.equals("[byebye债见]")) {
						out.writeUTF("[byebye债见]");
						break;
					}
					out.writeUTF(s);
				}

				out.close();
			} catch (Exception e) {  
				System.out.println("服务器 run 异常fromSender: " + e.getMessage());  
			} finally {  
				if (socket != null) {  
					try {  
						socket.close();  
					} catch (Exception e) {  
						socket = null;  
						System.out.println("服务端 finally 异常:" + e.getMessage());  
					}  
				}  
			} 
		}  
	}
	
}  
