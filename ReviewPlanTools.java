package com.springmvc.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** �����������ϰ�����˹���䷽���ĸ�ϰ�ƻ�������
 * @author zhout
 *
 */
public class ReviewPlanTools {

	/**
	 * ÿ�츴ϰ��Ԫ��
	 */
	private static int unitPerDay = 2;
	/**
	 * �ܵĵ�Ԫ��
	 */
	private static int sumUnit = 31;
	
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static void main(String[] args){
		/*
		x��x��  ���У�xx  ��ϰ xx
		*/
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_MONTH, 6);
		printReviewPlan(cal.getTime());
	}
	/**
	 * �����ϰ�ƻ�
	 * <br>
	 * 	x��x��  ���У�xx  ��ϰ xx
	 * @param firstDate ���и�ϰ�ĵ�һ��
	 */
	private static void printReviewPlan(Date firstDate) {
		Map<Integer,String> weekNamesMap = new HashMap<Integer,String>();
		weekNamesMap.put(1, "������");
		weekNamesMap.put(2, "����һ");
		weekNamesMap.put(3, "���ڶ�");
		weekNamesMap.put(4, "������");
		weekNamesMap.put(5, "������");
		weekNamesMap.put(6, "������");
		weekNamesMap.put(7, "������");
		Calendar cal = Calendar.getInstance();
		cal.setTime(firstDate);
		int sumDay = sumUnit/unitPerDay;
		Map<String,ArrayList<String>> reviewPlansMap = new HashMap<String,ArrayList<String>>();
		for(int count = 1; count <= sumDay; count++){
			
			Date tempDate = cal.getTime();
			int unitStart = count * unitPerDay - unitPerDay + 1;
			int unitEnd = count * unitPerDay;
			setReviewPlans(tempDate,unitStart,unitEnd,reviewPlansMap);
			int week = cal.get(Calendar.DAY_OF_WEEK);
			if(unitStart == unitEnd){
				System.out.print(weekNamesMap.get(week) + " " + simpleDateFormat.format(tempDate) + " ����:" + unitStart);
				System.out.print(" ��ϰ:" + unitStart);
			}else{
				System.out.print(weekNamesMap.get(week) + " " + simpleDateFormat.format(tempDate) + " ����:" + unitStart + "-"+unitEnd);
				System.out.print(" ��ϰ:" + unitStart + "-"+unitEnd);
			}
			printThisDateReviewPlan(tempDate,reviewPlansMap);
			System.out.println("");
			if(week == 1){
				System.out.println("");
			}
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		
	}
	/**�����һ����Ҫ��ϰ�ļƻ���Ԫ
	 * @param tempDate ��һ��
	 * @param reviewPlansMap �ƻ���Ԫ
	 */
	private static void printThisDateReviewPlan(Date tempDate, Map<String, ArrayList<String>> reviewPlansMap) {
		ArrayList<String> plans = reviewPlansMap.get(simpleDateFormat.format(tempDate));
		if(plans != null){
			for (String plan : plans) {
				System.out.print( " " + plan);
			}
		}
		
	}
	/** ���� ���α�������Ҫ��ϰ�ļƻ�
	 * @param tempDate ���θ�ϰ������
	 * @param unitStart ��ʼ��Ԫ
	 * @param unitEnd ������Ԫ
	 * @param reviewPlansMap ��ϰ�ƻ�map
	 */
	private static void setReviewPlans(Date tempDate, int unitStart, int unitEnd, Map<String, ArrayList<String>> reviewPlansMap) {
		Calendar tempCal = Calendar.getInstance();
		int[] periods = new int[]{1,2,4,7,15};//������˹���䷨
		int count = 0;
		for (int afterDay = 1; afterDay <= periods.length; afterDay++) {
			
			tempCal.setTime(tempDate);
			tempCal.add(Calendar.DAY_OF_MONTH, periods[count]);
			String reviewDate = simpleDateFormat.format(tempCal.getTime());
			ArrayList<String> plans = reviewPlansMap.get(reviewDate);
			if(null == plans){
				plans = new ArrayList<String>();
				if(unitStart == unitEnd){
					plans.add(unitStart+"");
				}else{
					plans.add(unitStart+"-"+unitEnd);
				}
				reviewPlansMap.put(reviewDate, plans);
			}else{
				if(unitStart == unitEnd){
					plans.add(unitStart+"");
				}else{
					plans.add(unitStart+"-"+unitEnd);
				}
			}
			count++;
		}
		
		
		
	}
}
