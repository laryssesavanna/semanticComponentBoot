package br.imd.ufrn.controller;

public class Rules {

	public static String TRANSITIVITYRULE = "[rule1:(?a http://www.semanticweb.org/bartirarocha/ontologies/2019/4/LGeoSIMOntology#isPartOf ?b) "
			+ "(?b http://www.semanticweb.org/bartirarocha/ontologies/2019/4/LGeoSIMOntology#isPartOf ?c)"
			+ "->(?a http://www.semanticweb.org/bartirarocha/ontologies/2019/4/LGeoSIMOntology#isPartOf ?c)]";
	
	public static String COMPOSITIONRULE = "[rule2:(?a http://www.semanticweb.org/bartirarocha/ontologies/2019/4/LGeoSIMOntology#locatedAt ?b) "
			+ "(?b http://www.semanticweb.org/bartirarocha/ontologies/2019/4/LGeoSIMOntology#isPartOf ?c)"
			+ "->(?a http://www.semanticweb.org/bartirarocha/ontologies/2019/4/LGeoSIMOntology#locatedAt ?c)]";
	
	
}
