package com.selenium.interview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

public class InterviewWipro {
	
	String str = "Earth is only planet which contains life";
	Integer[] numbers = {1,23,4,5,7};
	
	@Test
	public void sentenceReversal(){
		String[] objArray = str.split(" ");
		StringBuilder sb = new StringBuilder();
		
		for (int i=objArray.length-1;i>=0;i--){
			sb.append(objArray[i]+" ");
		}
		
		System.out.println(sb.toString());
	}
	
	@Test
	public void sortingNumbers(){
		ArrayList<Integer> list = new ArrayList<Integer>(Arrays.asList(numbers));
		
		//Using SortedSet and TreeSet
		SortedSet<Integer> ss = new TreeSet<Integer>().descendingSet();
		ss.addAll(list);
		System.out.println(ss);
		
		//Using Comparable
		Collections.sort(list);
		
		//Using Comparator
		Collections.sort(list, new NumberComparator());
		System.out.println(list);

	}
	
	
	
}
