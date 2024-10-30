package com.cakkie.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.cakkie.backend.repository.DashboardTableRepo;
import com.cakkie.backend.dto.TableOrderDTO;
import com.cakkie.backend.dto.TableCustomerDTO;
import com.cakkie.backend.dto.MinMaxYearDTO;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardTableService {
    private final DashboardTableRepo dashboardTableRepo;

    public List<TableOrderDTO> getOrderTable(){
        List<Object[]> resultSet = dashboardTableRepo.tableOrder();
        return mapToTableOrderDTO(resultSet);
    }

    private List<TableOrderDTO> mapToTableOrderDTO(List<Object[]> resultSet) {
        List<TableOrderDTO> orderTableDTOList = new ArrayList<>();
        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        for (Object[] row : resultSet) {
            int id = (Integer) row[0];
            String username = (String) row[1];
            String name = (String) row[2];
            String payment = (String) row[3];
            String status = (String) row[4];
            Long orderTotal = ((Number) row[5]).longValue(); // Assuming the order total is numeric
            String formattedTotalPayment = currencyFormat.format(orderTotal) + " đ";

            TableOrderDTO orderDTO = new TableOrderDTO(id, username, name, payment, status, formattedTotalPayment);
            orderTableDTOList.add(orderDTO);
        }
        return orderTableDTOList;
    }

    public List<TableCustomerDTO> getCustomerTable(){
        List<Object[]> resultSet = dashboardTableRepo.tableCustomer();
        return mapToTableCustomerDTO(resultSet);
    }

    public List<TableCustomerDTO> mapToTableCustomerDTO(List<Object[]> resultSet){
        List<TableCustomerDTO> customerTableDTOList = new ArrayList<>();
        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        for (Object[] row : resultSet) {
            int id = (Integer) row[0];
            String name = (String) row[1];
            String email = (String) row[2];
            int totalOrder = ((Number) row[3]).intValue();
            Long totalPayment = ((Number) row[4]).longValue();

            String formattedTotalPayment = currencyFormat.format(totalPayment) + " đ";

            TableCustomerDTO customerDTO = new TableCustomerDTO(id, name, email, totalOrder, formattedTotalPayment);
            customerTableDTOList.add(customerDTO);
        }
        return customerTableDTOList;
    }

    public List<Map<String, String>> getCards(){
        List<Map<String, String>> cardsList = new ArrayList<>();

        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

        long revenue = dashboardTableRepo.getRevenue();
        String formattedRevenue = currencyFormat.format(revenue) + " đ";
        long costs = dashboardTableRepo.getCosts();
        String formattedCosts = currencyFormat.format(costs) + " đ";
        long sales = dashboardTableRepo.getSales();
        String formattedSales = currencyFormat.format(sales) + " đ";
        int totalOrder = dashboardTableRepo.getTotalOrder();

        cardsList.add(Map.of("title", "Revenue", "value", formattedRevenue));
        cardsList.add(Map.of("title", "Costs", "value", formattedCosts));
        cardsList.add(Map.of("title", "Total Sales", "value", formattedSales));
        cardsList.add(Map.of("title", "Total Orders", "value", String.valueOf(totalOrder)));

        return cardsList;
    }

    public List<Integer> getChartOrderData(){
        List<Object[]> resultSet = dashboardTableRepo.chartOrder();
        List<Integer> orderCounts = new ArrayList<>();

        for (Object[] row : resultSet) {
            Integer totalOrders = ((Number) row[1]).intValue();
            orderCounts.add(totalOrders);
        }

        return orderCounts;
    }

    public List<Integer> getChartCustomerData(){
        List<Object[]> resultSet = dashboardTableRepo.chartCustomer();
        List<Integer> customerCounts = new ArrayList<>();

        for (Object[] row : resultSet) {
            Integer totalCustomer = ((Number) row[1]).intValue();
            customerCounts.add(totalCustomer);
        }

        return customerCounts;
    }

    public MinMaxYearDTO getYearOrder(){
        int minYear = dashboardTableRepo.getMinYearOrder();
        int maxYear = dashboardTableRepo.getMaxYearOrder();
        return new MinMaxYearDTO(minYear, maxYear);
    }

    public MinMaxYearDTO getYearCustomer(){
        int minYear = dashboardTableRepo.getMinYearCustomer();
        int maxYear = dashboardTableRepo.getMaxYearCustomer();
        return new MinMaxYearDTO(minYear, maxYear);
    }

    public List<Integer> getChartOrderByYear(int year){
        List<Object[]> results = dashboardTableRepo.chartOrderByYear(year);
        List<Integer> orderCounts = new ArrayList<>();

        for (Object[] row : results) {
            Integer totalOrders = ((Number) row[1]).intValue();
            orderCounts.add(totalOrders);
        }

        return orderCounts;
    }

    public List<Integer> getChartCustomerByYear(int year){
        List<Object[]> resultSet = dashboardTableRepo.chartCustomerByYear(year);
        List<Integer> customerCounts = new ArrayList<>();

        for (Object[] row : resultSet) {
            Integer totalCustomer = ((Number) row[1]).intValue();
            customerCounts.add(totalCustomer);
        }

        return customerCounts;
    }
}
