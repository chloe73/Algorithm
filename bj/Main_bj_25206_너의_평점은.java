package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_25206_너의_평점은 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		double totalSum = 0;
		double cnt = 0;
		for(int i=0;i<20;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			st.nextToken(); // subject
			
			double grade = Double.parseDouble(st.nextToken());
			
			String cl = st.nextToken();
			
			if(!cl.equals("P")) {
				cnt += grade;
				double c = getClass(cl);
				
				totalSum += (grade * c);
			}
			
		}
		
		System.out.printf("%.6f",totalSum / cnt);
	}
	
	private static double getClass(String s) {
		if(s.equals("A+")) return 4.5;
		else if(s.equals("A0")) return 4.0;
		else if(s.equals("B+")) return 3.5;
		else if(s.equals("B0")) return 3.0;
		else if(s.equals("C+")) return 2.5;
		else if(s.equals("C0")) return 2.0;
		else if(s.equals("D+")) return 1.5;
		else if(s.equals("D0")) return 1.0;
		else return 0.0;
	}

}
