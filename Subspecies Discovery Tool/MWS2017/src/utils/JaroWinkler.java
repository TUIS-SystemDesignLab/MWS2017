package utils;

import org.apache.lucene.search.spell.JaroWinklerDistance;
import org.apache.lucene.search.spell.StringDistance;

public class JaroWinkler {

	public static double calcJaroWinkler(String[] s1, String[] s2) {

		StringDistance stringDistance = new JaroWinklerDistance();
		double distance = stringDistance.getDistance(String.join("", s1), String.join("", s2));
		return distance;
	}

}
