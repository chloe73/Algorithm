package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main_bj_2941_크로아티아_알파벳 {
	
	static int result;
	static String input;
	static ArrayList<String> targetList;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		input = br.readLine();
		targetList = new ArrayList<>();
		
		targetList.add("c");
		targetList.add("d");
		targetList.add("l");
		targetList.add("n");
		targetList.add("s");
		targetList.add("z");
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		
		for(int i=0;i<input.length();i++) {
			String temp = input.charAt(i)+"";
			
			if(targetList.contains(temp)) {
				if(temp.equals("c")) {
					if(i+1 >= input.length()) {
						result++;
						continue;
					}
					
					temp += input.charAt(i+1);
					
					if(temp.equals("c=")) {
						i += 1;
						result++;
						continue;
					}
					
					else if(temp.equals("c-")) {
						i += 1;
						result++;
						continue;
					}
					
					else {
						result++;
						continue;
					}
				}
				
				else if(temp.equals("d")) {
					
					if(i+1 >= input.length()) {
						result++;
						continue;
					}
					
					temp += input.charAt(i+1);
					
					if(temp.equals("d-")) {
						i += 1;
						result++;
						continue;
					}
					
					if(i+2 >= input.length()) {
						result++;
						continue;
					}
					
					temp += input.charAt(i+2);
					
					if(temp.equals("dz=")) {
						i += 2;
						result++;
						continue;
					}
					
					else {
						result++;
						continue;
					}
				}
				
				else if(temp.equals("l")) {
					if(i+1 >= input.length()) {
						result++;
						continue;
					}
					
					temp += input.charAt(i+1);
					
					if(temp.equals("lj")) {
						i += 1;
						result++;
						continue;
					}
					
					else {
						result++;
						continue;
					}
				}
				
				else if(temp.equals("n")) {
					if(i+1 >= input.length()) {
						result++;
						continue;
					}
					
					temp += input.charAt(i+1);
					
					if(temp.equals("nj")) {
						i += 1;
						result++;
						continue;
					}
					
					else {
						result++;
						continue;
					}
				}
				
				else if(temp.equals("s")) {
					if(i+1 >= input.length()) {
						result++;
						continue;
					}
					
					temp += input.charAt(i+1);
					
					if(temp.equals("s=")) {
						i += 1;
						result++;
						continue;
					}
					
					else {
						result++;
						continue;
					}
				}
				
				else if(temp.equals("z")) {
					if(i+1 >= input.length()) {
						result++;
						continue;
					}
					
					temp += input.charAt(i+1);
					
					if(temp.equals("z=")) {
						i += 1;
						result++;
						continue;
					}
					else {
						result++;
						continue;
					}
				}
			}
			
			else {
				result++;
				continue;
			}
		}
		
	}

}
