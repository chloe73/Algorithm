package algo.codetree1.secondHalf2023;

import java.util.*;

public class M_왕실의기사대결 {
    public static final int MAX_N = 31;
    public static final int MAX_L = 41;

    public static int l, n, q;
    public static int[][] info = new int[MAX_L][MAX_L];
    public static int[] bef_k = new int[MAX_N];
    public static int[] r = new int[MAX_N], c = new int[MAX_N], h = new int[MAX_N], w = new int[MAX_N], k = new int[MAX_N];
    public static int[] nr = new int[MAX_N], nc = new int[MAX_N];
    public static int[] dmg = new int[MAX_N];
    public static boolean[] is_moved = new boolean[MAX_N];

    public static int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};

    // 움직임을 시도해봅니다.
    public static boolean tryMovement(int idx, int dir) {
        Queue<Integer> q = new LinkedList<>();
        boolean is_pos = true;

        // 초기화 작업입니다.
        for(int i = 1; i <= n; i++) {
            dmg[i] = 0;
            is_moved[i] = false;
            nr[i] = r[i];
            nc[i] = c[i];
        }

        q.add(idx);
        is_moved[idx] = true;

        while(!q.isEmpty()) {
            int x = q.poll();

            nr[x] += dx[dir];
            nc[x] += dy[dir];

            // 경계를 벗어나는지 체크합니다.
            if(nr[x] < 1 || nc[x] < 1 || nr[x] + h[x] - 1 > l || nc[x] + w[x] - 1 > l)
                return false;

            // 대상 조각이 다른 조각이나 장애물과 충돌하는지 검사합니다.
            for(int i = nr[x]; i <= nr[x] + h[x] - 1; i++) {
                for(int j = nc[x]; j <= nc[x] + w[x] - 1; j++) {
                    if(info[i][j] == 1) 
                        dmg[x]++;
                    if(info[i][j] == 2)
                        return false;
                }
            }

            // 다른 조각과 충돌하는 경우, 해당 조각도 같이 이동합니다.
            for(int i = 1; i <= n; i++) {
                if(is_moved[i] || k[i] <= 0) 
                    continue;
                if(r[i] > nr[x] + h[x] - 1 || nr[x] > r[i] + h[i] - 1) 
                    continue;
                if(c[i] > nc[x] + w[x] - 1 || nc[x] > c[i] + w[i] - 1) 
                    continue;

                is_moved[i] = true;
                q.add(i);
            }
        }

        dmg[idx] = 0;
        return true;
    }

    // 특정 조각을 지정된 방향으로 이동시키는 함수입니다.
    public static void movePiece(int idx, int dir) {
        if(k[idx] <= 0) return;

        // 이동이 가능한 경우, 실제 위치와 체력을 업데이트합니다.
        if(tryMovement(idx, dir)) {
            for(int i = 1; i <= n; i++) {
                r[i] = nr[i];
                c[i] = nc[i];
                k[i] -= dmg[i];
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 입력값을 받습니다.
        l = sc.nextInt();
        n = sc.nextInt();
        q = sc.nextInt();

        for(int i = 1; i <= l; i++)
            for(int j = 1; j <= l; j++)
                info[i][j] = sc.nextInt();

        for(int i = 1; i <= n; i++) {
            r[i] = sc.nextInt();
            c[i] = sc.nextInt();
            h[i] = sc.nextInt();
            w[i] = sc.nextInt();
            k[i] = sc.nextInt();
            bef_k[i] = k[i];
        }

        for(int i = 1; i <= q; i++) {
            int idx = sc.nextInt();
            int dir = sc.nextInt();
            movePiece(idx, dir);
        }

        // 결과를 계산하고 출력합니다.
        long ans = 0;
        for(int i = 1; i <= n; i++) {
            if(k[i] > 0) {
                ans += bef_k[i] - k[i];
            }
        }

        System.out.println(ans);
    }
}