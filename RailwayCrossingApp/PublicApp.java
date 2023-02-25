package RailwayCrossingApp;
import java.util.*;
import java.lang.*;
import java.util.Map;
import java.util.Scanner;

import RailwayCrossingApp.controller.RailwayCrossingController;
import RailwayCrossingApp.controller.UserController;
import RailwayCrossingApp.model.RailwayCrossing;
import RailwayCrossingApp.model.User;

public class PublicApp {

	UserController controller;
	RailwayCrossingController railController;
	Scanner scanner;
	
	private static PublicApp app;
	
	public static PublicApp getInstance() {
		if(app == null) {
			app = new PublicApp();
		}
		return app;
	}
	
	private PublicApp(){
		controller = UserController.getInstance();
		railController = RailwayCrossingController.getInstance();
		scanner = new Scanner(System.in);
	}
	
	void register() {
		User user = new User();
		// Empty Next Line for Scanner Issue
		scanner.nextLine();
		
		System.out.println("Enter Name: ");
		user.setName(scanner.nextLine());
		
		System.out.println("Enter Email: ");
		user.setEmail(scanner.nextLine());
		
		System.out.println("Enter Password: ");
		user.setPassword(scanner.nextLine());
		
		user.setUserType(1);
		
		if(controller.registerUser(user)) {
			System.out.println(user.getName()+", You have Registered Successfully..");
			System.out.println("Navigating to the Railway Crossing Application");

			// Navigate to Home
			home();
			
		}else {
			System.err.println("Something Went Wrong. Please Try Again");
		}
	}
	
	void login() {
		User user = new User();
		
		// Empty Next Line for Scanner Issue
		scanner.nextLine();
		
		System.out.println("Enter Email: ");
		user.setEmail(scanner.nextLine());
		
		System.out.println("Enter Password: ");
		user.setPassword(scanner.nextLine());
		
		user.setUserType(1);
		
		if(controller.loginUser(user)) {
			System.out.println(user.getName()+", You have Logged In Successfully..");
			System.out.println("Navigating to the Railway Crossing Application");
			
			// Navigate to Home
			home();
		}else {
			System.err.println("Something Went Wrong. Please Try Again");
		}
	}
	
	void listCrossings() {
		Map<String, RailwayCrossing> crossings = (Map<String, RailwayCrossing>) railController.fetchCrossings();
		for(String key : crossings.keySet()) {
			System.out.println(crossings.get(key));
		}
	}
	void searchCrossing() {
		scanner.nextLine();
		System.out.println("Enter the Crossing name: ");
		String name=scanner.nextLine();
		if(railController.fetchCrossings(name)==null)
			System.err.println("No such crossing exists!!");
		else
			System.out.println(railController.fetchCrossings(name));
	}

	void sortCrossings() {
		Map<String, RailwayCrossing> hm = (Map<String, RailwayCrossing>) railController.fetchCrossings();

		List<Map.Entry<String, RailwayCrossing> > list =
				new LinkedList<Map.Entry<String, RailwayCrossing> >(hm.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, RailwayCrossing> >() {
			public int compare(Map.Entry<String, RailwayCrossing> o1,
							   Map.Entry<String, RailwayCrossing> o2)
			{
				return (o1.getValue().getName()).compareTo(o2.getValue().getName());
			}
		});
		Map<String, RailwayCrossing> temp = new LinkedHashMap<String, RailwayCrossing>();
		for (Map.Entry<String, RailwayCrossing> aa : list) {
			temp.put(aa.getKey(), aa.getValue());
		}
		for(String key : temp.keySet()) {
			System.out.println(temp.get(key));
		}
	}
	void home() {
		
		while(true) {
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("Welcome to Railway Crossing Home");
			System.out.println("1: List Railway Crossings");
			System.out.println("2: Search Railway Crossings");
			System.out.println("3: Sort Railway Crossings by Schedule");
			System.out.println("0: Close Public Application");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.print("Enter your choice: ");
			int choice = scanner.nextInt();
			switch (choice) {
				case 1: 
					listCrossings();
					break;
				case 2:
					searchCrossing();
					break;
				case 3:
					sortCrossings();
					break;
				case 0:
					System.out.println("Thank You for using Railway Crossing App");
					break;
				default:
					System.err.println("Invalid Choice");
			}
			if(choice == 0) {
				break;
			}
		}
	}
	
	void startPublicApp() {
		
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Welcome User");
		System.out.println("We have "+controller.getUserCount()+" Users in the DataBase");
		System.out.println("1: Register");
		System.out.println("2: Login");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		
		int choice = scanner.nextInt();
		switch (choice) {
			
			case 1: 
				register();
				break;
			
			case 2:
				login();
				break;
		
			default:
				System.err.println("Invalid Choice");
		}		
	}

}
