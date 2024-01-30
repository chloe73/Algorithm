package algo.SW_DX.day12;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

class Solution_Pro_메모장_프로그램 {
	private final static int CMD_INIT       = 100;
	private final static int CMD_INSERT     = 200;
	private final static int CMD_MOVECURSOR = 300;
	private final static int CMD_COUNT      = 400;
	
	private final static UserSolution usersolution = new UserSolution();
	
	private static void String2Char(char[] buf, String str, int maxLen) {
		for (int k = 0; k < str.length(); k++)
			buf[k] = str.charAt(k);
			
		for (int k = str.length(); k <= maxLen; k++)
			buf[k] = '\0';
	}
	
	private static char[] mStr = new char[90001];
	
	private static boolean run(BufferedReader br) throws Exception {
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		int queryCnt = Integer.parseInt(st.nextToken());
		boolean correct = false;
		
		for (int q = 0; q < queryCnt; q++) {
			st = new StringTokenizer(br.readLine(), " ");
			
			int cmd = Integer.parseInt(st.nextToken());
			
			if (cmd == CMD_INIT) {
				int H = Integer.parseInt(st.nextToken());
				int W = Integer.parseInt(st.nextToken());
				
				String2Char(mStr, st.nextToken(), 90000);
				
				usersolution.init(H, W, mStr);
				correct = true;
			}
			else if (cmd == CMD_INSERT) {
				char mChar = st.nextToken().charAt(0);
				
				usersolution.insert(mChar);
			}
			else if (cmd == CMD_MOVECURSOR) {
				int mRow = Integer.parseInt(st.nextToken());
				int mCol = Integer.parseInt(st.nextToken());
				
				char ret = usersolution.moveCursor(mRow, mCol);
				
				char ans = st.nextToken().charAt(0);
				if (ret != ans) {
					correct = false;
				}
			}
			else if (cmd == CMD_COUNT) {
				char mChar = st.nextToken().charAt(0);
				
				int ret = usersolution.countCharacter(mChar);
				
				int ans = Integer.parseInt(st.nextToken());
				if (ret != ans) {
					correct = false;
				}
			}
		}
		return correct;
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
	
	static Deque<Character>[] grid = new ArrayDeque[303];
    static int[][] counting = new int[303][26];
    static int h, w;
    static int cur_r, cur_c;
    static int len;
	
	void init(int H, int W, char mStr[]) {
		len = 0;
        h = H;
        w = W;
        counting = new int[303][26];
        for (int i = 0; i < 303; ++i) grid[i] = new ArrayDeque<>();
        for (int i = 0; i < H; ++i) {
            for (int j = 0; j < W; ++j) {
                if (len < mStr.length && mStr[len] != '\0') {
                    counting[i][mStr[len] - 'a']++;
                    grid[i].add(mStr[len++]);
                } else break;
            }
        }
        cur_r = cur_c = 0;
	}
	
	void insert(char mChar) {
		grid[cur_r].addLast(mChar);
        counting[cur_r][mChar - 'a']++;
        int row = cur_r;
        ++cur_c;
        ++len;
        if (cur_c == w) {
            ++cur_r;
            cur_c = 0;
        }
        while (grid[row].size() > w) {
            char bk = grid[row].pollLast();

            counting[row][bk - 'a']--;
            grid[row + 1].addFirst(bk);
            ++row;
        }
	}

	char moveCursor(int mRow, int mCol) {
		--mRow;
        --mCol;
        if (mRow * w + mCol >= len) {
        	mRow = len / w;
        	mCol = len % w;
            cur_r = mRow;
            cur_c = mCol;
            return '$';
        }
        cur_r = mRow;
        cur_c = mCol;
        return grid[mRow].peekFirst();
	}

	int countCharacter(char mChar) {
        int row = cur_r;
        int col = cur_c;
        int ret = 0;
        if (col > 0) {
            for (Character ch : grid[row]) {
                if (ch == mChar) {
                    ret++;
                }
            }
            row++;
        }
        for (int i = row; i < h; ++i) {
            ret += counting[i][mChar - 'a'];
        }
        return ret;
    }
}