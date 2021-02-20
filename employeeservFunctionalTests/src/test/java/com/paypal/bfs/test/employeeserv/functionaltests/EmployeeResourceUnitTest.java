package com.paypal.bfs.test.employeeserv.functionaltests;

import com.paypal.bfs.test.employeeserv.EmployeeservApplication;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.impl.EmployeeResourceImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = EmployeeservApplication.class)
public class EmployeeResourceUnitTest {
    @Autowired
    private EmployeeResourceImpl controller;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void updateAndRetrieve() throws Exception {
        Employee employee1 = new Employee();
        Address address = new Address();

        address.setId("1");
        address.setCity("Bangalore");
        address.setCountry("India");
        address.setLine1("Lane Number 1");
        address.setState("Karnataka");
        address.setZipCode("400014");

        employee1.setId(101);
        employee1.setFirstName("John");
        employee1.setLastName("Doe");
        employee1.setDateOfBirth(Date.valueOf("1997-09-22"));
        employee1.setAddress(address);

        controller.saveEmployee(employee1);
        Employee employee = (Employee) controller.employeeGetById(101).getBody();
        assertEquals(employee.getFirstName(),"John");
    }


}
