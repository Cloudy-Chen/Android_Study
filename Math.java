class Math{
	public static final Integer CONSTANT_1 = 666;
	public static Object obj = new Object();
	public int math(){
		int a = 1;
		int b = 2;
		int c = (a+b)*10;
		return c;	
	}
	public static void main(String[] args){
		Math math = new Math();
		Math math2 = new Math();
		System.out.println(math.math());
	}
}