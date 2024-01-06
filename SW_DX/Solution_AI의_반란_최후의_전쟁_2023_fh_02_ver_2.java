package algo.SW_DX;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution_AI의_반란_최후의_전쟁_2023_fh_02_ver_2 {

	static int N,result;
	static int sumPower; // 모든 최정예 요원의 능력치 총합
	static int chooseNum; // 요원의 선택된 능력치 총합
	static int[][] power;
	// 갤럭시는 힘, 지능, 민첩을 적어도 한 번씩은 공유받아야 가동할 수 있다.
	// joker에 3개의 능력이 모두 같거나, 3개의 능력치 중 최댓값의 개수가 2개인 경우 저장해둔다.
	static ArrayList<Integer> joker;
	static boolean[] isChecked; // a,b,c 능력이 모두 충족되었는지 확인

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int TC = Integer.parseInt(br.readLine());
		for(int tc=1;tc<=TC;tc++) {
			sb.append("#"+tc+" ");
			N = Integer.parseInt(br.readLine());
			power = new int[N][4];
			sumPower = 0;
			chooseNum = 0;
			isChecked = new boolean[3];
			joker = new ArrayList<>();
			
			for(int i=0;i<N;i++) {
				st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				
				int powerIdx = -1; 
				int max = 0; // a,b,c 능력 중 최댓값 미리 구해놓기
				max = Math.max(max, a);
				if(max == a) powerIdx = 0;
				max = Math.max(max, b);
				if(max == b) powerIdx = 1;
				max = Math.max(max, c);
				if(max == c) powerIdx = 2;
				
				power[i][0] = a;
				power[i][1] = b;
				power[i][2] = c;
				power[i][3] = max;
				
				if(is_joker(i)) {
					joker.add(i);
				}
				else {
					isChecked[powerIdx] = true;
					chooseNum += max;
				}
				
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

		System.out.println(sb.toString());
	}
	
	private static void solve() {
		
		// 이미 joker를 제외한 것들로 a,b,c 능력이 충족된 경우
		if(is_all_checked(isChecked)) {
			for(int i : joker) {
				chooseNum += power[i][3];
			}
			result = sumPower - chooseNum;
			return;
		}
		else {
			// joker로 a,b,c 능력 중 충족되지 못한 것들을 채워야 하는 경우
			int[] visited = new int[joker.size()];
			Arrays.fill(visited, -1);
			fill_ability(0, visited);
		}
	}
	
	private static void fill_ability(int idx, int[] visited) {
		
		if(idx == joker.size()) {
			
			// 기존 선택된 a,b,c 능력 표시
			boolean[] renewalIsChecked = new boolean[3];
			for(int i=0;i<3;i++) {
				if(isChecked[i]) renewalIsChecked[i] = true;
			}
			int num = 0;
			for(int i=0;i<joker.size();i++) {
				int n = joker.get(i);
				int powerIdx = visited[i];
				renewalIsChecked[powerIdx] = true;
				num += power[n][powerIdx];
			}
			
			if(is_all_checked(renewalIsChecked)) {
				result = Math.min(result, sumPower-chooseNum-num);
			}
			
			return;
		}
		
		for(int i=0;i<3;i++) {
			if(visited[idx] == -1) {
				visited[idx] = i;
				fill_ability(idx+1, visited);
				visited[idx] = -1;
			}
		}

	}

	private static boolean is_all_checked(boolean[] visited) {
		
		for(int i=0;i<visited.length;i++) {
			if(!visited[i]) return false;
		}
		
		return true;
	}

	private static boolean is_joker(int idx) {
		
		int max = power[idx][3];
		boolean flag = false;
		
		int cnt = 0;
		for(int i=0;i<3;i++) {
			if(power[idx][i] == max) cnt++;
		}
		
		if(cnt >= 2) flag = true;
		
		return flag;
	}

}
