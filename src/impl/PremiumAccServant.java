package impl;

import Bank.IncorrectAccountNumber;
import Bank.IncorrectAmount;
import Bank.IncorrectData;
import Bank._PremiumAccountDisp;
import Bank.currency;
import FinancialNews.Currency;
import Ice.Current;
import Ice.FloatHolder;
import Ice.IntHolder;

public class PremiumAccServant extends _PremiumAccountDisp{

	@Override
	public void calculateLoan(int amount, currency curr, int period,
			IntHolder totalCost, FloatHolder interestRate, Current __current)
			throws IncorrectData {
		interestRate.value = NewsReceiverI.interestMap.get( changeType(curr) );
		totalCost.value = (int) ( NewsReceiverI.exchangeMap.get( changeType(curr) ) * amount );
	}
	
	public static Currency changeType(currency c1){
		if(c1 == currency.CHF)
			return Currency.CHF;
		if(c1 == currency.USD)
			return Currency.USD;
		if(c1 == currency.EUR)
			return Currency.EUR;
		
		return Currency.PLN;
	}

	@Override
	public int getBalance(Current __current) {
		AccountServant account = new AccountServant();
		return account.getBalance(__current);
	}

	@Override
	public String getAccountNumber(Current __current) {
		return super.getAccountNumber();
	}

	@Override
	public void transfer(String accountNumber, int amount, Current __current)
			throws IncorrectAccountNumber, IncorrectAmount {
	}
	
	
}
