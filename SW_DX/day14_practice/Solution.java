package algo.SW_DX.day14_practice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {

    private static BufferedReader br;
    private static UserSolution usersolution = new UserSolution();

    private final static int MAX_ROW = 40;
    private final static int MAX_COL = 30;

    private static int[][] mCells = new int[MAX_ROW][MAX_COL];
    private static int[][] mPuzzle = new int[3][3];

    private final static int CMD_INIT = 1;
    private final static int CMD_PUT = 2;
    private final static int CMD_CLR = 3;

    static class Result {
        int row;
        int col;

        Result(int r, int c) {
            row = r;
            col = c;
        }
    }

    private static boolean run() throws Exception {

        StringTokenizer stdin = new StringTokenizer(br.readLine(), " ");
        int query_num = Integer.parseInt(stdin.nextToken());
        boolean ok = false;

        for (int q = 0; q < query_num; q++) {
            stdin = new StringTokenizer(br.readLine(), " ");
            int query = Integer.parseInt(stdin.nextToken());

            if (query == CMD_INIT) {
                int mRows = Integer.parseInt(stdin.nextToken());
                int mCols = Integer.parseInt(stdin.nextToken());
                for (int i = 0; i < mRows; i++) {
                    stdin = new StringTokenizer(br.readLine(), " ");
                    for (int j = 0; j < mCols; j++) {
                        mCells[i][j] = Integer.parseInt(stdin.nextToken());
                    }
                }
                usersolution.init(mRows, mCols, mCells);
                ok = true;
            } else if (query == CMD_PUT) {
                char strPuzzle[] = stdin.nextToken().toCharArray();
                int cnt = 0;
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        mPuzzle[i][j] = strPuzzle[cnt] - '0';
                        cnt++;
                    }
                }
                int ans_row = Integer.parseInt(stdin.nextToken());
                int ans_col = Integer.parseInt(stdin.nextToken());
                Result ret = usersolution.putPuzzle(mPuzzle);
                if (ans_row != ret.row || ans_col != ret.col) {
                    ok = false;
                }
            } else if (query == CMD_CLR) {
				usersolution.clearPuzzles();
            }
        }
        return ok;
    }

    public static void main(String[] args) throws Exception {
        int T, MARK;

        // System.setIn(new java.io.FileInputStream("res/sample_input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer stinit = new StringTokenizer(br.readLine(), " ");
        T = Integer.parseInt(stinit.nextToken());
        MARK = Integer.parseInt(stinit.nextToken());

        for (int tc = 1; tc <= T; tc++) {
            int score = run() ? MARK : 0;
            System.out.println("#" + tc + " " + score);
        }
        br.close();
    }
}

class UserSolution {
	
	static int R,C;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static int[][] board;
	static boolean[][] visited;
	static ArrayList<Integer>[] puzzleType;

	public void init(int mRows, int mCols, int mCells[][]) {
		// 각 테스트 케이스의 처음에 호출된다.
		// 크기가 mRows * mCols인 게임판이 주어진다.
		// 게임 판의 행의 개수는 mRows이고, 열의 개수는 mCols가 된다.
		// 게임판 내에서 i번째 행, j번째 열에 위치한 셀에 적힌 숫자는 mCells[i][j]로 주어진다.
		// 초기에는 게임판에 어떠한 조각도 놓여있지 않다.
		
		R = mRows;
		C = mCols;
		board = new int[R][C];
		visited = new boolean[R][C];
		for(int i=0;i<R;i++) {
			board[i] = Arrays.copyOf(mCells[i], C);
		}
		
		puzzleType = new ArrayList[6];
		for(int i=1;i<=5;i++) {
			puzzleType[i] = new ArrayList<>();
		}
		
		puzzleType[1].add(3);
		
		puzzleType[2].add(3);
		puzzleType[2].add(3);

		puzzleType[3].add(1);
		puzzleType[3].add(1);

		puzzleType[4].add(3);
		puzzleType[4].add(1);
		puzzleType[4].add(3);
		
		puzzleType[5].add(1);
		puzzleType[5].add(3);
		puzzleType[5].add(3);
		puzzleType[5].add(1);
	}
	
	// 규칙 1. 주어진 조각 내의 각각의 블록들에 대하여 블록의 숫자와 그 블록이 맞닿은 게임판 셀의 숫자의 차이(조각의 블록의 숫자 – 게임판 셀의 숫자)가 모두 동일한 경우에만 올려 놓을 수 있다.

	public Solution.Result putPuzzle(int mPuzzle[][]) {
		// 조각의 정보가 주어질 때 위 규칙에 맞는 위치를 찾아 놓고 그 위치를 반환해야 한다.
		// 만약, 놓을 위치가 없는 경우, Result.row와 Result.col 값을 -1로 설정한 후 반환한다.
		// 조각의 모양과 각 블록에 적혀있는 숫자의 정보는 mPuzzle로 주어진다.
		// (조각의 모양은 위에서 언급한 5가지 중 한가지로 주어진다.)
		// mPuzzle[][]의 값은 블록에 적혀있는 숫자이고, 값이 0인 경우 해당 위치는 블록이 없음을 의미한다.
		// 주어진 조각의 가장 윗부분 중 가장 왼쪽의 블록의 숫자는 mPuzzle[0][0]에 위치함을 보장한다.
		Solution.Result ret = new Solution.Result(-1, -1);

		int type = getPuzzleType(mPuzzle);
		
		outer:for(int i=0;i<R;i++) {
			for(int j=0;j<C;j++) {
				if(visited[i][j]) continue;
				
				int diff = board[i][j] - mPuzzle[0][0];
				boolean flag = true;
				int ax = i;
				int ay = j;
				int bx = 0;
				int by = 0;
				for(int d : puzzleType[type]) {
					ax += dx[d];
					ay += dy[d];
					
					if(!isValid(ax, ay)) {
						flag = false;
						break;
					}
					
					if(visited[ax][ay]) {
						flag = false;
						break;
					}
					
					bx += dx[d];
					by += dy[d];
					
					if(diff != board[ax][ay] - mPuzzle[bx][by]) {
						flag = false;
						break;
					}
				}
				
				if(!flag) continue;
				
				ret.row = i;
				ret.col = j;
				visited[i][j] = true;
				ax = i;
				ay = j;
				for(int d : puzzleType[type]) {
					ax += dx[d];
					ay += dy[d];
					
					visited[ax][ay] = true;
				}
				break outer;
			}
		}
		
		return ret;
	}
	
	private static boolean isValid(int r, int c) {
		if(r<0 || c<0 || r>=R || c>=C) return false;
		return true;
	}
	
	private static int getPuzzleType(int[][] puzzle){
		
		if(puzzle[0][0] > 0 && puzzle[0][1] > 0 && puzzle[0][2] > 0) {
			return 2;
		}
		else if(puzzle[0][0] > 0 && puzzle[0][1] > 0 && puzzle[0][2] == 0
				&& puzzle[1][1] == 0) {
			return 1;
		}
		else if(puzzle[0][0] > 0 && puzzle[1][0] > 0 && puzzle[2][0] > 0) {
			return 3;
		}
		else if(puzzle[0][0] > 0 && puzzle[0][1] > 0 && puzzle[1][1] > 0
				&& puzzle[1][2] > 0) {
			return 4;
		}
		
		return 5;
	}

	public void clearPuzzles() {
		visited = new boolean[R][C];
		return;
	}

}