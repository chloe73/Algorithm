package algo.SW_DX.day22;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

class Solution_Pro_전기차대여소 {
	private static final int CMD_INIT				= 0;
	private static final int CMD_ADD				= 1;
	private static final int CMD_DISTANCE			= 2;
	private static final int MAX_N					= 350;

	private static int[][] region = new int[MAX_N][MAX_N];
	private static UserSolution usersolution = new UserSolution();
	private static Scanner sc;

	private static boolean run() throws Exception {
		int Q = sc.nextInt();
		
		boolean okay = false;

		for (int q = 0; q < Q; ++q) {
			int cmd = sc.nextInt();
			int ret, ans, N, range, id, r, c, id2;
			
			switch(cmd) {
			case CMD_INIT:
				N = sc.nextInt();
				range = sc.nextInt();
				for (int i = 0; i < N; i++)
				{
					for (int j = 0; j < N; j++)
					{
						int in = sc.nextInt();
						region[i][j] = in;
					}
				}
				usersolution.init(N, range, region);
				okay = true;
				break;
			case CMD_ADD:
				id = sc.nextInt();
				r = sc.nextInt();
				c = sc.nextInt();

				usersolution.add(id, r, c);
				break;
			case CMD_DISTANCE:
				id = sc.nextInt();
				id2 = sc.nextInt();
				ret = usersolution.distance(id, id2);
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
	
	static int Nn,range; // 충전하지 않고 한 번에 갈 수 있는 거리 마지노선
	static int[][] board;
	static HashMap<Integer, RentalStation> rentalMap; // 전기차 대여소 정보
	static class RentalStation {
		int id, x, y;
		
		public RentalStation(int id, int x, int y) {
			this.id = id;
			this.x = x;
			this.y = y;
		}
	}
	static class Node implements Comparable<Node>{
		int x,y,dist,battery;
		public Node(int x, int y, int dist, int battery) {
			this.x = x;
			this.y = y;
			this.dist = dist;
			this.battery = battery;
		}
		public int compareTo(Node o) {
			return this.dist - o.dist;
		}
	}
	
	void init(int N, int mRange, int mMap[][]) {
		
		// 각 TC의 처음에 호출되며 새로운 지역의 정보가 주어진다.
		// 배터리가 가득 충전된 상태에서 충전 없이 mRange를 이동하여 도착한 구역에도 대여소가 없다면 추가 이동이 불가능하다.
		// [Fig. 4]와 같이 행, 열의 좌표로 구역을 나타낼 때, 구역 (Row, Col)의 지형 정보는 mMap[Row][Col]에 저장되어 있다. 
		// mMap[Row][Col]의 값이 0이면 장애물이 없음을, 1이면 장애물이 있음을 의미한다.
		board = new int[N][N];
		range = mRange;
		Nn = N;
		
		for(int i=0;i<N;i++) {
			board[i] = Arrays.copyOf(mMap[i], N);
		}
		
		rentalMap = new HashMap<>();
		
		// Parameters
		// N : 지도의 크기 N x N ( 10 ≤ N ≤ 350 )
		// mRange : 충전 후 전기차의 이동 가능 거리 ( 5 ≤ mRange ≤ 100 )
		// mMap : 각 지역의 지형 정보. ( 0 ≤ mMap ≤ 1 )
		return;
	}

	void add(int mID, int mRow, int mCol) {
		// 구역 (mRow, mCol)에 새로운 대여소가 설치된다.
		// mID는 TC 내에서 0에서 시작해 설치 시 마다 1씩 증가한다.
		// (mRow, mCol)의 구역에 기존 대여소나 장애물이 있는 경우는 없다.
		
		rentalMap.put(mID, new RentalStation(mID, mRow, mCol));
		board[mRow][mCol] = 200 + mID;
		// Parameters
		// mID : 대여소의 고유번호 ( 0 ≤ mID < 200 )
		// mRow : 대여소의 행 좌표 ( 0 ≤ mRow < N )
		// mCol : 대여소의 열 좌표 ( 0 ≤ mCol < N )
		return;
	}

	int distance(int mFrom, int mTo) {
		// 고유번호가 mFrom인 대여소에서 고유번호가 mTo인 대여소까지 이동할 때의 최단 거리를 반환한다.
		// 사용자가 전기차를 빌렸을 때, 배터리는 가득 충전되어 있다.
		// mFrom과 mTo는 기존에 설치된 대여소이며, 서로 다르다.
		
		int[] dx = {-1,1,0,0};
		int[] dy = {0,0,-1,1};
		int ret = Integer.MAX_VALUE;
		
		
		
		// Parameter
		// mFrom : 출발지 대여소의 고유번호 ( 0 ≤ mFrom < 200 )
		// mTo : 목적지 대여소의 고유번호 ( 0 ≤ mTo < 200 )

		// Returns
		// 목적지까지 전기차로 이동하는 최단 거리를 반환한다.
		// 도달 불가능한 경우 -1을 반환한다.
		return ret == Integer.MAX_VALUE ? -1 : ret;
	}
	
	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=Nn || c>=Nn) return false;
		return true;
	}
}