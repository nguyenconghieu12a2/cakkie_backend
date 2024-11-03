package com.cakkie.backend.repository.customerRV;

import com.cakkie.backend.model.userReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerReviewRepo extends JpaRepository<userReview, Integer> {

    @Query(value = "select \n" +
            "\tur.id as review_id, us.id as [customer_id], us.username as customer_name,\n" +
            "\tp.id as product_id, ur.rating, ur.feedback, ur.review_image,\n" +
            "\tFORMAT(ur.approved_date, 'yyyy-MM-dd hh-mm-ss') as approved_date, FORMAT(ur.valid_date, 'yyyy-MM-dd hh-mm-ss') as valid_date,\n" +
            "\tur.isHide\n" +
            "from user_review ur \n" +
            "join user_site us on us.id = ur.[user_id]\n" +
            "join order_line ol on ol.id = ur.order_product_id\n" +
            "join product_item [pi] on [pi].id = ol.product_item_id\n" +
            "join product p on p.id = [pi].pro_id\n" +
            "where p.id = ?1 and ur.status_id = 1", nativeQuery = true)
    List<Object[]> findByCustomerId(int customerId);
}
