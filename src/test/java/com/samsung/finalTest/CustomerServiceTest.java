package com.samsung.finalTest;

import com.samsung.finalTest.repositories.Customer;
import com.samsung.finalTest.repositories.CustomerRepository;
import com.samsung.finalTest.services.CustomerService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @BeforeClass
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddCustomer() {
        Customer customer = new Customer();
        customer.setName("jason");
        customer.setCustomerNumber("123456");
        customer.setEmail("jason@gmail.com");

        when(customerRepository.save(customer)).thenReturn(customer);

        Customer result = customerService.addCustomer(customer);

        Assert.assertNotNull(result);
        Assert.assertEquals(result.getName(), "jason");
        verify(customerRepository, times(1)).save(customer);
    }

    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Customer number is required")
    public void testAddCustomerWithoutCustomerNumber() {
        Customer customer = new Customer();
        customer.setName("Jason");

        customer.setEmail("Jason@example.com");

        customerService.addCustomer(customer);


        verify(customerRepository, never()).save(any(Customer.class));
    }
    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Duplicate customer number")
    public void testAddCustomerWithDuplicateCustomerNumber() {
        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setCustomerNumber("C12345");
        customer.setEmail("john@example.com");

        when(customerRepository.existsByCustomerNumber("C12345")).thenReturn(true);

        customerService.addCustomer(customer);


        verify(customerRepository, never()).save(any(Customer.class));
    }
    @Test(expectedExceptions = IllegalArgumentException.class,
            expectedExceptionsMessageRegExp = "Duplicate email")
    public void testAddCustomerWithDuplicateEmail() {
        Customer customer = new Customer();
        customer.setName("Jason");
        customer.setCustomerNumber("C12345");
        customer.setEmail("Jason@example.com");

        when(customerRepository.existsByEmail("Jason@example.com")).thenReturn(true);

        customerService.addCustomer(customer);


        verify(customerRepository, never()).save(any(Customer.class));
    }
    @Test
    public void testGetAllCustomers() {
        Customer customer1 = new Customer();
        customer1.setName("jason");
        customer1.setCustomerNumber("123456");
        customer1.setEmail("jason@gmail.com");

        Customer customer2 = new Customer();
        customer2.setName("anh1");
        customer2.setCustomerNumber("123456");
        customer2.setEmail("anh1@gmail.com");

        List<Customer> customers = Arrays.asList(customer1, customer2);

        when(customerRepository.findAll())
                .thenReturn(customers);

        List<Customer> result = customerService.getAllCustomers();

        Assert.assertEquals(result.size(), 2);
        verify(customerRepository, times(1))
                .findAll();
    }
}
