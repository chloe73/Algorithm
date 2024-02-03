package algo.SW_DX.day15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

class Solution_Pro_신소재_케이블2 {
	private static final int CMD_INIT				= 100;
	private static final int CMD_CONNECT			= 200;
	private static final int CMD_MEASURE			= 300;
	private static final int CMD_TEST				= 400;

	private static UserSolution usersolution = new UserSolution();

	private static boolean run(Scanner sc) throws Exception {
		int Q;
		int mDevice, mOldDevice, mNewDevice, mDevice1, mDevice2;
		int mLatency;
		
		int ret = -1, ans;

		Q = sc.nextInt();
		
		boolean okay = false;
		
		for (int q = 0; q < Q; ++q) {
			int cmd = sc.nextInt();
			
			switch(cmd) {
			case CMD_INIT:
				mDevice = sc.nextInt();
				usersolution.init(mDevice);
				okay = true;
				break;
			case CMD_CONNECT:
				mOldDevice = sc.nextInt();
				mNewDevice = sc.nextInt();
				mLatency = sc.nextInt();
				usersolution.connect(mOldDevice, mNewDevice, mLatency);
				break;
			case CMD_MEASURE:
				mDevice1 = sc.nextInt();
				mDevice2 = sc.nextInt();
				ret = usersolution.measure(mDevice1, mDevice2);
				ans = sc.nextInt();
				if (ret != ans)
					okay = false;
				break;
			case CMD_TEST:
				mDevice = sc.nextInt();
				ret = usersolution.test(mDevice);
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
		//System.setIn(new java.io.FileInputStream("res/sample_input.txt"));

		Scanner sc = new Scanner(System.in);
	
		int TC = sc.nextInt();
		int MARK = sc.nextInt();
		
		for (int testcase = 1; testcase <= TC; ++testcase) {
			int score = run(sc) ? MARK : 0;
			System.out.println("#" + testcase + " " + score);
		}

		sc.close();
	}
}

class UserSolution {

	static class Node implements Comparable<Node> {
		int to,cost;
		public Node(int to, int cost) {
			this.to = to;
			this.cost = cost;
		}
		public int compareTo(Node o) {
			return o.cost - this.cost; // 최대 비용 return
		}
	}
	static HashMap<Integer, Integer> map;
	static ArrayList<ArrayList<Node>> list;
	
	public void init(int mDevice) {
		map = new HashMap<>();
		list = new ArrayList<ArrayList<Node>>();
		map.put(mDevice, list.size());
		ArrayList<Node> li = new ArrayList<>();
		list.add(li);
		return;
	}
	
	public void connect(int mOldDevice, int mNewDevice, int mLatency) {
		// 새로운 장비 mNewDevice를 추가하고 장비 mNewDevice와 장비 mOldDevice를 전송 속도가 mLatency인 케이블로 연결한다.
		// mOldDevice는 이미 존재하는 장비 번호이다.
		// mNewDevice는 추가되는 새로운 장비 번호이다.
		list.get(map.get(mOldDevice)).add(new Node(mNewDevice, mLatency));
		map.put(mNewDevice, list.size());
		ArrayList<Node> temp = new ArrayList<>();
		temp.add(new Node(mOldDevice, mLatency));
		list.add(temp);
		return;
	}
	
	public int measure(int mDevice1, int mDevice2) {
		// 장비 mDevice1에서 장비 mDevice2로 신호를 전송했을 때 전송 시간을 반환한다.
		// mDevice1와 mDevice2는 이미 존재하는 장비 번호이고 서로 다르다.
		// 두 장비가 직접 연결되어 있지 않더라도 두 장비 사이에 케이블과 다른 장비로 구성된 경로가 존재할 경우 신호 전송이 가능하다.

		PriorityQueue<Node> pq = new PriorityQueue<>();
		boolean[] visited = new boolean[map.size()];
		int[] dist = new int[map.size()];
		Arrays.fill(dist, Integer.MAX_VALUE);
		
		pq.add(new Node(mDevice1, 0));
		dist[map.get(mDevice1)] = 0;
		
		while(!pq.isEmpty()) {
			Node temp = pq.poll();
			
			// 이 때, 전송 시간은 경로상에 있는 케이블의 전송 시간의 합이 되고 신호는 이미 지나간 장비를 다시 지나가지 않는다.
			if(visited[map.get(temp.to)]) continue;
			
			visited[map.get(temp.to)] = true;
			
			for(Node next : list.get(map.get(temp.to))) {

				if(dist[map.get(next.to)] >  temp.cost + next.cost) {
					dist[map.get(next.to)] = temp.cost + next.cost;
					pq.add(new Node(next.to, dist[map.get(next.to)]));
				}
			}
		}
		return dist[map.get(mDevice2)];
	}
	
	public int test(int mDevice) {
		// 신호를 모니터링하는 장비를 장비 mDevice로 하고 테스트를 진행한다.
		// 전송 신호가 장비 mDevice를 지나가고 전송 시간이 최대가 되도록 보내는 장비와 받는 장비를 선택하고 이때의 전송 시간을 반환한다. (본문 설명 참조)
		// mDevice는 이미 존재하는 장비 번호이다.
		// 함수 호출 시, 이미 존재하는 장비는 2개 이상 있음을 보장한다.
		
		int ret = 0;
		PriorityQueue<Integer> pq = new PriorityQueue<>((o1,o2)->{
			return o2-o1;
		});
		for(Node next : list.get(map.get(mDevice))) {
			pq.add(bfs(mDevice,next.to,next.cost));
		}
		if(pq.size()>=2)
			ret = pq.poll()+pq.poll();
		else if(pq.size() == 1) ret = pq.poll();
		return ret;
	}
	
	private static int bfs(int parent, int m, int c) {
		Queue<int[]> q = new LinkedList<>();
		boolean[] visited = new boolean[map.size()];
		q.add(new int[] {m,c});
		visited[map.get(m)] = true;
		int ret = Integer.MIN_VALUE;
		
		while(!q.isEmpty()) {
			int[] temp = q.poll();
			int index = temp[0];
			int dist = temp[1];
			
			ret = Math.max(ret, dist);
			
			for(Node next : list.get(map.get(index))) {
				if(next.to == parent || visited[map.get(next.to)]) continue;
				
				visited[map.get(next.to)] = true;
				q.add(new int[] {next.to, dist+next.cost});
			}
		}
		
		return ret;
	}
}