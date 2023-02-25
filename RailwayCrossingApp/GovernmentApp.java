package RailwayCrossingApp;

import java.util.Map;
import java.util.Scanner;

import RailwayCrossingApp.controller.RailwayCrossingController;
import RailwayCrossingApp.model.RailwayCrossing;
import RailwayCrossingApp.model.User;

public class GovernmentApp {
	
	RailwayCrossingController controller;
	Scanner scanner;
	
	private static GovernmentApp app;
	
	public static GovernmentApp getInstance() {
		if(app == null) {
			app = new GovernmentApp();
		}
		return app;
	}
	
	private GovernmentApp(){
		controller = RailwayCrossingController.getInstance();
		scanner = new Scanner(System.in);
	}
	
	void listCrossings() {
		
		Map<String, RailwayCrossing> crossings = (Map<String, RailwayCrossing>) controller.fetchCrossings();
		for(String key : crossings.keySet()) {
			System.out.println(crossings.get(key));
		}
	}
	void searchCrossing() {
		scanner.nextLine();
		System.out.println("Enter the Crossing name: ");
		String name=scanner.nextLine();
		if(controller.fetchCrossings(name)==null)
			System.err.println("No such crossing exists!!");
		else
			System.out.println(controller.fetchCrossings(name));
	}
	void addCrossing() {
		
		// Empty Next Line for Scanner Issue
		scanner.nextLine();
		
		User user = new User();
		RailwayCrossing crossing = new RailwayCrossing();
		
		System.out.println("Enter Person InCharge Details");
		
		System.out.println("Enter Name: ");
		user.setName(scanner.nextLine());
		
		System.out.println("Enter Email: ");
		user.setEmail(scanner.nextLine());
		
		System.out.println("Enter Password: ");
		user.setPassword(scanner.nextLine());
		
		user.setUserType(3);
		

		System.out.println("Enter Railway Crossing Details");

		System.out.println("Enter Crossing Name: ");
		crossing.setName(scanner.nextLine());
		
		System.out.println("Enter Crossing Address: ");
		crossing.setAddress(scanner.nextLine());
		
		System.out.println("Enter Crossing Schedule: ");
		String scheduleKey = scanner.nextLine();
		String scheduleValue = scanner.nextLine();
		
		crossing.getSchedules().put(scheduleKey, scheduleValue);
		crossing.setPersonInCharge(user);
		
		if(controller.addOrUpdateCrossing(crossing)) {
			System.out.println(crossing.getName()+" Added Successfully...");
		}else {
			System.err.println("Something Went Wrong. Please Try Again");

		}
	}
	
	void login() {
		User user = new User();
		
		// Empty Next Line for Scanner Issue
		
		System.out.println("Enter Email: ");
		user.setEmail(scanner.nextLine());
		
		System.out.println("Enter Password: ");
		user.setPassword(scanner.nextLine());
				
		if(controller.loginUser(user)) {
			System.out.println(user.getName()+", You have Logged In Successfully..");
			System.out.println("Navigating to the Government Railway Crossing Application");
			
			// Navigate to Home
			home();
		}else {
			System.err.println("Something Went Wrong. Please Try Again");
		}
	}
	void deleteCrossing()
	{
		scanner.nextLine();
		System.out.println("Enter the Crossing name: ");
		String name=scanner.nextLine();
		Map<String, RailwayCrossing> crossings = (Map<String, RailwayCrossing>) controller.fetchCrossings();
		if(crossings.get(name)!=null)
			controller.deleteCrossing(crossings.get(name));
		else
			System.err.println("No such crossing exists !!");
	}
	void updateCrossing()
	{

	}
	void home() {
		
		while(true) {
		
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			System.out.println("Welcome to Government Railway Crossing Home");
			System.out.println("We have "+controller.getCrossingsCount()+" Crossings in the DataBase");
			System.out.println("1: List Railway Crossings");
			System.out.println("2: Search Railway Crossings");
			System.out.println("3: Add or update Railway Crossing");
			System.out.println("4: Delete Railway Crossing");
			System.out.println("0: Close Goverment Application");
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			
			int choice = scanner.nextInt();
			switch (choice) {
				
				case 1: 
					listCrossings();
					break;
				
				case 2:

					searchCrossing();
					break;
					
				case 3:
					addCrossing();
					break;
				case 4:
					deleteCrossing();
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
	
	void startGovernmentApp() {

		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("Welcome Admin User");
		System.out.println("Proceed to Login");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		login();
			
	}

}
