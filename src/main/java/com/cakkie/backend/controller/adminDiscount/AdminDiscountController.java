package com.cakkie.backend.controller.adminDiscount;

import com.cakkie.backend.dto.adminDiscount.*;
import com.cakkie.backend.service.adminDiscount.AdminDiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/admin")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminDiscountController {
    private final AdminDiscountService adminDiscountService;

    //getCate discount list
    @GetMapping("/category-discount-list")
    public List<CateDiscountListDTO> getCateDiscountList(){
        return adminDiscountService.getCateDiscountList();
    }

    //get common discount list
    @GetMapping("/common-discount-list")
    public List<CommonDiscountListDTO> getCommonDiscountList(){
        return adminDiscountService.getCommonDiscountList();
    }

    //detail common discount with applied cate
    @GetMapping("/detail-common-discount/{id}")
    public DetailCommonDiscountDTO getDetailCommonDiscount(@PathVariable int id){
        return adminDiscountService.detailCommonDiscount(id);
    }

    //list Cate NOT Applied Common Discount By Id
    @GetMapping("/cate-not-applied-common-discount/{id}")
    public List<CommonCateAppliedDTO> getCateNotAppliedCommonDiscount(@PathVariable int id){
        return adminDiscountService.cateNotAppliedCommonDiscount(id);
    }

    //discount by cate activate (wherether it's common or discrete)
    @GetMapping("/detail-discount-activate/{id}")
    public List<DetailCateDiscountDTO> getDetailCateActivate(@PathVariable int id){
        return adminDiscountService.getDiscountByCategoryActivate(id);
    }
    //discrete discount by cate inactivate
    @GetMapping("/detail-discount-inactivate/{id}")
    public List<DetailCateDiscountDTO> getDetailCateInactivate(@PathVariable int id){
        return adminDiscountService.getDiscountByCategoryInactivate(id);
    }
    //common discount by cate inactivate
        @GetMapping("/detail-common-discount-activate/{id}")
    public List<DetailCateDiscountDTO> getDetailCommonCateInactivate(@PathVariable int id){
        return adminDiscountService.getCommonDiscountByCategoryInactivate(id);
    }
}
