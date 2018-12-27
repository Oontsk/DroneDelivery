package main;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;

public class OrderComparator implements Comparator<Order> {
	
	private Time currentTime;
	private ArrayList<Point2D.Double> badCoords;
	
	public OrderComparator(Time currentTime, ArrayList<Point2D.Double> badCoords) {
		this.currentTime = currentTime;
		this.badCoords = badCoords;
	}
	
	public int compare(Order a, Order b) {
		/*I need to find the time it would take
		*to send each order. If sending an order will
		*no matter what make a detractor,
		*the order will not be sent out yet.
		* Then orders which can be fulfilled 
		*before being made detractors will be sent out
		*in efforts to minimize detractors. Then,
		*orders which can be fulfilled to make promoters are
		*next in priority.
		*/
		
		
		Point2D.Double aAddress = a.getOrderAddress(), bAddress = b.getOrderAddress();
		
		if (badCoords.contains(aAddress)) {
			return 1;
		}
		
		if (badCoords.contains(bAddress)) {
			return -1;
		}
		
		/*Line2D.Double xAWardLineBottom = new Line2D.Double(0, 0, aAddress.x, 0),
				xAWardLineTop = new Line2D.Double(0, aAddress.y, aAddress.x, 0),
				yAWardLineLeft = new Line2D.Double(0, 0, 0, aAddress.y),
				yAWardLineRight = new Line2D.Double(aAddress.x, 0, aAddress.x, aAddress.y);
		
		Line2D.Double xBWardLineBottom = new Line2D.Double(0, 0, bAddress.x, 0),
				xBWardLineTop = new Line2D.Double(0, bAddress.y, bAddress.x, 0),
				yBWardLineLeft = new Line2D.Double(0, 0, 0, bAddress.y),
				yBWardLineRight = new Line2D.Double(bAddress.x, 0, bAddress.x, bAddress.y);
		
		for (int i = 0 ; i < badCoords.size(); ++i) {
			if (badCoords.get(i).intersects(xAWardLineBottom)) {
				
			}
			 
		}
		*/
		
		
		
		
		
		
		
		
		
		
		Time orderAPlaced = a.getOrderTime(), orderBPlaced = b.getOrderTime();
		int aDist = (int) Math.abs(a.getOrderAddress().x) + (int) Math.abs(a.getOrderAddress().y),
			bDist = (int) Math.abs(b.getOrderAddress().x) + (int) Math.abs(b.getOrderAddress().y);
		//figure out when it will be if the order is placed right now
		Time aElapsed = new Time(currentTime), bElapsed = new Time(currentTime);
		int hoursAElapsed = aDist / 60, minutesAElapsed = aDist % 60, 
			hoursBElapsed = bDist / 60, minutesBElapsed = bDist % 60;
		
		aElapsed.incrementTime(hoursAElapsed, minutesAElapsed, 0);
		bElapsed.incrementTime(hoursBElapsed, minutesBElapsed, 0);
		//aElapsed and bElapsed represent the time of delivery if drone left at currentTime
		int aHoursElapsedSinceOrderIfDelivered = Time.hoursElapsedSinceOrder(orderAPlaced, aElapsed),
			bHoursElapsedSinceOrderIfDelivered = Time.hoursElapsedSinceOrder(orderBPlaced, bElapsed);
		
		//now check to see who to put ahead in order queue... phew
		if (aHoursElapsedSinceOrderIfDelivered > 4 && bHoursElapsedSinceOrderIfDelivered > 4) {
			return 0; //no preference if both can't be delivered
		} else if (aHoursElapsedSinceOrderIfDelivered > 4) {
			return 1; //a will be detractor no matter what, so don't deliver
		} else if (bHoursElapsedSinceOrderIfDelivered > 4) {
			return -1; //b will be detractor no matter what, so don't deliver
		} else { /*we want the one closer to 4 hours to be the first one sent out, so just return the difference
			considering it is known at this point neither are greater than 4*/
			return aHoursElapsedSinceOrderIfDelivered - bHoursElapsedSinceOrderIfDelivered;
		}
		
	}
	
}
