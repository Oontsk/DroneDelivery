package main;

import java.awt.geom.Point2D;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Order {
	private String orderNumber;
	private Point2D.Double orderAddress; //(0,0) is the origin where the store is
	//north is positive y, south negative y, west positive x, east negative y
	private Time orderTime;
	
	public Order(String orderNumber, String orderAddress, String orderTime) {
		//get the order number for the eventual output file
		this.orderNumber = orderNumber;
		
		//now parse the order address into coordinates
		Pattern pattern = Pattern.compile("([NS])(\\d+)([EW])(\\d+)");
		//this will capture the direction and distance
		Matcher matcher = pattern.matcher(orderAddress);
		//now matcher has groups, considering input was already correctly formed
		double x = 0, y = 0; //to be used to parametrize orderAddress as a Point2D.Double
		if (matcher.find()) {
			if (matcher.group(1).equals("N")) {
				y += Double.parseDouble(matcher.group(2));
			} else /*(matcher.group(0).equals("S"))*/ {
				y -= Double.parseDouble(matcher.group(2));
			}
			if (matcher.group(3).equals("W")) {
				x += Double.parseDouble(matcher.group(4));
			} else /*(matcher.group(2).equals("E"))*/ {
				x -= Double.parseDouble(matcher.group(4));
			}	
		}
		
		this.orderAddress = new Point2D.Double(x, y);
		//now orderAddress is a coordinate relative to the store
		//Point2D.Double is used because there is no Point2D.Integer
	
		//finally, parse the orderTime into Time object
		pattern = Pattern.compile("(\\d{2}):(\\d{2}):(\\d{2})");
		matcher = pattern.matcher(orderTime);
		//again, known that input is properly formed
		int h = 0, m = 0, s = 0;
		if (matcher.find()) {
			h = Integer.parseInt(matcher.group(1));
			m = Integer.parseInt(matcher.group(2));
			s = Integer.parseInt(matcher.group(3));
		}
		//used to parametrize orderTime as Time object
		this.orderTime = new Time(h, m, s);
	}
	
	public String getOrderNumber() {
		return orderNumber;
	}
	
	public Point2D.Double getOrderAddress() {
		return orderAddress;
	}
	
	public Time getOrderTime() {
		return orderTime;
	}
	
	
}
