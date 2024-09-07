package pet.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.CustomerData;
import pet.store.controller.model.PetStoreData.EmployeeData;
import pet.store.entity.PetStore;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {
	
	@Autowired
	private PetStoreService petStoreService;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public PetStoreData insertPetStore (@RequestBody PetStoreData petStoreData) {
		log.info("Creating petStore {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	
	@PutMapping("/{petStoreId}")
	public PetStoreData updatePetStore(@PathVariable Long petStoreId, @RequestBody PetStoreData petStoreData) {
		petStoreData.setPetStoreId(petStoreId);
		log.info("updating customer {}", petStoreData);
		return petStoreService.savePetStore(petStoreData);
	}
	
	@GetMapping("/petStore")
	public List<PetStoreData> retrieveAllPetStores() {
		log.info("Retrieve all pet stores called.");
		return petStoreService.retrieveAllPetStores();
	}
	
	@GetMapping("/customer/{customerId}")
	public PetStore retrievePetStoreById (@PathVariable Long petStoreId, @RequestBody PetStoreData petStoreData) {
		log.info("Retrieving store with ID = []", petStoreId);
		return petStoreService.findPetStoreById(petStoreId);
	}
	
	@DeleteMapping("/petStore")
	public void deleteAllPetStores() {
		log.info("Attempting to delete all pet stores");
		throw new UnsupportedOperationException("Deleting all contributors is not allowed.");
	}
	
	@DeleteMapping("/petStore/{petStoreId}")	
	public Map<String, String> deletePetStoreById(@PathVariable Long petStoreId) {
		log.info("Deleting pet store with ID ={}", petStoreId);
		
		petStoreService.deletePetStore(petStoreId);
		return Map.of("Message", "Deleting of pet store with ID=" + petStoreId + " was successful.");
	}
	
	@PostMapping("/petStore/{petStoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public EmployeeData insertEmployee (@PathVariable Long petStoreId, @RequestBody EmployeeData employeeData) {
		log.info("Creating employee {} for pet store with ID = {}", petStoreId, employeeData);
		return petStoreService.saveEmployee(petStoreId, employeeData);
	}
	
	@PostMapping("/petStore/{petStoreId}/customer")
	@ResponseStatus(code = HttpStatus.CREATED)
	public CustomerData insertCustomer (@PathVariable Long petStoreId, @RequestBody CustomerData customerData) {
		log.info("Creating customer {} for pet store with ID = {}", petStoreId, customerData);
		return petStoreService.saveCustomers(petStoreId, customerData);
		}
}	