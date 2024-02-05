package algo.SW_DX.day16;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

class Solution_Pro_상품권배분 {
	private final static int CMD_INIT = 1;
	private final static int CMD_ADD = 2;
	private final static int CMD_REMOVE = 3;
	private final static int CMD_DISTRIBUTE = 4;

	private final static UserSolution usersolution = new UserSolution();

	private static boolean run(BufferedReader br) throws Exception {
		int q = Integer.parseInt(br.readLine());

		int[] midArr = new int[1000];
		int[] mnumArr = new int[1000];
		int mid, mnum, mparent, n, k;
		int cmd, ans, ret = 0;
		boolean okay = false;

		for (int i = 0; i < q; ++i) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			cmd = Integer.parseInt(st.nextToken());
			switch (cmd) {
				case CMD_INIT:
					n = Integer.parseInt(st.nextToken());
					for (int j = 0; j < n; ++j) {
						StringTokenizer dep = new StringTokenizer(br.readLine(), " ");
						midArr[j] = Integer.parseInt(dep.nextToken());
						mnumArr[j] = Integer.parseInt(dep.nextToken());
					}
					usersolution.init(n, midArr, mnumArr);
					okay = true;
					break;
				case CMD_ADD:
					mid = Integer.parseInt(st.nextToken());
					mnum = Integer.parseInt(st.nextToken());
					mparent = Integer.parseInt(st.nextToken());
					ans = Integer.parseInt(st.nextToken());
					ret = usersolution.add(mid, mnum, mparent);
					if (ret != ans)
						okay = false;
					break;
				case CMD_REMOVE:
					mid = Integer.parseInt(st.nextToken());
					ans = Integer.parseInt(st.nextToken());
					ret = usersolution.remove(mid);
					if (ret != ans)
						okay = false;
					break;
				case CMD_DISTRIBUTE:
					k = Integer.parseInt(st.nextToken());
					ans = Integer.parseInt(st.nextToken());
					ret = usersolution.distribute(k);
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

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		TC = Integer.parseInt(st.nextToken());
		MARK = Integer.parseInt(st.nextToken());

		for (int testcase = 1; testcase <= TC; ++testcase) {
			int score = run(br) ? MARK : 0;
			System.out.println("#" + testcase + " " + score);
		}

		br.close();
	}
}

class UserSolution {
    
	static int nGroup,size;
	static HashMap<Integer, Integer> idMap;
	int[] parent; // 부모 정보
	int[] sub; // 인원 수 총합 정보
	int[] child; // 자식 수 정보
	boolean[] isDead; // 이미 삭제된 부서인지에 대한 정보
    
	public void init(int N, int mId[], int mNum[]) {
		// N개의 그룹에 대한, 최상위 부서 ID와 부서 인원 수가 각각 배열로 주어진다.
		
		nGroup = N;
		size = 0;
		idMap = new HashMap<>();
		parent = new int[18000];
		sub = new int[18000];
		child = new int[18000];
		isDead = new boolean[18000];
		
		for(int i=0;i<N;i++) {
			idMap.put(mId[i], size);
			parent[size] = -1;
			sub[size] = mNum[i];
			child[size] = 0;
			size++;
		}
		return;
	}

	public int add(int mId, int mNum, int mParent) {
		// mId 부서를 mParent 부서의 하위 부서로 추가한다.
		// mId 부서의 인원 수는 mNum이다.
		// mParent 값은 항상 존재하는 부서의 ID만 주어진다.
		// mId 값으로 이미 존재하는 부서의 ID가 주어지는 경우는 없다.
		
		int p = idMap.get(mParent);
		// mParent 부서에 이미 3개의 하위 부서가 존재한다면, 추가에 실패하고 -1을 반환한다.
		if(child[p] >= 3) return -1;
		
		int id = size++; // 현재 mId의 index 번호
		idMap.put(mId, id);
		child[p]++;
		parent[id] = p; // 현재 mId의 부모는 p
		sub[id] = 0;
		child[id] = 0;
		
		while(id != -1) {
			// id의 조상들에 인원 수를 더한다.
			sub[id] += mNum;
			id = parent[id];
		}
		// 추가에 성공할 경우, mParent 부서를 포함하여 그 아래 모든 부서의 인원 수 합을 반환한다. 
		// 다시 말해, mParent가 루트 노드인 서브 트리의 인원 수 합을 반환한다.
		return sub[p];
	}

	public int remove(int mId) {
		// ID가 mId인 부서를 삭제한다. mId 부서 아래 모든 부서도 함께 삭제된다.
		// 최상위 부서의 ID가 주어지는 경우는 없다.
		
		// mId 부서가 존재하지 않을 경우, -1을 반환한다.
		if(!idMap.containsKey(mId)) return -1;
		
		int id = idMap.get(mId);
		// 이미 삭제된 부서의 ID가 주어질 수도 있다.
		for(int node=id;node != -1; node = parent[node]) {
			if(isDead[node]) return -1;
		}
		
		isDead[id] = true;
		child[parent[id]]--; // 현재 id의 부모가 가진 자식 수도 1 줄어든다.
		
		int num = sub[id];
		while(id != -1){
			sub[id] -= num;
			id = parent[id];
		}
		// mId 부서가 존재할 경우, mId 부서를 포함하여 그 아래 모든 부서의 인원 수 합을 반환한다. 
		// 다시 말해, mId가 루트 노드인 서브 트리의 인원 수 합을 반환한다.
		return num;
	}

	public int distribute(int K) {
        // N개의 그룹에 상품권 K개를 배분 규칙에 맞게 최대한 많이 나누어 주었을 때, 
		// 각 그룹에 배분된 상품권 개수 중에서 가장 큰 값을 반환한다.
		int[] arr = Arrays.copyOf(sub, nGroup);
        Arrays.sort(arr);

        int totalNum = Arrays.stream(arr).sum();

        if (totalNum <= K) return arr[arr.length - 1];

        for (int i = nGroup - 1; i >= 0; i--) {
            totalNum -= arr[i];
            int L = (K - totalNum) / (nGroup - i);
            if ((i - 1 >= 0 ? arr[i - 1] : 0) <= L) {
                return L;
            }
        }
		
		return 0;
	}
}