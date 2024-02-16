package algo.SW_DX.day23_codebattle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeSet;

class Solution {
    private static Scanner sc;
    private static UserSolution usersolution = new UserSolution();

    private final static int CMD_INIT = 100;
    private final static int CMD_MOVE = 200;
    private final static int CMD_TRADE = 300;

    private static boolean run() throws Exception {

        int query_num = sc.nextInt();
		int ans;
        boolean ok = false;

        for (int q = 0; q < query_num; q++) {
            int query = sc.nextInt();

            if (query == CMD_INIT) {
                int N = sc.nextInt();
                int L = sc.nextInt();
                int mAbility[] = new int[N];
                for (int i = 0; i < N; i++){
                    mAbility[i] = sc.nextInt();
                }
                usersolution.init(N, L, mAbility);
                ok = true;
            } else if (query == CMD_MOVE) {
                int ret = usersolution.move();
                ans = sc.nextInt();
                if (ans != ret) {
                    ok = false;
                }
            } else if (query == CMD_TRADE) {
                int ret = usersolution.trade();
                ans = sc.nextInt();
                if (ans != ret) {
                    ok = false;
                }
            }
        }
        return ok;
    }

    public static void main(String[] args) throws Exception {
        int T, MARK;

        // System.setIn(new java.io.FileInputStream("res/sample_input.txt"));
        sc = new Scanner(System.in);
        T = sc.nextInt();
        MARK = sc.nextInt();

        long beforeTime = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기
        for (int tc = 1; tc <= T; tc++) {
            int score = run() ? MARK : 0;
            System.out.println("#" + tc + " " + score);
        }
        long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
        long secDiffTime = (afterTime - beforeTime)/1000; //두 시간에 차 계산
        System.out.println("시간차이(m) : "+secDiffTime);
        sc.close();
    }
}

// 14616. [Pro] 승강제리그
// 25개 테스트케이스를 합쳐서 C++ 의 경우 3초 / Java 의 경우 3초
class UserSolution {
	
	static int Nn, Ll; // 총 선수 인원, 총 리그 개수
	static TreeSet<Player>[] boardTreeSet;
	static TreeSet<Player>[] upperSet;
	static TreeSet<Player>[] lowerSet;
	static class Player implements Comparable<Player>{
		int id, ability;
		
		public Player(int id, int ability) {
			this.id = id;
			this.ability = ability;
		}
		
		public int compareTo(Player o) {
			if(this.ability == o.ability)
				return this.id - o.id;
			return o.ability - this.ability;
		}
	}

    void init(int N, int L, int mAbility[]) {
    	// 각 테스트 케이스의 처음에 호출된다.
    	// N명의 선수들이 L개의 리그에 참여하게 된다.
    	// 선수의 ID는 0부터 N-1까지 순차적으로 부여되고, ID가 i인 선수의 능력 값은 mAbility[i]이다.
    	// 리그의 ID는 0부터 L-1까지 순차적으로 부여된다.
    	// 앞 번호 리그부터 선수들의 ID 순서대로 N/L 명씩 차례대로 배치한다.
    	// N은 L의 배수이며, N / L은 홀수이다.
    	Nn = N;
    	Ll = L;
    	boardTreeSet = new TreeSet[L];
    	upperSet = new TreeSet[L]; // 우선순위가 가장 높은 선수 포함
    	lowerSet = new TreeSet[L]; // 우선순위가 중간인 선수와 우선순위가 가장 낮은 선수 포함
    	int cnt = N / L; // cnt만큼 각 리그에 선수가 분배된다.
    	
    	int id = 0;
    	for(int i=0;i<L;i++) {
    		boardTreeSet[i] = new TreeSet<>();
    		upperSet[i] = new TreeSet<>();
    		lowerSet[i] = new TreeSet<>();
    		for(int j=0;j<cnt;j++) {
    			Player temp = new Player(id, mAbility[id]);
    			boardTreeSet[i].add(temp);
    			id++;
    		}
    	}
    	
    	// boardTreeSet에는 정렬된 Player 정보가 들어있으니 upperSet[i], lowerSet[i]에 우선순위 나눠서 선수 분배하기
    	for(int i=0;i<L;i++) {
    		int index = 0;
    		for(Player p : boardTreeSet[i]) {
    			if(index >= cnt / 2) {
    				// lowerSet
    				lowerSet[i].add(p);
    			}
    			else {
    				// upperSet
    				upperSet[i].add(p);
    			}
    			
    			index++;
    		}
    	}
    	 
    	// Parameters
    	// N : 선수들의 수 (9 ≤ N ≤ 39,990)
    	// L : 리그의 개수 (3 ≤ L ≤ 10, 3 ≤ N / L ≤ 3,999)
    	// mAbility : 각 선수들의 능력 값 (1 ≤ mAbility[] ≤ 10,000)
    }

