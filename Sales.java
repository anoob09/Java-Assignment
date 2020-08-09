import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class Sales{

	public static BufferedReader getBufferedReader(String path) {
		BufferedReader br = null;
		try {
		    br = new BufferedReader (new FileReader(path));
		} catch (FileNotFoundException ex) {
		    System.out.println(ex);
		}
		return br;
	}

	public static void main(String []args) throws FileNotFoundException, IOException {
		BufferedReader br = getBufferedReader("sales-data.txt");
		if (br == null)
			return;
		br.readLine();

		List<HashMap<String, int[]>> mapPolularItem = new ArrayList<HashMap<String, int[]>>();
		for (int i = 0; i < 12; i++)
			mapPolularItem.add(new HashMap<String, int[]>());

		int totalSales = 0;
		String line = br.readLine();
		while (line != null) {
			String arr[] = line.split(",");
			String date = arr[0];
			String sku = arr[1];
			int unit_price = Integer.parseInt(arr[2]);
			int quantity = Integer.parseInt(arr[3]);
			int total = Integer.parseInt(arr[4]);
			String date_arr[] = date.split("-");
			int month = Integer.parseInt(date_arr[1]);

			totalSales += total;
			

			HashMap<String, int[]> map = mapPolularItem.get(month - 1);
			int temp_arr[] = map.getOrDefault(sku, new int[]{0, Integer.MAX_VALUE, Integer.MIN_VALUE, 0, unit_price});
			temp_arr[0] += quantity;
			temp_arr[1] = Math.min(temp_arr[1], quantity);
			temp_arr[2] = Math.max(temp_arr[2], quantity);
			temp_arr[3]++;
			map.put(sku, temp_arr);

			line = br.readLine();
			month++;
		}
		
		// Qestion 1
		System.out.println("\n----- Total sales -----");
		System.out.println("Total Sales = " + totalSales);
		
		int month = 1;
		for (HashMap<String, int[]> map : mapPolularItem) {
			if (map.isEmpty())
				continue;
			int total = 0;
			int maxQuantity = 0;
			int maxRevenue = 0;
			int minOrders = Integer.MAX_VALUE;
			int maxOrders = Integer.MIN_VALUE;
			double avg_orders = 0;
			String mostPoplularItem = "";
			String mostRevenueItem = "";
			
			for (String key : map.keySet()) {
				int temp_array[] = map.get(key);
				
				// Question 2
				total += temp_array[0] * temp_array[temp_array.length - 1];

				// Question 3
				int quantity = temp_array[0];
				if (quantity > maxQuantity) {
					maxQuantity = quantity;
					mostPoplularItem = key;
				}

			// Question 4
				int revenue = temp_array[0] * temp_array[temp_array.length - 1];
				if (revenue > maxRevenue) {
					maxRevenue = revenue;
					mostRevenueItem = key;
				}
			} 

			// Question 5
			int mostPoplularItemArray[] = map.get(mostPoplularItem);
			minOrders = mostPoplularItemArray[1];
			maxOrders = mostPoplularItemArray[2];
			avg_orders = Math.round((double)mostPoplularItemArray[0] / (double)mostPoplularItemArray[3]);


			System.out.println("======== Data for Month " + (month++) + " ========");
			System.out.println("Total Sales in the Month = " + total);
			System.out.println("Most Popular Item = " + mostPoplularItem + " with " + maxQuantity + " units sold.");
			System.out.println("Item generating most revenue = " + mostRevenueItem + " by generating " + maxRevenue  +" revenue.");
			System.out.println("Min Order " + minOrders);
			System.out.println("Max Order " + maxOrders);
			System.out.printf("Average Order %.2f", avg_orders);
			System.out.println();
		}
	}
}