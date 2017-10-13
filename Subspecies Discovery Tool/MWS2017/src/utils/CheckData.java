package utils;

import java.io.PrintWriter;

public class CheckData extends Thread{

	private double[][] cos_resultlist;
	private double[][] euc_resultlist;
	private double[][] fuzzyhash_resultlist;
	//private double[][] judgement_resultlist;
	private double[][] jaro_resultlist;
	private String[] filelist;

	// コンストラクタ: 引数はファイル名のリスト, 類似度を格納したテーブル
	//public CheckData(String[] filelist, double[][] cos_resultlist, double[][] euc_resultlist, double[][] fuzzyhash_resultlist, double[][] judgement_resultlist) throws Exception{
	public CheckData(String[] filelist, double[][] cos_resultlist, double[][] euc_resultlist, double[][] fuzzyhash_resultlist, double[][] jaro_resultlist) throws Exception{
		//logfile
		PrintWriter log = utils.Util.openFile(utils.Util.combinePath(".", "logs", "CheckData_Logs.txt"));

		this.cos_resultlist = cos_resultlist;
		this.euc_resultlist = euc_resultlist;
		this.fuzzyhash_resultlist = fuzzyhash_resultlist;
		//this.judgement_resultlist = judgement_resultlist;
		this.jaro_resultlist = jaro_resultlist;
		this.filelist = filelist;
		utils.Util.writeText(log, "オブジェクト生成");

		utils.Util.closeFile(log);
	}

	// ユークリッド距離
	//値が0.75以上になっているファイルの名前をString型でreturnするメソッド
	public String[][] checkEucSimilarity() throws Exception{
		//logfile
		PrintWriter log = utils.Util.openFile(utils.Util.combinePath(".", "logs", "CheckData_Logs.txt"));

		String[][] filenames = new String[filelist.length][filelist.length];

		// テーブルの場所を指す変数
		int count_h = 0;
		int count_v = 0;

		// テーブル内の探索
		for (double[] d : euc_resultlist) {
			for (double dd : d) {
				if(dd >= 0.75) {
					filenames[count_v][count_h] = filelist[count_h];
					count_h++;
					utils.Util.writeText(log, "filenames[" + count_v + "][" + count_h + "] 格納");
				}
			}
			count_h = 0;
			count_v++;
		}
		utils.Util.closeFile(log);
		return filenames;
	}

	// コサイン類似度
	//値が0.75以上になっているファイルの名前をString型でreturnするメソッド
	public String[][] checkCosSimilarity() throws Exception{
		//logfile
		PrintWriter log = utils.Util.openFile(utils.Util.combinePath(".", "logs", "CheckData_Logs.txt"));

		String[][] filenames = new String[filelist.length][filelist.length];

		// テーブルの場所を指す変数
		int count_h = 0;
		int count_v = 0;

		// テーブル内の探索
		for (double[] d : cos_resultlist) {
			for (double dd : d) {
				if (dd >= 0.7) {
					filenames[count_v][count_h] = filelist[count_h];
					utils.Util.writeText(log, "filenames[" + count_v + "][" + count_h + "] 格納");
				}
				count_h++;
			}
			count_h = 0;
			count_v++;
		}
		utils.Util.closeFile(log);
		return filenames;
	}

	// fuzzyhash
	//値が0.75以上になっているファイルの名前をString型でreturnするメソッド
	public String[][] checkfuzzyhashSimilarity() throws Exception{
	//	//logfile
		PrintWriter log = utils.Util.openFile(utils.Util.combinePath(".", "logs", "CheckData_Logs.txt"));

		String[][] filenames = new String[filelist.length][filelist.length];

	// テーブルの場所を指す変数
		int count_h = 0;
	int count_v = 0;

	/// テーブル内の探索
		for (double[] d : fuzzyhash_resultlist) {
			for (double dd : d) {
				if(dd >= 0.75) {
					filenames[count_v][count_h] = filelist[count_h];
					count_h++;
					utils.Util.writeText(log, "filenames[" + count_v + "][" + count_h + "] 格納");
				}
			}
			count_h = 0;
			count_v++;
		}
		utils.Util.closeFile(log);
		return filenames;
	}

	// 総合判断
	//値が0.75以上になっているファイルの名前をString型でreturnするメソッド
	//public String[][] checkJudgementSimilarity() throws Exception{
	//	//logfile
	//	PrintWriter log = utils.Util.openFile(utils.Util.combinePath(".", "logs", "CheckData_Logs.txt"));

	//	String[][] filenames = new String[filelist.length][filelist.length];

	//	// テーブルの場所を指す変数
	//	int count_h = 0;
	//	int count_v = 0;

	//	// テーブル内の探索
	//	for (double[] d : judgement_resultlist) {
	//		for (double dd : d) {
	//			if(dd >= 0.5) {
	//				filenames[count_v][count_h] = filelist[count_h];
	//				count_h++;
	//				utils.Util.writeText(log, "filenames[" + count_v + "][" + count_h + "] 格納");
	//			}
	//		}
	//		count_h = 0;
	//		count_v++;
	//	}
	//	utils.Util.closeFile(log);
	//	return filenames;
	//}

	// ジャロ・ウィンクラー
	//値が0.75以上になっているファイルの名前をString型でreturnするメソッド
	public String[][] checkJaroSimilarity() throws Exception{
		//logfile
		PrintWriter log = utils.Util.openFile(utils.Util.combinePath(".", "logs", "CheckData_Logs.txt"));

		String[][] filenames = new String[filelist.length][filelist.length];

		// テーブルの場所を指す変数
		int count_h = 0;
		int count_v = 0;

		// テーブル内の探索s
		for (double[] d : jaro_resultlist) {
			for (double dd : d) {
				if(dd >= 0.6) {
					filenames[count_v][count_h] = filelist[count_h];
					count_h++;
					utils.Util.writeText(log, "filenames[" + count_v + "][" + count_h + "] 格納");
				}
			}
			count_h = 0;
			count_v++;
		}
		utils.Util.closeFile(log);
		return filenames;
	}
}
