package Practice_Jdnbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class TrainReservationSystem {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("1.Register Page");
		System.out.println("2.Login Page");
		System.out.println("Enter Your Choice: ");
		int choice1 = sc.nextInt();
		switch (choice1) {
		case 1:
			Register();
			break;
		case 2:
			Login();
			break;
		default:
			System.out.println("Invalid Choice");
			break;
		}

		System.out.println("1.Book Ticked");
		System.out.println("2.Cancel Ticked");

		System.out.println("Enter your choice");
		int choice = sc.nextInt();
		switch (choice) {
		case 1:
			BookingTicket();
			break;
		case 2:
			CancelTicket();
			break;
		default:
			System.out.println("Invalid Choice");
			break;
		}
	}

// Register PAge
	static void Register() throws Exception {
		DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/june_2024", "root", "root");

		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your name: ");
		String name = sc.next();
		System.out.println("Enter your username:");
		String username = sc.next();

		while (true) {
			String s = "select*from Register where username=?";
			PreparedStatement pstmt = con.prepareStatement(s);
			pstmt.setString(1, username);
			ResultSet res = pstmt.executeQuery();

			if (res.next()) {
				System.out.println("Username already exists.Please enter another username");
				username = sc.next();
			} else
				break;
		}

		String password;
		String CPassword;
		do {
			System.out.println("Enter Password:");
			password = sc.next();
			System.out.println("Enter Conform Password: ");
			CPassword = sc.next();
		} while (!password.equals(CPassword));

		String s1 = "Insert into Register Values(?,?,?)";
		PreparedStatement pstmt1 = con.prepareStatement(s1);
		pstmt1.setString(1, name);
		pstmt1.setString(2, username);
		pstmt1.setString(3, password);

		pstmt1.executeUpdate();

		System.out.println("Registered Successfully!!!");

	}
//Login page

	static void Login() throws Exception {
	    
	    DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
	    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/june_2024", "root", "root");

	    Scanner sc = new Scanner(System.in);
	    String username, password;

	    ResultSet res;
	    do {
	        System.out.println("Enter Your username: ");
	        username = sc.next();

	        System.out.println("Enter your Password: ");
	        password = sc.next();

	        
	        String s = "select * from Register where username=? and password=?";
	        PreparedStatement pstmt = con.prepareStatement(s);
	        pstmt.setString(1, username);
	        pstmt.setString(2, password);
	        res = pstmt.executeQuery();

	        
	        if (res.next()) {
	            System.out.println("Login Successfully");
	            break;
	        } else {
	            System.out.println("Invalid Username or Password. Please try again.");
	        }
	    } while (true); 
	}

//Booking ticket
	static void BookingTicket() throws Exception {
		DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/june_2024", "root", "root");

		Scanner sc = new Scanner(System.in);
		System.out.println("Book your Reservation");
		System.out.println("Enter a Passenger Name:");
		String Pname = sc.next();

		System.out.println("Enter a Age: ");
		int Age = sc.nextInt();
		System.out.println("Enter a gender: ");
		String Gender = sc.next();
		System.out.println("Enter a Mobile Number: ");
		long Mnumber = sc.nextLong();
		System.out.println("Enter a Booking Date: ");
		String Bdate = sc.next();
		System.out.println("Enter a Pickup Point: ");
		String Ppoint = sc.next();
		System.out.println("Enter a Drop Point: ");
		String Dpoint = sc.next();
		System.out.println("Enter a Seat Number:");
		int Seat = sc.nextInt();
		
		do{
			String s = "select*from Booking where Seat=?";
			PreparedStatement pstmt = con.prepareStatement(s);
			pstmt.setInt(1, Seat);
			ResultSet res = pstmt.executeQuery();

			if (res.next()) {
				System.out.println("Seat number is already booked.Please enter another seat number");
				 Seat = sc.nextInt();
			} else
				break;
		}while(true);
		

		String s1 = "Insert into Booking Values(?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = con.prepareStatement(s1);
		pstmt.setString(1, Pname);
		pstmt.setInt(2, Age);
		pstmt.setString(3, Gender);
		pstmt.setLong(4, Mnumber);
		pstmt.setString(5, Bdate);
		pstmt.setString(6, Ppoint);
		pstmt.setString(7, Dpoint);
		pstmt.setInt(8, Seat);
		pstmt.executeUpdate();

		System.out.println("Your Ticked Was Booking Successfully , Enjoy your Travel");

	}

	// Cancel Ticket
	static void CancelTicket() throws Exception {
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/june_2024", "root", "root");

			Scanner sc = new Scanner(System.in);
			int Seat = sc.nextInt();
			int rows;
			
			do {
	            System.out.println("Enter your Seat number: ");
	            Seat = sc.nextInt();
	            String s = "delete from Booking where Seat=?";
	            PreparedStatement pstmt = con.prepareStatement(s);
	            pstmt.setInt(1, Seat);
	            rows = pstmt.executeUpdate();

	            if (rows > 0) {
	                System.out.println("Ticket cancelled successfully.");
	            } else {
	                System.out.println("Invalid Seat number. No ticket found. Please enter a valid seat number.");
	            }

	        } while (rows==0); 			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
