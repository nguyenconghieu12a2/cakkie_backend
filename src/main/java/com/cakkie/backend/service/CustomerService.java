package com.cakkie.backend.service;

import com.cakkie.backend.model.userSite;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.cakkie.backend.repository.CustomerRepo;
import com.cakkie.backend.exception.CustomerNotFoundException;
import com.cakkie.backend.dto.CustomerDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepo customerRepo;

    public List<CustomerDTO> getAllCustomers(){
        List<Object[]> customerData = customerRepo.getAllCustomer();

        Map<Integer, CustomerDTO> customerMap = new HashMap<>();

        for (Object[] row : customerData) {
            Integer customerId = (Integer) row[0];

            if(customerMap.containsKey(customerId)) {
                CustomerDTO existingCustomer = customerMap.get(customerId);
                existingCustomer.getCustomerAddress().add((String) row[9]);
            }else{
                CustomerDTO customer = new CustomerDTO(
                        customerId,
                        (String) row[1], //firstname
                        (String) row[2], //lastname
                        (String) row[3], //username
                        (String) row[4], //gender
                        (String) row[5], //birthday
                        (String) row[6], //emai
                        (String) row[7], //phone
                        (String) row[8], //account create date
                        new ArrayList<>(Collections.singletonList((String) row[9])), //address
                        (String) row[10] //status
                        );
                customerMap.put(customerId, customer);
            }
        }
        return new ArrayList<>(customerMap.values());
    }

    public List<CustomerDTO> getAllBannedCustomers(){
        List<Object[]> customerData = customerRepo.getAllBannedCustomer();

        Map<Integer, CustomerDTO> customerMap = new HashMap<>();

        for (Object[] row : customerData) {
            Integer customerId = (Integer) row[0];

            if(customerMap.containsKey(customerId)) {
                CustomerDTO existingCustomer = customerMap.get(customerId);
                existingCustomer.getCustomerAddress().add((String) row[9]);
            }else{
                CustomerDTO customer = new CustomerDTO(
                        customerId,
                        (String) row[1], //firstname
                        (String) row[2], //lastname
                        (String) row[3], //username
                        (String) row[4], //gender
                        (String) row[5], //birthday
                        (String) row[6], //emai
                        (String) row[7], //phone
                        (String) row[8], //account create date
                        new ArrayList<>(Collections.singletonList((String) row[9])), //address
                        (String) row[10] //status
                );
                customerMap.put(customerId, customer);
            }
        }
        return new ArrayList<>(customerMap.values());
    }

    public List<CustomerDTO> getAllDeletedCustomers(){
        List<Object[]> customerData = customerRepo.getAllDeletedCustomer();

        Map<Integer, CustomerDTO> customerMap = new HashMap<>();

        for (Object[] row : customerData) {
            Integer customerId = (Integer) row[0];

            if(customerMap.containsKey(customerId)) {
                CustomerDTO existingCustomer = customerMap.get(customerId);
                existingCustomer.getCustomerAddress().add((String) row[9]);
            }else{
                CustomerDTO customer = new CustomerDTO(
                        customerId,
                        (String) row[1], //firstname
                        (String) row[2], //lastname
                        (String) row[3], //username
                        (String) row[4], //gender
                        (String) row[5], //birthday
                        (String) row[6], //emai
                        (String) row[7], //phone
                        (String) row[8], //account create date
                        new ArrayList<>(Collections.singletonList((String) row[9])), //address
                        (String) row[10] //status
                );
                customerMap.put(customerId, customer);
            }
        }
        return new ArrayList<>(customerMap.values());
    }

    public userSite updateCustomerById(userSite customer, int id) {
        return customerRepo.findById(id).map(cm -> {
            cm.setFirstname(customer.getFirstname());
            cm.setLastname(customer.getLastname());
            cm.setBirthday(customer.getBirthday());
            return customerRepo.save(cm);
        }).orElseThrow(() -> new CustomerNotFoundException("Sorry, this customer could not be found!"));
    }

    public CustomerDTO getCustomerById(int id){
        List<Object[]> customerData = customerRepo.getCustomerById(id);

        if(customerData.isEmpty()){
            throw new CustomerNotFoundException("Sorry, customer not found with the id: " + id);
        }

        Object[] row = customerData.get(0);

        CustomerDTO customer = new CustomerDTO(
                (Integer) row[0], //id
                (String) row[1], //firstname
                (String) row[2], //lastname
                (String) row[3], //username
                (String) row[4], //gender
                (String) row[5], //birthday
                (String) row[6], //emai
                (String) row[7], //phone
                (String) row[8], //account create date
                new ArrayList<>(), //address
                (String) row[10] //status
        );

        for (Object[] rows : customerData) {
            String address = (String) rows[9];
            customer.getCustomerAddress().add(address);
        }
        return customer;
    }

    public CustomerDTO getBannedCustomerById(int id){
        List<Object[]> customerData = customerRepo.getBannedCustomerById(id);

        if(customerData.isEmpty()){
            throw new CustomerNotFoundException("Sorry, customer not found with the id: " + id);
        }

        Object[] row = customerData.get(0);

        CustomerDTO customer = new CustomerDTO(
                (Integer) row[0], //id
                (String) row[1], //firstname
                (String) row[2], //lastname
                (String) row[3], //username
                (String) row[4], //gender
                (String) row[5], //birthday
                (String) row[6], //emai
                (String) row[7], //phone
                (String) row[8], //account create date
                new ArrayList<>(), //address
                (String) row[10] //status
        );

        for (Object[] rows : customerData) {
            String address = (String) rows[9];
            customer.getCustomerAddress().add(address);
        }
        return customer;
    }

    @Transactional
    public void updateCustomerInfo(int id, CustomerDTO customerDTO){
        if(!customerRepo.existsById(id)){
            throw new CustomerNotFoundException("Customer not found with id: " + id);
        }
//
//        String formattedBirthday = convertToDate(customerDTO.getBirthday());

        customerRepo.updateCustomerInfo(
                id,
                customerDTO.getFirstname(),
                customerDTO.getLastname());
    }

//    private String convertToDate(String dateStr) {
//        try {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            LocalDate date = LocalDate.parse(dateStr, formatter);
//            return date.format(formatter); // Convert back to string in the correct format
//        } catch (DateTimeParseException e) {
//            throw new IllegalArgumentException("Invalid date format: " + dateStr);
//        }
//    }

    @Transactional
    public void deleteCustomer(int id) {
        if(!customerRepo.existsById(id)) {
            throw new CustomerNotFoundException("Sorry, Banner not found!");
        }
        customerRepo.deleteCustomerById(id);
    }

    @Transactional
    public void recoverCustomer(int id) {
        if(!customerRepo.existsById(id)) {
            throw new CustomerNotFoundException("Sorry, Banner not found!");
        }
        customerRepo.recoverCustomerById(id);
    }
}

