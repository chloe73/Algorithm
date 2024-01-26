package algo.SW_DX.day10;

import java.util.ArrayList;
import java.util.HashMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class CodeBattle_Pro_조별경기 {
    private static BufferedReader br;
    private static final UserSolution userSolution = new UserSolution();

    private final static int CMD_INIT = 100;
    private final static int CMD_UPDATE_SCORE = 200;
    private final static int CMD_UNION_TEAM = 300;
    private final static int CMD_GET_SCORE = 400;

    private static boolean run() throws IOException
    {
        int queryCnt = Integer.parseInt(br.readLine());
        boolean okay = false;
        int res, ans;

        for (int i = 0; i < queryCnt; i++)
        {
            StringTokenizer stdin = new StringTokenizer(br.readLine());
            switch (Integer.parseInt(stdin.nextToken()))
            {
                case CMD_INIT:
                    int N = Integer.parseInt(stdin.nextToken());
                    userSolution.init(N);
                    okay = true;
                    break;
                case CMD_UPDATE_SCORE:
                    int mWinnerID = Integer.parseInt(stdin.nextToken());
                    int mLoserID = Integer.parseInt(stdin.nextToken());
                    int mScore = Integer.parseInt(stdin.nextToken());
                    userSolution.updateScore(mWinnerID, mLoserID, mScore);
                    break;
                case CMD_UNION_TEAM:
                    int mPlayerA = Integer.parseInt(stdin.nextToken());
                    int mPlayerB = Integer.parseInt(stdin.nextToken());
                    userSolution.unionTeam(mPlayerA, mPlayerB);
                    break;
                case CMD_GET_SCORE:
                    int mID = Integer.parseInt(stdin.nextToken());
                    res = userSolution.getScore(mID);
                    ans = Integer.parseInt(stdin.nextToken());
                    if (ans != res)
                    {
                        okay = false;
                    }
                    break;
            }
        }

        return okay;
    }

    public static void main(String[] args) throws IOException {
        // System.setIn(new java.io.FileInputStream("res/sample_input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stinit = new StringTokenizer(br.readLine(), " ");

        int T, MARK;
        T = Integer.parseInt(stinit.nextToken());
        MARK = Integer.parseInt(stinit.nextToken());

        for (int tc = 1; tc <= T; tc++)
        {
            int score = run() ? MARK : 0;
            System.out.printf("#%d %d\n", tc, score);
        }

        br.close();
    }
}

class UserSolution {
	
	static ArrayList<Integer> aList, bList;
	static int[] players;
	static HashMap<Integer, Player> playerMap;
	static class Player {
		int teamIdx;
		int num, score;
		
		Player(int num, int teamIdx, int score){
			this.num = num;
			this.teamIdx = teamIdx;
			this.score = score;
		}
	}
	
    public void init(int N) {
    	playerMap = new HashMap<>();
    	players = new int[N+1];
    	for(int i=1;i<=N;i++) {
    		playerMap.put(i, new Player(i, 0, 0));
    		players[i] = i;
    	}
    }

    public void updateScore(int mWinnerID, int mLoserID, int mScore) {
    	aList = new ArrayList<>();
    	bList = new ArrayList<>();
    	
    	int A = find(mWinnerID);
    	int B = find(mLoserID);
    	
    	traverse(A, aList);
    	traverse(B, bList);
    	
    	for(int i : aList) {
    		playerMap.get(i).score += mScore;
    	}
    	
    	for(int i : bList) {
    		playerMap.get(i).score -= mScore;
    	}
    }
    
    public void traverse(int idx, ArrayList<Integer> list) {
    	int parent = players[idx];
    	
    	if(parent != idx) {
    		traverse(parent, list);
    	}
    	
    	list.add(idx);
    }

    public void unionTeam(int mPlayerA, int mPlayerB) {
    	// 서로 다른 두 개의 조를 선정하여 하나의 조로 합치는 함수이다.
    	// ID가 mPlayerA인 선수가 속한 조와 ID가 mPlayerB인 선수가 속한 조를 하나의 조로 합친다.
    	// mPlayerA 선수와 mPlayerB 선수의 조가 같은 경우는 주어지지 않는다.
    	// mPlayerA : 선정된 선수의 ID (1 ≤ mPlayerA ≤ N)
    	// mPlayerB : 선정된 선수의 ID (1 ≤ mPlayerB ≤ N)
    	
    	int A = find(mPlayerA);
    	int B = find(mPlayerB);
    	
    	if(A != B) players[B] = A;
    }
    
    public int getScore(int mID) {
        return playerMap.get(mID).score;
    }
    
    public int find(int a) {
    	if(players[a] == a) return a;
    	
    	return players[a] = find(players[a]);
    }
}

