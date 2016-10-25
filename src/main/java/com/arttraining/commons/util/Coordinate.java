package com.arttraining.commons.util;

/**
 * @description 坐标相关工具类
 */
public class Coordinate {
	private final static double PI = 3.14159265358979323; // 圆周率
	private final static double R = 6371229; // 地球的半径
	
	/**
	 * @description 求两个坐标之间的距离
	 */
	public static double getDistance(double[] loc1, double[] loc2) {
		double lon1=loc1[0];
		double lat1=loc1[1];
		double lon2=loc2[0];
		double lat2=loc2[1];
        double x, y, distance;
        x = (lon2 - lon1) * PI * R
                * Math.cos(((lat1 + lat2) / 2) * PI / 180) / 180;
        y = (lat2 - lat1) * PI * R / 180;
        distance = Math.hypot(x, y);
        return distance;
    }
	
	public static void main(String[] args) {
		double[] gis1 = {113.228708,23.428109};
		double[] gis2 = {114.228708,23.428109};
		System.out.println( getDistance(gis1,gis2));
	}
}
