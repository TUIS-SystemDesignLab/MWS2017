package utils;

import java.io.File;

public class Judgement {
	public static void judge() throws Exception{
		File Cosin = utils.Util.combinePath(".", "cosin", "cosinsimiler.txt");
		File Euclid = utils.Util.combinePath(".", "euclid", "euclideandistance.txt");
		File Minhash = utils.Util.combinePath(".", "hash", "SimilarityRate.txt");
		File Judgementdata =  utils.Util.combinePath(".", "judgement", "judgement.txt");
		String[] Cosindata = utils.Reader.readline(Cosin);
		String[] Eucliddata = utils.Reader.readline(Euclid);
		String[] Minhashdata = utils.Reader.readline(Minhash);
		double sum = 0.0;

		for (int i = 0; i < Cosindata.length; i++) {
			String[] cosindata = Cosindata[i].split(" ");
			String[] eucliddata = Eucliddata[i].split(" ");
			String[] minhashdata = Minhashdata[i].split(" ");
			for (int j = 0; j < cosindata.length; j++) {
				sum+=(Double.parseDouble(cosindata[j])+Double.parseDouble(eucliddata[j])+Double.parseDouble(minhashdata[j]))/3;
				utils.Writer.write(Judgementdata, utils.Desimal.decimal(sum, 5)+" ");
				sum = 0;
			}
			utils.Writer.writeline(Judgementdata, "");
		}
	}

}
