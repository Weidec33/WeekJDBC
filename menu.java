package application;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import dao.VehicleDao;
import entity.Car;
import entity.vehicle;

public class menu {
		private VehicleDao vehicleDao = new VehicleDao() ;
		public final String GET_ALL_VEHICLES = "SELECT * FROM vehicles";
		String URL = "jdbc:mysql://localhost:3306/vehicles";	
		String CREATE_NEW_VEHICLE = "INSERT INTO vehicles(vehicle_id, vehicle_inventory, vehicle_condition)  VALUES (?,?,?) ";
		String DELETE_FROM_VEHCILE = "DELETE FROM vehicles WHERE vehicle_id = ?";
	private Scanner scanner = new Scanner(System.in);
	private Connection connection;
   
  String connect = "jdbc:mysql://localhost:3306/vehicles";
	private List<String> options = Arrays.asList("Display list of  Vehicles", "Display Vehicle",															
		"Create Vehicle", "Delete Vehicle", "Update Vehicle");
	
	public void start() throws SQLException {
		String selection = "";
		
		do {
			printMenu();
			selection = scanner.nextLine();
			try {
				if(selection.equals("1")) {
					 displayListOfVehicles();
				} else if (selection.equals("2")) {
			//		displayVehicle();
				}else if (selection.equals("3")) {
					 createVehicle();
				}else if (selection.equals("4")) {
					deleteVehicle();
				}else if (selection.equals("5")) {
				//	updateVehicle();
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
			
			
			System.out.println("Press enter to continue... ");
			scanner.nextLine();
		}while (!selection.equals("-1"));
		
	
}
		private void deleteVehicle() {
		System.out.println("Enter vehicle Id that your would like to delete: ");
		int vehicleId = scanner.nextInt();
		try {
		PreparedStatement ps = DriverManager.getConnection(connect, "chris", "Blacknig#33").prepareStatement(DELETE_FROM_VEHCILE);
		ps.setInt(1,vehicleId);
		ps.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
		private void createVehicle() {
			System.out.print("Enter Vehicle id: ");
			int vehicleId = scanner.nextInt();
			 System.out.println("Enter vehicle Invetory:  ");
			 int vehicleInvetory = scanner.nextInt();
			 System.out.println("Enter vehicle Condition: ");
			 String vehicleCondition = scanner.next();
			
			try {
				Connection conn = DriverManager.getConnection(connect, "chris", "Blacknig#33");
				PreparedStatement ps = DriverManager.getConnection(connect, "chris", "Blacknig#33").prepareStatement(CREATE_NEW_VEHICLE);
				ps.setInt(1, vehicleId);
				ps.setInt(2, vehicleInvetory);
				ps.setString(3,vehicleCondition);
				ps.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
			}
	}
		private void displayVehicle() throws SQLException {
		System.out.println("Enter Vehicle Id: ");
		int id = Integer.parseInt(scanner.nextLine());
		vehicle vehicle = vehicleDao.getVehiclesById(id);
		System.out.println(vehicle.getVehicleId() + ": " + vehicle.getVehicleInventory() + ": " + vehicle.getVehicleCondition());
		for(Car car : vehicle.getCars()) {
			System.out.println("\tCarId: " + car.getVehicleId() + " Car Brand : " + car.getCarBrand() + " Vehicle Id: " + car.getVehicleId());
			ResultSet ps =  DriverManager.getConnection(URL, "chris", "Blacknig#33").prepareStatement(GET_ALL_VEHICLES).executeQuery();
			while(ps.next()){
				System.out.println(" Vehicle Id; " + ps.getInt(1) + " " + "Vehicle Inventory: " + ps.getInt(2) + " " + "Vehicle Condition: " +  ps.getString(3));
		}}
	}
		private void displayListOfVehicles() throws SQLException {
			try {
				Connection conn = DriverManager.getConnection(connect, "chris", "Blacknig#33");
				
				ResultSet rs =  DriverManager.getConnection(connect, "chris", "Blacknig#33").prepareStatement(GET_ALL_VEHICLES).executeQuery();
				while(rs.next()){
						System.out.println("Vehicle ID: " + rs.getInt(1) + " " + "Vehicle Inventory: " + rs.getInt(2) + " " + "vehicle Condition: " 
				+ rs.getString(3));
	}	}
			catch(SQLException e) {
				e.printStackTrace();}
			}
		private void printMenu() {
			System.out.println("Select an Option: \n----------------------------");
			for(int i = 0; i < options.size(); i++) {
				System.out.println(i + 1 + ") "  + options.get(i));
			}
		}}
