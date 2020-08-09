import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.io.IOException;
public class IceCreamSales{

	public static BufferedReader getFile(String path) {
		BufferedReader br = null;
		try {
		    br = new BufferedReader (new FileReader(path));
		} catch (FileNotFoundException ex) {
		    System.out.println(ex);
		}
		return br;
	}

	public static String getMostPopularItem(HashMap<String, int[]> map) {
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
		if (item != null) 
			System.out.println("Most Popular Item = " + item + " with " + max + " units sold.");
		return item;
	}

	public static String getItemGeneratingMostRevenue(HashMap<String, int[]> map) {
		int max = 0;
		String item = "";
		for (String key : map.keySet()) {
			int temp_arr[] = map.get(key);
			int q = temp_arr[0] * temp_arr[temp_arr.length - 1];
			if (q > max)
			{
				max = q;
				item = key;
			}
		}
		if (item != null)
			System.out.println("Item generating most revenue = " + item + " by generating " + max  +" revenue.");
		return item;
	}

	public static void getMinMaxAvg(HashMap<String, int[]> map, String item) {
		if (item != null) {
			int temp_arr[] = map.get(item);
			int min = temp_arr[1];
			int max = temp_arr[2];
			double avg_orders = (double)temp_arr[0] / (double)temp_arr[3];
			System.out.println("Min Order " + min);
			System.out.println("Max Order " + max);
			System.out.printf("Average Order %.2f", avg_orders);
		}
	}

	public static void main(String []args) throws FileNotFoundException, IOException {
		
		BufferedReader br = getFile("sales-data.txt");
		int totalSales = 0;
		int monthWiseSales = 0;
		HashMap<String, int[]> map = new HashMap<String, int[]>();
		if (br != null)
			br.readLine();
		else
			return;
		String line = br.readLine();
		int month = 0;
		while (line != null) {
			String arr[] = line.split(",");
			String date = arr[0];
			String sku = arr[1];
			int unit_price = Integer.parseInt(arr[2], 10);
			int quantity = Integer.parseInt(arr[3], 10);
			int total = Integer.parseInt(arr[4], 10);
			String date_arr[] = date.split("-");
			int curr_month = Integer.parseInt(date_arr[1], 10);

			if (month != curr_month) {
				if (month == 0) {
					month = curr_month;
					continue;
				}
				System.out.println("======== Data for Month " + month + " ========");
				System.out.println("Total Sales in the Month = " + monthWiseSales);

				String mostPoplularItem = getMostPopularItem(map);
				String itemGeneratingMostRevenue = getItemGeneratingMostRevenue(map);
				getMinMaxAvg(map, itemGeneratingMostRevenue);
				
				System.out.println();
				monthWiseSales = 0;
				map.clear();
				month = curr_month;
			}
			monthWiseSales += total;
			totalSales += total;
			int temp_arr[] = map.getOrDefault(sku, new int[]{0, Integer.MAX_VALUE, Integer.MIN_VALUE, 0, unit_price});
			temp_arr[0] += quantity;
			temp_arr[1] = Math.min(temp_arr[1], quantity);
			temp_arr[2] = Math.max(temp_arr[2], quantity);
			temp_arr[3]++;
			map.put(sku, temp_arr);

			line = br.readLine();
		}
		
		if (!map.isEmpty()) {
			System.out.println("======== Data for Month " + month + " ========");
			System.out.println("Total Sales in the Month = " + monthWiseSales);
			// int totalSalesMonth = getTotalSalesByMonth(map); 
			String mostPoplularItem = getMostPopularItem(map);
			String itemGeneratingMostRevenue = getItemGeneratingMostRevenue(map);
			getMinMaxAvg(map, itemGeneratingMostRevenue);
			map.clear();
				
		}
		System.out.println("\n----- Total sales -----");
		System.out.println("Total Sales = " + totalSales);
	}
}