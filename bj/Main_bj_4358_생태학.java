package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeMap;

public class Main_bj_4358_생태학 {
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		TreeMap<String, Integer> map = new TreeMap<>();
		double total = 0;
		String input;
		while((input = br.readLine()) != null) {
			
			if(!map.containsKey(input)) {
				map.put(input, 1);
			}
			else {
				map.put(input, map.get(input)+1);
			}
			
			total++;
		} // input end
		
		for(String s : map.keySet()) {
			sb.append(s+" ");
			sb.append(String.format("%.4f", map.get(s) / total * 100)+"\n");
		}
		
		System.out.println(sb.toString());
	}

}
