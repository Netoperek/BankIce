package impl;

import java.util.HashMap;
import java.util.Map;

import Bank.currency;
import FinancialNews.Currency;
import FinancialNews._NewsReceiverDisp;
import Ice.Current;

public class NewsReceiverI extends _NewsReceiverDisp {

	public static Map<Currency, Float> exchangeMap = new HashMap<Currency, Float>();
	public static Map<Currency, Float> interestMap = new HashMap<Currency, Float>();
	
	public NewsReceiverI(){
		NewsReceiverI.exchangeMap.put(Currency.EUR, 0.74f);
		NewsReceiverI.exchangeMap.put(Currency.CHF, 0.954f);
		NewsReceiverI.exchangeMap.put(Currency.USD, 0.154f);
		
		NewsReceiverI.interestMap.put(Currency.EUR, 0.74f);
		NewsReceiverI.interestMap.put(Currency.CHF, 0.954f);
		NewsReceiverI.interestMap.put(Currency.USD, 0.154f);
	}
	
	@Override
	public void exchangeRate(Currency curr1, Currency curr2, float rate, Current __current){
		System.out.println("Currency1: " + curr1 + " Currency2: " + curr2 + " rate: " + rate);
		exchangeMap.put(curr1, rate);
	}

	@Override
	public void interestRate(Currency curr, float rate, Current __current) {
		System.out.println("Currency" + curr + " rate: " + rate);
		interestMap.put(curr, rate);
	}

}
