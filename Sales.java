import java.io.*;
import java.util.*;
public class Sales{

	// To convert String to number
	public static int getNumber(String str) {
		int base = 10, num = 0;
		int length = str.length(), index = 0;
		while (index < length) {
			num *= base;
			num += str.charAt(index++) - '0';
		}
		return num;
	}

	public static void main(String []args) throws FileNotFoundException, IOException {
		BufferedReader br = null;
		try {
		    br = new BufferedReader (new FileReader("sales-data.txt"));
		} catch (FileNotFoundException ex) {
		    System.out.println(ex);
		}
		if (br != null)
			System.out.println(br.readLine());
		else {
			System.out.println("NULL");
			return;
		}



		int totalSales = 0;
		HashMap<String, Integer> mapPriceCatalogue = new HashMap<>();
		List<HashMap<String, int[]>> mapPolularItem = new ArrayList<HashMap<String, int[]>>();
		for (int i = 0; i < 12; i++)
			mapPolularItem.add(new HashMap<String, int[]>());

		String line = br.readLine();
		while (line != null) {
			String arr[] = line.split(",");
			String date = arr[0];
			String sku = arr[1];
			int unit_price = getNumber(arr[2]);
			int quantity = getNumber(arr[3]);
			int total = getNumber(arr[4]);
			String date_arr[] = date.split("-");
			int month = getNumber(date_arr[1]);

			if (!mapPriceCatalogue.containsKey(sku))
				mapPriceCatalogue.put(sku, unit_price);

			totalSales += total;
			

			HashMap<String, int[]> map = mapPolularItem.get(month - 1);
			int temp_arr[] = map.getOrDefault(sku, new int[]{0, Integer.MAX_VALUE, Integer.MIN_VALUE, 0, unit_price});
			temp_arr[0] += quantity;
			temp_arr[1] = Math.min(temp_arr[1], quantity);
			temp_arr[2] = Math.max(temp_arr[2], quantity);
			temp_arr[3]++;
			map.put(sku, temp_arr);

			line = br.readLine();
		}
		
		// Qestion 1
		System.out.println("\n----- Total sales -----");
		System.out.println("Total Sales = "+totalSales);
		
		// Qestion 2
		int m = 1;
		System.out.println("\n----- Month wise sales total -----");
		for (HashMap<String, int[]> map : mapPolularItem) {
			int total = 0;
			for (String key : map.keySet()) {
				int temp_arr[] = map.get(key);
				total += temp_arr[0] * temp_arr[temp_arr.length - 1];
			}
			if (total > 0)
				System.out.println("Total sales in " + (m++) + " month is " + total);
		}
		
		
		// Qestion 3
		System.out.println("\n----- Most Popular Item Month wise -----");
		m = 1;
		String[] mostPoplularItem = new String[12];
		for (HashMap<String, int[]> map : mapPolularItem) {
			int max = 0;
			String item = null;
			for (String key : map.keySet()) {
				int temp_arr[] = map.get(key);
				int q = temp_arr[0];
				if (q > max)
				{
					max = q;
					item = key;
				}
			}
			if (max > 0) {
				System.out.println("Popular Item in " + m + " month is " + item + " with " + max + " quantity sold.");
				mostPoplularItem[m - 1] = item;
				m++;
			} 
		}

		// Qestion 4
		System.out.println("\n----- Item generating most revenue in each month -----");
		m = 1;
		for (HashMap<String, int[]> map : mapPolularItem) {
			int max = 0;
			String item = "";
			for (String key : map.keySet()) {
				int temp_arr[] = map.get(key);
				int q = temp_arr[0] * mapPriceCatalogue.get(key);
				if (q > max)
				{
					max = q;
					item = key;
				}
			}
			if (max > 0)
				System.out.println("Item generating most revenue " + (m++) + " month is " + item + " with " + max + " quantity sold."); 
		}

		// Qestion 5
		System.out.println("\n----- Minimum, Maximum and Average number of orders in each month -----");
		m = 1;
		for (HashMap<String, int[]> map : mapPolularItem) {
			String item = mostPoplularItem[m - 1];
			if (item != null) {
				int temp_arr[] = map.get(item);
				System.out.println(temp_arr.length + " " + item);
				int min = temp_arr[1];
				int max = temp_arr[2];
				int avg_orders = temp_arr[0] / temp_arr[3];
				System.out.println("Min, Max & Average Orders for " + m + " month item " + item);
				System.out.println("Min Order " + min);
				System.out.println("Max Order " + max);
				System.out.println("Average Order " + avg_orders);
			}
			m++;
		}
	}
}