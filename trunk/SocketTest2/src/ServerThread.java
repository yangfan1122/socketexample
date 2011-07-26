/*
 * $Id: ServerThread.java $ 
 * @author  soda sujun10@21cn.com
 * @version  1.0
 * <br>This program is protected by copyright laws.
 * <br>Program Name:
 * <br>Date:
 */

import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/*
 *子线程，与一个flash客户端对应，负责收发数据
 */
public class ServerThread extends Thread
{
	private Socket				socket;
	private BufferedReader		input;
	private PrintWriter			output;

	public ServerThread(Socket socket)
	{
		this.socket = socket;
	}

	public void run()
	{
		System.out.println("------------启动一个线程---------------");
		//向所有的用户发送当前用户登陆的信息
		try
		{
			input = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			//output = new PrintWriter(socket.getOutputStream());
			output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8")),true);
			
			while(true)
			{
				//System.out.println("input:" + input);
				String msg = input.readLine();
				if(msg == null)
				{
					break;
				}
				System.out.println("收到客户端的数据:" + msg);
				output.print("服务器返回:" + msg + "\0");
				output.flush();
			}
			System.out.println("子线程结束了");
		}
		catch(Exception error)
		{
			System.out.println("子线程发生异常");
			try
			{
				input.close();
				output.close();
			}
			catch (Exception er)
			{
				System.out.println("关闭资源发生异常");
			}
			System.out.println("子线程结束了");
		}
	}
}
