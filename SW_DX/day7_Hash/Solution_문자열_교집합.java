package algo.SW_DX.day7_Hash;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Solution_문자열_교집합 {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			bw.write("#"+tc+" ");
			StringTokenizer st = new StringTokenizer(br.readLine());
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());
			
			HashMap<String, Integer> a = new HashMap<>();
			st = new StringTokenizer(br.readLine());
			for(int i=0;i<N;i++) {
				String s = st.nextToken();
				a.put(s, i+1);
			}
			
			int result = 0;
			st = new StringTokenizer(br.readLine());
			for(int i=0;i<M;i++) {
				String s = st.nextToken();
				if(a.containsKey(s)) {
					result++;
				}
			} // input end
			
			bw.write(result+"\n");
		}

		bw.flush();
		br.close();
		bw.close();
	}

}
