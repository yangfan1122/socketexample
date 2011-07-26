package
{
	import flash.display.Sprite;
	import flash.external.ExternalInterface;
	/**
	 * ...
	 * @author yf
	 */
	public class Test extends Sprite 
	{
		static public function t(str:Object):void
		{
			trace(str.toString());
			ExternalInterface.call("test",str.toString());
		}
		static public function tt(str:Object):void
		{
			ExternalInterface.call("test1",str.toString());
		}
	}
	
}