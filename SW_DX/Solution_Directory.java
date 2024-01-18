package algo.SW_DX;

import java.util.Scanner;

class Solution_Directory {

	private static UserSolution usersolution = new UserSolution();
	
	private final static int CMD_MKDIR		= 1;
	private final static int CMD_RM			= 2;
	private final static int CMD_CP			= 3;
	private final static int CMD_MV			= 4;
	private final static int CMD_FIND		= 5;
	
	private final static int NAME_MAXLEN	= 6;
	private final static int PATH_MAXLEN	= 1999;

	
//	private static boolean run(Scanner sc, int m) throws Exception {
//
//		boolean isAccepted = true;
//		int cmd;
//		char[] name;
//		char[] path1;
//		char[] path2;
//		String inputStr = "";
//
//		while (m-- > 0) {
//			
//			cmd = sc.nextInt();
//
//			if (cmd == CMD_MKDIR) {
//				inputStr = sc.next() + " ";
//				path1 = inputStr.toCharArray();
//				path1[inputStr.length() - 1] = '\0';
//				inputStr = sc.next() + " ";
//				name = inputStr.toCharArray();
//				name[inputStr.length() - 1] = '\0';
//				
//				usersolution.cmd_mkdir(path1, name);
//			}
//			else if (cmd == CMD_RM) {
//				inputStr = sc.next() + " ";
//				path1 = inputStr.toCharArray();
//				path1[inputStr.length() - 1] = '\0';
//				
//				usersolution.cmd_rm(path1);
//			}
//			else if (cmd == CMD_CP) {
//				inputStr = sc.next() + " ";
//				path1 = inputStr.toCharArray();
//				path1[inputStr.length() - 1] = '\0';
//				inputStr = sc.next() + " ";
//				path2 = inputStr.toCharArray();
//				path2[inputStr.length() - 1] = '\0';
//				
//				usersolution.cmd_cp(path1, path2);
//			}
//			else if (cmd == CMD_MV) {
//				inputStr = sc.next() + " ";
//				path1 = inputStr.toCharArray();
//				path1[inputStr.length() - 1] = '\0';
//				inputStr = sc.next() + " ";
//				path2 = inputStr.toCharArray();
//				path2[inputStr.length() - 1] = '\0';
//				
//				usersolution.cmd_mv(path1, path2);
//			}
//			else {
//				int ret;
//				int answer;
//
//				inputStr = sc.next() + " ";
//				path1 = inputStr.toCharArray();
//				path1[inputStr.length() - 1] = '\0';
//				
//				ret = usersolution.cmd_find(path1);
//				
//				answer = sc.nextInt();
//
//				isAccepted &= (ret == answer);
//			}
//		}
//
//		return isAccepted;
//	}
//	
//	public static void main(String[] args) throws Exception {
//		int test, T;
//		int n, m;
//
//		// System.setIn(new java.io.FileInputStream("sample_input.txt"));
//		
//		Scanner sc = new Scanner(System.in);
//		
//		T = sc.nextInt();
//		
//		for (test = 1; test <= T; ++test) {
//			
//			n = sc.nextInt();
//			m = sc.nextInt();
//
//			usersolution.init(n);
//
//			if (run(sc, m)) {
//				System.out.println("#" + test + " 100");
//			}
//			else {
//				System.out.println("#" + test + " 0");
//			}
//		}
//		
//		sc.close();
//	}
}


//class UserSolution {
//
//	private final static int NAME_MAXLEN	= 6;
//	private final static int PATH_MAXLEN	= 1999;
//	
////	 The below commented methods are for your reference. If you want 
////	 to use it, uncomment these methods.
////	
////	int mstrcmp(char[] a, char[] b) {
////		int i;
////		for (i = 0; a[i] != '\0'; i++) {
////			if (a[i] != b[i])
////				return a[i] - b[i];
////		}
////		return a[i] - b[i];
////	}
////
////	int mstrncmp(char[] a, char[] b, int len) {
////		for (int i = 0; i < len; i++) {
////			if (a[i] != b[i])
////				return a[i] - b[i];
////		}
////		return 0;
////	}
////
////	int mstrlen(char[] a) {
////		int len = 0;
////
////		while (a[len] != '\0')
////			len++;
////
////		return len;
////	}
////
////	void mstrcpy(char[] dest, char[] src) {
////		int i = 0;
////		while (src[i] != '\0') {
////			dest[i] = src[i];
////			i++;
////		}
////		dest[i] = src[i];
////	}
////
////	void mstrncpy(char[] dest, char[] src, int len) {
////		for (int i = 0; i < len; i++) {
////			dest[i] = src[i];
////		}
////		dest[len] = '\0';
////	}
//	
//	void init(int n) {
//		
//	}
//	
//	void cmd_mkdir(char[] path, char[] name) {
//		
//	}
//	
//	void cmd_rm(char[] path) {
//		
//	}
//	
//	void cmd_cp(char[] srcPath, char[] dstPath) {
//		
//	}
//	
//	void cmd_mv(char[] srcPath, char[] dstPath) {
//		
//	}
//	
//	int cmd_find(char[] path) {
//		
//		return 0;
//	}
//}
