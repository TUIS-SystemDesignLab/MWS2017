package test;

import java.io.File;
import java.io.PrintWriter;

import utils.CheckData;
import utils.JaroWinkler;
import utils.OperateFile;

public class Main extends Thread {

	public static void main(String[] args) throws Exception {
		// ログファイル用
		PrintWriter log = utils.Util.openFile(utils.Util.combinePath(".", "logs", "Logs.txt"));

		try {

			// 時間計算用変数
			long totaltime_start = System.currentTimeMillis();
			long start = 0;

			// メソッド実行フラグ
			boolean flag_initialize = true;
			boolean flag_createAPIList = true;
			boolean flag_createFileList = true;
			boolean flag_writeFileNames = true;
			boolean flag_extractAPIs = true;
			boolean flag_convertPredataToAPIList = true; // BadAPIListが存在する場合は不要
			boolean flag_jaccard = true; // Jaccard距離の計算
			boolean flag_createVector = true; // vectordataが存在する場合は不要
			boolean flag_hashSimilarity = true;
			boolean flag_calculateCosin = true;
			boolean flag_calculateEuclid = true;
			boolean flag_checkCosinandEuclid = true;
			boolean flag_writeFiles = true;
			boolean flag_jarowin = true;

			// 0~3
			// md5, sha1, sha256, sha512
			final int HASHFUNC = 0;

			// -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
			// フォルダの初期化(不要データの削除処理)
			if (flag_initialize) {

				start = System.currentTimeMillis();
				// このメソッドを呼び出すときはパス指定を絶対間違えないように！！！
				if (flag_calculateCosin) {
					OperateFile.recursiveDelete(utils.Util.combinePath(".", "cosin"));
				}

				if (flag_calculateEuclid) {
					OperateFile.recursiveDelete(utils.Util.combinePath(".", "euclid"));
				}
				if (flag_createFileList) {
					OperateFile.recursiveDelete(utils.Util.combinePath(".", "filelist"));
				}
				if (flag_writeFiles) {
					OperateFile.recursiveDelete(utils.Util.combinePath(".", "group", "cos"));
					OperateFile.recursiveDelete(utils.Util.combinePath(".", "group", "euc"));
					OperateFile.recursiveDelete(utils.Util.combinePath(".", "group", "fuzzyhash"));
					OperateFile.recursiveDelete(utils.Util.combinePath(".", "group", "judgement"));
					OperateFile.recursiveDelete(utils.Util.combinePath(".", "group", "jac"));
				}
				if (flag_hashSimilarity) {
					OperateFile.recursiveDelete(utils.Util.combinePath(".", "hash"));
				}
				if (flag_extractAPIs) {
					OperateFile.recursiveDelete(utils.Util.combinePath(".", "predata"));
				}
				if (flag_createVector) {
					OperateFile.recursiveDelete(utils.Util.combinePath(".", "vectordata"));
				}
				if (flag_jarowin) {
					OperateFile.recursiveDelete(utils.Util.combinePath(".", "jarowinkler"));
				}
				if (flag_hashSimilarity) {
					OperateFile.recursiveDelete(utils.Util.combinePath(".", "hashdata"));
				}
				if (flag_hashSimilarity) {
					OperateFile.recursiveDelete(utils.Util.combinePath(".", "jarowinkler"));
				}
				if (flag_jaccard) {
					OperateFile.recursiveDelete(utils.Util.combinePath(".", "jaccard"));
				}
				OperateFile.recursiveDelete(utils.Util.combinePath(".", "logs")); // 標準で消されます。必要な場合は必ずコピーを保存してください。
				OperateFile.recursiveDelete(utils.Util.combinePath(".", "judgement"));// 現在制作中の総合判断機能です。作成中のため削除されます。

				// こんな感じで書くと時刻とメッセージを./logs/Logs.txtに追記します
				utils.Util.writeText(log, "- process start -");

				long extime_init = System.currentTimeMillis() - start;
				System.out.println(utils.Util.writeText(log, "initialize: " + utils.Util.time(extime_init)));
			}

			// -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
			// ファイルを読み込みAPIリストを作成する。
			File filename;
			String[] apilist = null;

			if (flag_createAPIList) {
				start = System.currentTimeMillis();
				filename = utils.Util.combinePath(".", "basedata", "BadAPIList.csv");
				apilist = utils.ExtractionAPI.makeAPIList(filename);
				long extime_createAPIList = System.currentTimeMillis() - start;
				System.out
						.println(utils.Util.writeText(log, "create APIList: " + utils.Util.time(extime_createAPIList)));
			}
			// -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
			// ディレクトリを読み込みファイルリストを作成する。
			File foldername;
			File[] filelist = null;

			if (flag_createFileList) {
				start = System.currentTimeMillis();
				foldername = utils.Util.combinePath(".", "basedata", "3-2files");
				filelist = utils.Reader.readfolder(foldername);
				long extime_createFileList = System.currentTimeMillis() - start;
				System.out.println(
						utils.Util.writeText(log, "create FileList: " + utils.Util.time(extime_createFileList)));
			}

			// -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
			// ファイル名一覧を書き込む
			File filelistname;
			if (flag_writeFileNames) {
				start = System.currentTimeMillis();
				filelistname = utils.Util.combinePath(".", "filelist", "filelist.txt");
				utils.Writer.creater(filelistname);
				for (int i = 0; i < filelist.length; i++) {
					String truename = filelist[i].getName();
					utils.Writer.writeline(filelistname, truename);
				}
				long extime_writeFileNames = System.currentTimeMillis() - start;
				System.out.println(
						utils.Util.writeText(log, "write FileNames: " + utils.Util.time(extime_writeFileNames)));
			}

			// -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
			// jsonファイルからAPIのみを抽出する。
			if (flag_extractAPIs) {
				start = System.currentTimeMillis();
				for (int i = 0; i < filelist.length; i++) {
					utils.ExtractionAPI.extraction(filelist[i]);
					utils.ExtractionAPI.extractHash(filelist[i]);
				}
				long extime_extractAPI = System.currentTimeMillis() - start;
				System.out.println(utils.Util.writeText(log, "extract APIs: " + utils.Util.time(extime_extractAPI)));
			}

			// -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
			// predataに含まれるAPIをリスト化する。
			File prefolder;
			if (flag_convertPredataToAPIList) {
				start = System.currentTimeMillis();
				// baseフォルダに格納される。
				prefolder = utils.Util.combinePath(".", "predata");
				utils.CheckAPI.checker(prefolder);
				long extime_predataToAPIList = System.currentTimeMillis() - start;
				System.out.println(utils.Util.writeText(log,
						"convert predata to APIList: " + utils.Util.time(extime_predataToAPIList)));
			}

			// -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
			// Jaccard
			if (flag_jaccard) {
				start = System.currentTimeMillis();
				File[] files = utils.Reader.readfolder(utils.Util.combinePath(".", "predata"));
				File writingfile = utils.Util.combinePath(".", "jaccard", "jaccardsimiler.txt");
				utils.Writer.creater(writingfile);
				for (int i = 0; i < files.length; i++) {
					for (int j = 0; j < files.length; j++) {
						double jaccard = utils.Jaccard.jaccard(files[i], files[j]);
						utils.Writer.write(writingfile, utils.Desimal.decimal(jaccard, 5).toString() + " ");
					}
					utils.Writer.writeline(writingfile, "");
				}
				long extime_jaccard = System.currentTimeMillis() - start;
				System.out.println(utils.Util.writeText(log, "create jaccard: " + utils.Util.time(extime_jaccard)));
			}

			// -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
			// ジャロ・ウィンクラー距離
			if (flag_jarowin) {
				File jinbi2 = new File(utils.Util.combinePath(".", "jarowinkler", "jarowinkler.txt").toString());
				utils.Writer.creater(jinbi2);
				File[] jaro_exefiles = utils.Reader.readfolder(utils.Util.combinePath(".", "predata"));
				double jaro = 0;
				double[][] jaro_table = new double[jaro_exefiles.length][jaro_exefiles.length];
				String line = "";

				for (int i = 0; i < jaro_exefiles.length; i++) {
					for (int j = 0; j < jaro_exefiles.length; j++) {

						if (i == j) {
							jaro_table[i][j] = 1;
						} else if (i > j) {
							jaro_table[i][j] = jaro_table[j][i];
						} else {
							jaro = JaroWinkler.calcJaroWinkler(utils.Reader.readline(jaro_exefiles[i]),
									utils.Reader.readline(jaro_exefiles[j]));
							jaro_table[i][j] = jaro;
						}

						line += utils.Desimal.decimal(jaro_table[i][j], 5) + " ";
					}
					utils.Writer.writeline(utils.Util.combinePath(".", "jarowinkler", "jarowinkler.txt"), line);
					line = "";
				}
			}
			// -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
			// ベクトル化を行う。
			if (flag_createVector) {
				start = System.currentTimeMillis();
				// vectordataフォルダに格納される。
				for (int i = 0; i < filelist.length; i++) {
					utils.Vector.search(filelist[i].getName(), apilist);
				}
				long extime_createVector = System.currentTimeMillis() - start;
				System.out.println(utils.Util.writeText(log, "create Vector: " + utils.Util.time(extime_createVector)));
			}

			// -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
			// ハッシュ値の類似度を計算する。
			if (flag_hashSimilarity) {
				start = System.currentTimeMillis();
				File junbi = new File(utils.Util.combinePath(".", "hash", "SimilarityRate.txt").toString());
				utils.Writer.creater(junbi);
				File hashsimiller = utils.Util.combinePath(".", "hash", "SimilarityRate.txt");
				File hashfiles[] = utils.Util.combinePath(".", "hashdata").listFiles();
				String hashlist[] = new String[hashfiles.length];

				int count = 0;
				for (File f : hashfiles) {
					hashlist[count] = utils.Reader.readline(f)[HASHFUNC];
					count++;
				}

				double resulttable[][] = new double[hashlist.length][hashlist.length];
				String similarities = "";

				for (int i = 0; i < resulttable.length; i++) {
					for (int j = 0; j < resulttable.length; j++) {
						double similarity = utils.SimilarityRate.getSimilarityRateLD(hashlist[i], hashlist[j]);
						// System.out.print(utils.Desimal.decimal(similarity, 5)
						// + " ");
						similarities += utils.Desimal.decimal(similarity, 5) + " ";
					}
					utils.Writer.writeline(hashsimiller, similarities);
					similarities = "";
				}

				long extime_hashSimilarity = System.currentTimeMillis() - start;
				System.out.println(
						utils.Util.writeText(log, "hash Similarity: " + utils.Util.time(extime_hashSimilarity)));
			}

			// -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
			// コサイン距離の計算
			File file;

			if (flag_calculateCosin) {
				start = System.currentTimeMillis();
				// cosinフォルダに格納される
				file = utils.Util.combinePath(".", "cosin", "cosinsimiler.txt");
				utils.Writer.write(file, "");
				// 計算開始
				Double[][] Cos = new Double[filelist.length][filelist.length];
				for (int i = 0; i < filelist.length; i++) {
					for (int j = 0; j < filelist.length; j++) {
						if (i <= j) {
							utils.CosinCalculation.cos(
									utils.Util.combinePath(".", "vectordata", filelist[i].getName()).toString(),
									utils.Util.combinePath(".", "vectordata", filelist[j].getName()).toString());
							Cos[i][j] = utils.CosinCalculation.cosin;
							utils.Writer.write(file, String.valueOf(utils.Desimal.decimal(Cos[i][j], 5)) + " ");
						} else {
							Cos[i][j] = Cos[j][i];
							utils.Writer.write(file, String.valueOf(utils.Desimal.decimal(Cos[i][j], 5)) + " ");
						}
					}
					utils.Writer.writeline(file, "");
				}
				long extime_calcCosin = System.currentTimeMillis() - start;
				System.out.println(utils.Util.writeText(log, "calculate Cosin: " + utils.Util.time(extime_calcCosin)));
			}

			// -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
			// ユークリッド距離の計算
			File euclidresultfile;

			if (flag_calculateEuclid) {
				start = System.currentTimeMillis();
				euclidresultfile = utils.EuclidianCalculation.euclidianresultfile();
				for (int i = 0; i < filelist.length; i++) {
					for (int j = 0; j < filelist.length; j++) {
						double euclid = utils.EuclidianCalculation.euclidean(
								utils.Util.combinePath(".", "vectordata", filelist[i].getName()).toString(),
								utils.Util.combinePath(".", "vectordata", filelist[j].getName()).toString());
						utils.Writer.write(euclidresultfile, String.valueOf(utils.Desimal.decimal(euclid, 5)) + " ");
					}
					utils.Writer.writeline(euclidresultfile, "");
				}
				long extime_calcEuclid = System.currentTimeMillis() - start;
				System.out
						.println(utils.Util.writeText(log, "calculate Euclid: " + utils.Util.time(extime_calcEuclid)));
			}

			// -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
			// 数値テーブルの読み込み
			String[] filelist2 = null;
			double cos_resulttable[][];
			double euc_resulttable[][];
			double fuzzyhash_resulttable[][];
			// double judgement_resulttable[][];
			double jaro_resulttable[][];
			String cos_similarfiles[][] = null;
			String euc_similarfiles[][] = null;
			String fuzzyhash_similarfiles[][] = null;
			// String judgement_similarfiles[][] = null;
			String jaro_similarfiles[][] = null;

			// utils.Judgement.judge();

			if (flag_checkCosinandEuclid) {
				start = System.currentTimeMillis();
				// - コサイン類似度
				OperateFile file1 = new OperateFile(utils.Util.combinePath(".", "cosin", "cosinsimiler.txt"));
				cos_resulttable = file1.returnValueTable();
				// - ユークリッド距離
				OperateFile file2 = new OperateFile(utils.Util.combinePath(".", "euclid", "euclideandistance.txt"));
				euc_resulttable = file2.returnValueTable();
				//// - fuzzyhash
				OperateFile file3 = new OperateFile(utils.Util.combinePath(".", "hash", "SimilarityRate.txt"));
				fuzzyhash_resulttable = file3.returnValueTable();

				//// - judge :今後正式版を作成予定
				// OperateFile file4 = new
				//// OperateFile(utils.Util.combinePath(".", "judgement",
				//// "judgement.txt"));
				// judgement_resulttable = file4.returnValueTable();

				// - ジャロ・ウィンクラー距離
				OperateFile file4 = new OperateFile(utils.Util.combinePath(".", "jarowinkler", "jarowinkler.txt"));
				jaro_resulttable = file4.returnValueTable();

				// ファイル名リストの読み込み
				OperateFile file5 = new OperateFile(utils.Util.combinePath(".", "filelist", "filelist.txt"));
				filelist2 = file5.readText();

				// コサイン類似度 ユークリッド距離 fuzzyhash 総合
				CheckData cd1 = new CheckData(filelist2, cos_resulttable, euc_resulttable,
						fuzzyhash_resulttable/* , judgement_resulttable */, jaro_resulttable);
				// CheckData cd = new CheckData(filelist2, cos_resulttable,
				// euc_resulttable, jaro_resulttable);
				cos_similarfiles = cd1.checkCosSimilarity();
				euc_similarfiles = cd1.checkEucSimilarity();
				fuzzyhash_similarfiles = cd1.checkfuzzyhashSimilarity();
				jaro_similarfiles = cd1.checkJaroSimilarity();
				// judgement_similarfiles = cd1.checkJudgementSimilarity();
				long extime_checkCosAndEuc = System.currentTimeMillis() - start;
				System.out.println(
						utils.Util.writeText(log, "check Cosin and Euclid: " + utils.Util.time(extime_checkCosAndEuc)));
			}
			// -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
			// 書き出すファイルのオブジェクトを連続で生成してそれぞれがwriteFilenamesする
			if (flag_writeFiles) {
				start = System.currentTimeMillis();
				OperateFile[] objlist = new OperateFile[filelist2.length];
				for (int i = 0; i < filelist2.length; i++) {
					objlist[i] = new OperateFile();
					objlist[i].writeFilenames(cos_similarfiles[i],
							utils.Util.combinePath(".", "group", "cos", filelist2[i]));
					objlist[i].writeFilenames(euc_similarfiles[i],
							utils.Util.combinePath(".", "group", "euc", filelist2[i]));
					objlist[i].writeFilenames(fuzzyhash_similarfiles[i],
							utils.Util.combinePath(".", "group", "fuzzyhash", filelist2[i]));
					// objlist[i].writeFilenames(judgement_similarfiles[i],
					// utils.Util.combinePath(".", "group", "judgement",
					// filelist2[i]));
					objlist[i].writeFilenames(jaro_similarfiles[i],
							utils.Util.combinePath(".", "group", "jaro", filelist2[i]));
				}
				long extime_writeFiles = System.currentTimeMillis() - start;
				System.out.println(utils.Util.writeText(log, "write Files: " + utils.Util.time(extime_writeFiles)));
			}
			// -=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-
			// 処理終了
			long totaltime = System.currentTimeMillis() - totaltime_start;
			System.out.println(utils.Util.writeText(log, "- TotalTime is " + utils.Util.time(totaltime) + " -"));

			System.out.println(utils.Util.writeText(log, "- process completed -"));

		} catch (

		Exception e) {
			System.out.println(e);
			System.out.println("main");
		} finally {
			utils.Util.closeFile(log);
		}
	}
}
