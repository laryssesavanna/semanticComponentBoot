package br.imd.ufrn.services;

import java.io.File;
import java.util.LinkedList;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.FileManager;

public class InferenceFromRealData {
	public static final String SCHEMA = "static/LGeoSIMOntology.owl";

	// If you want to use a fuseki dataset
	public static final String FUSEKIDATASET = "http://localhost:3030/geralDataset2";

	public static final String MUNICIPIOS = "static/Municipios";

	public static final String QUADRAS = "static/Quadras";

	public static final String BAIRROS = "static/Bairros";

	/*--- BEGIN - TEST QUERIES: These queries are just some tests that I did. They are not necessary ---*/
	public static final String GETALLQUERY = "SELECT * WHERE {?x ?y ?z}";

	public static final String GETMUNICIPIOS = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
			+ "prefix lgsim: <http://www.semanticweb.org/bartirarocha/ontologies/2019/4/LGeoSIMOntology#>"
			+ "SELECT ?municipio WHERE { ?municipio rdf:type lgsim:City }";

	public static final String GETBAIRROS = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
			+ "prefix lgsim: <http://www.semanticweb.org/bartirarocha/ontologies/2019/4/LGeoSIMOntology#>"
			+ "SELECT ?bairro WHERE { ?bairro rdf:type lgsim:NeighborHood }";

	public static final String GETQUADRAS = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
			+ "prefix lgsim: <http://www.semanticweb.org/bartirarocha/ontologies/2019/4/LGeoSIMOntology#>"
			+ "SELECT ?quadra WHERE { ?quadra rdf:type lgsim:Block }";

	public static final String GETBAIRROSRELATEDWITHMUNICIPIOS = "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
			+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
			+ "prefix owl: <http://www.w3.org/2002/07/owl#>"
			+ "prefix lgsim: <http://www.semanticweb.org/bartirarocha/ontologies/2019/4/LGeoSIMOntology#>"
			+ "prefix ngsi: <https://uri.etsi.org/ngsi-ld/>"
			+ "SELECT ?bairro ?municipio WHERE {?bairro rdf:type lgsim:NeighborHood . ?bairro lgsim:isPartOf ?municipio }";

	public static final String GETQUADRASRELATEDWITHBAIRROS = "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
			+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
			+ "prefix owl: <http://www.w3.org/2002/07/owl#>"
			+ "prefix lgsim: <http://www.semanticweb.org/bartirarocha/ontologies/2019/4/LGeoSIMOntology#>"
			+ "prefix ngsi: <https://uri.etsi.org/ngsi-ld/>"
			+ "SELECT ?quadra ?bairro WHERE {?quadra rdf:type lgsim:Block . ?quadra lgsim:isPartOf ?bairro }";
	
	// ----------------- SCHOOLS ----------------------------------
	
	public static final String GETSCHOOLAPPROVEDBYBAIRROS = "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
			+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
			+ "prefix owl: <http://www.w3.org/2002/07/owl#>"
			+ "prefix lgsim: <http://www.semanticweb.org/bartirarocha/ontologies/2019/4/LGeoSIMOntology#>"
			+ "prefix ngsi-ld: <https://uri.etsi.org/ngsi-ld/>"
			+ "SELECT ?b (AVG(?aprovados) AS ?apPorBairro) WHERE { "
			+ "?e rdf:type ngsi-ld:default-context\\/escola . "
			+ "?e ngsi-ld:name ?d . "
			+ "?d ngsi-ld:hasValue ?n . " 
			+ "?e ngsi-ld:default-context\\/locatedAt ?b . "
			+ "?e ngsi-ld:default-context\\/sigeduc ?x. "
			+ "?x rdf:type ngsi-ld:Property ."
			+ "?x ngsi-ld:hasValue ?y .\n" 
			+ "?y ngsi-ld:default-context\\/ensino_fundamental_iniciais ?z . "
			+ "?z ngsi-ld:default-context\\/approved ?aprovados "
			+ "} GROUP BY ?b";
	
	public static final String GETSCHOOLAPPROVEDEFINICIAIS = "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
			+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
			+ "prefix owl: <http://www.w3.org/2002/07/owl#>"
			+ "prefix lgsim: <http://www.semanticweb.org/bartirarocha/ontologies/2019/4/LGeoSIMOntology#>"
			+ "prefix ngsi-ld: <https://uri.etsi.org/ngsi-ld/>"
			+ "SELECT ?n ?aprovados\n" 
			+ "WHERE { \n" 
			+ "	#apresenta nomes das escolas\n" 
			+ "	?e rdf:type ngsi-ld:default-context\\/escola .\n" 
			+ "  	?e ngsi-ld:name ?d .\n" 
			+ "    	?d ngsi-ld:hasValue ?n .\n" 
			+ "  	#apresenta endereço da escola (objeto)\n" 
			+ "	?e ngsi-ld:default-context\\/locatedAt ?b .\n" 
			+ "  	#busca dados de aprovados nas F1 - anos iniciais\n" 
			+ "  	?e ngsi-ld:default-context\\/sigeduc ?x.\n" 
			+ "  	?x rdf:type ngsi-ld:Property .\n" 
			+ "  	?x ngsi-ld:hasValue ?y .\n" 
			+ "  	?y ngsi-ld:default-context\\/ensino_fundamental_iniciais ?z .\n" 
			+ "  	?z ngsi-ld:default-context\\/approved ?aprovados}";
	
	/*--- END - TEST QUERIES ---*/

