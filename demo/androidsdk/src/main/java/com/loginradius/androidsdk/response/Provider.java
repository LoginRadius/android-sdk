package com.loginradius.androidsdk.response;

import java.util.List;

public class Provider
{
	public String Name;
	public String Endpoint;

	public static Provider findByName(String pName, List<Provider> providers) {
		for (Provider p : providers)
			if (p.Name.equalsIgnoreCase(pName))
				return p;
		return null;
	}
}
