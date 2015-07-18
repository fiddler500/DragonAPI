/*******************************************************************************
 * @author Reika Kalseki
 * 
 * Copyright 2015
 * 
 * All rights reserved.
 * Distribution of the software in any form is only allowed with
 * explicit, prior permission from the owner.
 ******************************************************************************/
package Reika.DragonAPI.IO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.jar.JarFile;

import org.apache.commons.io.FileUtils;

import Reika.DragonAPI.DragonAPICore;
import Reika.DragonAPI.Libraries.Java.ReikaJavaLibrary;

public class ReikaFileReader extends DragonAPICore {

	public static int getFileLength(File f) {
		int len;
		try {
			LineNumberReader lnr = new LineNumberReader(new FileReader(f));
			lnr.skip(Long.MAX_VALUE);
			len = lnr.getLineNumber()+1+1;
			lnr.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Could not load file data due to "+e.getCause()+" and "+e.getClass()+" !");
		}
		return len;
	}

	public static BufferedReader getReader(File f) {
		try {
			return new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static BufferedReader getReader(String path) {
		try {
			return new BufferedReader(new InputStreamReader(new FileInputStream(path)));
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public static String readTextFile(Class root, String path) {
		InputStream in = root.getResourceAsStream(path);
		StringBuilder sb = new StringBuilder();
		BufferedReader p;
		try {
			p = new BufferedReader(new InputStreamReader(in));
		}
		catch (NullPointerException e) {
			ReikaJavaLibrary.pConsole("File "+path+" does not exist!");
			return sb.toString();
		}
		int i = 0;
		try {
			String line = null;
			while((line = p.readLine()) != null) {
				if (!line.isEmpty()) {
					sb.append(line);
					i++;
					sb.append("\n");
				}
			}
			p.close();
		}
		catch (Exception e) {
			ReikaJavaLibrary.pConsole(e.getMessage()+" on loading line "+i);
		}
		return sb.toString();
	}

	public static ArrayList<String> getFileAsLines(String path, boolean printStackTrace) {
		return getFileAsLines(getReader(path), printStackTrace);
	}

	public static ArrayList<String> getFileAsLines(URL url, int timeout, boolean printStackTrace, ConnectionErrorHandler ch) {
		return getFileAsLines(url, timeout, printStackTrace, ch, null);
	}

	public static ArrayList<String> getFileAsLines(URL url, int timeout, boolean printStackTrace, ConnectionErrorHandler ch, DataFetcher f) {
		BufferedReader r = getReader(url, timeout, ch, f);
		return r != null ? getFileAsLines(r, printStackTrace) : null;
	}

	public static ArrayList<String> getFileAsLines(File f, boolean printStackTrace) {
		return getFileAsLines(getReader(f), printStackTrace);
	}

	public static ArrayList<String> getFileAsLines(BufferedReader r, boolean printStackTrace) {
		ArrayList<String> li = new ArrayList();
		String line = "";
		try {
			while (line != null) {
				line = r.readLine();
				if (line != null) {
					li.add(line);
				}
			}
			r.close();
		}
		catch (Exception e) {
			if (printStackTrace)
				e.printStackTrace();
		}
		return li;
	}
}
