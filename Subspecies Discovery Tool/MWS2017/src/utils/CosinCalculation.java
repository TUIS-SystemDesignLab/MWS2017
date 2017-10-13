package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class CosinCalculation extends Thread {

	public static Double cosin;

	// 2ファイルのコサイン類似度の計算
	public static void cos(String a, String b) throws Exception{
		//logfile
		PrintWriter log = utils.Util.openFile(utils.Util.combinePath(".", "logs", "CosinCalculation_Logs.txt"));

		try {
			// cosの初期化
			cosin = null;

			// ファイルの読み込み
			File FILEa = new File(a);
			File FILEb = new File(b);
			BufferedReader fileA = new BufferedReader(new FileReader(FILEa));
			BufferedReader fileB = new BufferedReader(new FileReader(FILEb));

			// String変換
			String filea = fileA.readLine();
			utils.Util.writeText(log, "String" + filea + "読み込み");
			String fileb = fileB.readLine();
			utils.Util.writeText(log, "String" + fileb + "読み込み");
			fileA.close();
			fileB.close();
			String[] base = filea.split(" ", 0);
			utils.Util.writeText(log, filea + "分割");
			String[] second = fileb.split(" ", 0);
			utils.Util.writeText(log, fileb + "分割");

			// ファイルチェック
			if (base.length != second.length) {
				System.out.println("ファイルが壊れています。続けますか？");
				System.out.println("理由:ファイルに含まれる数値の長さが異なるため。");
				utils.Util.writeText(log, "ファイルエラー: ファイル破損");
				BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
				while (true) {
					System.out.println("続ける場合はyesを、終了する場合はnoを入力してください。");
					if (buf.readLine() == "yes") {
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

			// コサイン類似度の計算
			Double sumbase = 0.0;
			Double sumsecond = 0.0;
			Double basesecond = 0.0;
			for (int i = 0; i < base.length; i++) {
				Double baseSquare = Math.pow(Double.parseDouble(base[i]), 2);
				sumbase += baseSquare;
				Double secondSquare = Math.pow(Double.parseDouble(second[i]), 2);
				sumsecond += secondSquare;
				basesecond += Double.parseDouble(base[i]) * Double.parseDouble(second[i]);
			}
			utils.Util.writeText(log, "コサイン類似度の計算終了");
			Double basesqrt = Math.sqrt(sumbase);
			Double secondsqr = Math.sqrt(sumsecond);
			cosin = basesecond / (basesqrt * secondsqr);
			utils.Util.writeText(log, "cosin計算完了");

		} catch (IOException e) {
			System.out.println("cosメソッドエラー：IOException");
			System.out.println(utils.Util.writeText(log, e.toString()));
		} catch (NullPointerException e) {
			System.out.println("cosメソッドエラー：NullPointerException");
			System.out.println(utils.Util.writeText(log, e.toString()));
		} finally {
			utils.Util.closeFile(log);
		}
	}

}
