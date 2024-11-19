package com.cakkie.backend.service.adminCustomers;

import com.cakkie.backend.dto.adminCustomer.*;
import com.cakkie.backend.model.userSite;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.cakkie.backend.repository.adminCustomers.AdminCustomerRepo;
import com.cakkie.backend.exception.adminException.CustomerNotFoundException;

import java.text.NumberFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminCustomerService {
    private final AdminCustomerRepo adminCustomerRepo;

    public List<CustomerDTO> getAllCustomers(){
        List<Object[]> customerData = adminCustomerRepo.getAllCustomer();

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
        List<Object[]> customerData = adminCustomerRepo.getAllBannedCustomer();

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
        List<Object[]> customerData = adminCustomerRepo.getAllDeletedCustomer();

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
        return adminCustomerRepo.findById(id).map(cm -> {
            cm.setFirstname(customer.getFirstname());
            cm.setLastname(customer.getLastname());
            cm.setBirthday(customer.getBirthday());
            return adminCustomerRepo.save(cm);
        }).orElseThrow(() -> new CustomerNotFoundException("Sorry, this customer could not be found!"));
    }

    public CustomerDTO getCustomerById(int id){
        List<Object[]> customerData = adminCustomerRepo.getCustomerById(id);

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

    public CustomerBannedDTO getBannedCustomerById(int id){
        List<Object[]> customerData = adminCustomerRepo.getBannedCustomerById(id);

        if(customerData.isEmpty()){
            throw new CustomerNotFoundException("Sorry, customer not found with the id: " + id);
        }

        Object[] row = customerData.get(0);

        CustomerBannedDTO customer = new CustomerBannedDTO(
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
                (String) row[10], //status
                (String) row[11] //banned reason
        );

        for (Object[] rows : customerData) {
            String address = (String) rows[9];
            customer.getCustomerAddress().add(address);
        }
        return customer;
    }

    @Transactional
    public void updateCustomerInfo(int id, CustomerDTO customerDTO){
        if(!adminCustomerRepo.existsById(id)){
            throw new CustomerNotFoundException("Customer not found with id: " + id);
        }
//
//        String formattedBirthday = convertToDate(customerDTO.getBirthday());

        adminCustomerRepo.updateCustomerInfo(
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
        if(!adminCustomerRepo.existsById(id)) {
            throw new CustomerNotFoundException("Sorry, Customer not found!");
        }
        adminCustomerRepo.deleteCustomerById(id);
    }

    @Transactional
    public void recoverCustomer(int id) {
        if(!adminCustomerRepo.existsById(id)) {
            throw new CustomerNotFoundException("Sorry, Customer not found!");
        }
        adminCustomerRepo.recoverCustomerById(id);
    }

    @Transactional
    public void bannedCustomer(int id, BannedCustomerReasonDTO bannedReason) {
        if(!adminCustomerRepo.existsById(id)) {
            throw new CustomerNotFoundException("Sorry, Customer not found!");
        }
        adminCustomerRepo.bannedCustomerById(id, bannedReason.getReason());
    }

    public List<Map<String, Object>> getCustomerStatisticOrder(int id) {
        List<Map<String, Object>> customerStatisticOrder = new ArrayList<>();

        int processingOrder = adminCustomerRepo.getCustomerProcessdingOrder(id);
        int completeOrder = adminCustomerRepo.getCustomerCompleteOrder(id);
        int cancelOrder = adminCustomerRepo.getCustomerCancelOrder(id);

        customerStatisticOrder.add(Map.ofEntries(Map.entry("status", "Processing Order"), Map.entry("value", processingOrder)));
        customerStatisticOrder.add(Map.ofEntries(Map.entry("status", "Completed Order"), Map.entry("value", completeOrder)));
        customerStatisticOrder.add(Map.ofEntries(Map.entry("status", "Cancel Order"), Map.entry("value", cancelOrder)));

        return customerStatisticOrder;
    }

    //Processing order table
    public List<CustomerProcessingDTO> getCustomerProcessingOrder(int id) {
        List<Object[]> resultSet = adminCustomerRepo.getProcessingTableOrder(id);
        return mapToCustomerProcessingOrder(resultSet);
    }

    private List<CustomerProcessingDTO> mapToCustomerProcessingOrder(List<Object[]> resultSet) {
        List<CustomerProcessingDTO> customerProcessingOrder = new ArrayList<>();
        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

        for (Object[] row : resultSet) {
            int orderId = (Integer) row[0];
            String orderDate = (String) row[1];
            String approvedDate = row[2] != null ? (String) row[2] : "Not update yet!";
            String shippingDate = row[3] != null ? (String) row[3] : "Not update yet!";
            Long orderTotal = ((Number) row[4]).longValue();
            String formatterTotalPayment = currencyFormat.format(orderTotal) + " VND";

            CustomerProcessingDTO customerProcessingDTO = new CustomerProcessingDTO(orderId, orderDate, approvedDate, shippingDate, formatterTotalPayment);
            customerProcessingOrder.add(customerProcessingDTO);
        }
        return customerProcessingOrder;
    }

    //Completed order table
    public List<CustomerCompleteDTO> getCustomerCompleteOrder(int id) {
        List<Object[]> resultSet = adminCustomerRepo.getCompleteTableOrder(id);
        return mapToCustomerCompleteOrder(resultSet);
    }

    private List<CustomerCompleteDTO> mapToCustomerCompleteOrder(List<Object[]> resultSet) {
        List<CustomerCompleteDTO> customerCompleteOrder = new ArrayList<>();
        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

        for (Object[] row : resultSet) {
            int orderId = (Integer) row[0];
            String orderDate = (String) row[1];
            String approvedDate = (String) row[2];
            String shippingDate = (String) row[3];
            String arrivedDate = (String) row[4];
            Long orderTotal = ((Number) row[5]).longValue();
            String formatterTotalPayment = currencyFormat.format(orderTotal) + " VND";

            CustomerCompleteDTO customerCompleteDTO = new CustomerCompleteDTO(orderId, orderDate, approvedDate, shippingDate, arrivedDate, formatterTotalPayment);
            customerCompleteOrder.add(customerCompleteDTO);
        }
        return customerCompleteOrder;
    }

    //Cancel order table
    public List<CustomerCancelDTO> getCustomerCancelOrder(int id) {
        List<Object[]> resultSet = adminCustomerRepo.getCancelTableOrder(id);
        return mapToCustomerCancelOrder(resultSet);
    }

    private List<CustomerCancelDTO> mapToCustomerCancelOrder(List<Object[]> resultSet) {
        List<CustomerCancelDTO> customerCancelOrder = new ArrayList<>();
        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

        for (Object[] row : resultSet) {
            int orderId = (Integer) row[0];
            String orderDate = (String) row[1];
            String approvedDate = (String) row[2];
            String cancelDate = (String) row[3];
            String cancelReason = row[4] != null ? (String) row[4] : "No reason!";
            Long orderTotal = ((Number) row[5]).longValue();
            String formatterTotalPayment = currencyFormat.format(orderTotal) + " VND";

            CustomerCancelDTO customerCancelDTO = new CustomerCancelDTO(orderId, orderDate, approvedDate, cancelDate, cancelReason, formatterTotalPayment);
            customerCancelOrder.add(customerCancelDTO);
        }
        return customerCancelOrder;
    }
}

