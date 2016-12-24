package com.arttraining.commons.util;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.arttraining.api.bean.RandomBean;

public class RandomnNumberUtil {
	private static List<RandomBean> funs=null;
	private static Random random;
	private static int count = 0;   //总的权重
	private static int index=-1;//下标
	
	//事先最好是排序好的列表
	public static void init(List<RandomBean> obj) {
		if(funs!=null) {
			return;
		}
		//自定义排序(权重)
		funs=obj;
		Collections.sort(funs);
	   //列表初始化
	   random = new Random();
	   for (RandomBean f : funs)
	     count += f.getWeight();
	 }
	 public static RandomBean getRandom() {
	      int index_ran = -1;
	      while(index_ran == -1 || index_ran==index) {
	    	  index_ran = searchIndex(random.nextInt(count) + 1);
	      }
	      index=index_ran;
	      return funs.get(index);
	 }
    /**
     * 根据总权重 和 积分寻找下标
     * @return 目标对象下标
     */
    private static int searchIndex(int a) {
        int temp = a;
        int i;
        for (i = 0; i < funs.size(); i++) {
            temp -= funs.get(i).getWeight();
            if (temp <= 0)
                break;
        }
        return i;
    }

}
