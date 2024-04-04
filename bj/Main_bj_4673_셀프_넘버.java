package algo.bj;

public class Main_bj_4673_셀프_넘버 {

	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder();
		
		boolean[] isChecked = new boolean[10001];
		
		for(int i=1;i<=10000;i++) {
			int num = i;
			String number = i+"";
			for(int k=0;k<number.length();k++) {
				num += number.charAt(k)-'0';
			}
			if(num > 10000) continue;
			isChecked[num] = true;
		}
		
		for(int i=1;i<=10000;i++) {
			if(!isChecked[i]) sb.append(i+"\n");
		}
		System.out.println(sb.toString());
	}

}
