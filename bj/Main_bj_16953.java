package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_16953 {
	
	static long A,B,result;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		A = Long.parseLong(st.nextToken());
		B = Long.parseLong(st.nextToken());
		result = Integer.MAX_VALUE;
		
		solve();
		
		System.out.println(result == Integer.MAX_VALUE ? -1 : result+1);
	}

	private static void solve() {
		Queue<long[]> q = new LinkedList<>();
		q.add(new long[] {A,0});
		
		while(!q.isEmpty()) {
			long[] temp = q.poll();
			long num = temp[0];
			long cnt = temp[1];
			
			if(num == B) {
				result = Math.min(result, (int)cnt);
				continue;
			}
			
			// 2를 곱한다.
			if(num*2 <= 1000000000) {
				q.add(new long[] {num*2, cnt+1});
			}
			
			// 1을 수의 가장 오른쪽에 추가한다. 
			long next = Long.parseLong(num+""+1);
			if(next <= 1000000000) {
				q.add(new long[] {next,cnt+1});
			}
		}
	}

}
