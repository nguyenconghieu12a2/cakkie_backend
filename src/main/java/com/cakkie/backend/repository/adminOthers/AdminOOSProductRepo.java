package com.cakkie.backend.repository.adminOthers;

import com.cakkie.backend.model.productItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminOOSProductRepo extends JpaRepository<productItem, Integer> {
    @Query(value = "select [pi].id, p.name, COALESCE(NULLIF(TRIM([pi].size), ''), 'No size') as size, p.product_image, [pi].qty_in_stock, [pi].price from product_item [pi]\n" +
            "join product p on p.id = [pi].pro_id\n" +
            "where [pi].qty_in_stock = 0", nativeQuery = true)
    List<Object[]> getOOSProduct();
}
