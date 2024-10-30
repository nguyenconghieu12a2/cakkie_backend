package com.cakkie.backend.controller;

import com.cakkie.backend.model.userSite;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.cakkie.backend.service.CustomerService;
import com.cakkie.backend.dto.CustomerDTO;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/customer")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<CustomerDTO> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/banned-customer")
    public ResponseEntity<List<CustomerDTO>> getAllBannedCustomers() {
        List<CustomerDTO> customers = customerService.getAllBannedCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/deleted-customer")
    public ResponseEntity<List<CustomerDTO>> getAllDeletedCustomers() {
        List<CustomerDTO> customers = customerService.getAllDeletedCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/get-customer/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable int id) {
        CustomerDTO customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/get-banned-customer/{id}")
    public ResponseEntity<CustomerDTO> getBannedCustomerById(@PathVariable int id) {
        CustomerDTO customer = customerService.getBannedCustomerById(id);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/edit-customer/{id}")
    public ResponseEntity<String> updateCustomer(@PathVariable int id, @RequestBody CustomerDTO customerDT){
        customerService.updateCustomerInfo(id, customerDT);
        return ResponseEntity.ok("Customer updated successfully");
    }

    @PutMapping("/delete-customer/{id}")
    public void deleteCustomer(@PathVariable int id) {
        customerService.deleteCustomer(id);
    }

    @PutMapping("/recover-customer/{id}")
    public void recoverCustomer(@PathVariable int id) {
        customerService.recoverCustomer(id);
    }

    @PutMapping("/unlock-customer/{id}")
    public void unlockCustomer(@PathVariable int id) {
        customerService.recoverCustomer(id);
    }
}
