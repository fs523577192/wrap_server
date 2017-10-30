package org.firas.jiadian.controller;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.firas.common.request.PageInput;
import org.firas.common.controller.RequestController;
import org.firas.common.datatype.ModelParser;
import org.firas.common.response.JsonResponse;
import org.firas.common.response.JsonResponseFailUndefined;
import org.firas.common.response.JsonResponseSuccess;
import org.firas.common.response.notfound.JsonResponseNotFound;
import org.firas.common.response.occupied.JsonResponseNameOccupied;
import org.firas.common.response.input.JsonResponseInvalidInput;
import org.firas.common.validator.ValidationException;
import org.firas.jiadian.input.OrderInput;
import org.firas.jiadian.datatype.OrderIdNotFoundException;
import org.firas.jiadian.datatype.ActivityIdNotFoundException;
import org.firas.jiadian.entity.Order;
import org.firas.jiadian.service.OrderService;

/**
 *
 */
@Slf4j
@RestController
@RequestMapping("/jiadian/order")
public class OrderController extends RequestController {

    private OrderService orderService;
    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    private static ModelParser<Order> orderParser = (order) -> order.toMap();

    @RequestMapping(method = RequestMethod.GET)
    public JsonResponse listAllOrders(
            OrderInput orderInput,
            PageInput pageInput
    ) {
        try {
            Page<Order> result = orderService.findOrders(orderInput, pageInput);
            return new JsonResponseSuccess("查询维修单列表成功",
                    parsePage(result, orderParser));
        } catch (ValidationException ex) {
            return ex.toResponse();
        } catch (ActivityIdNotFoundException ex) {
            return new JsonResponseNotFound(ex.getMessage());
        } catch (Exception ex) {
            return new JsonResponseFailUndefined("查询维修单列表失败",
                    ex.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public JsonResponse createOrder(
            OrderInput input,
            HttpServletRequest request
    ) {
        try {
            Order order = orderService.create(input);
            return new JsonResponseSuccess("接修成功",
                    orderParser.parse(order));
        } catch (ValidationException ex) {
            return ex.toResponse();
        } catch (ActivityIdNotFoundException ex) {
            return new JsonResponseNotFound(ex.getMessage());
        } catch (Exception ex) {
            return new JsonResponseFailUndefined("接修失败",
                    ex.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public JsonResponse updateOrder(
            OrderInput input,
            HttpServletRequest request
    ) {
        try {
            Order order = orderService.update(input);
            return new JsonResponseSuccess("修改维修单成功",
                    orderParser.parse(order));
        } catch (ValidationException ex) {
            return ex.toResponse();
        } catch (OrderIdNotFoundException ex) {
            return new JsonResponseNotFound(ex.getMessage());
        } catch (Exception ex) {
            return new JsonResponseFailUndefined("修改维修单失败",
                    ex.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public JsonResponse removeOrder(
            OrderInput input,
            HttpServletRequest request
    ) {
        try {
            Order order = orderService.remove(input);
            return new JsonResponseSuccess("删除维修单成功",
                    order.getStatusInfo());
        } catch (ValidationException ex) {
            return ex.toResponse();
        } catch (OrderIdNotFoundException ex) {
            return new JsonResponseNotFound(ex.getMessage());
        } catch (Exception ex) {
            return new JsonResponseFailUndefined("删除维修单失败",
                    ex.getMessage());
        }
    }

    @RequestMapping(value = "/normal", method = RequestMethod.POST)
    public JsonResponse setNormal(
            OrderInput input,
            HttpServletRequest request
    ) {
        try {
            Order order = orderService.normal(input);
            return new JsonResponseSuccess(
                    "设置维修单状态为“待维修”成功",
                    order.getStatusInfo());
        } catch (ValidationException ex) {
            return ex.toResponse();
        } catch (OrderIdNotFoundException ex) {
            return new JsonResponseNotFound(ex.getMessage());
        } catch (Exception ex) {
            return new JsonResponseFailUndefined(
                    "设置维修单状态为“待维修”成功",
                    ex.getMessage());
        }
    }

    @RequestMapping(value = "/mend", method = RequestMethod.POST)
    public JsonResponse setMending(
            OrderInput input,
            HttpServletRequest request
    ) {
        try {
            Order order = orderService.mend(input);
            return new JsonResponseSuccess(
                    "设置维修单状态为“维修中”成功",
                    order.getStatusInfo());
        } catch (ValidationException ex) {
            return ex.toResponse();
        } catch (OrderIdNotFoundException ex) {
            return new JsonResponseNotFound(ex.getMessage());
        } catch (Exception ex) {
            return new JsonResponseFailUndefined(
                    "设置维修单状态为“维修中”成功",
                    ex.getMessage());
        }
    }

    @RequestMapping(value = "/fail", method = RequestMethod.POST)
    public JsonResponse setFail(
            OrderInput input,
            HttpServletRequest request
    ) {
        try {
            Order order = orderService.fail(input);
            return new JsonResponseSuccess(
                    "设置维修单状态为“维修失败”成功",
                    order.getStatusInfo());
        } catch (ValidationException ex) {
            return ex.toResponse();
        } catch (OrderIdNotFoundException ex) {
            return new JsonResponseNotFound(ex.getMessage());
        } catch (Exception ex) {
            return new JsonResponseFailUndefined(
                    "设置维修单状态为“维修失败”成功",
                    ex.getMessage());
        }
    }

    @RequestMapping(value = "/succeed", method = RequestMethod.POST)
    public JsonResponse setSucceed(
            OrderInput input,
            HttpServletRequest request
    ) {
        try {
            Order order = orderService.succeed(input);
            return new JsonResponseSuccess(
                    "设置维修单状态为“维修成功”成功",
                    order.getStatusInfo());
        } catch (ValidationException ex) {
            return ex.toResponse();
        } catch (OrderIdNotFoundException ex) {
            return new JsonResponseNotFound(ex.getMessage());
        } catch (Exception ex) {
            return new JsonResponseFailUndefined(
                    "设置维修单状态为“维修成功”成功",
                    ex.getMessage());
        }
    }

    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public JsonResponse getById(
            OrderInput input,
            HttpServletRequest request
    ) {
        try {
            Order order = orderService.getById(input);
            return new JsonResponseSuccess("查询维修单成功",
                    order.toMap());
        } catch (ValidationException ex) {
            return ex.toResponse();
        } catch (OrderIdNotFoundException ex) {
            return new JsonResponseNotFound(ex.getMessage());
        } catch (Exception ex) {
            return new JsonResponseFailUndefined("查询维修单失败",
                    ex.getMessage());
        }
    }
}
