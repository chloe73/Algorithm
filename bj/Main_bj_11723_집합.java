package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_11723_집합 {

	static int M;
	static Queue<Integer> q;

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		M = Integer.parseInt(br.readLine());
		q = new LinkedList<>();
		
		for(int i=0;i<M;i++) {
			st = new StringTokenizer(br.readLine());
			String cmd = st.nextToken();
			int num = 0;
			
			if(cmd.equals("add")) {
				num = Integer.parseInt(st.nextToken());
				// add x: S에 x를 추가한다. (1 ≤ x ≤ 20) S에 x가 이미 있는 경우에는 연산을 무시한다.
				if(q.contains(num)) continue;
				else q.add(num);
			}
			else if(cmd.equals("remove")) {
				num = Integer.parseInt(st.nextToken());
				// remove x: S에서 x를 제거한다. (1 ≤ x ≤ 20) S에 x가 없는 경우에는 연산을 무시한다.
				if(q.contains(num))
					q.remove(num);
			}
			else if(cmd.equals("check")) {
				num = Integer.parseInt(st.nextToken());
				// check x: S에 x가 있으면 1을, 없으면 0을 출력한다. (1 ≤ x ≤ 20)
				if(q.contains(num))
					sb.append(1+"\n");
				else
					sb.append(0+"\n");
			}
			else if(cmd.equals("toggle")) {
				num = Integer.parseInt(st.nextToken());
				// toggle x: S에 x가 있으면 x를 제거하고, 없으면 x를 추가한다. (1 ≤ x ≤ 20)
				if(q.contains(num))
					q.remove(num);
				else
					q.add(num);
			}
			else if(cmd.equals("all")) {
				// all: S를 {1, 2, ..., 20} 으로 바꾼다.
				q.clear();
				for(int j=1;j<=20;j++) {
					q.add(j);
				}
			}
			else {
				// empty: S를 공집합으로 바꾼다.
				q.clear();
			}
		}

		System.out.println(sb.toString());
	}

}
