package main;

import java.awt.geom.Point2D;
import java.io.BufferedWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Deliverer {
	private ArrayList<Order> orders;
	private BufferedWriter out;
	
	private ArrayList<Point2D.Double> badCoords;

	private int ordersDelivered = 0, detractors = 0, promoters = 0;
	
	private Time currentTime = new Time(6, 0, 0);
	//day starts at 6AM
	
	public Deliverer(ArrayList<Order> orders, BufferedWriter out, ArrayList<Point2D.Double> badCoords) {
		this.orders = orders;
		this.out = out;
		this.badCoords = badCoords;
	}
	
	private boolean isActive() {
		return currentTime.getHour() > 5 && currentTime.getHour() < 22;
		//between 6AM and 10PM
	}
	
	private boolean hasDeliveriesToMake() {
		return !orders.isEmpty();
	}
	
	public boolean canMakeDelivery() {
		return isActive() && hasDeliveriesToMake();
	}

	public void makeDelivery() throws IOException {
		PriorityQueue<Order> pq = new PriorityQueue<Order>(new OrderComparator(currentTime, badCoords));
		for (int i = 0; i < orders.size(); ++i) {
			Order order = orders.get(i);
			if (currentTime.compareTo(order.getOrderTime()) > 0) {
				//if the current time is later than when the order came in according to text file
				pq.add(order);
			}
		}
		Order nextToBeCompleted = pq.poll();
		
		orders.remove(nextToBeCompleted); //order is no longer in need of being fulfilled after this
		
		//append completed order to output file
		String outputLine = nextToBeCompleted.getOrderNumber() + " " + currentTime.toString() + "\n";
		//in the format "WM### HH:MM:SS\n"
		out.write(outputLine);
		
		//update the currentTime
		int distance = (int) Math.abs(nextToBeCompleted.getOrderAddress().x) 
				+ (int) Math.abs(nextToBeCompleted.getOrderAddress().y);
		//distance to get there and back is the minutes to be added to current time
		int hoursPassed = distance / 60, minutesPassed = distance % 60; 
		currentTime.incrementTime(hoursPassed, minutesPassed, 0);
	
		//determine whether this customer is a detractor or promoter
		int lagHours = Time.hoursElapsedSinceOrder(nextToBeCompleted.getOrderTime(), currentTime);
		if (lagHours > 4) {
			++detractors;
		} else if (lagHours < 2) {
			++promoters;
		}
		
		//update ordersDelivered
		++this.ordersDelivered;
	}
	
	public void appendNetPromoterScore() throws IOException {
		String res = "NPS ";
		
		if (ordersDelivered == 0) { //avoid division by zero error
			res += "0";
		} else {
			double percentDetractors = (double) detractors / (double) ordersDelivered,
				percentPromoters = (double) promoters / (double) ordersDelivered,
				ch = 100. * (percentPromoters - percentDetractors);
			DecimalFormat df = new DecimalFormat("0.000");
			String roundedNPS = df.format(ch);
			res += roundedNPS;
		}
		
		//append line to output.txt
		out.write(res);
	}
	
	
}
