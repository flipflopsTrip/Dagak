package com.ssafy.backend.product.controller;

import com.ssafy.backend.common.exception.BaseException;
import com.ssafy.backend.common.exception.MyException;
import com.ssafy.backend.common.response.BaseResponse;
import com.ssafy.backend.product.model.vo.ProductListVO;
import com.ssafy.backend.product.model.vo.ProductVO;
import com.ssafy.backend.product.service.ProductService;
import com.ssafy.backend.user.model.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

import static com.ssafy.backend.common.response.BaseResponseStatus.*;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("list") BaseResponse<?> store(@RequestParam(value = "page", defaultValue = "0") int page){
        ProductListVO productList = productService.getList(page);
        return new BaseResponse<>(productList);
    }

    @PostMapping("")
    public BaseResponse<?> store(@RequestBody Map<String, Object> body, HttpServletRequest httpServletRequest) throws MyException {
        String sign = (String) body.get("sign");
        HttpSession session = httpServletRequest.getSession(false);
        if (session == null) {
            throw new BaseException(NEED_LOGIN);
        }
        User user = (User) session.getAttribute("User");
        String userId = user.getUserId();
        if(sign == null){
            throw new BaseException(EMPTY_SESSION);
        }

        switch (sign){
            case("sell"):
                int inventoryId = Integer.parseInt((String)body.get("inventoryId"));
                productService.sellProduct(inventoryId,userId);

                return new BaseResponse<>(SUCCESS);
            case("buy"):
                int productId = Integer.parseInt((String)body.get("productId"));
                productService.buyProduct(productId,userId);

                return new BaseResponse<>(SUCCESS);
            case("search"):
                int categoryId = -1;
                try{
                    categoryId = Integer.parseInt((String) body.get("categoryId"));
                } catch (Exception e){
                    throw new BaseException(WRONG_TYPE);
                }
                List<ProductVO> searchList = productService.searchList(categoryId);

                return new BaseResponse<>(searchList);
        }

        throw new BaseException(EMPTY_SIGN);
    }

}
