package br.imd.ufrn.controller;

public class Queries {

	// Return highest approved indices
	
	// Return AVG approved ordered by neighborhood
	public static String getSchoolApprovedByNeighborhood() {
		String GETSCHOOLAPPROVEDBYNEIGHBORHOOD = "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "prefix owl: <http://www.w3.org/2002/07/owl#>"
				+ "prefix lgsim: <http://www.semanticweb.org/bartirarocha/ontologies/2019/4/LGeoSIMOntology#>"
				+ "prefix ngsi-ld: <https://uri.etsi.org/ngsi-ld/>"
				+ "SELECT ?neighborhood (AVG(?aprovados) AS ?approvedByneighborhood) WHERE { "
				+ "?e rdf:type ngsi-ld:default-context\\/escola . "
				+ "?e ngsi-ld:name ?d . "
				+ "?d ngsi-ld:hasValue ?n . " 
				+ "?e lgsim:locatedAt ?neighborhood . "
				+ "?e ngsi-ld:default-context\\/sigeduc ?x. "
				+ "?x rdf:type ngsi-ld:Property ."
				+ "?x ngsi-ld:hasValue ?y .\n" 
				+ "?y ngsi-ld:default-context\\/ensino_fundamental_iniciais ?z . "
				+ "?z ngsi-ld:default-context\\/approved ?aprovados "
				+ "} GROUP BY ?neighborhood";
		return GETSCHOOLAPPROVEDBYNEIGHBORHOOD;
	}
	
	// Return the school with highest number of approved students in a given neighborhood
	public static String getHighestSchoolApprovedInNeighborhood(String neighboorhood) {
		//Query string
		String GETHIGHESTSCHOOLAPPROVEDINNEIGHBORHOOD = "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "prefix owl: <http://www.w3.org/2002/07/owl#>"
				+ "prefix lgsim: <http://www.semanticweb.org/bartirarocha/ontologies/2019/4/LGeoSIMOntology#>"
				+ "prefix ngsi-ld: <https://uri.etsi.org/ngsi-ld/>"
				+ "SELECT ?n ?neighborhood ?qtdApproved\n"
				+ "WHERE {\n" 
				+ "{SELECT (MAX(?aprovados) as ?qtdApproved)\n" 
				+ "WHERE {\n" 
				+ "#busca dados de aprovados nas F1 - anos iniciais\n" 
				+ "?e ngsi-ld:default-context\\/sigeduc ?x.\n" 
				+ "?x rdf:type ngsi-ld:Property .\n"
				+ " ?x ngsi-ld:hasValue ?y .\n" 
				+ "?y ngsi-ld:default-context\\/ensino_fundamental_iniciais ?z .\n" 
				+ "?z ngsi-ld:default-context\\/approved ?aprovados}}" 
				+ "	{\n"
				+ "#apresenta nomes das escolas\n" 
				+ "?e rdf:type ngsi-ld:default-context\\/escola .\n" 
				+ "?e ngsi-ld:name ?d .\n" 
				+ " ?d ngsi-ld:hasValue ?n .\n" 
				+ "#endereço da escola (objeto)\n" 
				+ "?e ngsi-ld:default-context\\/locatedAt ?neighborhood .\n" 
				+ "?e ngsi-ld:default-context\\/sigeduc ?x.\n" 
				+ "?x rdf:type ngsi-ld:Property .\n"
				+ "?x ngsi-ld:hasValue ?y .\n" 
				+ "?y ngsi-ld:default-context\\/ensino_fundamental_iniciais ?z .\n" 
				+ "?z ngsi-ld:default-context\\/approved ?qtdApproved .\n" 
				+ "FILTER (?neighborhood IN (<"+neighboorhood+">))}}";
		return GETHIGHESTSCHOOLAPPROVEDINNEIGHBORHOOD;
	}
	
}
