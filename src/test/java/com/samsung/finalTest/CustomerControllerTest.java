package com.samsung.finalTest;

import com.samsung.finalTest.controller.CustomerController;
import com.samsung.finalTest.repositories.Customer;
import com.samsung.finalTest.services.CustomerService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddCustomerWithMissingData() {
        Customer customer = new Customer();
        customer.setName("jason");
        // customerNumber is missing
        customer.setEmail("jason@gmail.com");

        ResponseEntity<Customer> response = customerController.addCustomer(customer);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        verify(customerService, never()).addCustomer(any(Customer.class));
    }

    @Test
    public void testAddCustomer() {
        Customer customer = new Customer();
        customer.setName("jason");
        customer.setCustomerNumber("123456");
        customer.setEmail("jason@gmail.com");

        when(customerService.addCustomer(customer)).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.addCustomer(customer);

        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody().getName(), "jason");
        verify(customerService, times(1)).addCustomer(customer);
    }

    @Test
    public void testGetAllCustomers() {
        Customer customer1 = new Customer();
        customer1.setName("jason");
        customer1.setCustomerNumber("123456");
        customer1.setEmail("jason@gmail.com");

        Customer customer2 = new Customer();
        customer2.setName("jason2");
        customer2.setCustomerNumber("123456");
        customer2.setEmail("jason2@gmail.com");

        List<Customer> customers = Arrays.asList(customer1, customer2);

        when(customerService.getAllCustomers()).thenReturn(customers);

        ResponseEntity<List<Customer>> response = customerController.getAllCustomers();

        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
        Assert.assertEquals(response.getBody().size(), 2);
        verify(customerService, times(1)).getAllCustomers();
    }
}
