package utils;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Jaccard extends Thread{
	public static double jaccard(File a, File b){
		double jaccardsimilarity=0.0;
		try {
			File Base = utils.Util.combinePath(".", "predata", a.getName());
			String[] base = utils.Reader.readline(Base);
			File Second = utils.Util.combinePath(".", "predata", b.getName());
			String[] second = utils.Reader.readline(Second);
			Set<String> basedata=new HashSet<String>();
			Set<String> seconddata=new HashSet<String>();
			for(int i=0; i<base.length; i++){
				basedata.add(base[i]);
			}
			for(int i=0; i<second.length; i++){
				seconddata.add(second[i]);
			}
			Set <String> seki=new HashSet<String>(basedata);
			seki.retainAll(seconddata);
			Set <String> sum=new HashSet<String>(basedata);
			sum.addAll(seconddata);
			jaccardsimilarity=(double)seki.size()/(double)sum.size();

		} catch (Exception e) {
			System.out.println(e);
			System.out.println("jaccard method error");
			System.exit(1);
		}
		return jaccardsimilarity;
	}

}
