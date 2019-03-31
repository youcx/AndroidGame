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
	//服务器连接
	public static ServerSocket serverSocket;
	//连接
	public static Socket socket;
	//端口
	public static final int PORT = 8888;
	public static void main(String[] args) {
		DataInputStream dis = null;
		DataOutputStream dos = null;
		try {
			serverSocket = new ServerSocket(PORT);
			System.out.println("正在等待客户端连接...");
			//这里处于等待状态，如果没有客户端连接，程序不会向下执行
			socket = serverSocket.accept();
			dis = new DataInputStream(socket.getInputStream());
			dos = new DataOutputStream(socket.getOutputStream());
			//读取数据
			String clientStr = dis.readUTF();
			//写出数据
			dos.writeUTF(clientStr);
			System.out.println("----客户端已成功连接!----");
			//得到客户端的IP
			System.out.println("客户端的IP =" + socket.getInetAddress());
			//得到客户端的端口号
			System.out.println("客户端的端口号 =" + socket.getPort());
			//得到本地端口号
			System.out.println("本地服务器端口号=" + socket.getLocalPort());
			System.out.println("-----------------------");
			System.out.println("客户端：" + clientStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {//我们把流的关闭写在finally里，即使读写出现问题，我们也能正常的关闭流！
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
