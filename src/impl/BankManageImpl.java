package impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import Bank.IncorrectData;
import Bank.NoSuchAccount;
import Bank.PersonalData;
import Bank.RequestRejected;
import Bank._BankManagerDisp;
import Bank.accountType;
import Ice.Current;
import Ice.StringHolder;

public class BankManageImpl extends _BankManagerDisp {

	static Set<String> accountNumbers = new HashSet<>();
	private final static String accounts = "accounts/";
	private Ice.Communicator ic;
	private Ice.ObjectAdapter adapter;
	
	public BankManageImpl(Ice.Communicator ic, Ice.ObjectAdapter adapter){
		this.ic = ic;
		this.adapter = adapter;
	}
	
	private static String createUniqueAccountNr(){
		int digits = 4;
		StringBuilder builder = new StringBuilder();
		Random random = new Random();
		
		do{
			for(int i = 0 ; i < digits; i++){
				builder.append(random.nextInt(10));
			}
		}while( accountNumbers.contains( builder.toString()) );
		accountNumbers.add(builder.toString());
		return builder.toString();
	}

	@Override
	public void createAccount(PersonalData data, accountType type,
			StringHolder accountID, Current __current) throws IncorrectData,
			RequestRejected {
		PrintWriter writer;
		int balance = 1500100900;
		String accountNumber = createUniqueAccountNr();
		if(!data.firstName.matches("([a-zA-Z])+"))
			throw new IncorrectData("Incorrect first name");
		if(!data.lastName.matches("([a-zA-Z])+"))
			throw new IncorrectData("Incorrect last name");
		if(data.NationalIDNumber.length() != 11)
			throw new IncorrectData("Wrong NationalIDNumber length");
		try {
			writer = new PrintWriter(accounts + accountNumber, "UTF-8");
			writer.println(balance);
			writer.println(accountNumber.toString());
			writer.println(data.firstName);
			writer.println(data.lastName);
			writer.println(data.NationalIDNumber);
			writer.println(type);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		accountID.value = accountNumber;
		
		if(type == accountType.PREMIUM){
			adapter.add( new PremiumAccServant(), ic.stringToIdentity(accountNumber) );
		}
	}


@Override
	public void removeAccount(String accountID, Current __current)
			throws IncorrectData, NoSuchAccount {

		File f = new File(accounts + accountID);
		if (!f.exists())
			throw new NoSuchAccount();
		if (accountID.matches("//d+"))
			throw new IncorrectData();
		
		String pesel = AccountServant.readFile(accounts + accountID)[4].replace('\n', ' ').trim();
		if( !pesel.equals(__current.ctx.get("pesel")) ){
			throw new IncorrectData("You cannot remove" +
					" account that is NOT yours");
		}
		f.delete();
	}

}
