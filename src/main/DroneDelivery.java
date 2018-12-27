package main;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DroneDelivery {
	public static void main(String[] args) {
	
		//check for proper input at invocation
		if (args.length != 1 && args.length != 4) {
			System.out.println("Improper arguments. To execute, run:\njava main.DroneDelivery [input file name]\n"
				+ "Or provide valid arguments for generating test input file:\n"
				+ "java main.DroneDelivery [file name] "
				+ "[number of orders] [number of digits in coordinates]"
				+ " [minutes between orders]");
			return;
		}
		
		if (args.length == 4) {
			try {
				TestFileGenerator.generateTestFile(args[0], args[1], args[2], args[3]);
			} catch (IOException e) {
				System.out.println("Encountered IOException. Exiting.");
				return;
			}
			return; //test file generated to correct location (:
		}
		
		//args.length == 1 for normal invocation of program
		ArrayList<Point2D.Double> badCoords = new ArrayList<Point2D.Double>();
		Point2D.Double a = new Point2D.Double(2, 2),
				b = new Point2D.Double(2, 3),
				c = new Point2D.Double(2, 4),
				d = new Point2D.Double(3, 2),
				ee = new Point2D.Double(3, 4),
				f = new Point2D.Double(4, 2),
				g = new Point2D.Double(4, 4);
		
		badCoords.add(a);
		badCoords.add(b);
		badCoords.add(c);
		badCoords.add(d);
		badCoords.add(ee);
		badCoords.add(f);
		badCoords.add(g);
		
		
		
		String inputFileName = args[0]; //strip input file name string
		File inputFile = new File(inputFileName); //input file populated with entries
		FileReader fileReader;
		try {
			fileReader = new FileReader(inputFile);
		} catch (FileNotFoundException e) {
			return;
		} //seeking to read from file
		BufferedReader bufferedReader = new BufferedReader(fileReader); //has a convenient .readLine() function
		
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter("output.txt", true);
		} catch (IOException e) {
			try {
				bufferedReader.close();
			} catch (IOException e1) {
				return;
			}
			return;
		}
		BufferedWriter outputFile = new BufferedWriter(fileWriter);
		
		/*At this point, we want to check the contents of the input file.
		 * The bufferedReader will be passed to a function that returns
		 * an ArrayList<Order> which has the order information.
		 */
		ArrayList<Order> orders = null;
		try {
			orders = InputParser.parseInputFile(bufferedReader);
		} catch (IOException e) {
			try {
				outputFile.close();
			} catch (IOException e1) {
				return;
			}
			return;
		}
		
		if (orders == null) {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				try {
					outputFile.close();
				} catch (IOException e1) {
					return;
				}
				return;
			}
			if (outputFile != null) {
				try {
					outputFile.close();
				} catch (IOException e) {
					return;
				}
			}
			return; //error message printed in the InputParser
		}
		
		//instantiate Deliverer object to start processing orders
		Deliverer deliverer = new Deliverer(orders, outputFile, badCoords);
		while (deliverer.canMakeDelivery()) {
			try {
				deliverer.makeDelivery();
			} catch (IOException e) {
				return;
			}
		}
		//output.txt should now be written to as per deliverer makeDelivery method 
		//finally, add in the net promoter score
		try {
			deliverer.appendNetPromoterScore();
		} catch (IOException e) {
			return;
		}
		//done (:
		System.out.println("Path to output file is: [present_working_directory]/output.txt");
		
		if (outputFile != null) {
			try {
				outputFile.close();
			} catch (IOException e) {
				return;
			}
		}
		
	}
}
