package org.ef.esper;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

import java.util.Random;
import java.util.Date;
public class App 
{
	public static void main( String[] args ) throws InterruptedException
	{
		App app = new App();
		app.run();
	}
	private Random generator = new Random();

	public void GenerateRandomTick(EPRuntime cepRT) {

		double price = (double) generator.nextInt(100);
		long timeStamp = System.currentTimeMillis();
		String symbol = "AAPL";
		Tick tick = new Tick(symbol, price, timeStamp);
		System.out.println("Sending tick:" + tick);
		cepRT.sendEvent(tick);

	}
	public void run() throws InterruptedException {
		//The Configuration is meant only as an initialization-time object.
		Configuration cepConfig = new Configuration();
		cepConfig.addEventType("StockTick", Tick.class.getName());
		EPServiceProvider cep = EPServiceProviderManager.getProvider("myCEPEngine", cepConfig);
		EPRuntime cepRT = cep.getEPRuntime();

		EPAdministrator cepAdm = cep.getEPAdministrator();
		EPStatement cepStatement = cepAdm.createEPL("select avg(price) as pricetotal from " +
			"StockTick(symbol='AAPL').win:length(1) " +
			"having avg(price) > 6.0");

		cepStatement.addListener(new CEPListener());

	       // We generate a few ticks...
		for (int i = 0; i < 5; i++) {
		    GenerateRandomTick(cepRT);
		}

	}
}