    int move() {
    	// 각 테스트 케이스에서 move() 함수는 최대 500회 호출된다.
    	// 각 리그 별로 능력이 가장 좋은 선수와 상위 리그의 가장 나쁜 선수를 선발하여 교환하고,
    	// 전체 리그에서 이동한 선수들의 ID 값의 합을 반환한다.
    	 int ret = 0;
    	 
    	Queue<Player>[] q = new LinkedList[Ll];
    	for(int i=0;i<Ll;i++) {
    		q[i] = new LinkedList<>();
    	}
    	
    	for(int i=0;i<Ll;i++) {
    		if(i == 0) {
    			// 0번째 리그 -> 능력이 가장 좋지 않은 선수만 이동
    			Player worst = boardTreeSet[i].last();
    			ret += worst.id;
    			q[i+1].add(worst);
    			boardTreeSet[i].remove(worst);
    			continue;
    		}
    		else if(i == Ll-1) {
    			// L-1번째 리그 -> 능력이 가장 좋은 선수만 이동
    			Player best = boardTreeSet[i].first();
    			ret += best.id;
    			q[i-1].add(best);
    			boardTreeSet[i].remove(best);
    			continue;
    		}
    		
    		Player worst = boardTreeSet[i].last();
    		Player w = lowerSet[i].last();
    		Player best = boardTreeSet[i].first();
    		Player b = upperSet[i].first();
    		ret += worst.id;
    		ret += best.id;
    		
    		// 각각의 리그에서 능력이 가장 좋지 않은 선수는 바로 아래 리그로 내려가고,
    		q[i+1].add(worst);
			boardTreeSet[i].remove(worst);
			
			// 리그에서 능력이 가장 좋은 선수는 바로 위 리그로 올라간다.
			q[i-1].add(best);
			boardTreeSet[i].remove(best);
    	}
    	
    	for(int i=0;i<Ll;i++) {
    		while(!q[i].isEmpty()) {
    			boardTreeSet[i].add(q[i].poll());
    		}
    	}
    	
    	// Returns
    	// 이동한 선수들의 ID 값의 합
        return ret;
    }

    int trade() {
    	// 각 테스트 케이스에서 trade() 함수는 최대 1,000 회 호출된다.
    	// 각 리그 별로 능력이 가장 좋은 선수와 상위 리그의 중간급 선수를 선발하여 교환하고,
    	// 전체 리그에서 이동한 선수들의 ID 값의 합을 반환한다.
    	// 1~L-1리그들이 트레이드 진행
    	int ret = 0;
   	 
    	Queue<Player>[] q = new LinkedList[Ll];
    	ArrayList<Player>[] removeList = new ArrayList[Ll];
		for(int i=0;i<Ll;i++) {
			q[i] = new LinkedList<>();
			removeList[i] = new ArrayList<>();
		}
		
		for(int i=1;i<Ll;i++) {
			// 현재 리그에서 능력이 가장 좋은 선수
			Player best = boardTreeSet[i].first();
			removeList[i].add(best);
			ret += best.id;
			q[i-1].add(best);
			
			// 상위 리그에서 중간급 선수
			int index = boardTreeSet[i-1].size() / 2;
			Player middle = null;
			int idx = 0;
			for(Player p : boardTreeSet[i-1]) {
				if(idx == index) {
					middle = p;
					break;
				}
				idx++;
			}
			removeList[i-1].add(middle);
			ret += middle.id;
			q[i].add(middle);
		}
		
		for(int i=0;i<Ll;i++) {
			boardTreeSet[i].removeAll(removeList[i]);
			
    		while(!q[i].isEmpty()) {
    			boardTreeSet[i].add(q[i].poll());
    		}
    	}

    	// Returns
    	// 이동한 선수들의 ID 값의 합
        return ret;
    }

}