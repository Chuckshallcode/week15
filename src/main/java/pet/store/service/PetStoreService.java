package pet.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreData.CustomerData;
import pet.store.controller.model.PetStoreData.EmployeeData;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Service

public class PetStoreService {
	
	@Autowired
	private PetStoreDao petStoreDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private EmployeeDao employeeDao;
	
	@Transactional(readOnly = false)
	public PetStoreData savePetStore(PetStoreData petStoreData) {
		Long petStoreId = petStoreData.getPetStoreId();
		PetStore petStore = findOrCreatePetStore(petStoreId);
		
		copyPetStoreFields(petStore, petStoreData); //Rob calls it "retrievePetxFields
		petStoreDao.save(petStore);
		return new PetStoreData(petStore);
	}
	
	public void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) { //Rob calls it "retrievePetxFields
		 petStore.setPet_store_id(petStoreData.getPetStoreId());	//more fields to follow city state etc
		 petStore.setPet_store_address(petStoreData.getPetStoreAddress());
		 petStore.setPet_store_city(petStoreData.getPetStoreCity());
		 petStore.setPet_store_name(petStoreData.getPetStoreName());
		 petStore.setPet_store_phone(petStoreData.getPetStorePhone());
		 petStore.setPet_store_state(petStoreData.getPetStoreState());
		 petStore.setPet_store_zip(petStoreData.getPetStoreZip());
		 }
	
	
	public PetStore findPetStoreById(Long petStoreId) {
		return petStoreDao.findById(petStoreId).orElseThrow(() -> new NoSuchElementException("Pet store with ID=" + petStoreId + " was not found"));
	}
	
	private PetStore findOrCreatePetStore (Long petStoreId) {
		PetStore petStore;
		
		if (Objects.isNull(petStoreId)) {
			petStore = new PetStore();
	} else {
		petStore = findPetStoreById(petStoreId);
	}
		return petStore;
	}

	public PetStoreData retrievePetStoreById(Long petStoreId) {
		return new PetStoreData(findPetStoreById(petStoreId));
	}
	
	@Transactional (readOnly = true)
	public List<PetStoreData> retrieveAllPetStores() {
		List<PetStore> petStores = petStoreDao.findAll();
		List<PetStoreData> response = new LinkedList<>();
		
		for(PetStore petStore : petStores) { //ask M about
			response.add(new PetStoreData(petStore));
		}
		return response;
	}
	
	@Transactional(readOnly = false)
	public void deletePetStore (Long petStoreId) {
		PetStore petStore = findPetStoreById(petStoreId);
		petStoreDao.delete(petStore);
	}

	// end of Pet Store Service
	
	//Set<Customer> customers = customerDao.
	@Transactional(readOnly = false)
	public EmployeeData saveEmployee(Long petStoreId, EmployeeData employeeData) {
		PetStore petStore = findPetStoreById(petStoreId);
		Long employeeId = employeeData.getEmployeeId();
		Employee employee = findOrCreateEmployee(employeeId, petStoreId);
		
		
		copyEmployeeFields(employee, employeeData);
		employee.setPetStore(petStore);
		petStore.getEmployees().add(employee);
		employeeDao.save(employee);
		return new EmployeeData(employee);
		
	}

	private Employee findOrCreateEmployee(Long employeeId, Long petStoreId) {
		Employee employee;
		
		if (Objects.isNull(employeeId)) {
			employee = new Employee();
		} else {
			employee = findEmployeeById(employeeId, petStoreId);
		}
		return employee;
	}
	
	private Employee findEmployeeById(Long employeeId, Long petStoreId) {
		Employee employee = employeeDao.findById(employeeId).orElseThrow(() -> new NoSuchElementException("Employee with ID=" + employeeId + " was not found"));
		if (employee.getPetStore().getPet_store_id() != petStoreId) {
			throw new IllegalArgumentException("employee with this ID " + employeeId + " does not work here");
		}
		return employee;
	}
	
	public void copyEmployeeFields (Employee employee, EmployeeData employeeData) {
		employee.setEmployee_id(employeeData.getEmployeeId());
		employee.setEmployee_first_name(employeeData.getEmployeeFirstName());
		employee.setEmployee_last_name(employeeData.getEmployeeLastName());
		employee.setEmployee_job_title(employeeData.getEmployeeJobTitle());
		employee.setEmployee_phone(employeeData.getEmployeePhone());
	}
	// end of employee service

public CustomerData saveCustomers(Long petStoreId, CustomerData customerData) {
	PetStore petStore = findPetStoreById(petStoreId);
	Long customerId = customerData.getCustomerId();
	Customer customer = findOrCreateCustomer(customerId, petStoreId);
	copyCustomerFields(customer, customerData);
	customer.getPetStores().add(petStore);
	petStore.getCustomers().add(customer);
	customerDao.save(customer);
	return new CustomerData(customer);
	
}

private void copyCustomerFields(Customer customer, CustomerData customerData) {
	customer.setCustomer_id(customerData.getCustomerId());
	customer.setCustomer_first_name(customerData.getCustomerFirstName());
	customer.setCustomer_last_name(customerData.getCustomerLastName());
	customer.setCustomer_email(customerData.getCustomerEmail());
}

private Customer findOrCreateCustomer(Long customerId, Long petStoreId) {
	Customer customer;
	
	if (Objects.isNull(customerId)) {
		customer = new Customer();
	} else {
		customer = findCustomerById(customerId, petStoreId);
	}
	return customer;
}

private Customer findCustomerById(Long customerId, Long petStoreId) {
	Customer customer = customerDao.findById(customerId).orElseThrow(() -> new NoSuchElementException("Customer with ID=" + customerId + " was not found"));
	
	boolean found = false;
	
	for (PetStore petStore : customer.getPetStores()) {
		if(petStore.getPet_store_id() == petStoreId) {
			found = true;
			break;
		}
	}
	
	if (!found) {
		throw new IllegalArgumentException("The customer with ID= " + customerId + " doesn't shop here.");
	}
	return customer;
	}
}
