package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class TestFileGenerator {
	
	public static void generateTestFile(String inputFileName, String numOrders, String numDigitsInCoordinates, String minutesBetweenOrders) throws IOException {
		
		FileWriter fw = new FileWriter(inputFileName, true);
		BufferedWriter bw = new BufferedWriter(fw);
		//we are writing the specified input file
		Time curTime = new Time(0, 0, 0);
		Random rand = new Random();//used to create random coordinates
		
		int nOrders, nDigitsInCoordinates, mnutesBetweenOrders;
		nOrders = Integer.parseInt(numOrders);
		nDigitsInCoordinates = Integer.parseInt(numDigitsInCoordinates);
		mnutesBetweenOrders = Integer.parseInt(minutesBetweenOrders);
		
		
		for (int i = 0; i < nOrders; ++i) {
			String line = "WM" + String.valueOf(i) + " ",
				maxCoord = "1";
			for (int j = 0; j < nDigitsInCoordinates; ++j) {
				maxCoord += "0";
			}
			
			int upperBound = Integer.parseInt(maxCoord); //parsing max number based on number of digits
			
			int rng1 = rand.nextInt(2), rng2 = rand.nextInt(2),
				rng3 = rand.nextInt(upperBound),
				rng4 = rand.nextInt(upperBound);
			if (rng1 == 0) {
				line += "N";
			} else {
				line += "S";
			}
			line += String.valueOf(rng3);
			
			if (rng2 == 0) {
				line += "W";
			} else {
				line += "E";
			}
			line += String.valueOf(rng4) + " " + curTime.toString();
			
			if (i != nOrders - 1) {
				line += "\n";
			}
			
			//update time
			curTime.incrementTime(0, mnutesBetweenOrders, 0);
			
			bw.append(line);
		}
		//remember to close file
		bw.close();
	}	
}