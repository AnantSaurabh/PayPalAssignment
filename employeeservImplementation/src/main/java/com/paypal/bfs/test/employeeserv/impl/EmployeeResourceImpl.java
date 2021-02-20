package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public ResponseEntity employeeGetById(Integer id) {

        //If there is no employee with the given id
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Employee not found with id " + id));

        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    public ResponseEntity<Employee> saveEmployee(Employee employee) {
        //If an employee is not assigned a id yet
        if(employee.getId()==null) {
            Integer id = employeeRepository.findTopByOrderById().map(Employee::getId).orElse(0)+1;
            employee.setId(id);
        }
        Address address = employee.getAddress();
        if(address.getId()==null) {
            String uuid = UUID.randomUUID().toString();
            address.setId(uuid);
        }
        employeeRepository.save(employee);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

}
