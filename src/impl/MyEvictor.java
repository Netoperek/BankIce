package impl;

import java.util.HashMap;
import java.util.Map;

import Ice.Current;
import Ice.LocalObjectHolder;
import Ice.Object;
import Ice.ServantLocator;
import Ice.UserException;

public class MyEvictor implements ServantLocator {

	private int base;
	private Map<String, AccountServant> evictors = new HashMap<>();

	public MyEvictor() {
		this.base = 2;
	}

	public MyEvictor(int base) {
		this.base = base;
	}

	@Override
	public Object locate(Current curr, LocalObjectHolder cookie)
			throws UserException {
		if (evictors.containsKey(curr.id.name)){
			evictors.get(curr.id.name).setTime();
			return evictors.get(curr.id.name);
		}
			
		if (evictors.size() + 1 > base) {
			long max = -1;
			String id = "";
			for(String e : evictors.keySet()){
				if(max < evictors.get(e).getTime()){
					max = evictors.get(e).getTime();
					id = e;
				}
			}
			System.out.println("EVICTOR# REMOVING SERVANT");
			evictors.remove(id);
		}
		AccountServant aServ = new AccountServant();
		aServ.setTime();
		System.out.println("EVICTOR# ADDING NEW SERVANT");
		evictors.put(curr.id.name, aServ);
		return aServ;
	}

	@Override
	public void finished(Current curr, Object servant, java.lang.Object cookie)
			throws UserException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deactivate(String category) {
		// TODO Auto-generated method stub

	}
}
