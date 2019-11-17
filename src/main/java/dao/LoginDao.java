package dao;

import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class LoginDao {
	public void insertIntoDB(String name, String password, long l) throws IOException {
		File file = new File(getClass().getClassLoader().getResource("transactions.txt").getFile());
		BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
		writer.newLine();
		writer.write("TransactionId:" + l + ", name:" + name + ", Time:" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		writer.close();
	}

	public boolean isValidUser(String name, String password) throws IOException {

		/*
		 * InputStream is = getClass().getResourceAsStream("password.txt");
		 * InputStreamReader isr = new InputStreamReader(is);
		 */
		ClassLoader cl = this.getClass().getClassLoader();
		InputStream inputStream = cl.getResourceAsStream("password.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)); 
		String line; 
		while ((line = reader.readLine()) != null) { 
			if (name.equals(line.split(":")[0])) { 
				reader.close();
				return line.split(":")[1].equals(password); 
				} 
			} 
		reader.close(); 
		return false
							;

	}
}

