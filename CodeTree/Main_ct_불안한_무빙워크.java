package algo.CodeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_ct_불안한_무빙워크 {
	
	static int N,K,result;
	static int[][] arr; // 안정성 값
	static boolean[] isChecked; // 무빙워크 칸에 사람 있는지 확인하기 위한 변수
	static Queue<Integer> personQ;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		arr = new int[2][N];
		isChecked = new boolean[N];
		
		st = new StringTokenizer(br.readLine());
		
		for(int i=0;i<N;i++) arr[0][i] = Integer.parseInt(st.nextToken());
		
		for(int i=N-1;i>=0;i--) arr[1][i] = Integer.parseInt(st.nextToken());
		
		// input end
		personQ = new LinkedList<>();
		
		solve();

		System.out.println(result);
	}

	private static void solve() {
		
		// 안정성이 0인 칸이 k개 이상이라면 과정을 종료합니다.
		while(!is_done()) {
			result++;
			// 1. 무빙워크가 한 칸 회전합니다.
			move_arr();
			
			// 2. 가장 먼저 무빙워크에 올라간 사람부터 무빙워크가 회전하는 방향으로 한 칸 이동할 수 있으면 이동합니다. 
			// 만약 앞선 칸에 사람이 이미 있거나 앞선 칸의 안정성이 0인 경우에는 이동하지 않습니다.
			move_person();
			
			// 3. 1번 칸에 사람이 없고 안정성이 0이 아니라면 사람을 한 명 더 올립니다.
			if(!isChecked[0] && arr[0][0] > 0) {
				personQ.add(0);
				arr[0][0]--;
				isChecked[0] = true;
			}
			
		}
		
	}

	private static void move_person() {
		
		int size = personQ.size();
		
		for(int i=0;i<size;i++) {
			int temp = personQ.poll();
			
			if(arr[0][temp+1] > 0 && !isChecked[temp+1]) {
				isChecked[temp] = false;
				temp++;
				arr[0][temp]--;
				isChecked[temp] = true;
			}
			
			if(temp == N-1) {
				isChecked[N-1] = false;
				continue;
			}
			
			personQ.add(temp);
		}
	}

	private static void move_arr() {
		
		int temp = arr[0][N-1];
		for(int i=N-2;i>=0;i--) {
			arr[0][i+1] = arr[0][i];
		}
		
		arr[0][0] = arr[1][0];
		for(int i=1;i<N;i++) {
			arr[1][i-1] = arr[1][i];
		}
		
		arr[1][N-1] = temp;
		
		// 무빙워크 한 칸 이동하면서 사람도 함께 한 칸 이동
		int size = personQ.size();
		
		for(int i=0;i<size;i++) {
			int pNum = personQ.poll();
			isChecked[pNum] = false;
			
			pNum++;
			
			if(pNum == N-1) continue;
			
			personQ.add(pNum);
			isChecked[pNum] = true;
		}
	}

	private static boolean is_done() {
		int num = 0;
		
		for(int i=0;i<2;i++) {
			for(int j=0;j<N;j++) {
				if(arr[i][j] == 0) num++;
				if(num == K) return true;
			}
		}
		
		return false;
	}
}
