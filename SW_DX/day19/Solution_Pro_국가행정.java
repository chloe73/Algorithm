package algo.SW_DX.day19;

import java.util.PriorityQueue;
import java.util.Scanner;

public class Solution_Pro_국가행정 {
	private static final int CMD_INIT				= 100;
	private static final int CMD_EXPAND				= 200;
	private static final int CMD_CALCULATE			= 300;
	private static final int CMD_DIVIDE				= 400;
	private static final int MAX_N					= 10000;

	private static int[] population = new int[MAX_N];
	private static UserSolution usersolution = new UserSolution();
	private static Scanner sc;

	private static boolean run() throws Exception {
		boolean okay = false;
		int Q = sc.nextInt();

		for (int q = 0; q < Q; ++q) {
			int ret, ans, N, from, to, num;
			int cmd = sc.nextInt();
			
			switch(cmd) {
			case CMD_INIT:
				N = sc.nextInt();
				for (int i = 0; i < N; i++) {
					int in = sc.nextInt();
					population[i] = in;
				}
				usersolution.init(N, population);
				okay = true;
				break;
			case CMD_EXPAND:
				num = sc.nextInt();
				ret = usersolution.expand(num);
				ans = sc.nextInt();
				if (ret != ans)
					okay = false;
				break;
			case CMD_CALCULATE:
				from = sc.nextInt();
				to = sc.nextInt();
				ret = usersolution.calculate(from, to);
				ans = sc.nextInt();
				if (ret != ans)
					okay = false;
				break;
			case CMD_DIVIDE:
				from = sc.nextInt();
				to = sc.nextInt();
				num = sc.nextInt();
				ret = usersolution.divide(from, to, num);
				ans = sc.nextInt();
				if (ret != ans)
					okay = false;
				break;
			default:
				okay = false;
				break;
			}
		}

		return okay;
	}
	
	public static void main(String[] args) throws Exception {
		// System.setIn(new java.io.FileInputStream("res/sample_input.txt"));

		sc = new Scanner(System.in);
	
		int TC = sc.nextInt();
		int MARK = sc.nextInt();
		
		for (int testcase = 1; testcase <= TC; ++testcase) {
			int score = run() ? MARK : 0;
			System.out.println("#" + testcase + " " + score);
		}
		
		sc.close();
	}
}

class UserSolution {
	
	static int Nn;
	static int[] seg, population, roadCnt,pop;
	static PriorityQueue<Node> pq;
	static class Node implements Comparable<Node> {
		int to, weight;
		public Node(int to, int weight) {
			this.to = to;
			this.weight = weight;
		}
		public int compareTo(Node o) {
			if(this.weight == o.weight)
				return this.to - o.to;
			return o.weight - this.weight;
		}
	}
	
	private static void update(int node, int start, int end, int queryIndex, int value) {
		if(queryIndex < start || queryIndex > end) return;
		
		if(start == end) {
			seg[node] = value;
			return;
		}
		
		int mid = (start + end) / 2;
		
		update(node*2, start, mid, queryIndex, value);
		update(node*2+1, mid+1, end, queryIndex, value);
		
		seg[node] = seg[node*2] + seg[node*2+1];
	}
	
	private static int querySum(int node, int start, int end, int queryLeft, int queryRight) {
		
		if(queryRight < start || queryLeft > end) return 0;
		
		if(queryLeft <= start && end <= queryRight) return seg[node];
		
		int mid = (start + end) / 2;
		
		int leftSum = querySum(node*2, start, mid, queryLeft, queryRight);
		int rightSum = querySum(node*2+1, mid+1, end, queryLeft, queryRight);
		
		return leftSum + rightSum;
	}
	
	private static void seg_init(int node, int start, int end) {
		if(start == end) {
			seg[node] = population[start];
			return;
		}
		
		int mid = (start + end) / 2;
		
		seg_init(node*2, start, mid);
		seg_init(node*2+1, mid+1, end);
		
		seg[node] = seg[node*2] + seg[node*2+1];
	}
	
	void init(int N, int mPopulation[]) {
		pop = mPopulation;
		population = new int[N];
		for(int i=0;i<N;i++) {
			population[i] = mPopulation[i];
		}
		Nn = N;
		pq = new PriorityQueue<>();
		seg = new int[N*4];
		
		roadCnt = new int[N];
		population[0] = 0;
		for(int i=0;i<N-1;i++) {
			int weight = mPopulation[i]+mPopulation[i+1];
			pq.add(new Node(i+1, weight));
			roadCnt[i+1] = 1; // 연결된 도로 개수
			population[i+1] = weight;
		}
		
		seg_init(1, 0, N-1);

		return;
	}

	int expand(int M) {
		// 이웃한 도시 간의 이동 시간이 가장 긴 도로를 한 차선 확장하기를 M번 반복한다.
		// 이동 시간이 같은 도로가 여럿일 경우 고유번호가 작은 도시를 잇는 도로가 우선이다.
		// 같은 도로가 여러번 선택될 수 있다.
		// 확장된 도로의 이동 시간을 계산할 때 (두 도시의 인구의 합 / 도로의 차선 수) 에서 소수점 이하 부분은 버린다.
		
		int ret = 0;
		
		while(M-- > 0) {
			if(!pq.isEmpty()) {
				Node temp = pq.poll();
				roadCnt[temp.to]++; // 도로 개수 1 증가시킴.
				ret = (pop[temp.to-1]+pop[temp.to]) / roadCnt[temp.to];
				pq.add(new Node(temp.to, ret));
				update(1, 0, Nn-1, temp.to, ret);
			}
		}
		
		return ret;
	}
	
	int calculate(int mFrom, int mTo) {
		// 고유번호가 mFrom인 도시에서 고유번호가 mTo인 도시까지 이동하는 데 걸리는 시간을 반환한다.
		// mFrom과 mTo는 서로 다르다.
		// 차선이 여러 개인 각 도로의 이동시간에서 버림한 부분은 합산할 때 고려하지 않는다.
		
		if(mFrom > mTo) {
			int temp = mFrom;
			mFrom = mTo;
			mTo = temp;
		}
		
		// Parameters
	    // mFrom : 출발 도시의 고유번호 ( 0 ≤ mFrom < N )
	    // mTo : 도착 도시의 고유번호 ( 0 ≤ mTo < N )
		
		return querySum(1, 0, Nn-1, mFrom+1, mTo);
	}
	
	int divide(int mFrom, int mTo, int K) {
		// 본문의 조건에 따라 mFrom부터 mTo까지의 도시에 대해서 K개의 선거구를 결정한다.
		// 가장 인구 수가 많은 선거구의 인구 수를 반환한다.
		int left = 1; int right = Integer.MAX_VALUE;
		
		while(left < right) {
			int mid = (left + right) / 2;
			
			int p = 0;
			
			for(int i=mFrom;i<=mTo && p <= K; p++) {
				int sum = 0;
				int j= i;
				
				while(j <= mTo && sum + pop[j] <= mid) {
					sum += pop[j++];
				}
				i = j;
			}
			
			if(p <= K) {
				right = mid;
			}
			else {
				left = mid+1;
			}
		}
		
		return right;
	}
}