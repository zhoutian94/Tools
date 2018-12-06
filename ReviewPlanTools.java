
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** 用于生成符合艾宾浩斯记忆方法的复习计划工具类
 * @author zhout
 *
 */
public class ReviewPlanTools {

	/**
	 * 每天复习单元数
	 */
	private static int unitPerDay = 2;
	/**
	 * 总的单元数
	 */
	private static int sumUnit = 31;
	
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static void main(String[] args){
		/*
		x月x号  背诵：xx  复习 xx
		*/
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.DAY_OF_MONTH, 6);
		printReviewPlan(cal.getTime());
	}
	/**
	 * 输出复习计划
	 * <br>
	 * 	x月x号  背诵：xx  复习 xx
	 * @param firstDate 进行复习的第一天
	 */
	private static void printReviewPlan(Date firstDate) {
		Map<Integer,String> weekNamesMap = new HashMap<Integer,String>();
		weekNamesMap.put(1, "星期天");
		weekNamesMap.put(2, "星期一");
		weekNamesMap.put(3, "星期二");
		weekNamesMap.put(4, "星期三");
		weekNamesMap.put(5, "星期四");
		weekNamesMap.put(6, "星期五");
		weekNamesMap.put(7, "星期六");
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
				System.out.print(weekNamesMap.get(week) + " " + simpleDateFormat.format(tempDate) + " 背诵:" + unitStart);
				System.out.print(" 复习:" + unitStart);
			}else{
				System.out.print(weekNamesMap.get(week) + " " + simpleDateFormat.format(tempDate) + " 背诵:" + unitStart + "-"+unitEnd);
				System.out.print(" 复习:" + unitStart + "-"+unitEnd);
			}
			printThisDateReviewPlan(tempDate,reviewPlansMap);
			System.out.println("");
			if(week == 1){
				System.out.println("");
			}
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		
	}
	/**输出这一天需要复习的计划单元
	 * @param tempDate 这一天
	 * @param reviewPlansMap 计划单元
	 */
	private static void printThisDateReviewPlan(Date tempDate, Map<String, ArrayList<String>> reviewPlansMap) {
		ArrayList<String> plans = reviewPlansMap.get(simpleDateFormat.format(tempDate));
		if(plans != null){
			for (String plan : plans) {
				System.out.print( " " + plan);
			}
		}
		
	}
	/** 生成 本次背诵所需要复习的计划
	 * @param tempDate 本次复习的日期
	 * @param unitStart 开始单元
	 * @param unitEnd 结束单元
	 * @param reviewPlansMap 复习计划map
	 */
	private static void setReviewPlans(Date tempDate, int unitStart, int unitEnd, Map<String, ArrayList<String>> reviewPlansMap) {
		Calendar tempCal = Calendar.getInstance();
		int[] periods = new int[]{1,2,4,7,15};//艾宾浩斯记忆法
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