	// Query used to test the transitivity rule
	public static final String FINALQUERY = "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
			+ "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
			+ "prefix owl: <http://www.w3.org/2002/07/owl#>"
			+ "prefix lgsim: <http://www.semanticweb.org/bartirarocha/ontologies/2019/4/LGeoSIMOntology#>"
			+ "SELECT ?quadra ?municipio WHERE {?quadra rdf:type lgsim:Block ." + " ?quadra lgsim:isPartOf ?municipio ."
			+ " FILTER(?municipio = (<urn:ngsi-ld:layer:municipios:f24079d4-d295-4d1c-92bf-c8fff359231d>)) }";

	public static final String TRANSITIVITYRULE = "[rule1:(?a http://www.semanticweb.org/bartirarocha/ontologies/2019/4/LGeoSIMOntology#isPartOf ?b) "
			+ "(?b http://www.semanticweb.org/bartirarocha/ontologies/2019/4/LGeoSIMOntology#isPartOf ?c)"
			+ "->(?a http://www.semanticweb.org/bartirarocha/ontologies/2019/4/LGeoSIMOntology#isPartOf ?c)]";

	// main: Infer data from the json-ld files using the transitivityrule
	public static void main(String[] args) {

//		If the data was upload to a fuseki dataset, it can be loaded instead of the .jsonld files
		Model data = FileManager.get().loadModel(FUSEKIDATASET);
		/*
		LinkedList<String> municipiosInstancesPaths = listInstancesPaths(MUNICIPIOS);
		LinkedList<String> bairrosInstancesPaths = listInstancesPaths(BAIRROS);
		LinkedList<String> quadrasInstancesPaths = listInstancesPaths(QUADRAS);

		Model municipiosModel = loadModels(municipiosInstancesPaths);
		Model bairrosModel = loadModels(bairrosInstancesPaths);
		Model quadrasModel = loadModels(quadrasInstancesPaths);
		*/
		// Model data = municipiosModel.add(bairrosModel).add(quadrasModel);

		Reasoner reasoner = new GenericRuleReasoner(Rule.parseRules(TRANSITIVITYRULE));

		InfModel infModel = ModelFactory.createInfModel(reasoner, data);

		// printQuadrasRelatedToMunicipios(infModel);
		// printSchoolsApprovedAVGByBairros(infModel);
		printSchoolsApprovedEF1Iniciais(infModel);
	}

	// printBairrosAndMunicipios: Just for testing
	public static void printBairrosAndMunicipios(InfModel infModel) {
		System.out.println("==================== BAIRROS E SEUS MUNICIPIOS ====================");
		printInstancesOfEntity(infModel, GETBAIRROSRELATEDWITHMUNICIPIOS);
	}

	// printQuadrasAndBairros: Just for testing
	public static void printQuadrasAndBairros(InfModel infModel) {
		System.out.println("==================== QUADRAS E SEUS BAIRROS ====================");
		printInstancesOfEntity(infModel, GETQUADRASRELATEDWITHBAIRROS);
	}

	// printQuadrasRelatedToMunicipios: Just for testing
	public static void printQuadrasRelatedToMunicipios(InfModel infModel) {
		System.out.println("==================== QUADRAS E SEUS MUNICIPIOS ====================");
		printInstancesOfEntity(infModel, FINALQUERY);
	}

	// printQuadrasRelatedToMunicipios: printCidadesBairrosQuadras
	public static void printCidadesBairrosQuadras(InfModel infModel) {
		System.out.println("==================== CIDADES ====================");
		printInstancesOfEntity(infModel, GETMUNICIPIOS);

		System.out.println("\n==================== BAIRROS ====================");
		printInstancesOfEntity(infModel, GETBAIRROS);

		System.out.println("\n==================== QUADRAS ====================");
		printInstancesOfEntity(infModel, GETQUADRAS);
	}
	public static void printSchoolsApprovedAVGByBairros(InfModel infModel) {
		System.out.println("==================== SCHOOLS APPROVED BY BAIRROS ====================");
		printInstancesOfEntity(infModel, GETSCHOOLAPPROVEDBYBAIRROS);
	}
	public static void printSchoolsApprovedEF1Iniciais(InfModel infModel) {
		System.out.println("==================== SCHOOLS APPROVED IN EF1-INICIAIS ====================");
		printInstancesOfEntity(infModel, GETSCHOOLAPPROVEDEFINICIAIS);
	}

	// listInstancesPaths: List the absolute paths of all files in a given folder --
	// IMPORTANT
	public static LinkedList<String> listInstancesPaths(String path) {
		File directoryPath = new File(path);

		// List of all files and directories
		File filesList[] = directoryPath.listFiles();

		// Complete path of all files
		LinkedList<String> completePath = new LinkedList<String>();
		for (File file : filesList) {
			completePath.add(file.getAbsolutePath());
		}
		return completePath;
	}

	// loadModels: Given the absolute paths of the models, load them all in a single
	// jena model -- IMPORTANT
	public static Model loadModels(LinkedList<String> paths) {
		Model groupedModels = ModelFactory.createDefaultModel();
		for (String path : paths) {
			groupedModels.add(FileManager.get().loadModel(path));
		}
		return groupedModels;
	}

	// printInstancesOfEntity: Just for testing
	public static void printInstancesOfEntity(InfModel infmodel, String queryString) {
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, infmodel);
		ResultSet results = qe.execSelect();

		printResults(results);
	}

	// printResults: Print the results of a sparql query
	public static void printResults(ResultSet results) {
		while (results.hasNext()) {
			QuerySolution soln = results.nextSolution();
			System.out.println(soln + "\n");
		}
	}
}
