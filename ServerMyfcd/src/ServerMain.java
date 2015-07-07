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
                new HandlerThread(client, clientCount);  
            }  
        } catch (Exception e) {  
            System.out.println("serverException: " + e.getMessage());  
        }  
    }  
  
    private class HandlerThread implements Runnable {  
        private Socket socket;  
        public HandlerThread(Socket client, int clientCount) {  
            socket = client;  
            new Thread(this).start();  
        }  
  
        public void run() {  
            try {  
                // 读取客户端数据  
                DataInputStream input = new DataInputStream(socket.getInputStream());
                // 向客户端回复信息  
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());  
                
                while (true) {
                	 String clientInputStr = input.readUTF();//这里要注意和客户端输出流的写方法对应,否则会抛 EOFException
                     // 处理客户端数据  
                	 System.out.println("client" + clientCount + "：" + clientInputStr); 
                     
                     System.out.print("me:\t");  
                     // 发送键盘输入的一行  
                     String s = new BufferedReader(new InputStreamReader(System.in)).readLine();  
                     out.writeUTF(s);
                     if (s.equals("[byebye债见]")) {
						break;
					}
				}
                
                out.close();  
                input.close(); 
            } catch (Exception e) {  
                System.out.println("服务器 run 异常: " + e.getMessage());  
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
