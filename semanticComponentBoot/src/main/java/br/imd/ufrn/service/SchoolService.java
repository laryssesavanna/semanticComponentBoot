package br.imd.ufrn.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.imd.ufrn.dao.EducationDAO;
import br.imd.ufrn.util.ServiceMap;

@RestController
public class SchoolService implements ServiceMap {
	
	@RequestMapping(method = RequestMethod.GET, path = "/approved-by-neighborhood")
    public String approvedByNeighborhood() {
		try {
			EducationDAO educDAO = new EducationDAO();
			String resultS = educDAO.listSchoolApprovedByNeighborhood();			
			
			return resultS;
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return e.getMessage();
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/highest-approved-in-neighborhood")
    public String highestSchoolApprovedInNeighborhood(@RequestParam("neighb-id") String id) {
		try {
			EducationDAO educDAO = new EducationDAO();
			String resultS = educDAO.listHighestSchoolApprovedInNeighborhood(id);
			
			return resultS;
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return e.getMessage();
		}
	}
}
