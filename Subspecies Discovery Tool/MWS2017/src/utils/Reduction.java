//このクラスは何か取り除くときに使うクラスです。
package utils;

import java.io.PrintWriter;

public class Reduction extends Thread{
	// このプログラムはファイル名から拡張子を取り除くプログラムです。
	// input String型ファイル名 例: ReadMe.txt
	// output Sting型ファイル名 例: ReadMe
	public static String extention(String FileName) throws Exception{
		//logfile
		PrintWriter log = utils.Util.openFile(utils.Util.combinePath(".", "logs", "EuclidianCalculation_Logs.txt"));

		String[] truename=FileName.split(".", 0);
		utils.Util.writeText(log, FileName + "分割");
		String name="";
		for(int i=0; i<truename.length; i++){
			name+=truename[i];
			utils.Util.writeText(log, name + "追加");
		}

		utils.Util.closeFile(log);

		return name;
	}
}
