package algo.SW_DX.day14;

import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class Solution {
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
// 19339. [Pro] 숫자조각게임
class UserSolution {
	
	static int R,C;
	static int[][] board;
	static boolean[][] visited;
	static ArrayList<int[]>[] dirList;

	public void init(int mRows, int mCols, int mCells[][]) {
		// 각 테스트 케이스의 처음에 호출된다.
		// 크기가 mRows * mCols인 게임판이 주어진다.
		// 게임 판의 행의 개수는 mRows이고, 열의 개수는 mCols가 된다.
		// 게임판 내에서 i번째 행, j번째 열에 위치한 셀에 적힌 숫자는 mCells[i][j]로 주어진다.
		// 초기에는 게임판에 어떠한 조각도 놓여있지 않다.
		R = mRows; C = mCols;
		board = new int[mRows][mCols];
		visited = new boolean[mRows][mCols];
		for(int i=0;i<mRows;i++) {
			for(int j=0;j<mCols;j++) {
				board[i][j] = mCells[i][j];
			}
		}
		
		dirList = new ArrayList[6];
		for(int i=0;i<6;i++) {
			dirList[i] = new ArrayList<>();
		}
		dirList[1].add(new int[] {0,1});
		
		dirList[2].add(new int[] {0,1});
		dirList[2].add(new int[] {0,1});
		
		dirList[3].add(new int[] {1,0});
		dirList[3].add(new int[] {1,0});
		
		dirList[4].add(new int[] {0,1});
		dirList[4].add(new int[] {1,0});
		dirList[4].add(new int[] {0,1});
		
		dirList[5].add(new int[] {1,0});
		dirList[5].add(new int[] {0,1});
		dirList[5].add(new int[] {0,1});
		dirList[5].add(new int[] {1,0});
	}

	public Solution.Result putPuzzle(int mPuzzle[][]) {
		// 조각의 정보가 주어질 때 위 규칙에 맞는 위치를 찾아 놓고 그 위치를 반환해야 한다.
		// 만약, 놓을 위치가 없는 경우, Result.row와 Result.col 값을 -1로 설정한 후 반환한다.
		// 조각의 모양과 각 블록에 적혀있는 숫자의 정보는 mPuzzle로 주어진다.
		// (조각의 모양은 위에서 언급한 5가지 중 한가지로 주어진다.)
		// mPuzzle[][]의 값은 블록에 적혀있는 숫자이고, 값이 0인 경우 해당 위치는 블록이 없음을 의미한다.
		// 주어진 조각의 가장 윗부분 중 가장 왼쪽의 블록의 숫자는 mPuzzle[0][0]에 위치함을 보장한다.
		Solution.Result ret = new Solution.Result(-1, -1);
		
		int[] type = getPuzzleType(mPuzzle);
		int[] position = findPosition(type, mPuzzle);
		ret.row = position[0];
		ret.col = position[1];
		
		return ret;
	}
	
	private int[] findPosition(int[] type, int[][] mPuzzle) {
		int[] ret = new int[] {-1,-1};
		
		// 규칙 1. 주어진 조각 내의 각각의 블록들에 대하여 블록의 숫자와 
		// 그 블록이 맞닿은 게임판 셀의 숫자의 차이(조각의 블록의 숫자 – 게임판 셀의 숫자)가 
		// 모두 동일한 경우에만 올려 놓을 수 있다.
		// 규칙 2. 게임판에 주어진 조각을 놓을 때, 그 조각의 일부가 이미 놓여있는 조각의 일부와 중첩되게 놓을 수 없다.
		int t = type[0];
		int rSize = type[1];
		int cSize = type[2];
		outer:for(int i=0;i<=R-rSize;i++) {
			for(int j=0;j<=C-cSize;j++) {
				int x = i;
				int y = j;
				
				int px = 0;
				int py = 0;
				
				if(visited[x][y]) continue;
				
				int diff = mPuzzle[px][py]-board[x][y];
				boolean flag = true;
				
				for(int[] dir : dirList[t]) {
					x += dir[0];
					y += dir[1];
					px += dir[0];
					py += dir[1];
					
					if(visited[x][y]) {
						flag = false;
						break;
					}
					
					if(diff == (mPuzzle[px][py]-board[x][y])) continue;
					
					else {
						flag = false;
						break;
					}
				}
				
				if(flag) {
					// 현재 위치에 마킹 가능하면 visited 배열에 check 표시
					x = i;
					y = j;
					ret[0] = i;
					ret[1] = j;
					visited[x][y] = true;
					for(int[] dir : dirList[t]) {
						x += dir[0];
						y += dir[1];
						
						visited[x][y] = true;
					}
					
					break outer;
				}
			}
		}
		
		return ret;
	}

	private static int[] getPuzzleType(int[][] mPuzzle) {
		
		if(mPuzzle[0][0] > 0 && mPuzzle[0][1] > 0 
				&& mPuzzle[1][1] > 0 && mPuzzle[1][2] > 0)
			return new int[] {4,2,3};
		if(mPuzzle[0][0] > 0 && mPuzzle[0][1] > 0 && mPuzzle[0][2] == 0)
			return new int[] {1,1,2};
		if(mPuzzle[0][0] > 0 && mPuzzle[0][1] > 0 && mPuzzle[0][2] > 0)
			return new int[] {2,1,3};
		if(mPuzzle[0][0] > 0 && mPuzzle[1][0] > 0 && mPuzzle[2][0] > 0)
			return new int[] {3,3,1};
		
		return new int[] {5,3,3};
	}

	public void clearPuzzles() {
		visited = new boolean[R][C];
		return;
	}

}