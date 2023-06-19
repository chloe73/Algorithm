package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_bj_16235_나무_재테크 {
	
	static int N,M,K,result;
	static int[][] A, nourishment;
	static PriorityQueue<Tree> treePq;
	static Queue<Tree> aliveTreeList;
	static ArrayList<Tree> deadTreeList;
	static int[] dx = {-1,-1,0,1,1,1,0,-1};
	static int[] dy = {0,1,1,1,0,-1,-1,-1};
	static class Tree implements Comparable<Tree>{
		int x,y,z;
		public Tree(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		public int compareTo(Tree o) {
			return this.z - o.z;
		}
	}

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		A = new int[N][N];
		nourishment = new int[N][N];
		for(int i=0;i<N;i++) {
			Arrays.fill(nourishment[i], 5);
		} // 처음에 각 칸 모두 양분 5씩 있음.
		
		for(int i=0;i<N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0;j<N;j++) {
				A[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		treePq = new PriorityQueue<>();
		aliveTreeList = new LinkedList<>();
		deadTreeList = new ArrayList<>();
		
		for(int i=1;i<=M;i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken())-1;
			int y = Integer.parseInt(st.nextToken())-1;
			int z = Integer.parseInt(st.nextToken());
			
			treePq.add(new Tree(x, y, z));
		} // input end
		
		solve();
		
		System.out.println(result);
	}

	private static void solve() {
		
		int year = 0;
		while(year++ < K) {
			
			// 봄 : 
			spring();
			
			// 여름 :
			summer();
			
			// 가을 :
			fall();
			
			// 겨울 :
			winter();
		}
		
		// K년 후, 살아있는 나무의 개수 구하기
		result = treePq.size();
	}
	
	private static void winter() {
		// S2D2가 땅을 돌아다니면서 땅에 양분을 추가한다. 각 칸에 추가되는 양분의 양은 A[r][c]이고, 입력으로 주어진다.
		
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				nourishment[i][j] += A[i][j];
			}
		}
	}

	private static void fall() {
		// 나무가 번식한다.
		// 번식하는 나무는 나이가 5의 배수이어야 하며, 인접한 8개의 칸에 나이가 1인 나무가 생긴다.
		// 상도의 땅을 벗어나는 칸에는 나무가 생기지 않는다.
		
		while(!aliveTreeList.isEmpty()) {
			Tree t = aliveTreeList.poll();
			int x = t.x;
			int y = t.y;
			int age = t.z;
			
			if(age % 5 == 0) {
				
				for(int dir=0;dir<8;dir++) {
					int nx = x + dx[dir];
					int ny = y + dy[dir];
					
					if(!isValid(nx, ny)) continue;
					
					treePq.add(new Tree(nx, ny, 1)); // 새로 번식한 나무 추가
				}
			}
			
			treePq.add(t);
		}
		
	}

	private static void summer() {
		// 봄에 죽은 나무가 양분으로 변하게 된다. 각각의 죽은 나무마다 나이를 2로 나눈 값이 나무가 있던 칸에 양분으로 추가된다. 소수점 아래는 버린다.
		
		for(Tree t : deadTreeList) {
			int age = t.z / 2;
			
			nourishment[t.x][t.y] += age;
		}
		
		deadTreeList.clear();
	}

	private static void spring() {
		// 나무가 자신의 나이만큼 양분을 먹고, 나이가 1 증가한다. 
		// 각각의 나무는 나무가 있는 1×1 크기의 칸에 있는 양분만 먹을 수 있다. 
		// 하나의 칸에 여러 개의 나무가 있다면, 나이가 어린 나무부터 양분을 먹는다. 
		// 만약, 땅에 양분이 부족해 자신의 나이만큼 양분을 먹을 수 없는 나무는 양분을 먹지 못하고 즉시 죽는다.
		while(!treePq.isEmpty()) {
			Tree t = treePq.poll();
			int x = t.x;
			int y = t.y;
			int age = t.z;
			
			if(nourishment[x][y] >= age) {
				nourishment[x][y] -= age;
				age += 1;
				aliveTreeList.add(new Tree(x, y, age));
			}
			else deadTreeList.add(new Tree(x, y, age));
		}
	}

	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=N || c>=N) return false;
		return true;
	}

}
