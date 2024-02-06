package algo.SW_DX.day17;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
// Pro_블록쌓기게임
public class Solution {
    private static BufferedReader br;
    private static UserSolution usersolution = new UserSolution();

    private final static int CMD_INIT = 100;
    private final static int CMD_DROP = 200;
	
	
	public static final class Result{
		int top;
		int count;

		Result(){
			top = 0;
			count = 0;
		}
	}

    private static boolean run() throws Exception {

        StringTokenizer stdin = new StringTokenizer(br.readLine(), " ");

        int query_num = Integer.parseInt(stdin.nextToken());
		int ans_top, ans_count;
        boolean ok = false;

        for (int q = 0; q < query_num; q++) {
            stdin = new StringTokenizer(br.readLine(), " ");
            int query = Integer.parseInt(stdin.nextToken());

            if (query == CMD_INIT) {
                int C = Integer.parseInt(stdin.nextToken());
                usersolution.init(C);
                ok = true;
            } else if (query == CMD_DROP) {
                int mCol = Integer.parseInt(stdin.nextToken());
                int mHeight = Integer.parseInt(stdin.nextToken());
                int mLength = Integer.parseInt(stdin.nextToken());
				
				Result ret = usersolution.dropBlocks(mCol, mHeight, mLength);
                ans_top = Integer.parseInt(stdin.nextToken());
				ans_count = Integer.parseInt(stdin.nextToken());
                
                if (ans_top != ret.top || ans_count != ret.count) {
                    ok = false;
                }
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
	
	static int col;
	static long totalCnt;
	static int[] arr, min, max;

    void init(int C) {
    	// 각 테스트 케이스의 처음에 호출된다.
    	// 격자판의 열의 개수 C가 주어진다.
    	// 처음 격자판은 비어있다.
    	// C: 격자판의 열의 개수 (10 ≤ C ≤ 1,000,000)
    	col = C;
    	
    	int p = 1;
    	while(p < C) {
    		p *= 2;
    	}
    	
    	arr =  new int[p*2];
    	min = new int[p*2];
    	max = new int[p*2];
    	totalCnt = 0;
    }
    
    void updateRange(int node, int nodeLeft, int nodeRight, int queryLeft, int queryRight, int queryValue) {
    	if(queryRight < nodeLeft || nodeRight < queryLeft) return;
    	
    	if(queryLeft <= nodeLeft && nodeRight <= queryRight) {
    		arr[node] += queryValue;
    		min[node] += queryValue;
    		max[node] += queryValue;
    	}
    	else {
    		int mid = (nodeLeft + nodeRight) / 2;
    		updateRange(node*2, nodeLeft, mid, queryLeft, queryRight, queryValue);
    		updateRange(node*2+1, mid+1, nodeRight, queryLeft, queryRight, queryValue);
    		min[node] = Math.min(min[node*2], min[node*2+1]) + arr[node];
    		max[node] = Math.max(max[node*2], max[node*2+1]) + arr[node];
    	}
    }

    Solution.Result dropBlocks(int mCol, int mHeight, int mLength) {
    	// 격자판의 가로방향의 위치 mCol에 높이가 mHeight이고 길이가 mLength인 직사각형 모양으로 배치된 블록들을 위치시킨다.
    	// 격자판을 벗어나서 블록들이 위치하게 하는 mCol와 mLength 값이 주어지는 경우는 없다.
    	// 배치된 블록들이 아래로 내려가다 멈추게 되면 각 행마다 블록들로 가득 찼는지 여부를 확인하여 해당 행의 블록들을 삭제하고 위쪽의 블록들을 밑으로 내린다.
    	// 이후 남아있던 블록들의 개수를 1,000,000으로 나눈 나머지와 가장 높은 블록의 높이를 반환한다.
    	// 격자판에 남아있는 블록들의 개수는 Integer type의 최대값보다 클 수 있다는 점에 유의하라.
        Solution.Result ret = new Solution.Result();
        totalCnt += mHeight * mLength;
        
        updateRange(1, 0, col-1, mCol, mCol+mLength-1, mHeight);  
        ret.top = max[1] - min[1];
        ret.count = (int) ((totalCnt - min[1]*col) % 1000000);
        
        return ret;
    }

}