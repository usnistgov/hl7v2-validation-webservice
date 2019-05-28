/**
 * NIST HL7 Dashboard
 * IndexController.java Mar 12, 2009
 *
 * This code was produced by the National Institute of Standards and
 * Technology (NIST). See the "nist.disclaimer" file given in the distribution
 * for information on the use and redistribution of this software.
 */
package gov.nist.healthcare.hl7ws.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Harold AFFO (NIST)
 */
@Controller
public class IndexController {

	/**
	 * display the home page
	 */
	@RequestMapping("/home.htm")
	public ModelAndView getHome(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("home");
	}

	/**
	 * display the index page
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index.htm")
	public ModelAndView getIndex(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("index");
	}

	@RequestMapping("/documentation.htm")
	public ModelAndView getDocumentation(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("documentation");
	}

	@RequestMapping("/contact.htm")
	public ModelAndView getContact(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("contact");
	}

	@RequestMapping("/clients.htm")
	public ModelAndView getClients(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("clients");
	}

	@RequestMapping("/disclaimer.htm")
	public ModelAndView getDisclaimer(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView("disclaimer");
	}

}
