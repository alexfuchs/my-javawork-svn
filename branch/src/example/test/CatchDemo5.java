package example.test;

public class CatchDemo5 {
	static void throwProcess(){
		try{
//			throw new NullPointerException("��ָ���쳣");
//			throw new RuntimeException("����ʱ�쳣");
			throw new IllegalAccessException();
		}
		catch(IllegalAccessException e){
//			System.out.print("\n at throwProcess �����в���");
//			throw e;
		}
		System.out.print("\n I am OK");
	}
	
	public static void main(String args[]){
		try{
			throwProcess();
			
		}
		catch(RuntimeException e){
//			System.out.print("\n �ٴ� throwProcess �����в�");
		}
//		System.out.print("\n I am OK");
	}

}
