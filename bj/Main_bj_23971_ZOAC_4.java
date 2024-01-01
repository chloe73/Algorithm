package algo.bj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_bj_23971_ZOAC_4 {

	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        int H = Integer.valueOf(st.nextToken());
        int W = Integer.valueOf(st.nextToken());
        int N = Integer.valueOf(st.nextToken());
        int M = Integer.valueOf(st.nextToken());

        int height = (H - 1) / (N + 1) + 1;
        int width = (W - 1) / (M + 1) + 1;

        System.out.print(height * width);

    }

}
