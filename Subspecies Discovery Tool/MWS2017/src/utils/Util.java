package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util extends Thread{
	//アドレス区切り記号
	private static String FILESEP = File.separator;

	// public関数-Util-
	public static int getMaxLength(String str1, String str2) throws Exception{
		//logfile
		PrintWriter log = utils.Util.openFile(utils.Util.combinePath(".", "logs", "Util_Logs.txt"));

		int maxLength = 0;
		if (str1.isEmpty()) {
			utils.Util.writeText(log, str1 + "が空です");
			return -1;
		}
		if (str2.isEmpty()) {
			utils.Util.writeText(log, str2 + "が空です");
			return -2;
		}

		if (str1.length() >= str2.length()) {
			maxLength = str1.length();
		} else {
			maxLength = str2.length();
		}
		utils.Util.closeFile(log);
		return maxLength;
	}

	public static String getCurrentDateTime() {
		// Dateクラスで、現在日時を取得
		Date date = new Date();

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String datetime = format.format(date);
		return datetime;
	}

	public static String getCurrentTime() {
		// Dateクラスで、現在日時を取得
		Date date = new Date();

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String datetime = format.format(date);
		return datetime;
	}

	public static PrintWriter openFile(File file) throws Exception{
		PrintWriter filewriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
			new FileOutputStream(file, true), "SHIFT-JIS")));
		return filewriter;
	}

	public static void closeFile(PrintWriter file) {
		file.close();
	}

	public static String writeText(PrintWriter distpath, String message) {
		String writemessage = getCurrentDateTime() + " : " + message;

		distpath.write(writemessage);
		return message;
	}

	public static String time(long time){
		long mill=0;
		long sec=0;
		long min=0;
		long hour=0;
		long day=0;
		if(time>1000){
			sec=time/1000;
			mill=time%1000;
		}
		if(sec>60){
			min=sec/60;
			sec=sec%60;
		}
		if(min>60){
			hour=min/60;
			min=min%60;
		}
		if(hour>24){
			day=hour/60;
			hour=hour%60;
		}

		return "経過時間："+day+"日 "+hour+"時間 "+min+"分 "+sec+"秒 "+mill+"ミリ";
	}

	public static File combinePath(String... paths) {
		String returnpath = String.join(FILESEP, paths);
		File file = new File(returnpath);
		return file;
	}

}
