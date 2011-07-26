package
{
	import flash.display.Sprite;
	import flash.events.MouseEvent;
	import fl.controls.TextArea;
	import flash.text.TextFormat;

	public class SocketExample extends Sprite
	{
		var socket:CustomSocket;

		public function SocketExample()
		{
			sendBtn.label = "send";
			connectBtn.label = "connect";
			sendBtn.mouseEnabled = false;
			connectBtn.mouseEnabled = true;
			sendBtn.addEventListener(MouseEvent.CLICK, sendHandler);
			connectBtn.addEventListener(MouseEvent.CLICK, connectHandler);
		}
		
		private function connectHandler(event:MouseEvent):void
		{
			sendBtn.mouseEnabled = true;
			connectBtn.mouseEnabled = false;
			
			socket = new CustomSocket(serverName.text, uint(portNumber.text));
			socket._output = output;
		}
		
		private function sendHandler(event:MouseEvent):void
		{
			socket.sendRequest(command.text);
			command.text = "";
		}



	}
}

import fl.controls.TextArea;
import flash.errors.*;
import flash.events.*;
import flash.net.Socket;

class CustomSocket extends Socket
{
	private var response:String;
	public var _output:TextArea;

	public function CustomSocket(host:String = null, port:uint = 0)
	{
		super(host, port);
		configureListeners();
	}

	private function configureListeners():void
	{
		addEventListener(Event.CLOSE, closeHandler);
		addEventListener(Event.CONNECT, connectHandler); //连接服务器成功后返回的信息
		addEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
		addEventListener(SecurityErrorEvent.SECURITY_ERROR, securityErrorHandler);
		addEventListener(ProgressEvent.SOCKET_DATA, socketDataHandler); //是接受服务器的信息时响应
	}

	private function writeln(str:String):void
	{
		str += "\n";
		try
		{
			writeUTFBytes(str);
		}
		catch (e:IOError)
		{
			Test.t(e);
		}
	}

	public function sendRequest(obj:Object):void
	{
		//Test.t("sendRequest");
		response = "";
		writeln(obj.toString());
		flush();
	}

	private function readResponse():void
	{
		var str:String = readUTFBytes(bytesAvailable);
		response += str;

		//Test.t("from server: " + response);
		//_output.text += response + "\n";
		_output.appendText(response + "\n");

		setScroll();
	}

	private function setScroll():void
	{
		_output.verticalScrollPosition = _output.maxVerticalScrollPosition;
	}

	private function closeHandler(event:Event):void
	{
		Test.t("closeHandler: " + event);
		Test.t(response.toString());
	}

	private function connectHandler(event:Event):void
	{
		Test.t("connectHandler: " + event);
		sendRequest("connect !");
	}

	private function ioErrorHandler(event:IOErrorEvent):void
	{
		Test.t("ioErrorHandler: " + event);
	}

	private function securityErrorHandler(event:SecurityErrorEvent):void
	{
		Test.t("securityErrorHandler: " + event);
	}

	private function socketDataHandler(event:ProgressEvent):void
	{
		Test.t("socketDataHandler: " + event);
		readResponse();
	}
}

















