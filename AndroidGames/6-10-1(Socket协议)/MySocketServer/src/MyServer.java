import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 */

/**
 * @author Himi
 *
 */
public class MyServer {
	//����������
	public static ServerSocket serverSocket;
	//����
	public static Socket socket;
	//�˿�
	public static final int PORT = 8888;
	public static void main(String[] args) {
		DataInputStream dis = null;
		DataOutputStream dos = null;
		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("���ڵȴ��ͻ�������...");
			//���ﴦ�ڵȴ�״̬�����û�пͻ������ӣ����򲻻�����ִ��
			socket = serverSocket.accept();
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			//��ȡ����
			String clientStr = dis.readUTF();
			//д������
			dos.writeUTF(clientStr);
			System.out.println("----�ͻ����ѳɹ�����!----");
			//�õ��ͻ��˵�IP
			System.out.println("�ͻ��˵�IP =" + socket.getInetAddress());
			//�õ��ͻ��˵Ķ˿ں�
			System.out.println("�ͻ��˵Ķ˿ں� =" + socket.getPort());
			//�õ����ض˿ں�
			System.out.println("���ط������˿ں�=" + socket.getLocalPort());
			System.out.println("-----------------------");
			System.out.println("�ͻ��ˣ�" + clientStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {//���ǰ����Ĺر�д��finally���ʹ��д�������⣬����Ҳ�������Ĺر�����
			try {
				if (dis != null)
					dis.close();
				if (dos != null)
					dos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
