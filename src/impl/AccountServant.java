package impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import Bank.IncorrectAccountNumber;
import Bank.IncorrectAmount;
import Bank._AccountDisp;
import Ice.Current;

public class AccountServant extends _AccountDisp{
	
	private long time;
	private final static String accounts = "accounts/";
	
	@Override
	public int getBalance(Current __current) {
		File f = new File(accounts + __current.id.name);
		
		if(!f.exists()){
			System.out.println("Account does not exist");
			return 'Z';
		}
		return Integer.parseInt(readFile(accounts + __current.id.name)[0].replace('\n', ' ').trim());
	}
	
	@Override
	public String getAccountNumber(Current __current) {
		File f = new File(__current.id.name);
		if(!f.exists())
			return "error";
		return readFile(__current.id.name)[1];
	}

	@Override
	public void transfer(String accountNumber, int amount, Current __current)
			throws IncorrectAccountNumber, IncorrectAmount {
		if(amount < 0)
			throw new IncorrectAmount();
		if(!accountNumber.matches("\\d+")){
			throw new IncorrectAccountNumber();
		}
		File f = new File(accounts + accountNumber);
		if(!f.exists()){
			throw new IncorrectAccountNumber();
		}
			
		File g = new File(accounts + __current.id.name);
		if(!g.exists()){
			throw new IncorrectAccountNumber();
		}
			
		
		String[] toAccountData = readFile(accounts + accountNumber);
		String[] fromAccountData = readFile(accounts + __current.id.name);
		toAccountData[0] = Integer.toString( Integer.parseInt(toAccountData[0].replace('\n', ' ').trim() ) + amount);
		System.out.println("AMOUNT1" + toAccountData[0]);
		fromAccountData[0] = Integer.toString(Integer.parseInt( fromAccountData[0].replace('\n', ' ').trim() ) - amount);
		System.out.println("AMOUNT2" + fromAccountData[0]);
		
		f.delete();
		g.delete();
		
		saveToFile(fromAccountData, accounts + __current.id.name);
		saveToFile(toAccountData,accounts +  accountNumber);
	}
	
	public static void saveToFile(String[] data, String name){
		PrintWriter writer;
		
		try {
			writer = new PrintWriter(name, "UTF-8");
			for(int i = 0; i < data.length; i++){
				writer.println(data[i].replace('\n', ' ').trim());	
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public static String[] readFile(String filename)
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
	   return content.split("\\n");
	}

	public long getTime() {
		return time;
	}

	public void setTime() {
		this.time = System.currentTimeMillis();
	}
}
