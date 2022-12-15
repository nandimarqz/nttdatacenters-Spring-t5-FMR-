package nttdatacenters.nttdatacentersSpringt5FMR.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nttdatacenters.nttdatacentersSpringt5FMR.exceptions.CustomerNotFound;
import nttdatacenters.nttdatacentersSpringt5FMR.exceptions.ExistingCustomer;
import nttdatacenters.nttdatacentersSpringt5FMR.persistence.Customer;
import nttdatacenters.nttdatacentersSpringt5FMR.services.CustomerManagmentServiceI;

/**
 * Controlador de los clientes
 * 
 * @author nandi
 *
 */
@Controller
@RestController
@RequestMapping("/*")
public class CustomerController {

	/** Servicio de gestion de los clientes */
	@Autowired
	CustomerManagmentServiceI cms;

	/** Logger para la clase */
	final Logger CCLOG = LoggerFactory.getLogger(CustomerController.class);

	/**
	 * Muestra la tabla con todos los clientes de la BBDD
	 * 
	 * @param model
	 * @return lista de clientes
	 */
	@RequestMapping("showCustomers")
	public List<Customer> tableCustomers(Model model) {

		return cms.getAllCustomers();

	}

	/**
	 * Recoge la peticion y añade el cliente recogido de la peticion
	 * 
	 * @param c
	 * @return String
	 */
	@PostMapping("addNewCustomer")
	public void addCustomer(@ModelAttribute("customer") Customer c) {

		CCLOG.debug("Añadiendo cliente");

		try {
			cms.insertNewCustomer(c);
		} catch (ExistingCustomer e) {

			CCLOG.debug(e.getMessage());

		}
	}

	/**
	 * Recoge el dni pasado por parametro y muestra el cliente con el dni recogido
	 * en formato JSON
	 * 
	 * @param dni
	 * @param model
	 * @return String
	 */
	@PostMapping("showCustomer")
	public Customer getCustomerDni(@RequestParam String dni, Model model) {

		Customer c = null;

		try {
			c = cms.getCustomerByDNI(dni);
		} catch (CustomerNotFound e) {
			CCLOG.debug(e.getMessage());
		}

		return c;
	}
	
	/**
	 * Borra el cliente con el id que recibe por parametros
	 * 
	 * @param id
	 * @return redirige al metodo de la vista de los clientes 
	 */
	@PostMapping("deleteCustomer")
	public void deleteCustomer(@RequestParam String id) {
		
		if(id != "" && id != null) {
			
			cms.deleteCustomerById(Long.valueOf(id));
			
		}
		
	
		
	}
	

}
