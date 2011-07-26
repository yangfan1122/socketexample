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
 *���̣߳���һ��flash�ͻ��˶�Ӧ�������շ�����
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
		System.out.println("------------����һ���߳�---------------");
		//�����е��û����͵�ǰ�û���½����Ϣ
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
				System.out.println("�յ��ͻ��˵�����:" + msg);
				output.print("����������:" + msg + "\0");
				output.flush();
			}
			System.out.println("���߳̽�����");
		}
		catch(Exception error)
		{
			System.out.println("���̷߳����쳣");
			try
			{
				input.close();
				output.close();
			}
			catch (Exception er)
			{
				System.out.println("�ر���Դ�����쳣");
			}
			System.out.println("���߳̽�����");
		}
	}
}
