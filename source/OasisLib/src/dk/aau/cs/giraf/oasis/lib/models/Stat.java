package dk.aau.cs.giraf.oasis.lib.models;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class Stat<Key, Type, Value> extends HashMap<Key, HashMap<Type, Value>> {

	private static final long serialVersionUID = 1L;

	public Stat() {
		super();
	}

	public void addValue(Key key, Type type, Value value)
	{
		HashMap<Type, Value> typeMap = get(type);
		if(typeMap == null) {
			typeMap = new HashMap<Type, Value>();
			put(key, typeMap);
		}
		typeMap.put(type, value);
	}

	public static String toStringStat(Stat<String, String, String> stat) {
		String returnString = null;
		byte[] bytes = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos); 
			oos.writeObject(stat);
			oos.flush();
			oos.close();
			bos.close();
			bytes = bos.toByteArray();

			StringBuilder sb = new StringBuilder();
			for (byte b : bytes) {
				sb.append(b + ",");
			}
			returnString = sb.toString();

		}
		catch (IOException ex) {
			bytes = new byte[] {0};
		}
		return returnString;
	}

	public static Stat<String, String, String> toStat(String bytes)	{
		String[] stringBytes = bytes.split(",");
		byte[] newAllBytes = new byte[stringBytes.length];
		int i = 0;
		for (String s : stringBytes) {
			newAllBytes[i] = Byte.parseByte(s);
			i++;
		}

		Stat<String, String, String> stat = new Stat<String,String,String>();
		Object obj = null;
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream (newAllBytes);
			ObjectInputStream ois = new ObjectInputStream (bis);
			obj = ois.readObject();
		}
		catch (IOException ex) {
			stat.addValue("IO", "ex", "!!");
		}
		catch (ClassNotFoundException ex) {
			stat.addValue("CL", "ex", "!!");
		}
		if (obj != null)
			stat = (Stat<String, String, String>)obj;

		return stat;
	}
}