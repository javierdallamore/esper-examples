package org.ef.esper;

import org.ef.esper.MyListener;
import org.ef.esper.OrderEvent;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

public class App 
{
	public static void main( String[] args ) throws InterruptedException
	{
		App app = new App();
		app.run();
	}
	
	public void run() throws InterruptedException {
		Configuration config = new Configuration();
		config.addEventType(OrderEvent.class);
		EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);

		String expression = "select avg(price) from OrderEvent.win:time(10 sec)";
		EPStatement statement = epService.getEPAdministrator().createEPL(expression);
		MyListener listener = new MyListener();
		statement.addListener(listener);

		for(int i = 1; i <= 10; i++) {
			OrderEvent event = new OrderEvent("shirt", i);
			epService.getEPRuntime().sendEvent(event);
		}
		
		Thread.sleep(2000);
		
		for(int i = 1; i <= 10; i++) {
			OrderEvent event = new OrderEvent("shirt", i);
			epService.getEPRuntime().sendEvent(event);
		}
	}
}
