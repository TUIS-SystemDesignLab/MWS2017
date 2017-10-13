package utils;

import java.io.PrintWriter;

import org.apache.lucene.search.spell.LevensteinDistance;

public class SimilarityRate extends Thread{

	public SimilarityRate() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public static double getSimilarityRateLD(String str1, String str2) throws Exception{
		//logfile
		PrintWriter log = utils.Util.openFile(utils.Util.combinePath(".", "logs", "EuclidianCalculation_Logs.txt"));

		SimilarityRate self = new SimilarityRate();
		int maxLength = 0;
		double similarity = 0.0;
		if(str1.isEmpty()) {
			utils.Util.writeText(log, str1 + "が空です");
			return -1.0d;
		}
		if(str2.isEmpty()) {
			utils.Util.writeText(log, str2 + "が空です");
			return -2.0d;
		}

		maxLength = utils.Util.getMaxLength(str1, str2);
		if(maxLength < 0) {
			return -3.0d;
		}

		similarity = self.getNormalizedLevensteinDistanceSimilarityRate(str1, str2, maxLength);
		if(similarity > 1 || similarity < 0) {
			return -4.0d;
		}

		utils.Util.closeFile(log);

		return similarity;
	}

	//private
	private double getNormalizedLevensteinDistanceSimilarityRate(String str1, String str2, int maxLength) throws Exception{
		//logfile
		PrintWriter log = utils.Util.openFile(utils.Util.combinePath(".", "logs", "EuclidianCalculation_Logs.txt"));

		double similarity = 0.0;
		double levensteinDistance = 0.0;
		LevensteinDistance levensteinAlgorithm = new LevensteinDistance();
		levensteinDistance = levensteinAlgorithm.getDistance(str1, str2);
		similarity = (100 - ((levensteinDistance / maxLength) * 100)) / 100;
		utils.Util.writeText(log, similarity + "計算完了");
		utils.Util.closeFile(log);
		return similarity;
	}

}
