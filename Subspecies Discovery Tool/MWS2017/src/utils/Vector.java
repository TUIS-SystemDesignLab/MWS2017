//このクラスはBag of Wordsの考え方に基づき、predataをベクトル化するクラスです。
//input File型ファイルパス
//output なし　書き出し

package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Vector extends Thread {
	public static void search(String filePath, String[] api) throws Exception{
		//logfile
		PrintWriter log = utils.Util.openFile(utils.Util.combinePath(".", "logs", "EuclidianCalculation_Logs.txt"));

		try {

			// ファイル作成
			String Data = filePath;
			File file = utils.Util.combinePath(".", "vectordata", Data);
			PrintWriter fw = new PrintWriter(
					new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "Shift-JIS")));

			// predataの最初の行を読み込む
			BufferedReader filereader = new BufferedReader(new FileReader(
					utils.Util.combinePath(".", "predata", filePath)));
			String searchdata = filereader.readLine();
			//utils.Util.writeText(log, searchdata + "読み込み");

			// predata配列格納
			ArrayList<String> predata = new ArrayList<String>();
			while (searchdata != null) {
				predata.add(searchdata);
				searchdata = filereader.readLine();
				//utils.Util.writeText(log, searchdata + "読み込み");
			}

			int counter = 0;
			// 検索機構
			for (int i = 0; i < api.length; i++) {
				counter = 0;
				for (int j = 0; j < predata.size(); j++) {
					if (predata.get(j).matches(".*" + api[i] + ".*")) {
						//utils.Util.writeText(log, api[i] + "カウントアップ");
						counter++;
					}
				}
				fw.print(counter + " ");
			}
			filereader.close();
			fw.close();
		} catch (IOException e) {
			System.out.println("search method error:IOExeption");
			System.out.println(utils.Util.writeText(log, e.toString()));
		} finally {
			utils.Util.closeFile(log);
		}
	}
}
