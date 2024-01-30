package com.ssafy.backend.product.controller;

import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.common.response.BaseResponse;
import com.ssafy.backend.product.model.domain.Product;
import com.ssafy.backend.product.model.dto.ProductListResDto;
import com.ssafy.backend.product.service.ProductService;
import com.ssafy.backend.user.model.domain.User;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.ssafy.backend.common.response.BaseResponseStatus.*;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("list") BaseResponse<?> store(@RequestParam(value = "page", defaultValue = "0") int page){
        ProductListResDto productList = productService.getList(page);
        return new BaseResponse<>(productList);
    }

    @PostMapping("")
    public BaseResponse<?> store(@RequestBody Map<String, Object> body, HttpServletRequest httpServletRequest) throws MyException {
        String sign = (String) body.get("sign");
//        HttpSession session = httpServletRequest.getSession(false);
//        String userId = "";
//        if(session != null){
//            User user = (User) session.getAttribute("User");
//            userId = user.getUserId();
//        }
        String userId = "yj";
        if(sign == null){
            throw new BaseException(EMPTY_SESSION);
        }

        switch (sign){
            case("sell"):
                int inventoryId = Integer.parseInt((String)body.get("inventoryId"));
                productService.sellProduct(inventoryId,userId);
                return new BaseResponse<>(SUCCESS_SELL_PRODUCT);
            case("buy"):
                int productId = Integer.parseInt((String)body.get("productId"));
                productService.buyProduct(productId,userId);
                return new BaseResponse<>(SUCCESS_BUY_PRODUCT);
            case("search"):
                int categoryId = -1;
                try{
                    categoryId = Integer.parseInt((String) body.get("categoryId"));
                } catch (Exception e){
                    throw new BaseException(WRONG_TYPE);
                }
                List<Product> searchList = productService.searchList(categoryId);
                return new BaseResponse<>(searchList);
        }
        throw new BaseException(EMPTY_SIGN);
    }

}