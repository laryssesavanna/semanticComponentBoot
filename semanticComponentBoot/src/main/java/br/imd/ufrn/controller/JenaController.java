package br.imd.ufrn.controller;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.InfModel;

public class JenaController {

	// Fuseki endpoint
	public static String FUSEKIDATASET = "http://localhost:3030/semanticData2";
	
	// printInstancesOfEntity
	public static ResultSet printInstancesOfEntity(InfModel infmodel, String queryString) {
		Query query = QueryFactory.create(queryString);
		QueryExecution qe = QueryExecutionFactory.create(query, infmodel);
		ResultSet results = qe.execSelect();

		return results;
	}
	// printResults: Print the results of a sparql query (for tests)
	public static void printResults(ResultSet results) {
		while (results.hasNext()) {
			QuerySolution soln = results.nextSolution();
			System.out.println(soln + "\n");
		}
	}
}
