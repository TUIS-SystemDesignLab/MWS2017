package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class EuclidianCalculation extends Thread{
	//ユークリッド距離の結果用ファイルの作成
	public static File euclidianresultfile(){
		File euclidresultfile = utils.Util.combinePath(".", "euclid", "euclideandistance.txt");
		utils.Writer.write(euclidresultfile, "");
		return euclidresultfile;
	}

	// ユークリッド距離の計算
	public static double euclidean(String basefile, String targetfile) throws Exception{
		//logfile
		PrintWriter log = utils.Util.openFile(utils.Util.combinePath(".", "logs", "EuclidianCalculation_Logs.txt"));

		double euclid = 0;
		try {
			// ファイルの読み込み
			File FILEa = new File(basefile);
			utils.Util.writeText(log, "String" + FILEa+ "読み込み");
			File FILEb = new File(targetfile);
			utils.Util.writeText(log, "String" + FILEb + "読み込み");
			BufferedReader fileA = new BufferedReader(new FileReader(FILEa));
			BufferedReader fileB = new BufferedReader(new FileReader(FILEb));

			// String変換
			String filea = fileA.readLine();
			utils.Util.writeText(log, filea + "分割");
			String fileb = fileB.readLine();
			utils.Util.writeText(log, fileb + "分割");
			fileA.close();
			fileB.close();
			String[] base = filea.split(" ", 0);
			String[] second = fileb.split(" ", 0);
			int sum = 0;

			// ファイルチェック
			if (base.length != second.length) {
				System.out.println("ファイルが壊れている可能性があります。続けますか？");
				System.out.println("理由:2ファイルに含まれる数値の長さが異なるため。");
				utils.Util.writeText(log, "ファイルエラー: ファイル破損");
				BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
				while (true) {
					System.out.println("続ける場合はyesを、終了する場合はnoを入力してください。");
					if (buf.readLine() == "yes") {
						System.out.println("続けて行いますが、正常な結果は出ません");
						utils.Util.writeText(log, "yes");
						break;
					} else if (buf.readLine() == "no") {
						utils.Util.writeText(log, "no");
						System.out.println("終了します。");
						System.exit(0);
					} else {
						System.out.println(" yes か no で答えて下さい。");
						utils.Util.writeText(log, "その他の入力");
						continue;
					}
				}
			}
			// 計算
			for (int i = 0; i < base.length; i++) {
				sum += (Integer.parseInt(base[i]) - Integer.parseInt(second[i]))
						* (Integer.parseInt(base[i]) - Integer.parseInt(second[i]));

			}
			euclid = Math.sqrt(sum);
		} catch (IOException e) {
			System.out.println("euclidean Method error:IOException");
			System.out.println(utils.Util.writeText(log, e.toString()));
		} finally {
			utils.Util.closeFile(log);
		}
		euclid = 1/(1+euclid);
		return euclid;

	}
}
