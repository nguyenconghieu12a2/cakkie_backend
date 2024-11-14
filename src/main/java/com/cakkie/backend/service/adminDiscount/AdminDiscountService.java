package com.cakkie.backend.service.adminDiscount;

import com.cakkie.backend.dto.adminDiscount.*;
import com.cakkie.backend.exception.CustomerNotFoundException;
import com.cakkie.backend.exception.DiscountNotFoundException;
import com.cakkie.backend.model.discount;
import com.cakkie.backend.repository.adminDiscount.AdminCommonDiscountRepo;
import com.cakkie.backend.repository.adminDiscount.AdminDiscountRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminDiscountService {
    private final AdminDiscountRepo adminDiscountRepo;
    private final AdminCommonDiscountRepo adminCommonDiscountRepo;

    //getCateDiscountList
    public List<CateDiscountListDTO> getCateDiscountList(){
        List<Object[]> cateDiscountList = adminDiscountRepo.getCateDiscountList();
        List<CateDiscountListDTO> cateDiscountListDTOList = new ArrayList<>();

        for (Object[] row : cateDiscountList) {
            int id = ((Number) row[0]).intValue();
            String name = (String) row[1];
            String currentDiscount = (String) row[2];
            double discountValue = ((Number) row[3]).doubleValue();

            // Check if the discount rate should be displayed with or without decimals
            String discountRate;
            if (discountValue == 100 || discountValue == Math.floor(discountValue)) {
                discountRate = (int) discountValue + "%";
            } else {
                discountRate = discountValue + "%";
            }

            CateDiscountListDTO cateDiscountListDTO = new CateDiscountListDTO(
                    id, name, currentDiscount, discountRate
            );
            cateDiscountListDTOList.add(cateDiscountListDTO);
        }
        return cateDiscountListDTOList;
    }

    //getCommon discount list
    public List<CommonDiscountListDTO> getCommonDiscountList(){
        List<Object[]> commonDiscountList = adminCommonDiscountRepo.getCommonDiscountList();
        List<CommonDiscountListDTO> commonDiscountListDTOList = new ArrayList<>();

        for (Object[] row : commonDiscountList) {
            int id = ((Number) row[0]).intValue();
            String name = (String) row[1];
            double discountValue = ((Number) row[2]).doubleValue();

            // Check if the discount rate should be displayed with or without decimals
            String discountRate;
            if (discountValue == 100 || discountValue == Math.floor(discountValue)) {
                discountRate = (int) discountValue + "%";
            } else {
                discountRate = discountValue + "%";
            }

            String startDate = (String) row[3];
            String endDate = (String) row[4];

            CommonDiscountListDTO commonDiscountListDTO = new CommonDiscountListDTO(
                    id, name, discountRate, startDate, endDate
            );
            commonDiscountListDTOList.add(commonDiscountListDTO);
        }
        return commonDiscountListDTOList;
    }

    //detail common discount with applied cate
    public DetailCommonDiscountDTO detailCommonDiscount(int id){
        List<Object[]> detailCommonDiscount = adminCommonDiscountRepo.getDetailCommonDiscountById(id);
        List<Object[]> cateAppliedCommonDiscountList = adminDiscountRepo.getCateAppliedCommonDiscountById(id);
        List<CommonCateAppliedDTO> commonCateAppliedCommonDiscountList = new ArrayList<>();

        // Check if detailCommonDiscount is not null to avoid NullPointerException
        if(detailCommonDiscount.isEmpty()){
            throw new DiscountNotFoundException("Sorry, discount not found with the id: " + id);
        }

        Object[] row = detailCommonDiscount.get(0);

        int discountId = ((Number) row[0]).intValue();
        String discountName = (String) row[1];
        double discountValue = ((Number) row[2]).doubleValue();

        // Check if the discount rate should be displayed with or without decimals
        String discountRate;
        if (discountValue == 100 || discountValue == Math.floor(discountValue)) {
            discountRate = (int) discountValue + "%";
        } else {
            discountRate = discountValue + "%";
        }

        String description = (String) row[3];
        String startDate = (String) row[4];
        String endDate = (String) row[5];

        for (Object[] rows : cateAppliedCommonDiscountList) {
            int cateId = ((Number) rows[0]).intValue();
            String cateName = (String) rows[1];
            CommonCateAppliedDTO commonCateAppliedDTO = new CommonCateAppliedDTO(
                    cateId, cateName
            );
            commonCateAppliedCommonDiscountList.add(commonCateAppliedDTO);
        }
        return new DetailCommonDiscountDTO(
                discountId, discountName,description, discountRate, startDate, endDate, commonCateAppliedCommonDiscountList
        );
    }

    //list Cate NOT Applied Common Discount By Id
    public List<CommonCateAppliedDTO> cateNotAppliedCommonDiscount(int id){
        List<Object[]> cateNotApplied = adminDiscountRepo.getCateNOTAppliedCommonDiscountById(id);

        List<CommonCateAppliedDTO> commonCateAppliedCommonDiscountList = new ArrayList<>();
        for (Object[] row : cateNotApplied) {
            int cateId = ((Number) row[0]).intValue();
            String cateName = (String) row[1];

            CommonCateAppliedDTO commonCateAppliedDTO = new CommonCateAppliedDTO(
                    cateId, cateName
            );
            commonCateAppliedCommonDiscountList.add(commonCateAppliedDTO);
        }
        return commonCateAppliedCommonDiscountList;
    }

    //discount by cate activate (wherether it's common or discrete)
    public List<DetailCateDiscountDTO> getDiscountByCategoryActivate(int id){
        List<Object[]> cateActivate = adminDiscountRepo.getDiscountByCategoryActivate(id);
        List<DetailCateDiscountDTO> detailCateDiscountDTOList = new ArrayList<>();
        for (Object[] row : cateActivate) {
            int cateDisId = ((Number) row[0]).intValue();
            int disId = ((Number) row[1]).intValue();
            int cateId = ((Number) row[2]).intValue();
            String disName = (String) row[3];
            double discountValue = ((Number) row[4]).doubleValue();

            // Check if the discount rate should be displayed with or without decimals
            String discountRate;
            if (discountValue == 100 || discountValue == Math.floor(discountValue)) {
                discountRate = (int) discountValue + "%";
            } else {
                discountRate = discountValue + "%";
            }

            String startDate = (String) row[5];
            String endDate = (String) row[6];

            DetailCateDiscountDTO detailCateDiscountDTO = new DetailCateDiscountDTO(
                    cateDisId, disId, cateId, disName, discountRate, startDate, endDate
            );
            detailCateDiscountDTOList.add(detailCateDiscountDTO);
        }
        return detailCateDiscountDTOList;
    }

    //discrete discount by cate inactivate
    public List<DetailCateDiscountDTO> getDiscountByCategoryInactivate(int id){
        List<Object[]> cateInactivate = adminDiscountRepo.getDiscountByCategoryInactivate(id);
        List<DetailCateDiscountDTO> detailCateDiscountDTOList = new ArrayList<>();
        for (Object[] row : cateInactivate) {
            int cateDisId = ((Number) row[0]).intValue();
            int disId = ((Number) row[1]).intValue();
            int cateId = ((Number) row[2]).intValue();
            String disName = (String) row[3];
            double discountValue = ((Number) row[4]).doubleValue();

            // Check if the discount rate should be displayed with or without decimals
            String discountRate;
            if (discountValue == 100 || discountValue == Math.floor(discountValue)) {
                discountRate = (int) discountValue + "%";
            } else {
                discountRate = discountValue + "%";
            }

            String startDate = (String) row[5];
            String endDate = (String) row[6];

            DetailCateDiscountDTO detailCateDiscountDTO = new DetailCateDiscountDTO(
                    cateDisId, disId, cateId, disName, discountRate, startDate, endDate
            );
            detailCateDiscountDTOList.add(detailCateDiscountDTO);
        }
        return detailCateDiscountDTOList;
    }

    //common discount by cate inactivate
    public List<DetailCateDiscountDTO> getCommonDiscountByCategoryInactivate(int id){
        List<Object[]> cateInactivate = adminDiscountRepo.getCommonDiscountByCategoryInactivate(id);
        List<DetailCateDiscountDTO> detailCateDiscountDTOList = new ArrayList<>();
        for (Object[] row : cateInactivate) {
            int cateDisId = ((Number) row[0]).intValue();
            int disId = ((Number) row[1]).intValue();
            int cateId = ((Number) row[2]).intValue();
            String disName = (String) row[3];
            double discountValue = ((Number) row[4]).doubleValue();

            // Check if the discount rate should be displayed with or without decimals
            String discountRate;
            if (discountValue == 100 || discountValue == Math.floor(discountValue)) {
                discountRate = (int) discountValue + "%";
            } else {
                discountRate = discountValue + "%";
            }

            String startDate = (String) row[5];
            String endDate = (String) row[6];

            DetailCateDiscountDTO detailCateDiscountDTO = new DetailCateDiscountDTO(
                    cateDisId, disId, cateId, disName, discountRate, startDate, endDate
            );
            detailCateDiscountDTOList.add(detailCateDiscountDTO);
        }
        return detailCateDiscountDTOList;
    }
}
