package com.cakkie.backend.repository.reports;

import com.cakkie.backend.model.userReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportReviewRepo extends JpaRepository<userReview, Integer> {

    @Query(value = "SELECT \n" +
            "    FORMAT(MIN(DATEADD(DAY, - DATEPART(WEEKDAY, ur.comment_date) + 1, ur.comment_date)), 'yyyy-MM-dd') AS date_start,\n" +
            "    FORMAT(MAX(DATEADD(DAY, 7 - DATEPART(WEEKDAY, ur.comment_date), ur.comment_date)), 'yyyy-MM-dd') AS date_end,\n" +
            "    COUNT(\n" +
            "        CASE \n" +
            "            WHEN ur.approved_date IS NULL AND ur.reject_date IS NULL THEN ur.comment_date\n" +
            "        END\n" +
            "    ) AS [pendding_review],\n" +
            "    COUNT(ur.approved_date) AS [approved_review],\n" +
            "    COUNT(ur.reject_date) AS [reject_review],\n" +
            "    COUNT(ur.comment_date) AS [total_review]\n" +
            "FROM user_review ur\n" +
            "JOIN user_review_status usr ON usr.id = ur.status_id\n" +
            "GROUP BY \n" +
            "    DATEPART(YEAR, DATEADD(DAY, - DATEPART(WEEKDAY, ur.comment_date) + 1, ur.comment_date)),\n" +
            "    DATEPART(WEEK, DATEADD(DAY, - DATEPART(WEEKDAY, ur.comment_date) + 1, ur.comment_date)),\n" +
            "    DATEPART(YEAR, DATEADD(DAY, - DATEPART(WEEKDAY, ur.comment_date), ur.comment_date)),\n" +
            "    DATEPART(WEEK, DATEADD(DAY, - DATEPART(WEEKDAY, ur.comment_date), ur.comment_date))\n" +
            "ORDER BY MIN(DATEADD(DAY, - DATEPART(WEEKDAY, ur.comment_date) + 1, ur.comment_date)) ASC;", nativeQuery = true)
    List<Object[]> getCustomerReview();

    @Query(value = "SELECT \n" +
            "    FORMAT(MIN(DATEADD(DAY, - DATEPART(WEEKDAY, ur.comment_date) + 1, ur.comment_date)), 'yyyy-MM-dd') AS date_start,\n" +
            "    FORMAT(MAX(DATEADD(DAY, 7 - DATEPART(WEEKDAY, ur.comment_date), ur.comment_date)), 'yyyy-MM-dd') AS date_end,\n" +
            "\tCOUNT(ur.approved_date) AS [approved_review],\n" +
            "\tCOUNT(ur.reject_date) AS [reject_review],\n" +
            "\tCOUNT(ur.approved_date) + COUNT(ur.reject_date) AS [total_review]\n" +
            "FROM user_review ur\n" +
            "JOIN user_review_status usr ON usr.id = ur.status_id AND ur.comment_date BETWEEN ?1 AND ?2\n" +
            "GROUP BY DATEPART(YEAR, ur.comment_date), DATEPART(WEEK, ur.comment_date)\n" +
            "ORDER BY MIN(DATEADD(DAY, -DATEPART(WEEKDAY, ur.comment_date) + 1, ur.comment_date)) ASC", nativeQuery = true)
    List<Object[]> getCustomerReviewFilter(String startDate, String endDate);

}
