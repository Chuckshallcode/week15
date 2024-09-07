package pet.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Data
@NoArgsConstructor
public class PetStoreData { 
	
	private Long petStoreId;
	private String petStoreName;
	private String petStoreAddress;
	private String petStoreCity;
	private String petStoreState;
	private String petStoreZip;
	private String petStorePhone;
	private Set<CustomerData> customers = new HashSet<>();
	private Set<EmployeeData> employees = new HashSet<>();
	
public PetStoreData(PetStore petStore) {
	 this.petStoreId = petStore.getPet_store_id();
	 this.petStoreName = petStore.getPet_store_name();
	 this.petStoreAddress = petStore.getPet_store_address();
	 this.petStoreCity = petStore.getPet_store_city();
	 this.petStoreState = petStore.getPet_store_state();
	 this.petStoreZip = petStore.getPet_store_zip();
	 this.petStorePhone = petStore.getPet_store_phone();
	 
	 for (Customer customer : petStore.getCustomers()) {
			this.customers.add(new CustomerData(customer));
		}
	 
	 for (Employee employee : petStore.getEmployees()) {
			this.employees.add(new EmployeeData(employee));
		}
	 
	 
	 
} 
	
@Data
@NoArgsConstructor 
	public static class CustomerData {
	private Long customerId;
	private String customerFirstName;
	private String customerLastName;
	private String customerEmail;
	
	public CustomerData(Customer customer) {
		this.customerFirstName = customer.getCustomer_first_name();
		this.customerLastName = customer.getCustomer_last_name();
		this.customerId = customer.getCustomer_id();
		this.customerEmail = customer.getCustomer_email();
	 }
	

}

@Data
@NoArgsConstructor 
	public static class EmployeeData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long employeeId;
	private String employeeFirstName;
	private String employeeLastName;
	private String employeePhone;
	private String employeeJobTitle;
	
	public EmployeeData (Employee employee) {
		this.employeeId = employee.getEmployee_id();
		this.employeeFirstName = employee.getEmployee_first_name();
		this.employeeLastName = employee.getEmployee_last_name();
		this.employeePhone = employee.getEmployee_phone();
		this.employeeJobTitle = employee.getEmployee_job_title();
				
	}
	

}
}