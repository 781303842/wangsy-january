package smile.wangsy.january.order.controller;

import smile.wangsy.january.order.model.Order;
import smile.wangsy.january.order.dto.OrderDto;
import smile.wangsy.january.order.vo.OrderVo;
import smile.wangsy.january.order.valid.OrderValid;
import smile.wangsy.january.order.service.OrderService;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import wang.smile.common.base.BaseConstants;
import wang.smile.common.base.BaseResult;

import java.util.List;

/**
 * @author wangsy
 * @date 2018/08/31
 */
@RestController
@RequestMapping("/v1/order")
@Api(value = "xx", description = "xx")
public class OrderController {

    @Autowired
    private OrderService services;

    @PostMapping
    @ApiOperation(value = "新增", httpMethod = "POST", response = OrderController.class, notes = "新增")
    public BaseResult createModel(OrderDto dto) {
        try {
            services.insertByDto(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResult(BaseConstants.FAILED_CODE, BaseConstants.FAILED_MSG, "新增数据异常");
        }
        return new BaseResult(BaseConstants.SUCCESS_CODE, BaseConstants.SUCCESS_MSG, "SUCCESS");
    }

    @PutMapping
    @ApiOperation(value = "修改", httpMethod = "PUT", response = OrderController.class, notes = "修改")
    public BaseResult updateModel(OrderDto dto) {
        try {
            services.updateByDto(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResult(BaseConstants.FAILED_CODE, BaseConstants.FAILED_MSG, "修改数据异常");
        }
        return new BaseResult(BaseConstants.SUCCESS_CODE, BaseConstants.SUCCESS_MSG, "SUCCESS");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除", httpMethod = "DELETE", response = OrderController.class, notes = "删除")
    public BaseResult deleteById(@PathVariable Long id) {
        if(id == null || id <= 0) {
            return new BaseResult(BaseConstants.FAILED_CODE, BaseConstants.FAILED_MSG, "请求参数错误");
        }
        try {
            services.deleteByUpdate(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new BaseResult(BaseConstants.FAILED_CODE, BaseConstants.FAILED_MSG, "删除数据异常");
        }
        return new BaseResult(BaseConstants.SUCCESS_CODE, BaseConstants.SUCCESS_MSG, "SUCCESS");
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "根据id查询", httpMethod = "GET", response = OrderController.class, notes = "根据id查询")
    public BaseResult getById(@PathVariable Long id) {
        if(id == null || id <= 0) {
            return new BaseResult(BaseConstants.FAILED_CODE, BaseConstants.FAILED_MSG, "请求参数错误");
        }
        Order model = services.selectById(id);

        OrderVo modelVo = OrderVo.transModelToVo(model);

        return new BaseResult(BaseConstants.SUCCESS_CODE, BaseConstants.SUCCESS_MSG, modelVo);
    }

    /**
     * 根据条件查询
     * @param valid
     * @return
     */
    @GetMapping
    @ApiOperation(value = "根据condition查询", httpMethod = "GET", response = OrderController.class, notes = "根据条件查询")
    public BaseResult getById(OrderValid valid) {
        if(null == valid) {
            return new BaseResult(BaseConstants.FAILED_CODE, BaseConstants.FAILED_MSG, "请求参数错误");
        }
        List<Order> list = services.selectByConditions(valid);

        List<OrderVo> voList = OrderVo.transModelListToVoList(list);

        return new BaseResult(BaseConstants.SUCCESS_CODE, BaseConstants.SUCCESS_MSG, voList);
    }
}
