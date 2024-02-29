package algo.SW_DX.day27;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeSet;

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
	static int possibleSize; // 현재 저장 가능한 파일 크기 -> 현재 남아있는 저장소 크기
	static class File {
		int id, size;
		ArrayList<Seg> storageList;
		public File(int id, int size) {
			this.id = id;
			this.size = size;
			storageList = new ArrayList<>();
		}
	}
	static HashMap<Integer, File> fileMap;
	static class Seg implements Comparable<Seg> {
		int id; // 파일 id
		int start, end;
		public Seg(int id, int idx, int size) {
			this.id = id;
			this.start = idx;
			this.end = size;
		}
		public int compareTo(Seg o) {
			return this.start - o.start;
		}
	}
	static PriorityQueue<Seg> segPq;
	static TreeSet<Seg> treeSet; // 저장된 파일 seg 정보 (정렬된 데이터)
	
	public void init(int N) {
		// 각 테스트 케이스의 처음에 호출된다.
		// 전체 저장 공간의 크기 N이 주어진다. 모두 빈 공간이다.
		Nn = N;
		possibleSize = N;
		fileMap = new HashMap<>();
		segPq = new PriorityQueue<>();
		segPq.add(new Seg(0, 1, N));
		treeSet = new TreeSet<>();
		
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
		
		// 추가에 실패할 경우, -1을 반환한다.
		if(mSize > possibleSize) return ret;
		
		File temp = new File(mId, mSize);
		 
		boolean isSuccess = false; // 파일 저장이 성공적으로 되었는지 확인하기 위한 변수
		ArrayList<Seg> storageList = new ArrayList<>();
		ArrayList<Integer> retList = new ArrayList<>();
		int tempSize = mSize; // 현재 새로 저장할 파일 크기
		
		PriorityQueue<Seg> pq = new PriorityQueue<>(); // 만약 파일 저장에 실패한 경우 데이터 복구를 위한 변수
		while(!segPq.isEmpty()) {
			Seg s = segPq.poll(); // 저장소 파편 조각
			pq.add(s);
		 
			// 현재 파편화된 저장소 크기
			int range = s.end - s.start + 1;
			 
			if(tempSize <= range) {
				// 현재 seg로 저장공간을 채울 수 있으면
				Seg seg = new Seg(mId, s.start, s.start + tempSize-1);
				storageList.add(seg); // {시작 주소, 끝 주소}
				treeSet.add(seg);
				// 현재 파일이 저장되고 seg 저장공간이 남는 경우
				if(range - tempSize > 0) {
					segPq.add(new Seg(mId, s.start+tempSize, s.end));
				}
				else {					
					if(segPq.isEmpty()) {
						segPq.add(new Seg(mId, s.start+tempSize, Nn));
						
					}
					else {
						if(segPq.peek().start <= Nn) {
							int end = segPq.peek().start-1;
							segPq.add(new Seg(mId, s.start + tempSize, end));
						}					
					}
				}
				retList.add(s.start);
				isSuccess = true;
				break;
			 }
			 else {
				 // 만약 쪼개야 한다면
				 tempSize -= range;
				 Seg seg = new Seg(mId, s.start, s.end);
				 storageList.add(seg); // {시작 주소, 끝 주소}
				 treeSet.add(seg);
				 retList.add(s.start);
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
			possibleSize -= mSize;
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
		int ret = target.storageList.size();
		
		for(Seg s: target.storageList) {
			segPq.add(s);
			treeSet.remove(s);
			// 만약 연결되는 seg가 있다면 합치는 작업 필요
		}
		
		fileMap.remove(mId);
		possibleSize += target.size;
		// Parameters
		//  mId: 파일 ID ( 1 ≤ mId ≤ 1,000,000,000 )
		// Returns
		//  mId 파일이 저장되어 있던 파일 조각의 개수를 반환한다.
		return ret;
	}

	public int count(int mStart, int mEnd) {
		// mStart 주소부터 mEnd 주소까지 저장되어 있는 파일의 개수를 반환한다.
		// 파일의 일부만 존재해도 개수에 포함시킨다.

		boolean flag = false;
		HashSet<Integer> retSet = new HashSet<>();
		for(Seg s : treeSet) {
			if(flag) {
				if(mStart < s.start && s.start <= mEnd) {
					retSet.add(s.id);
					continue;
				}
			}
			
			if(s.end < mStart) continue;
			
			if(!flag && s.start <= mStart && mStart <= s.end) {
				flag = true;
				retSet.add(s.id);
				continue;
			}
			
//			if(s.start <= mEnd && mEnd <= s.end) {
//				
//			}
			
			if(mEnd < s.start) {
				break;
			}
		}

		// Returns
		//  주어진 주소 영역에 저장되어 있는 파일의 개수를 반환한다.
		return retSet.size();
	}
}