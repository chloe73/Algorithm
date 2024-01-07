package algo.SW_DX;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution_AI의_반란_최후의_전쟁_2023_fh_02 {
	
	static int N,result;
	static int sumPower; // 모든 최정예 요원의 능력치 총합
	static int maxPower; // N명의 최정예 요원의 능력치 중 최댓값만 합한 값
	static int[][] power;
	// 갤럭시는 힘, 지능, 민첩을 적어도 한 번씩은 공유받아야 가동할 수 있다.

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		
		int TC = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=TC;tc++) {
			sb.append("#"+tc+" ");
			N = Integer.parseInt(br.readLine());
			power = new int[N][4];
			sumPower = 0;
			maxPower = 0;
			
			for(int i=0;i<N;i++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				
				int max = 0; // a,b,c 능력 중 최댓값 미리 구해놓기
				max = Math.max(max, a);
				max = Math.max(max, b);
				max = Math.max(max, c);
				
				power[i][0] = a;
				power[i][1] = b;
				power[i][2] = c;
				power[i][3] = max;
				
				maxPower += max;
				sumPower += a+b+c;
			}
			
			result = Integer.MAX_VALUE;
			if(N>=3) {
				solve();
			}
			
			if(result == Integer.MAX_VALUE) {
				sb.append(-1+"\n");
			}
			else {
				sb.append(result+"\n");
			}
		}

		bw.write(sb.toString());
		bw.flush();
		bw.close();
//		System.out.println(sb.toString());
	}

	private static void solve() {
		
		int[] galaxy = new int[3];
		Arrays.fill(galaxy, -1);
		// 총 N명의 a,b,c 능력 중에서 a,b,c 3개의 능력치만 선택되면, 
		// 나머지 선택되지 않은 애들은 a,b,c 중에서 최댓값만 고르면 된다.
		choose_power(0, galaxy, new boolean[N],maxPower);
	}
	
	private static void choose_power(int idx, int[] galaxy, boolean[] visited, int tempPower) {
		
		if(idx == 3) {
			// a,b,c 능력 3개 다 골랐으면
			
//			int chooseNum = maxPower;
//			for(int i=0;i<3;i++) {
//				chooseNum = chooseNum - power[galaxy[i]][3] + power[galaxy[i]][i];
//			}
			
//			int chooseNum = maxPower;
//			ArrayList<Integer> target = new ArrayList<>();
//			for(int i=0;i<3;i++) {
//				target.add(galaxy[i]);
//				chooseNum = chooseNum - power[galaxy[i]][3] + power[galaxy[i]][i];
//				chooseNum += power[galaxy[i]][i];
//			}
			
//			for(int i=0;i<N;i++) {
//				if(target.contains(i)) continue;
//				chooseNum += power[i][3];
//			}
			
			result = Math.min(result, sumPower - tempPower);
			
			return;
		}
		
		for(int i=0;i<N;i++) {
			if(!visited[i]) {
				galaxy[idx] = i;
				visited[i] = true;
				tempPower = tempPower - power[i][3] + power[i][idx];
				choose_power(idx+1, galaxy, visited,tempPower);
				tempPower = tempPower + power[i][3] - power[i][idx];
				galaxy[idx] = -1;
				visited[i] = false;
			}
		}
	}

}
