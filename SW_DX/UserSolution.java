package algo.SW_DX;

//User Code
// MAX 10만명의 User에 대해 수입이 주어진다.
// 수입이 가장 큰 user 10명의 uID를 수입에 대해 내림차순으로 구하라.
public class UserSolution {
	
	static Heap heap;
	class User {
		int uId;
		int income;
		
		User(int uId, int income){
			this.uId = uId;
			this.income = income;
		}
	}
	class Heap {
		User[] arr;
		int size = 0;
		
		Heap(){
			arr = new User[100001];
		}
		
		void add(int uId, int income) {
			arr[++size] = new User(uId, income);
			int now = size;
			
			while(now > 1) {
				int parent = getPaprent(now);
				if(arr[now].income > arr[parent].income) {
					User temp = arr[parent];
					arr[parent] = arr[now];
					arr[now] = temp;
					now = parent;
				}
				else break;
			}
		}
		
		User poll() {
			User max = arr[1];
			
			arr[1] = arr[size];
			arr[size] = null;
			size--;
			heapify();
			
			return max;
		}
		
		void heapify() {
			int now = 1;
			
			while(getRight(now) <= size) {
				int larger = now;
				int left = getLeft(now);
				int right = getRight(now);
				
				if(arr[left].income > arr[larger].income) {
					larger = left;
				}
				
				if(arr[right].income > arr[larger].income) {
					larger = right;
				}
				
				if(larger != now) {
					User temp = arr[now];
					arr[now] = arr[larger];
					arr[larger] = temp;
					now = larger;
				}
				else break;
			}
		}
		
		int getLeft(int parent) {
			return parent * 2;
		}
		
		int getRight(int parent) {
			return parent * 2 + 1;
		}
		
		int getPaprent(int child) {
			return child / 2;
		}
	}

	
	public void init() {
		// 각 테스트케이스 처음에 호출되는 초기화 함수이다.
		heap = new Heap();
	}
	
	public void addUser(int uID, int income) {
		// user을 추가하는 함수이다.
		heap.add(uID, income);
	}
	
	int getTop10(int[] result) {
		int cnt = 0;
		// 수입이 가장 큰 user 10명의 uID를 수입에 대해 내림차순으로 구하는 함수이다.
		if(heap.size >= 10) {
			for(int i=0;i<10;i++) {
				result[i] = heap.poll().uId;
				cnt++;
			}
		}
		// 총 user의 수가 10명이 되지 않으면 존재하는 user의 uID를 수입에 대해 내림차순으로 구한다.
		else {
			int heapSize = heap.size;
			for(int i=0;i<heapSize;i++) {
				result[i] = heap.poll().uId;
				cnt++;
			}
		}
		
		// result의 개수를 반환한다.
		return cnt;
	}
}