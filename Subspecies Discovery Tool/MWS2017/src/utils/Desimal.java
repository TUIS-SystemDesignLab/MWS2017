package utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

//少数の桁数を操作します。
//input Double型の値,int型の返してほしい桁数
//output 指定桁数で切り落とした値
public class Desimal extends Thread{
	public static BigDecimal decimal(Double a ,int b) {
		BigDecimal dec1 = new BigDecimal(a);
		BigDecimal dec2 = dec1.setScale(b, RoundingMode.DOWN);
		return dec2;
	}

}
