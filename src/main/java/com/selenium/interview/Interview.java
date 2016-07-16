package com.selenium.interview;

import org.junit.Ignore;
import org.junit.Test;

public class Interview {
	public static StringBuilder sb;
	public static String str = "hello world, i am a java program, how are you today?";
	
	@Ignore
	@Test
	public void replace_recursion() {
		sb = new StringBuilder();
		myReplace(str, 'a', '/');
		System.out.println("Replace string using recursion: "+sb.toString());
	}

	@Ignore
	@Test
	public void replace_charAt(){
		char[] chrs = str.toCharArray();
		
		StringBuilder sb1 = new StringBuilder();
		for (char chr : chrs) {
			if (chr == 's'){
				chr = 'Z';
			}
			if(chr == 'a') {
				chr='B';
			}
			sb1.append(chr);
		}
		System.out.println("Replace string using charAt: "+sb1);

	}
	
	@Ignore
	@Test
	public void fibonacciTest(){
		//Enter a number upto which series will be calculated
		int n = 40;
		fibonacciSeries_recursion(n,1,1);
		fibonacciSeries_forLoop(n, 1, 1);
		
	}
	
	@Ignore
	@Test
	public void fizzBuzzTest(){
		int number_upto = 16;
		fizzBuzz(number_upto);
		
	}
	
	@Test
	public void armstrongNumber_Test(){
		
		int numberToVerify = 151;
		/*System.out.println("Armstrong numbers are below: ");
		for (int i=1;i<1000;i++){
			if (armstrongNumber(i)){
				System.out.println(i);
			}
		}*/
		
		if (armstrongNumber2(numberToVerify)){
			System.out.println("Number: " + numberToVerify + " is an Armstrong number");
		}else
			System.out.println("Number: " + numberToVerify + " is not an Armstrong number");
	}
	
	public boolean armstrongNumber2(int number)  {  
	    int c=0,a,temp;  
	    int n=number;//It is the number to check armstrong  
	    temp=n;  
	    while(n>0)  
	    {  
	    a=n%10;  
	    n=n/10;  
	    c=c+(a*a*a);  
	    }  
	    if(temp==c)
	    	return true;   
	    
	    return false;
	   }  
	
	public boolean armstrongNumber(int number){
		/*int dig1=0,dig2=0,dig3=0;
		dig1 = number/100;
		dig2 = (number%100)/10;
		dig3 = (number%100)%10;*/
		
		if (Math.pow(number/100, 3)+Math.pow((number%100)/10, 3)+Math.pow((number%100)%10, 3)==number){
			
			return true;
		}
		
		return false;
	}
	
	public void fizzBuzz(int number_upto){
		
		for (int i=1;i<number_upto;i++){
			
			if (i%(3*5)==0){
				System.out.println("fizz buzz");
			}else if (i%5==0){
				System.out.println("buzz");
			}else if (i%3==0){
				System.out.println("fizz");
			}else {
				System.out.println(i);
			}
			
		}
	
	}
	
	
	public void fibonacciSeries_forLoop(int number_upto,int n1, int total){
		if (total==1){
			System.out.println(total);
		}
		int temp = total;
		for (n1=temp;total<number_upto;total=total+n1){
			System.out.println(total);
			n1=temp;
			temp=total;
			
		}
	}
	
	
	
	public boolean fibonacciSeries_recursion(int number_upto,int n1, int total){

		if (total>number_upto){
			return false;
		}
		if (total==1){
			System.out.println(total);
		}
		
		System.out.println(total);
		int temp = total;
		total = total+n1;
		n1=temp;
		
		return fibonacciSeries_recursion(number_upto,n1,total);
	}
	
	public boolean myReplace(String theString, char origChar, char replaceChar){
			char[] charArr = theString.toCharArray();
			int len = charArr.length;
			if (len<1){
				return false;
			}

			if (charArr[0]==origChar){
				charArr[0]=replaceChar;
			}
			sb.append(charArr[0]);

			theString = theString.substring(1);

			return myReplace(theString, origChar, replaceChar);
	}

}
