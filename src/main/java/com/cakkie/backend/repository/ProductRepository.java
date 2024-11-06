    package com.cakkie.backend.repository;

    import com.cakkie.backend.model.product;
    import com.cakkie.backend.model.productItem;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;

    import java.util.List;

    public interface ProductRepository extends JpaRepository<productItem, Integer> {
        @Query(value = "select \n" +
                "p.id, p.name, c.id, c.cate_name, p.description,p.product_image,p.product_rating,\n" +
                "[pi].id, [pi].size,[pi].qty_in_stock, [pi].price,\n" +
                "pdt.desTitleName, pdi.des_info,\n" +
                "COALESCE(dc.discount_id, 0) as discount_id, COALESCE(d.name, '') as discount_name, COALESCE(d.discount_rate, 0) as discount_rate, FORMAT(COALESCE(d.start_date, ''), 'yyyy-MM-dd') as [start_date], FORMAT(COALESCE(d.end_date, ''), 'yyyy-MM-dd') as [end_date]\n" +
                "from product_item [pi]\n" +
                "join product p on p.id = [pi].pro_id\n" +
                "join productDesInfo pdi on pdi.proID = p.id\n" +
                "join productDesTitle pdt on pdt.desTitleID = pdi.desTitleID\n" +
                "join category c on c.id = p.categoryID\n" +
                "full join discount_category dc on dc.category_id = c.id\n" +
                "full join discount d on d.id = dc.discount_id\n" +
                "where p.id is not null\n" +
                "order by p.id, c.id, [pi].id", nativeQuery = true)
        List<Object[]> getAllProducts();

        @Query(value = "select p.id, p.description, pdt.desTitleName, pdi.des_info\n" +
                "from product_item [pi]\n" +
                "join product p on p.id = [pi].pro_id\n" +
                "join productDesInfo pdi on pdi.proID = p.id\n" +
                "join productDesTitle pdt on pdt.desTitleID = pdi.desTitleID\n" +
                "where p.id = ?1 AND p.is_deleted = 1\n" +
                "order by p.id, [pi].id", nativeQuery = true)
        List<Object[]> getProductsById(int id);


    }
