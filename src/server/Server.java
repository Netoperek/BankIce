package server;

import impl.AccountServant;
import impl.BankManageImpl;
import impl.MyEvictor;
import FinancialNews.NewsServerPrx;
import FinancialNews.NewsServerPrxHelper;
import Ice.Identity;

public class Server {
	
	public void registryToFinanceNews(String args[]) throws Exception{
		Ice.Communicator ic = null;
		
		try {
			//ic = Ice.Util.initialize(args);

			Ice.ObjectPrx base = ic.propertyToProxy("SR.Proxy");

			NewsServerPrx servPrx = NewsServerPrxHelper.checkedCast(base);
			if (servPrx == null)
				throw new Exception();
			
		    new Receiver(ic, servPrx).start();
		}
		catch (java.io.IOException ex)
		{
			System.err.println(ex);
		}
	}
	
	
    public void t1(String[] args)
    {
        int status = 0;
        Ice.Communicator ic = null;
        
        try
        {
            ic = Ice.Util.initialize(args);

            //Ice.PluginManager pluginMgr = ic.getPluginManager();
            //Ice.Plugin plugin = pluginMgr.getPlugin("IceSSL");
            //IceSSL.Plugin sslPlugin = (IceSSL.Plugin)plugin;
            //sslPlugin.setCertificateVerifier(new MyCertificateVerifier());
			Ice.ObjectPrx base = ic.propertyToProxy("SR.Proxy");

			NewsServerPrx servPrx = NewsServerPrxHelper.checkedCast(base);
			if (servPrx == null)
				throw new Exception();
			
		    new Receiver(ic, servPrx).start();
            
            Ice.ObjectAdapter adapter = ic.createObjectAdapter("czak_norris");
            BankManageImpl bankManageImpl = new BankManageImpl(ic, adapter);
            
            Ice.Object bankManageImplObj = bankManageImpl;
            
            adapter.add(bankManageImplObj, ic.stringToIdentity("bankManager"));

            Ice.ServantLocator myEvictor = new MyEvictor();
            adapter.addServantLocator(myEvictor, "");
            
            adapter.activate();
            
            System.out.println("Entering event processing loop...");

            ic.waitForShutdown();
        }
        catch (Exception e)
        {
    	    System.err.println(e);
            status = 1;
        }
        if (ic != null)
        {
            // Clean up
            //
            try
            {
                ic.destroy();
            }
            catch (Exception e)
            {
                System.err.println(e);
                status = 1;
            }
        }
        System.exit(status);
    }


    public static void main(String[] args) throws Exception
    {
        Server app = new Server();
        app.t1(args);
    }
}
