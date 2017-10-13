package utils;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

//target : 検索対象
//base : 格納対象
public class CheckAPI extends Thread {
	public static void checker(File Dir) throws Exception{
		//logfile
		PrintWriter log = utils.Util.openFile(utils.Util.combinePath(".", "logs", "CheckAPI_Logs.txt"));

		// ファイル作成
		File file = utils.Util.combinePath(".", "base", "baseapi.txt");
		utils.Writer.creater(file);
		utils.Util.writeText(log, "ファイル作成");

		// すべての行をまとめる
		File[] apitargetfiles = utils.Reader.readfolder(Dir);
		List<String> stringdata = new ArrayList<String>();
		for (int i = 0; i < apitargetfiles.length; i++) {
			String[] target = utils.Reader.readline(apitargetfiles[i]);
			for (int j = 0; j < target.length; j++) {
				stringdata.add(target[j]);
				utils.Util.writeText(log, "行まとめ" + j + "行目");
			}
		}
		List<String> hashset = new ArrayList<String>(new HashSet<>(stringdata));
		for (int i = 0; i < hashset.size(); i++) {
			utils.Writer.writeline(file, hashset.get(i));
			utils.Util.writeText(log, file + "追記完了");
		}

		utils.Util.closeFile(log);
	}
}
