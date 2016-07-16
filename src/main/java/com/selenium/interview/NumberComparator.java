package com.selenium.interview;

import java.util.Comparator;

public class NumberComparator implements Comparator<Integer> {

	public int compare(Integer o1, Integer o2) {
		
		if (o1==o2){
			return 0;
		}else if(o1<o2){
			return 1;
		}else
			return -1;
	}
	
	

}
