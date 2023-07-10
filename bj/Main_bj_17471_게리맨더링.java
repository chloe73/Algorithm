package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_17471_게리맨더링 {
	
	static int N,result;
	static int[] people;
	static boolean[] isChecked;
	static ArrayList<Integer>[] areaList;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		N = Integer.parseInt(br.readLine());
		
		people = new int[N+1];
		
		st = new StringTokenizer(br.readLine());
		for(int i=1;i<=N;i++) {
			people[i] = Integer.parseInt(st.nextToken());
		}
		
		areaList = new ArrayList[N+1];
		for(int i=0;i<=N;i++) {
			areaList[i] = new ArrayList<>();
		}
		
		for(int i=1;i<=N;i++) {
			st = new StringTokenizer(br.readLine());
			
			int num = Integer.parseInt(st.nextToken());
			for(int k=0;k<num;k++) {
				areaList[i].add(Integer.parseInt(st.nextToken()));
			}
		} // input end
		
		result = Integer.MAX_VALUE;
		isChecked = new boolean[N+1];
		
		subset(1);
		
		System.out.println(result == Integer.MAX_VALUE ? -1 : result);
	}

	private static void subset(int depth) {
		
		if(depth == N) {
			ArrayList<Integer> aList = new ArrayList<>();
			ArrayList<Integer> bList = new ArrayList<>();
			
			for(int i=1;i<=N;i++) {
				if(isChecked[i]) aList.add(i);
				else bList.add(i);
			}
			
			if(aList.size() == 0 || bList.size() == 0) return;
			
			// 각 a,b, 그룹이 각각 서로 연결되어 있는지 확인
			if(is_connected(aList, 'a') && is_connected(bList, 'b')) {
				int sum = 0;
				
				for(int i=0;i<aList.size();i++) {
					sum += people[aList.get(i)];
				}
				
				int sum2 = 0;
				for(int i=0;i<bList.size();i++) {
					sum2 += people[bList.get(i)];
				}
				
				int dif = Math.abs(sum-sum2);
				
				result = Math.min(result, dif);
			}
			
			return;
		}
		
		isChecked[depth] = true;
		subset(depth+1);
		
		isChecked[depth] = false;
		subset(depth+1);
	}
	
	private static boolean is_connected(ArrayList<Integer> arrList, char c) {
		
		boolean[] connect = new boolean[N+1];
		int temp = arrList.get(0);
		Queue<Integer> q = new LinkedList<>();
		q.add(temp);
		connect[temp] = true;
		
		while(!q.isEmpty()) {
			int tmp = q.poll();
			
			for(int i=0;i<areaList[tmp].size();i++) {
				int next = areaList[tmp].get(i);
				if(connect[next]) continue;
				
				if( (c == 'a' && isChecked[next]) || (c == 'b' && !isChecked[next])) {
					q.add(next);
					connect[next] = true;
				}
			}
		}
		
		for(int i=0;i<arrList.size();i++) {
			if(!connect[arrList.get(i)]) return false;
		}
		
		
		return true;
	}

}
