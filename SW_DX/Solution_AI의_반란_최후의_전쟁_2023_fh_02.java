package algo.SW_DX;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Solution_AI의_반란_최후의_전쟁_2023_fh_02 {
	
	static int N,result;
	static int sumPower; // 모든 최정예 요원의 능력치 총합
	static int[][] power;
	static PriorityQueue<Agent>[] pq;
	static class Agent implements Comparable<Agent>{
		int power;
		int powerNum;
		
		public Agent(int power, int powerNum) {
			this.power = power;
			this.powerNum = powerNum;
		}
		
		public int compareTo(Agent o) {
			return o.powerNum - this.powerNum;
		}
	}
	// 갤럭시는 힘, 지능, 민첩을 적어도 한 번씩은 공유받아야 가동할 수 있다.

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int TC = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=TC;tc++) {
			sb.append("#"+tc+" ");
			N = Integer.parseInt(br.readLine());
			power = new int[N][3];
			sumPower = 0;
			pq = new PriorityQueue[N];
			
			for(int i=0;i<N;i++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				
				power[i][0] = a;
				power[i][1] = b;
				power[i][2] = c;
				
				sumPower += a+b+c;
				pq[i] = new PriorityQueue<>();
				pq[i].add(new Agent(0, a));
				pq[i].add(new Agent(1, b));
				pq[i].add(new Agent(2, c));
			}
			
			result = Integer.MAX_VALUE;
			if(N>=3) {
				int[] isShared = new int[N];
				Arrays.fill(isShared, -1);
				solve();
			}
			
			if(result == Integer.MAX_VALUE) {
				sb.append(-1+"\n");
			}
			else {
				sb.append(result+"\n");
			}
		}

		System.out.println(sb.toString());
	}

	private static void solve() {
		
		for(int k=0;k<3;k++) {
			boolean[] visited = new boolean[3];
			boolean flag = true;
			int selectedNum = 0;
			
			for(int i=0;i<N;i++) {
				// i번째 요원의 a,b,c 능력치 중 최댓값 poll
				Agent temp = pq[i].poll();
				
				visited[temp.power] = true;
				selectedNum += temp.powerNum;
			}
			
			for(int i=0;i<3;i++) {
				if(!visited[i]) {
					flag = false;
					break;
				}
			}
			
			if(flag) {
				result = sumPower - selectedNum;
				return;
			}
		}
		
	}
	
}
