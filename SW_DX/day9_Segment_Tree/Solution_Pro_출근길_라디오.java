package algo.SW_DX.day9_Segment_Tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.ArrayList;

class Solution_Pro_출근길_라디오 {
	private static UserSolution usersolution = new UserSolution();
	static final int CMD_INIT = 100;
	static final int CMD_DESTROY = 200;
	static final int CMD_UPDATE = 300;
	static final int CMD_UPDATE_TYPE = 400;
	static final int CMD_CALC = 500;
	static final int MAX_N = 100000;
	static int[] mType = new int [MAX_N];
	static int[] mTime = new int [MAX_N];

	private static boolean run(BufferedReader br) throws IOException  {
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		boolean isOK = false;
		int C = new Scanner(st.nextToken()).nextInt();
		int cmd, result, check;
		int N, M;
		int mID, mTypeID, mNewTime, mRatio256;
		int mA, mB;
		for (int c = 0; c < C; ++c) {
			st = new StringTokenizer(br.readLine(), " ");
			cmd = Integer.parseInt(st.nextToken());
			switch (cmd)
			{
			case CMD_INIT:
				N = new Scanner(st.nextToken()).nextInt();
				M = new Scanner(st.nextToken()).nextInt();
				for (int i = 0; i < N - 1; i++) mType[i] = Integer.parseInt(st.nextToken());
				for (int i = 0; i < N - 1; i++) mTime[i] = Integer.parseInt(st.nextToken());
				usersolution.init(N, M, mType, mTime);
				isOK = true;
				break;
			case CMD_UPDATE:
				mID = Integer.parseInt(st.nextToken());
				mNewTime = Integer.parseInt(st.nextToken());
				usersolution.update(mID, mNewTime);
				break;
			case CMD_UPDATE_TYPE:
				mTypeID = new Scanner(st.nextToken()).nextInt();
				mRatio256 = new Scanner(st.nextToken()).nextInt();
				result = usersolution.updateByType(mTypeID, mRatio256);
				check = new Scanner(st.nextToken()).nextInt();
				if (result != check)
					isOK = false;
				break;
			case CMD_CALC:
				mA = Integer.parseInt(st.nextToken());
				mB = Integer.parseInt(st.nextToken());
				result = usersolution.calculate(mA, mB);
				check = Integer.parseInt(st.nextToken());
				if (result != check)
					isOK = false;
				break;
			default:
				isOK = false;
				break;
			}
		}
		usersolution.destroy();
		return isOK;
	}

	public static void main(String[] args) throws Exception {
		//System.setIn(new java.io.FileInputStream("res/sample_input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer line = new StringTokenizer(br.readLine(), " ");
		int TC = Integer.parseInt(line.nextToken());
		int MARK = Integer.parseInt(line.nextToken());
		for (int testcase = 1; testcase <= TC; ++testcase)
		{
			int score = run(br) ? MARK : 0;
			System.out.println("#" + testcase + " " + score);
		}
		br.close();
	}
}

class UserSolution {
	
	ArrayList<ArrayList<Integer>> typeNode;
	int[] seg;
	int base;
	
	private void update_seg(int index, int value) {
		seg[index+=base] = value;
		// 상위 노드들 또한 함께 update
		while((index >>= 1) != 0) {
			seg[index] = seg[index*2]+seg[index*2+1];
		}
	}
	
	private int get_sum(int s, int e) {
		int ret = 0;
		
		for(s += base, e+= base; s<=e; s>>=1, e>>=1) {
			if(s%2 == 1) ret += seg[s++];
			if(e%2 == 0) ret += seg[e--];
		}
		
		return ret;
	}
	
	void init(int N, int M, int mType[], int mTime[]) {
		// 각 테스트 케이스의 처음에 호출된다.
		// 지점은 N 개가 있다. 구간은 N-1개가 있고, 각 구간의 도로의 종류는 M개가 있다.
		// 지점의 ID는 도로의 시작 지점부터 순서대로 0 ~ N – 1 이고,
		// 구간의 ID는 도로의 시작 지점에 가까운 순서대로 0 ~ N – 2 이다.
		// 같은 도로의 종류를 갖는 구간은 최대 200 개 이하이다.
		
		base = 1;
		while(base < N-1) base <<= 1;
		
		seg = new int[base * 2];
		typeNode = new ArrayList<>();
		
		for(int i=0;i<M;i++) {
			typeNode.add(new ArrayList<>());
		}
		
		// 각 구간의 도로의 종류는 도로의 시작 지점에 가까운 순서대로 mType[] 으로 N-1개가 주어지며 0 ~ M – 1 의 값을 갖는다.
		// 각 구간의 현재 통과 예상 시간은 도로의 시작 지점에 가까운 순서대로 mTime[] 으로 주어진다.
		// 각 구간 별 소요시간 저장
		// 말단 노드 데이터 저장
		for(int i=0;i<N-1;i++) {
			seg[base+i] = mTime[i];
			typeNode.get(mType[i]).add(i);
		}
		
		// segment Tree 세팅 (bottom-up 방식)
		// 중간 노드 값 세팅
		for(int i=base-1;i>0;i--) {
			seg[i] = seg[i*2] + seg[i*2+1];
		}
	}

	void destroy() {

	}

	void update(int mID, int mNewTime) {
		update_seg(mID, mNewTime);
	}

	int updateByType(int mTypeID, int mRatio256) {
		// 도로의 종류가 mTypeID 인 구간들의 모든 도로의 통과 시간이 mRatio256 / 256 의 비율로 변경되며, 
		// 변경 이후 소수점 이하는 버린다. 변경된 모든 구간의 통과 시간의 합을 반환한다.
		int ret = 0;
		
		for(int node : typeNode.get(mTypeID)) {
			int original = seg[base+node];
			int renewal = original * mRatio256 / 256;
			update_seg(node, renewal);
			ret += renewal;
		}
		
		return ret;
	}

	int calculate(int mA, int mB) {
		if(mA > mB) {
			int temp = mA;
			mA = mB;
			mB = temp;
		}
		
		return get_sum(mA, mB-1);
	}
}