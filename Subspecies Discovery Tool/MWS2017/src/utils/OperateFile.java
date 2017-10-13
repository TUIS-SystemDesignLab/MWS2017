package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class OperateFile extends Thread{

	// 改行コード
	static final String LINESEP = System.lineSeparator();

	// 自身のファイルパス
	private File file;

	// デフォルトコンストラクタ
	public OperateFile() throws Exception{
		//logfile
		PrintWriter log = utils.Util.openFile(utils.Util.combinePath(".", "logs", "OperateFile_Logs.txt"));
		utils.Util.writeText(log, "デフォルトコンストラクタ生成");
		utils.Util.closeFile(log);
	}

	// コンストラクタ: 引数はファイルのパス
	public OperateFile(File file) throws Exception{
		//logfile
		PrintWriter log = utils.Util.openFile(utils.Util.combinePath(".", "logs", "OperateFile_Logs.txt"));
		utils.Util.writeText(log, "コンストラクタ生成 引数: " + file);
		this.file = file;
		utils.Util.closeFile(log);
	}

	// csv形式で書かれたファイルを読み込んでdouble多重配列に格納してreturnするメソッド
	public double[][] returnValueTable() throws Exception {
		//logfile
		PrintWriter log = utils.Util.openFile(utils.Util.combinePath(".", "logs", "OperateFile_Logs.txt"));

		BufferedReader br = new BufferedReader(new FileReader(file));
		int linecount = this.countLine();
		utils.Util.writeText(log, "行数カウント終了");
		double[][] resulttable = new double[linecount][linecount];

		try {
			// 配列の現在位置(ファイル番号を表す)
			int nowline = 0;

			String line = br.readLine();
			// 1ループごとに類似度を表すdouble配列を作成してdouble配列を格納する配列に格納する
			while (line != null) {
				String[] separatedvalues = line.split(" ", 0);

				for (int i = 0; i < separatedvalues.length; i++) {
					resulttable[nowline][i] = Double.parseDouble(separatedvalues[i]);
					utils.Util.writeText(log, "resulttable[" + nowline + "][" + i + "] 格納");
				}

				nowline++;
				line = br.readLine();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			br.close();
			utils.Util.closeFile(log);
		}
		return resulttable;
	}

	// ファイル全体をテキストとして読み込んで、行ごとに配列に格納したString配列をreturnする
	public String[] readText() throws Exception {
		//logfile
		PrintWriter log = utils.Util.openFile(utils.Util.combinePath(".", "logs", "OperateFile_Logs.txt"));

		String[] resultlist =utils.Reader.readline(file);
		utils.Util.writeText(log, file.toString() + "読み込み");
		utils.Util.closeFile(log);
		return resultlist;
	}

	// ファイルの行数を取得してintでreturnするメソッド
	int countLine() throws Exception {
		//logfile
		PrintWriter log = utils.Util.openFile(utils.Util.combinePath(".", "logs", "OperateFile_Logs.txt"));

		BufferedReader br = new BufferedReader(new FileReader(file));
		int linecount = 0;

		try {
			String line = br.readLine();

			while (line != null) {
				linecount++;
				line = br.readLine();
				utils.Util.writeText(log, linecount + "読み込み");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(utils.Util.writeText(log, e.toString()));
		} finally {
			utils.Util.closeFile(log);
			br.close();
		}
		return linecount;
	}

	// ファイル名を格納したString配列の中身をファイルに書き込むメソッド
	public void writeFilenames(String[] similarfiles, File distpath) throws Exception {
		//logfile
		PrintWriter log = utils.Util.openFile(utils.Util.combinePath(".", "logs", "OperateFile_Logs.txt"));

		FileWriter fw = new FileWriter(distpath);

		try {
			for (String s : similarfiles) {
				// ファイルの中身がnullでなければ(0.74以下でなければ)書き込む
				if (s != null) {
					fw.write(s + LINESEP);
					utils.Util.writeText(log, s + "書き込み");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(utils.Util.writeText(log, e.toString()));
		} finally {
			utils.Util.closeFile(log);
			fw.close();
		}
	}
	//フォルダ以下をすべて削除(再帰的にファイルのみを削除ではないので注意！！)
	public static void recursiveDelete(File dir ) throws Exception{
		//logfile
		PrintWriter log = utils.Util.openFile(utils.Util.combinePath(".", "logs", "OperateFile_Logs.txt"));

		File[] files = dir.listFiles();
		//削除処理
		for(File f: files) {
			f.delete();
			utils.Util.writeText(log, f + "削除");
		}
		utils.Util.closeFile(log);
	}
}
