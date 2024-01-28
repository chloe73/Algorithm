package algo.SW_DX.day6_그래프_다익스트라;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Solution_Pro_물류허브 {

	private final static int MAX_N = 1400;
	private final static int CMD_INIT = 1;
	private final static int CMD_ADD = 2;
	private final static int CMD_COST = 3;

	private final static UserSolution usersolution = new UserSolution();

	private static boolean run(Scanner sc) {
		int q = sc.nextInt();

		int n;
		int[] sCityArr = new int[MAX_N];
		int[] eCityArr = new int[MAX_N];
		int[] mCostArr = new int[MAX_N];
		int sCity, eCity, mCost, mHub;
		int cmd, ans, ret = 0;
		boolean okay = false;

		for (int i = 0; i < q; ++i) {
			cmd = sc.nextInt();
			switch (cmd) {
				case CMD_INIT:
					okay = true;
					n = sc.nextInt();
					for (int j = 0; j < n; ++j) {
						sCityArr[j] = sc.nextInt();
						eCityArr[j] = sc.nextInt();
						mCostArr[j] = sc.nextInt();
					}
					ans = sc.nextInt();
					ret = usersolution.init(n, sCityArr, eCityArr, mCostArr);
					if (ret != ans)
						okay = false;
					break;
				case CMD_ADD:
					sCity = sc.nextInt();
					eCity = sc.nextInt();
					mCost = sc.nextInt();
					usersolution.add(sCity, eCity, mCost);
					break;
				case CMD_COST:
					mHub = sc.nextInt();
					ans = sc.nextInt();
					ret = usersolution.cost(mHub);
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
		int TC, MARK;

		//System.setIn(new java.io.FileInputStream("res/sample_input.txt"));

		Scanner sc = new Scanner(System.in);

		TC = sc.nextInt();
		MARK = sc.nextInt();

		for (int testcase = 1; testcase <= TC; ++testcase) {
			int score = run(sc) ? MARK : 0;
			System.out.println("#" + testcase + " " + score);
		}

		sc.close();
	}
}

class UserSolution {
	
	static ArrayList<City>[] cityList = new ArrayList[600];
	static ArrayList<City>[] reverseCityList = new ArrayList[600];
	static HashMap<Integer, Integer> map;
	static class City implements Comparable<City> {
		int to,cost;
		City(int to, int cost){
			this.to = to;
			this.cost = cost;
		}
		public int compareTo(City o) {
			return this.cost - o.cost;
		}
	}
	public UserSolution() {
		for(int i=0;i<600;i++) {
			cityList[i] = new ArrayList<>();
			reverseCityList[i] = new ArrayList<>();
		}
	}
	
	public int init(int N, int sCity[], int eCity[], int mCost[]) {
		// N개의 도로 정보가 주어진다. 각 도로의 출발 도시와 도착 도시, 운송 비용이 주어진다.
		// Parameters
		// N: 도로의 개수 ( 10 ≤ N ≤ 1400 )
		// (0 ≤ i ＜ N)인 모든 i에 대해,
		// sCity[i]: 도로 i의 출발 도시 ( 1 ≤ sCity[i] ≤ 1,000,000,000 )
		// eCity[i]: 도로 i의 도착 도시 ( 1 ≤ eCity[i] ≤ 1,000,000,000 )
		
		// 각 테스트 케이스에서 도시의 최대 개수는 600 이하이다.
		map = new HashMap<>();
		
		for(int i=0;i<N;i++) {
			--sCity[i];
			--eCity[i];
			
			if(!map.containsKey(sCity[i]))
				map.put(sCity[i], map.size());
			if(!map.containsKey(eCity[i]))
				map.put(eCity[i], map.size());
		}
		
		for(int i=0;i<map.size();i++) {
			cityList[i].clear();
			reverseCityList[i].clear();
		}
		
		for(int i=0;i<N;i++) {
			sCity[i] = map.get(sCity[i]);
			eCity[i] = map.get(eCity[i]);
			int start = sCity[i];
			int end = eCity[i];
			int cost = mCost[i];
			
			cityList[start].add(new City(end, cost));
			reverseCityList[end].add(new City(start, cost));
		}
		
		// 도로 정보로 주어지는 도시의 총 개수를 반환한다.
		return map.size();
	}

	public void add(int sCity, int eCity, int mCost) {
		// 출발 도시가 sCity이고, 도착 도시가 eCity이고, 운송 비용이 mCost인 도로를 추가한다.
		// init()에 없던 새로운 도시는 주어지지 않는다.
		// sCity에서 eCity로 가는 도로가 이미 존재하는 경우는 입력으로 주어지지 않는다.
		
		sCity = map.get(--sCity);
		eCity = map.get(--eCity);
		
		cityList[sCity].add(new City(eCity, mCost));
		reverseCityList[eCity].add(new City(sCity, mCost));
		
		return;
	}

	public int cost(int mHub) {
		// mHub 도시에 물류 허브를 설치할 경우, 총 운송 비용을 계산하여 반환한다.
		// mHub 도시의 운송 비용은 0으로 계산한다.
		// 각 도시에서 mHub 도시까지 왕복이 불가능한 경우는 입력으로 주어지지 않는다.
		--mHub;
		mHub = map.get(mHub);
		int go = dijkstra(mHub, cityList);
		int back = dijkstra(mHub, reverseCityList);
		
		int result = go + back;
		
		return result;
	}
	
	public int dijkstra(int start, ArrayList<City>[] list) {
		PriorityQueue<City> pq = new PriorityQueue<>();
		int[] dist = new int[map.size()];
		boolean[] visited = new boolean[map.size()];
		Arrays.fill(dist, Integer.MAX_VALUE);
		
		pq.add(new City(start, 0));
		dist[start] = 0;
		
		while(!pq.isEmpty()) {
			City temp = pq.poll();
			
//			if(dist[temp.to] != temp.cost) continue;
			
			if(visited[temp.to]) continue;
			
			visited[temp.to] = true;
			
			for(City next : list[temp.to]) {
				if(dist[next.to] > temp.cost + next.cost) {
					dist[next.to] = temp.cost + next.cost;
					pq.add(new City(next.to, dist[next.to]));
				}
			}
		}
		
		int ret = 0;
		for(int i=0;i<dist.length;i++) {
			ret += dist[i];
		}
		
		return ret;
	}
}