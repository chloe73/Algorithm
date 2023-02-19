package algo.BType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_10726_이진수_표현 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int TC = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=TC;tc++) {
			st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			
			int lastBit = (1 << N) -1;
			
			if(lastBit == (M & lastBit))
				sb.append("#"+tc+" ").append("ON"+"\n");
			else
				sb.append("#"+tc+" ").append("OFF"+"\n");
			
		}
		System.out.println(sb.toString());
	}

}
