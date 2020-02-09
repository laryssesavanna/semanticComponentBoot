package br.imd.ufrn.dao;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.FileManager;

import br.imd.ufrn.controller.JenaController;
import br.imd.ufrn.controller.Queries;
import br.imd.ufrn.controller.Rules;

public class EducationDAO {
	
	// Return AVG approved ordered by neighborhood
	public String listSchoolApprovedByNeighborhood() {
		long time = System.currentTimeMillis();
		
		Model data = FileManager.get().loadModel(JenaController.FUSEKIDATASET);
		Reasoner reasoner = new GenericRuleReasoner(Rule.parseRules(Rules.COMPOSITIONRULE));
		InfModel infModel = ModelFactory.createInfModel(reasoner, data);
		
		long time2 = System.currentTimeMillis() - time;
		System.out.println("| " + time2);
		
		// Get results
		ResultSet res = JenaController.printInstancesOfEntity(infModel, Queries.getSchoolApprovedByNeighborhood());
		
		// Convert to JSON
		ByteArrayOutputStream opStream = new ByteArrayOutputStream();
		ResultSetFormatter.outputAsJSON(opStream, res);
		String resJson = new String(opStream.toByteArray());
		
		return resJson;
	}
	
	// Return the school with highest number of approved students in a given neighborhood
	public String listHighestSchoolApprovedInNeighborhood(String neighboorhood) {
		long time = System.currentTimeMillis();
		// System.out.println("Before call Fuseki" + time);
		
		Model data = FileManager.get().loadModel(JenaController.FUSEKIDATASET);
		Reasoner reasoner = new GenericRuleReasoner(Rule.parseRules(Rules.COMPOSITIONRULE));
		InfModel infModel = ModelFactory.createInfModel(reasoner, data);
		
		long time2 = System.currentTimeMillis() - time;
		
		System.out.println("| " + time2);
		// Get results
		ResultSet res = JenaController.printInstancesOfEntity(infModel, Queries.getHighestSchoolApprovedInNeighborhood(neighboorhood));
		
		// Convert to JSON
		ByteArrayOutputStream opStream = new ByteArrayOutputStream();
		ResultSetFormatter.outputAsJSON(opStream, res);
		String resJson = new String(opStream.toByteArray());
		
		return resJson;
	}
	
	
	
}
