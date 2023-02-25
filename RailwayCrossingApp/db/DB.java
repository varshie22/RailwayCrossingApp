package RailwayCrossingApp.db;

import java.util.LinkedHashMap;
import java.util.Map;
import java.io.*;

import RailwayCrossingApp.model.RailwayCrossing;
import RailwayCrossingApp.model.User;

public class DB implements DAO{

	// users is a collection which will hold User Objects, BUT Temporarily i.e. till the application is running
	private LinkedHashMap<String, User> users = new LinkedHashMap<String, User>();

	// crossings is a collection which will hold Railway Crossing Objects, BUT Temporarily i.e. till the application is running
	private LinkedHashMap<String, RailwayCrossing> crossings = new LinkedHashMap<String, RailwayCrossing>();
	
	private static DB db;
	
	private DB(){
		populateAdminUsers();
	}
	
	// Singleton Design Pattern
	public static DB getInstance() {
		if(db == null) {
			db = new DB();
		}
		return db;
	}
	
	void populateAdminUsers() {
		User user1 = new User("Samarth", "sam@gmail.com", "123qwe", 2);
		User user2 = new User("Varsha", "varsha@gmail.com", "123qwe", 2);
		set(user1);
		set(user2);
	}
	final static String outputFilePath2 = "crossings.txt";
	final static String outputFilePath1 = "users.txt";

	// Performs both insert and update
	public boolean set(Object object) {
		File file1 = new File(outputFilePath1);
		File file2 = new File(outputFilePath2);
		BufferedWriter bf1 = null;
		BufferedWriter bf2 = null;
		if(object instanceof User) {
			User user = (User)object;
			users.put(user.getEmail(), user);
			Map<String, User> map = (Map<String, User>) users;
			try {
				bf1 = new BufferedWriter(new FileWriter(file1));

				for (Map.Entry<String, User> entry :
						map.entrySet()) {
					// put key and value separated by a colon
					bf1.write(entry.getKey() + ":"
							+ entry.getValue());
					bf1.newLine();
				}
				bf1.flush();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				try {
					bf1.close();
				}
				catch (Exception e) {
				}
			}
			return true;
		}else {
			RailwayCrossing crossing = (RailwayCrossing)object;
			crossings.put(crossing.getPersonInCharge().getEmail(), crossing);
			Map<String, RailwayCrossing> map = (Map<String, RailwayCrossing>) crossings;
			try {
				bf2 = new BufferedWriter(new FileWriter(file2));

				for (Map.Entry<String, RailwayCrossing> entry :
						map.entrySet()) {
					// put key and value separated by a colon
					bf2.write(entry.getKey() + ":"
							+ entry.getValue());
					bf2.newLine();
				}
				bf2.flush();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				try {
					bf2.close();
				}
				catch (Exception e) {
				}
			}
			return true;
		}
	}
	
	public boolean delete(Object object) {
		if(object instanceof User) {
			User user = (User)object;
			users.remove(user.getEmail());
			return true;
		}else {
			RailwayCrossing crossing = (RailwayCrossing)object;
			crossings.remove(crossing.getPersonInCharge().getEmail());
			return true;
		}
	}
	
	public Map<String, ?> retrieve(Object object) {
		if(object instanceof User) {
			return users;
		}else {
			return crossings;
		}
	}
	
	@Override
	public Object retrieve(String key) {
		
		if(users.containsKey(key)) {
			return users.get(key);
		}else if (crossings.containsKey(key)){
			return crossings.get(key);
		}else {
			return null;
		}
	}
	
	public int getUserCount() {
		return users.size();
	}
	
	public int getCrossingsCount() {
		return crossings.size();
	}
	
}
