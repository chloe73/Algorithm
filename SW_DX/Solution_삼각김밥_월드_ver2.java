package algo.SW_DX;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Solution_삼각김밥_월드_ver2 {

    public static final int LEVEL = 0;
    public static final int DIFF = 1;
    
    public static void main(String []args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    	StringBuilder sb = new StringBuilder();

    	int[][] SequentialSequence = new int[10001][2];
        int LowerBound = 0;
        int UpperBound = 1;
        int Difference = 2;
        int Level = 1;
        
        for (int i = 1; i <= 10000; i++) {
            SequentialSequence[i][LEVEL] = Level;
            SequentialSequence[i][DIFF] = (i * 2) - (LowerBound + UpperBound + 1);
            if (i == UpperBound) {
                LowerBound = UpperBound;
                UpperBound += Difference;
                Difference++;
                Level++;
            }
        }
        
        int T = Integer.parseInt(br.readLine());
        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            
            int result = 0;
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            
            int LevelDiffBetweenTwoPoint = Math.abs(SequentialSequence[s][LEVEL] - SequentialSequence[e][LEVEL]);
            int WidthDiffBetweenTwoPoint = Math.abs(SequentialSequence[s][DIFF] - SequentialSequence[e][DIFF]);
            WidthDiffBetweenTwoPoint -= LevelDiffBetweenTwoPoint;
            result = LevelDiffBetweenTwoPoint;
            if (WidthDiffBetweenTwoPoint > 0) {
                result += WidthDiffBetweenTwoPoint / 2;
            }
            sb.append("#").append(tc).append(" ").append(result).append("\n");
        }
        
        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}