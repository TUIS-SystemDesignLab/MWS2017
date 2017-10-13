//APIの抽出に関するクラス
// Apache License, Version 2.0のライセンスで配布されている成果物を含んでいます。http://www.apache.org/licenses/LICENSE-2.0

package utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExtractionAPI extends Thread{

	public static void extraction(File FilePath) throws Exception{
		//logfile
		PrintWriter log = utils.Util.openFile(utils.Util.combinePath(".", "logs", "ExtractionAPI_Logs.txt"));

		try {
			String dataset = FilePath.getPath();
			utils.Util.writeText(log, dataset + "取得");
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(new File(dataset));
			// ファイルの記入
			File writefile = utils.Util.combinePath(".", "predata", FilePath.getName());
			String member_name = "";

			// Jsonを掘る
			for (JsonNode a : root.get("behavior").get("processes")) {
				for (JsonNode b : a.get("calls")) {
					member_name = b.get("api").asText();

					//utils.Util.writeLog(member_name + "取得");

					utils.Writer.writeline(writefile,member_name);

					//utils.Util.writeLog(member_name + "書き込み");
					// ループ終了条件
					if (member_name == null) {
						System.out.println("break");
						break;
					}
				}
			}
		} catch (JsonProcessingException e) {
			System.out.println("extension method error:JsonProcessionException");
			System.out.println(utils.Util.writeText(log, e.toString()));
		} catch (IOException e) {
			System.out.println("extentions method error:IOException");
			System.out.println(utils.Util.writeText(log, e.toString()));
		} finally {
			utils.Util.closeFile(log);
		}
	}

	//ハッシュの抜き出し
	public static void extractHash(File FilePath) throws Exception{
		//logfile
		PrintWriter log = utils.Util.openFile(utils.Util.combinePath(".", "logs", "ExtractionAPI_Logs.txt"));

		try {
			String dataset = FilePath.getPath();
			utils.Util.writeText(log, dataset + "取得");
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(new File(dataset));
			// ファイルの記入
			File writefile = utils.Util.combinePath(".", "hashdata", FilePath.getName());
			String[] hashlist = new String[4];

			// Jsonを掘る
			JsonNode a = root.get("target").get("file");
			hashlist[0]= a.get("md5").asText();
			hashlist[1]= a.get("sha1").asText();
			hashlist[2]= a.get("sha256").asText();
			hashlist[3]= a.get("sha512").asText();

			for(String s: hashlist) {
				utils.Writer.writeline(writefile,s);
			}

		} catch (JsonProcessingException e) {
			System.out.println("extension method error:JsonProcessionException");
			System.out.println(utils.Util.writeText(log, e.toString()));
		} catch (IOException e) {
			System.out.println("extentions method error:IOException");
			System.out.println(utils.Util.writeText(log, e.toString()));
		} finally {
			utils.Util.closeFile(log);
		}
	}
	public static String[] makeAPIList(File FilePath)throws Exception{
		//logfile
		PrintWriter log = utils.Util.openFile(utils.Util.combinePath(".", "logs", "ExtractionAPI_Logs.txt"));

		utils.Util.writeText(log, FilePath + "読み込み");
		utils.Util.closeFile(log);
		return Reader.readline(FilePath);
	}
}