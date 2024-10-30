package com.cakkie.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.cakkie.backend.repository.StatisticRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticService {

    private final StatisticRepo statisticRepo;
    public List<Map<String, String>> getStatistics() {
        List<Map<String, String>> statistics = new ArrayList<>();

        // Fetching data from repository
        long orderSales = statisticRepo.getOrderSaleValue();
        int orderProcessing = statisticRepo.getOrderProcessingValue();
        int ordersComplete = statisticRepo.getOrdersCompleteValue();
        int orderCancel = statisticRepo.getOrderCancelValue();
        int outOfStockProducts = statisticRepo.getOutofstockproductsValue();
        int pendingReviews = statisticRepo.getPendingReviewsValue();

        // Populating the list with name-value pairs
        statistics.add(Map.of("name", "Order Sales", "value", String.valueOf(orderSales)));
        statistics.add(Map.of("name", "Orders Processing", "value", String.valueOf(orderProcessing)));
        statistics.add(Map.of("name", "Orders Complete", "value", String.valueOf(ordersComplete)));
        statistics.add(Map.of("name", "Orders Cancel", "value", String.valueOf(orderCancel)));
        statistics.add(Map.of("name", "Out of stock products", "value", String.valueOf(outOfStockProducts)));
        statistics.add(Map.of("name", "Pending Reviews", "value", String.valueOf(pendingReviews)));
        return statistics;
    }

}
