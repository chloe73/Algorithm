package algo.SW_DX.day5_이분탐색;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Solution_영어_공부 {
	
	static int N,P;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int T = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=T;tc++) {
			bw.write("#"+tc+" ");
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			N = Integer.parseInt(st.nextToken());
			P = Integer.parseInt(st.nextToken());
			
			st = new StringTokenizer(br.readLine());
			int[] arr = new int[N];
			boolean[] visited = new boolean[1000001];
			for(int i=0;i<N;i++) {
				arr[i] = Integer.parseInt(st.nextToken());
				visited[arr[i]] = true;
			} // input end
			
			int result = P+1; // 연속 공부 기간의 가능한 최대 길이
			
			int start = 1;
			int end = 1;
			int day = 0;
			
			while(end < visited.length) {
				if(visited[end]) {
					day++;
					end++;
					result = Math.max(result, day);
				}
				else {
					if(P == 0) {
						result = Math.max(result, day);
						if(!visited[start]) P++;
						start++;
						day--;
					}
					else {
						P--;
						day++;
						end++;
					}
				}
			}
			
			bw.write(result+"\n");
		}
		
		bw.flush();
		br.close();
		bw.close();
	}

}
