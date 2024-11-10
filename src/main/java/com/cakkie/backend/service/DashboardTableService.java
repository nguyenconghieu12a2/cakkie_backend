package com.cakkie.backend.service;

import com.cakkie.backend.dto.dashboard.DayDataDTO;
import com.cakkie.backend.dto.dashboard.DaySetDTO;
import com.cakkie.backend.dto.dashboard.YearDataDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.cakkie.backend.repository.DashboardTableRepo;
import com.cakkie.backend.dto.TableOrderDTO;
import com.cakkie.backend.dto.TableCustomerDTO;
import com.cakkie.backend.dto.MinMaxYearDTO;

import java.text.NumberFormat;
import java.util.*;

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
            String formattedTotalPayment = currencyFormat.format(orderTotal) + " VND";

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

            String formattedTotalPayment = currencyFormat.format(totalPayment) + " VND";

            TableCustomerDTO customerDTO = new TableCustomerDTO(id, name, email, totalOrder, formattedTotalPayment);
            customerTableDTOList.add(customerDTO);
        }
        return customerTableDTOList;
    }

    public List<Map<String, String>> getCards(){
        List<Map<String, String>> cardsList = new ArrayList<>();

        NumberFormat currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

        long revenue = dashboardTableRepo.getRevenue();
        String formattedRevenue = currencyFormat.format(revenue) + " VND";
        long costs = dashboardTableRepo.getCosts();
        String formattedCosts = currencyFormat.format(costs) + " VND";
        long sales = dashboardTableRepo.getSales();
        String formattedSales = currencyFormat.format(sales) + " VND";
        int totalOrder = dashboardTableRepo.getTotalOrder();

        cardsList.add(Map.of("title", "Revenue", "value", formattedRevenue));
        cardsList.add(Map.of("title", "Costs", "value", formattedCosts));
        cardsList.add(Map.of("title", "Total Sales", "value", formattedSales));
        cardsList.add(Map.of("title", "Total Orders", "value", String.valueOf(totalOrder)));

        return cardsList;
    }

    //DAYS
    public List<DayDataDTO> getChartOrderDay(int month, int year){
        List<Object[]> orderDay = dashboardTableRepo.chartOrderDay(month, year);

        Map<Integer, DayDataDTO> orderDayMap = new HashMap<>();

        for (Object[] row : orderDay) {
            Integer months = (Integer) row[0];
            Integer day = (Integer) row[1];
            Integer value = (Integer) row[2];

            DaySetDTO daySetDTO = new DaySetDTO(day, value);

            if(orderDayMap.containsKey(months)){
                DayDataDTO existingMonth = orderDayMap.get(months);
                existingMonth.getDayset().add(daySetDTO);
            }else{
             DayDataDTO dayDataDTO = new DayDataDTO(
                     months,
                     new ArrayList<>(Collections.singletonList(daySetDTO))
             );
             orderDayMap.put(months, dayDataDTO);
            }
        }
        return new ArrayList<>(orderDayMap.values());
    }

    public List<DayDataDTO> getChartCustomerDay(int month, int year){
        List<Object[]> orderDay = dashboardTableRepo.chartCustomerDay(month, year);

        Map<Integer, DayDataDTO> orderDayMap = new HashMap<>();

        for (Object[] row : orderDay) {
            Integer months = (Integer) row[0];
            Integer day = (Integer) row[1];
            Integer value = (Integer) row[2];

            DaySetDTO daySetDTO = new DaySetDTO(day, value);

            if(orderDayMap.containsKey(months)){
                DayDataDTO existingMonth = orderDayMap.get(months);
                existingMonth.getDayset().add(daySetDTO);
            }else{
                DayDataDTO dayDataDTO = new DayDataDTO(
                        months,
                        new ArrayList<>(Collections.singletonList(daySetDTO))
                );
                orderDayMap.put(months, dayDataDTO);
            }
        }
        return new ArrayList<>(orderDayMap.values());
    }

    //MONTHS
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

    //QUARTERS
    public List<Integer> chartOrderByQuarter(int quarter){
        List<Object[]> resultSet = dashboardTableRepo.chartOrderByQuarter(quarter);
        List<Integer> orderCounts = new ArrayList<>();

        for (Object[] row : resultSet) {
            Integer totalOrders = ((Number) row[1]).intValue();
            orderCounts.add(totalOrders);
        }
        return orderCounts;
    }

    public List<Integer> chartCustomerByQuarter(int quarter){
        List<Object[]> resultSet = dashboardTableRepo.chartCustomerByQuarter(quarter);
        List<Integer> customerCounts = new ArrayList<>();
        for (Object[] row : resultSet) {
            Integer totalCustomer = ((Number) row[1]).intValue();
            customerCounts.add(totalCustomer);
        }
        return customerCounts;
    }

    //YEAR
    public List<YearDataDTO> getChartOrderYear(){
        List<Object[]> resultSet = dashboardTableRepo.chartOrderYear();
        List<YearDataDTO> orderCounts = new ArrayList<>();

        for (Object[] row : resultSet) {
            Integer year = ((Number) row[0]).intValue();
            Integer value = ((Number) row[1]).intValue();

            orderCounts.add(new YearDataDTO(year, value));

        }

        return orderCounts;
    }

    public List<YearDataDTO> getChartCustomerYear(){
        List<Object[]> resultSet = dashboardTableRepo.chartCustomerYear();
        List<YearDataDTO> customerCounts = new ArrayList<>();

        for (Object[] row : resultSet) {
            Integer year = ((Number) row[0]).intValue();
            Integer value = ((Number) row[1]).intValue();

            customerCounts.add(new YearDataDTO(year, value));
        }
        return customerCounts;
    }
}
