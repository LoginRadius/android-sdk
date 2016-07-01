package com.loginradius.sdk.util;

import java.util.Map;

/**
 * Object for setting LoginRadius end point into requesting urls
 * 
 *
 */
public class Endpoint 
{
	private static final String LOGINRADIUS_API_ROOT = "https://api.loginradius.com";

	/**
	 * Creates url after appending LoginRadius api root url
	 * @param relativeUrl url for appending to the api url
	 * @return complete url for fetching data
	 */
	public static String GetRequestUrl(String relativeUrl) 
	{
		if (relativeUrl.startsWith("/")) return LOGINRADIUS_API_ROOT + relativeUrl;
		else return LOGINRADIUS_API_ROOT + "/" + relativeUrl;
	}

	/**
	 * Creates url after appending loginradius api root url and query parameters
	 * @param relativeUrl url for appending to the api url
	 * @param queryArgs extra parameters for sending with url
	 * @return complete url for fetching data
	 */
	public static String GetRequestUrl(String relativeUrl, Map<String, String> queryArgs) 
	{
		String keyvalueString = createKeyValueString(queryArgs);
		String url = GetRequestUrl(relativeUrl);
		if (url.contains("?")) return url + "&" + keyvalueString;

		return url + "?" + keyvalueString;
	}

	/**
	 * Creates key-value string
	 * @param queryArgs parameters that will attach to the url
	 * @return query string with the given parameters
	 */
	public static String createKeyValueString(Map<String, String> queryArgs) 
	{
		if (queryArgs != null) 
		{
			String[] sb = new String[queryArgs.size()];
			int i = 0;
			for (Map.Entry<String, String> entry : queryArgs.entrySet()) 
			{
				sb[i] = entry.getKey() + "=" + entry.getValue();
				i++;
			}
			return combine(sb, "&");
		} 
		else return null;
	}

	/**
	 * Combine to create key-value string
	 * @param s Array String where the glue will be appended
	 * @param glue String to be appended with Array String
	 * @return appended String
	 */
	public static String combine(String[] s, String glue) 
	{
		int k = s.length;
		if (k == 0)return null;
		StringBuilder out = new StringBuilder();
		out.append(s[0]);
		for (int x = 1; x < k; ++x) out.append(glue).append(s[x]);
		return out.toString();
	}
}
