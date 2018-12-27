package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class InputParser {
	/*inputFile has the order entries,
	returns an array of order entries,
	where each order entry is a String
	array of length 3 containing the order number,
	coordinate, and time of order.  Returning null
	indicates an error */
	public static ArrayList<Order> parseInputFile(BufferedReader inputFile) throws IOException {
		ArrayList<Order> orders = new ArrayList<Order>(); //to be returned, holds all the orders
		String line; //to represent each line in lines from file
		while ((line = inputFile.readLine()) != null) { //read from file until end
			String[] entry = line.split(" ");
			
			//error check input file
			if (entry.length != 3 ||
				!entry[0].matches("^WM\\d+$") ||
				!entry[1].matches("^(N|S)\\d+(E|W)\\d+$") ||
				!entry[2].matches("^\\d{2}:\\d{2}:\\d{2}$")) {
				
				System.out.println("Input file formed incorrectly. Please provide a valid input file.");
				return null;
			}
			
			Order order = new Order(entry[0], entry[1], entry[2]); 
			/*parametrize an order by order number,
			   coordinate, and time*/
			orders.add(order);
		}
		
		return orders;
	}
}
