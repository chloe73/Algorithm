package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Main_bj_7785_회사에_있는_사람 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N = Integer.parseInt(br.readLine());
		
		HashMap<String, String> company = new HashMap<>();
		
		for(int i=0;i<N;i++) {
			String input[] = br.readLine().split(" ");
			
			String name = input[0];
			String cmd = input[1];
			
			if(cmd.equals("enter")) {
				company.put(name, cmd);
			}
			else {
				company.remove(name);
			}
			
		} // input end

		ArrayList<String> result = new ArrayList<>();
		
		for(String s : company.keySet()) {
			result.add(s);
		}
		
		Collections.sort(result);
		
		StringBuilder sb = new StringBuilder();
		
		for(int idx=result.size()-1;idx>=0;idx--) {
			sb.append(result.get(idx)+"\n");
		}
		
		System.out.println(sb.toString());
	}

}
