package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Search extends Thread{

	// ベクトル化
	public static void search(String filePath, String[] api) throws Exception{
		//logfile
		PrintWriter log = utils.Util.openFile(utils.Util.combinePath(".", "logs", "Search_Logs.txt"));

		try {

			// ファイル作成
			File file = utils.Util.combinePath(".", "vectordata", filePath);

			// predataの最初の行を読み込む
			BufferedReader filereader = new BufferedReader(new FileReader(
					utils.Util.combinePath(".", "predata", filePath)));
			String searchdata = filereader.readLine();
			utils.Util.writeText(log, searchdata + "読み込み");

			// predata配列格納
			ArrayList<String> predata = new ArrayList<String>();
			while (searchdata != null) {
				predata.add(searchdata);
				utils.Util.writeText(log, searchdata + "追加");
				searchdata = filereader.readLine();
			}

			// 検索機構
			for (int i = 0; i < api.length; i++) {
				int counter = 0;
				for (int j = 0; j < predata.size(); j++) {
					if (predata.get(j).matches(".*" + api[i] + ".*")) {
						utils.Util.writeText(log, api[i] + "マッチ");
						counter++;
					}
				}
				utils.Writer.write(file,counter + " ");
			}
			filereader.close();
		} catch (IOException e) {
			System.out.println("search method error:IOExeption");
			System.out.println(utils.Util.writeText(log, e.toString()));
		} finally {
			utils.Util.closeFile(log);
		}
	}
}
