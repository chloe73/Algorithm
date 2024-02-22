package algo.SW_DX.day27;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Solution_Pro_파일저장소 {

	private final static int CMD_INIT = 1;
	private final static int CMD_ADD = 2;
	private final static int CMD_REMOVE = 3;
	private final static int CMD_COUNT = 4;

	private final static UserSolution usersolution = new UserSolution();

	private static boolean run(Scanner sc) {
		int q = sc.nextInt();

		int mid, msize, mstart, mend, n;
		int cmd, ans, ret = 0;
		boolean okay = false;

		for (int i = 0; i < q; ++i) {
			cmd = sc.nextInt();
			switch (cmd) {
				case CMD_INIT:
					n = sc.nextInt();
					usersolution.init(n);
					okay = true;
					break;
				case CMD_ADD:
					mid = sc.nextInt();
					msize = sc.nextInt();
					ans = sc.nextInt();
					ret = usersolution.add(mid, msize);
					if (ret != ans)
						okay = false;
					break;
				case CMD_REMOVE:
					mid = sc.nextInt();
					ans = sc.nextInt();
					ret = usersolution.remove(mid);
					if (ret != ans)
						okay = false;
					break;
				case CMD_COUNT:
					mstart = sc.nextInt();
					mend = sc.nextInt();
					ans = sc.nextInt();
					ret = usersolution.count(mstart, mend);
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
	
	static int Nn;
	static class File {
		int id, size;
		ArrayList<int[]> storageList;
		public File(int id, int size) {
			this.id = id;
			this.size = size;
			storageList = new ArrayList<>();
		}
	}
	static HashMap<Integer, File> fileMap;
	static int[] storage;
	static class Seg implements Comparable<Seg> {
		int idx, size;
		public Seg(int idx, int size) {
			this.idx = idx;
			this.size = size;
		}
		public int compareTo(Seg o) {
			return this.idx - o.idx;
		}
	}
	static PriorityQueue<Seg> segPq;
	
	public void init(int N) {
		// 각 테스트 케이스의 처음에 호출된다.
		// 전체 저장 공간의 크기 N이 주어진다. 모두 빈 공간이다.
		Nn = N;
		fileMap = new HashMap<>();
		storage = new int[N+1];
		segPq = new PriorityQueue<>();
		segPq.add(new Seg(1, N));
		

		// Parameters
		//  N : 파일 저장소의 크기 ( 25 ≤ N ≤ 25,000,000 )
		return;
	}

	public int add(int mId, int mSize) {
		// mId 파일을 추가한다. 파일의 크기는 mSize이다.
		// 주소가 가장 앞 서는 빈 공간부터 저장한다. 저장에 성공하면 저장된 공간 중에서 가장 앞 서는 주소를 반환한다.
		// 파일을 저장할 빈 공간이 부족하다면, 추가에 실패하고 -1을 반환한다.
		// mId 값으로 이미 저장되어 있는 파일의 ID가 주어지는 경우는 없다.
		// 삭제되었던 파일이 다시 추가될 수는 있다.
		int ret = -1;
		
		File temp = new File(mId, mSize);
		 
		boolean isSuccess = false;
		ArrayList<int[]> storageList = new ArrayList<>();
		ArrayList<Integer> retList = new ArrayList<>();
		int tempSize = mSize;
		
		PriorityQueue<Seg> pq = new PriorityQueue<>();
		while(!segPq.isEmpty()) {
			Seg s = segPq.poll();
		 
			// 현재 파편화된 저장소 범위
			int range = s.size - s.idx + 1;
			 
			if(tempSize <= range) {
				// 현재 seg로 저장공간을 채울 수 있으면
				storageList.add(new int[] {s.idx, s.idx + tempSize-1});
				segPq.add(new Seg(s.idx + tempSize+1, Nn));
				retList.add(s.idx);
				isSuccess = true;
				break;
			 }
			 else {
				 // 만약 쪼개야 한다면
				 
				 continue;
			 }
		 }
		
		// Parameters
		//  mId: 파일 ID ( 1 ≤ mId ≤ 1,000,000,000 )
		//  mSize: 파일 크기 ( 1 ≤ mSize ≤ 10,000 )

		if(isSuccess) {
			temp.storageList = storageList;
			fileMap.put(mId, temp);
			ret = retList.get(0);
			
		}
		 

		// Returns
		//  추가에 성공할 경우, 저장된 공간 중에서 가장 앞 서는 주소를 반환한다.
		//  추가에 실패할 경우, -1을 반환한다.
		return ret;
	}

	public int remove(int mId) {
		// mId 파일을 삭제한다. mId 파일이 저장되어 있던 모든 파일 조각은 빈 공간이 된다.
		// mId 파일이 저장되어 있던 파일 조각의 개수를 반환한다. 분할이 없었다면 이 값은 항상 1이 된다.
		// 저장되어 있지 않은 파일의 ID가 주어지는 경우는 없다. 따라서 삭제에 실패하는 경우는 없다.

		File target = fileMap.get(mId);
		
		for(int[] arr: target.storageList) {
			segPq.add(new Seg(arr[0], arr[1]));
		}
		
		fileMap.remove(mId);
		
		// Parameters
		//  mId: 파일 ID ( 1 ≤ mId ≤ 1,000,000,000 )
		// Returns
		//  mId 파일이 저장되어 있던 파일 조각의 개수를 반환한다.
		return 0;
	}

	public int count(int mStart, int mEnd) {
		// mStart 주소부터 mEnd 주소까지 저장되어 있는 파일의 개수를 반환한다.
		// 파일의 일부만 존재해도 개수에 포함시킨다.

		
		
		// Parameters
		//  mStart: 시작 주소 ( 1 ≤ mStart ≤ N )
		//  mEnd: 끝 주소 ( mStart ≤ mEnd ≤ N )

		 

		// Returns
		//  주어진 주소 영역에 저장되어 있는 파일의 개수를 반환한다.
		return 0;
	}
}