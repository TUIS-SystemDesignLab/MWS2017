//読み込みに関するクラス
package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Reader extends Thread {

	// このプログラムはファイルを一行ずつ行を読み、配列に格納する。
	//input File型ファイルパス
	//output String型配列　中身は各行の文字列
	public static String[] readline(File filePath) throws Exception{
		//logfile
		PrintWriter log = utils.Util.openFile(utils.Util.combinePath(".", "logs", "Reader_Logs.txt"));

		String[] api=null;
		try {
			BufferedReader readfile = new BufferedReader(new FileReader(filePath));
			String str = "";
			List<String> API = new ArrayList<String>();
			while ((str = readfile.readLine()) != null) {
				API.add(str);
				utils.Util.writeText(log, str + "ArrayList格納");
			}
			api = new String[API.size()];

			api = (String[]) API.toArray(new String[0]);


			readfile.close();


		} catch (IOException e) {
			System.out.println("Error point:FileReader.readline");
			System.out.println(utils.Util.writeText(log, e.toString()));
		} finally {
			utils.Util.closeFile(log);
		}
		return api;
	}

	//このプログラムはフォルダを読み込み、指定フォルダ内のファイルを配列に格納する。
	//input File型ディレクトリパス
	//output File型配列　中身はファイルパス
	public static File[] readfolder(File Dir){
		File FilePath[] = Dir.listFiles();
		return FilePath;
	}
}
