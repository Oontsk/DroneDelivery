package main;

public class Time implements Comparable<Time> {
	private int hour, minute, second;
	
	public Time(int hour, int minute, int second) {
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}
	
	public Time(Time time) { //copy constructor
		this.hour = time.hour;
		this.minute = time.minute;
		this.second = time.second;
	}
	
	public int getHour() {
		return hour;
	}
	
	public int getMinute() {
		return minute;
	}
	
	public int getSecond() {
		return second;
	}
	
	public static int hoursElapsedSinceOrder(Time orderTime, Time timeOfDelivery) {
		int orderTimeHour = orderTime.getHour(), deliveryHour = timeOfDelivery.getHour(),
			orderTimeMinute = orderTime.getMinute(), deliveryMinute = timeOfDelivery.getMinute();
		
		int hoursElapsed = deliveryHour - orderTimeHour;
		//check if need to fix
		if (deliveryMinute < orderTimeMinute) {
			--hoursElapsed;
		}
		return hoursElapsed;
	}
	
	public boolean incrementTime(int hours, int minutes, int seconds) {
		if (hours < 0 || minutes < 0 || minutes > 59 || seconds < 0 || seconds > 59) {
			System.out.println("Please provide valid time to increment by");
			return false;
		}
		
		boolean dayChanged = false; //method returns true if day changes after this is incremented
	
		this.second += seconds; //update seconds
		if (this.second > 59) {
			++this.minute; //increment minutes for remainder
			this.second %= 60;
		}
		
		this.minute += minutes; //update minutes
		if (this.minute > 59) {
			++this.hour; //increment hours for remainder
			this.minute %= 60;
		}
		
		this.hour += hours; //update hours
		if (this.hour > 23) {
			this.hour %= 24;
			dayChanged = true;
		}
		return dayChanged;
	}
	
	public String toString() {
		String h = String.valueOf(hour), m = String.valueOf(minute), s = String.valueOf(second);
		if (h.length() == 1) {
			h = "0" + h;
		}
		if (m.length() == 1) {
			m = "0" + m;
		}
		if (s.length() == 1) {
			s = "0" + s;
		}
		
		return h + ":" + m + ":" + s;
	}

	public int compareTo(Time other) { //a Time is greater than another Time if the former is later
		if (hour != other.hour) {
			return hour - other.hour;
		} else if (minute != other.minute) {
			return minute - other.minute;
		} else {
			return second - other.second;
		}
	}
}
