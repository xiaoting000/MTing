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
                // ��ȡ�ͻ�������  
                DataInputStream input = new DataInputStream(socket.getInputStream());
                // ��ͻ��˻ظ���Ϣ  
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());  
                
                while (true) {
                	 String clientInputStr = input.readUTF();//����Ҫע��Ϳͻ����������д������Ӧ,������� EOFException
                     // ����ͻ�������  
                	 System.out.println("client" + clientCount + "��" + clientInputStr); 
                     
                     System.out.print("me:\t");  
                     // ���ͼ��������һ��  
                     String s = new BufferedReader(new InputStreamReader(System.in)).readLine();  
                     out.writeUTF(s);
                     if (s.equals("[byebyeծ��]")) {
						break;
					}
				}
                
                out.close();  
                input.close(); 
            } catch (Exception e) {  
                System.out.println("������ run �쳣: " + e.getMessage());  
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
