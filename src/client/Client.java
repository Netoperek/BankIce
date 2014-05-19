package client;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import SR.CertSignerPrx;
import SR.CertSignerPrxHelper;


public class Client 
{
	private final static String name = "Piotr";
	private final static String lastname = "Kala";
	private final static String crtFilePath = "c.csr"; 
	
	public static String readFile(String filename)
	{
	   String content = null;
	   File file = new File(filename); 
	   try {
	       FileReader reader = new FileReader(file);
	       char[] chars = new char[(int) file.length()];
	       reader.read(chars);
	       content = new String(chars);
	       reader.close();
	   } catch (IOException e) {
	       e.printStackTrace();
	   }
	   return content;
	}
	
	public static void main(String[] args) 
	{
		int status = 0;
		Ice.Communicator ic = null;

		try {
			ic = Ice.Util.initialize(args);

			Ice.ObjectPrx base = ic.propertyToProxy("SR.Proxy");

			CertSignerPrx cert = CertSignerPrxHelper.checkedCast(base);
			if (cert == null)
				throw new Error("Invalid proxy");

			String line = null;
			java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
			do
			{
				try
				{
					System.out.print("==> ");
					System.out.flush();
					line = in.readLine();
					if (line == null)
					{
						break;
					}
					if (line.equals("t"))
					{
				        Files.write( Paths.get("client.crt"), cert.signCSR( name, lastname, readFile(crtFilePath).getBytes()) );
					}
				}
				catch (java.io.IOException ex)
				{
					System.err.println(ex);
				}
			}
			while (!line.equals("x"));


		} catch (Ice.LocalException e) {
			e.printStackTrace();
			status = 1;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			status = 1;
		}
		if (ic != null) {
			try {
				ic.destroy();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				status = 1;
			}
		}
		System.exit(status);
	}
}
