package server;

import impl.NewsReceiverI;
import FinancialNews.*;

public class Receiver extends Thread{
	Ice.Communicator ic = null;
	NewsServerPrx proxy;
	
	public Receiver(Ice.Communicator ic, NewsServerPrx proxy){
		this.ic = ic;
		this.proxy = proxy;
	}
	
	public void run(){
        Ice.ObjectAdapter adapter = ic.createObjectAdapter("");

        Ice.Identity ident = new Ice.Identity();
        ident.name = java.util.UUID.randomUUID().toString();
        ident.category = "";
        NewsReceiverI callback = new NewsReceiverI();
        NewsReceiverPrx cprx = NewsReceiverPrxHelper.uncheckedCast(adapter.add(callback, ident));
        
        adapter.activate();
        Ice.Connection connection = proxy.ice_getConnection();
        connection.setAdapter(adapter);

        proxy.registerForNews(cprx);
        
        ic.waitForShutdown();

	}
	
}
