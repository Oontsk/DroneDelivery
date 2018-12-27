Hunter Klamut
Walmart Interview
Drone Delivery

Introduction

The task is to use the drone to deliver to customers to maximize the net promoter score.
The DroneDeliverer class contains the main method, where an argument is passed
to handle the input file containing orders.

Input Parsing

The file is immediately handed off to an InputParser class, which parses each 
line of the input file into objects called Orders. An order is parametrized by
its order number, coordinates, and time received. A Time object parametrizes
the order time with an hour, a minute, and a second field. The input parser 
returns an array of orders if the file is formed correctly according to 
particular regular expressions. A message is printed, and the program exits
should an error be encountered here.

Delivery

Now the array of orders is handed off to the Deliverer class, which carries out the
orders. This object creates and maintains the output file, "output.txt", in the 
user's present working directory. The Deliverer keeps track of the current Time.
Since the Deliverer can only operate during a fixed period in the day or when there
are orders to fulfill, it has a method "canMakeDelivery" to determine whether it may deliver.

Delivery Methodology

The Deliverer is always keeping track of the current time and its position.
Every time it makes a move, the time is updated to reflect the minutes that 
have passed. The order in which the Deliverer delivers is based
on what Order object is at the top of a Priority Queue. This Priority Queue is 
provided a Comparator at construction hoping to maximize the net promoter score
(NPS). The detractors are to be minimized, the promoters maximized.

Comparator Description

The approach prioritizes minimizing detractors. Orders that may cause a detractor
should they go unfilled are placed at the front of the queue. Then orders
which can be fulfilled to promoter customers come next in the queue. Anything else gets
the lowest priority.

Synthesis

Finally, the NPS is calculated and appended to the output file. output.txt should be in
the user's current directory after executing the program.

Testing

To generate a testing input file, a different set of arguments is passed at the command line.
To execute:
java main.DroneDelivery [input file name] [number of orders] [number of digits in coordinates] [minutes between orders]
This will create an input file of the given name with the given numbers of orders. The coordinates of the orders
are chosen randomly using a Random Java object up to the number of digits specified, and orders take 
place every specified interval.

Acknowledgements

Java Priority Queue, Java Point2D.Double, Java Random
