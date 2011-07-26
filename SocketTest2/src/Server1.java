import java.net.*;

import java.io.*;

public class Server1 {
	public static void main(String[] args) {

		String xml = "<cross-domain-policy>";
		xml = xml + "<allow-access-from domain=\"*\" to-ports=\"*\" />";
		// xml = xml +
		// "<allow-access-from domain=\"localhost\" to-ports=\"5000,8080\" />";
		xml = xml + "</cross-domain-policy>";

		ServerSocket server = null;

		try {

			server = new ServerSocket(88);
			// x1

			System.out.println("服务器套接字已经被创建");

			while (true) {

				System.out.println("等待客户机");
				Socket newSocket = server.accept();// x2
				System.out.println("已与客户机连接");

				BufferedReader br = new BufferedReader(new InputStreamReader(
						newSocket.getInputStream()));
				PrintWriter pw = new PrintWriter(newSocket.getOutputStream());
				// 接收用户名
				char[] by = new char[22];
				br.read(by, 0, 22);
				String head = new String(by);
				System.out.println("消息头:" + head + ":");
				if (head.equals("<policy-file-request/>")) {
					pw.print(xml + "\0");
					pw.flush();
				} else {
					ServerThread thread = new ServerThread(newSocket);
					thread.start();
				}

			}

		} catch (IOException ie) {

			System.out.println(ie);

		} finally {

			try

			{

				if (server != null)
					server.close();// x3

			} catch (IOException ie) {
			}

		}

	}
}
