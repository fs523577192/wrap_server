package org.firas.jiadian.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.firas.common.request.PageInput;
import org.firas.common.validator.IValidator;
import org.firas.common.validator.IntegerValidator;
import org.firas.common.validator.StringValidator;
import org.firas.common.validator.EmailValidator;
import org.firas.common.validator.ChineseMobileValidator;
import org.firas.common.validator.ValidationException;
import org.firas.common.helper.DateTimeHelper;

import org.firas.jiadian.datatype.ActivityIdNotFoundException;
import org.firas.jiadian.datatype.OrderIdNotFoundException;
import org.firas.jiadian.entity.Activity;
import org.firas.jiadian.entity.Order;
import org.firas.jiadian.input.OrderInput;
import org.firas.jiadian.repository.OrderRepository;

@Slf4j
@Service
public class OrderService {

    private OrderRepository orderRepository;
    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order getById(int id) throws OrderIdNotFoundException {
        Order order = orderRepository.findOne(id);
        if (null == order) {
            throw new OrderIdNotFoundException(id, "该ID的维修单不存在");
        }
        return order;
    }

    public Order getById(OrderInput input)
            throws ValidationException,
            OrderIdNotFoundException {
        Map<String, IValidator> validators = new HashMap<>(2, 1f);
        validators.put("id", idValidator);
        Order order = input.toOrder(validators);
        return getById(order.getId());
    }

    public List<Order> listByMobile(String mobile) {
        return orderRepository.findByMobileAndStatusNot(
                mobile, Order.STATUS_DELETED);
    }

    public List<Order> listByMobile(OrderInput input) {
        Map<String, IValidator> validators = new HashMap<>(2, 1f);
        validators.put("mobile", mobileValidator);
        Order order = input.toOrder(validators);
    }

    public List<Order> listBySchoolIdCode(String schoolIdCode) {
        return orderRepository.findBySchoolIdCodeAndStatusNot(
                schoolIdCode, Order.STATUS_DELETED);
    }

    public List<Order> listBySchoolIdCode(OrderInput input) {
        Map<String, IValidator> validators = new HashMap<>(2, 1f);
        validators.put("schoolIdCode", schoolIdCodeValidator);
        Order order = input.toOrder(validators);
    }

    public Page<Order> findByNameContaining(String name, PageInput input)
            throws ValidationException {
        return orderRepository.findByNameContainingAndStatusNot(
                name, Order.STATUS_DELETED,
                input.toPageRequest(true));
    }

    public Page<Order> findOrders(PageInput input)
            throws ValidationException {
        return orderRepository.findByStatusNot(
                Order.STATUS_DELETED,
                input.toPageRequest(true));
    }

    @Transactional
    public Order create(OrderInput input)
            throws ValidationException, OrderNameNotUniqueException {
        Map<String, IValidator> validators = new HashMap<>(16, 1f);
        validators.put("activityId", ActivityService.idValidator);
        validators.put("name", nameValidator);
        validateOwner(validators, input);
        if (input.getWorker() != null) {
            validators.put("worker", workerValidator);
        }
        Order order = input.toOrder(validators);
        order.setId(null);
        return orderRepository.save(order);
    }

    @Transactional
    public Order update(OrderInput input)
            throws ValidationException,
            OrderIdNotFoundException,
            OrderNameNotUniqueException {
        Map<String, IValidator> validators = new HashMap<>(16, 1f);
        validators.put("id", idValidator);
        validators.put("name", nameValidator);
        validateOwner(validators, input);
        if (input.getWorker() != null) {
            validators.put("worker", workerValidator);
        }
        if (input.getReason() != null) {
            validators.put("reason", reasonValidator);
        }
        Order order = input.toOrder(validators);

        Order realOrder = getById(order.getId());
        boolean changed = false;
        if (!realOrder.getName().equals(order.getName())) {
            changed = true;
            realOrder.setName(order.getName());
        }
        if (!Objects.equals(realOrder.getWorker(), order.getWorker())) {
            changed = true;
            realOrder.setWorker(order.getWorker());
        }
        changed = ownerChanged(realOrder, order);
        if (!Objects.equals(realOrder.getReason(), order.getReason())) {
            changed = true;
            realOrder.setReason(order.getReason());
        }
        if (changed) {
            return orderRepository.save(realOrder);
        }
        return realOrder;
    }

    @Transactional
    public Order remove(OrderInput input)
            throws ValidationException,
            OrderIdNotFoundException {
        Map<String, IValidator> validators = new HashMap<>(1, 1f);
        validators.put("id", idValidator);
        Order order = input.toOrder(validators);

        Order a = getById(order.getId());
        if (!a.isStatusDeleted()) {
            a.statusDeleted();
            return orderRepository.save(a);
        }
        return a;
    }
    
    private static final String ID_MESSAGE = "维修单的ID必须是一个正整数";
    private static final IntegerValidator idValidator = new IntegerValidator(
            ID_MESSAGE, 1, null, ID_MESSAGE, ID_MESSAGE);

    private static final String NAME_MIN_MESSAGE =
            "维修物品的名称至少" + Order.NAME_MIN_LENGTH + "个字符";
    private static final String NAME_MAX_MESSAGE =
            "维修物品的名称最多" + Order.NAME_MAX_LENGTH + "个字符";
    private static final StringValidator nameValidator = new StringValidator(
            Order.NAME_MAX_LENGTH, NAME_MAX_MESSAGE,
            Order.NAME_MIN_LENGTH, NAME_MIN_MESSAGE);

    private static final String MOBILE_MESSAGE = "手机号格式不正确";
    private static final ChineseMobileValidator mobileValidator =
            new ChineseMobileValidator(MOBILE_MESSAGE);

    private static final String EMAIL_MESSAGE = "Email格式不正确";
    private static final EmailValidator emailValidator = new EmailValidator(
            EMAIL_MESSAGE, Order.MIN_EMAIL, Order.MAX_EMAIL,
            EMAIL_MESSAGE, EMAIL_MESSAGE);

    private static final String PHONE_PATTERN = "^(\\d{3,4}-)?\\d{7,9}$";
    private static final String PHONE_MESSAGE = "固话格式不正确";
    private static final StringValidator phoneValidator =
            new StringValidator(PHONE_PATTERN, PHONE_MESSAGE);

    private static final String SCHOOL_IDCODE_PATTERN = "^\\d{6,10}$";
    private static final String SCHOOL_IDCODE_MESSAGE = "学号格式不正确";
    private static final StringValidator schoolIdCodeValidator =
            new StringValidator(SCHOOL_IDCODE_PATTERN, SCHOOL_IDCODE_MESSAGE);

    private static final String OWNER_MIN_MESSAGE =
            "维修物品的名称至少" + Order.OWNER_MIN_LENGTH + "个字符";
    private static final String OWNER_MAX_MESSAGE =
            "维修物品的名称最多" + Order.OWNER_MAX_LENGTH + "个字符";
    private static final StringValidator nameValidator = new StringValidator(
            Order.OWNER_MAX_LENGTH, OWNER_MAX_MESSAGE,
            Order.OWNER_MIN_LENGTH, OWNER_MIN_MESSAGE);

    private static final String WORKER_MIN_MESSAGE =
            "维修物品的名称至少" + Order.WORKER_MIN_LENGTH + "个字符";
    private static final String WORKER_MAX_MESSAGE =
            "维修物品的名称最多" + Order.WORKER_MAX_LENGTH + "个字符";
    private static final StringValidator nameValidator = new StringValidator(
            Order.WORKER_MAX_LENGTH, WORKER_MAX_MESSAGE,
            Order.WORKER_MIN_LENGTH, WORKER_MIN_MESSAGE);

    private static final String REASON_MESSAGE = "问题描述超长";
    private static final StringValidator reasonValidator = new StringValidator(
            65535, REASON_MESSAGE);

    private static void validateOwner(
            Map<String, IValidator<?>> validators, OrderInput input) {
        validators.put("owner", ownerValidator);
        if (input.getPhone() == null) {
            validators.put("mobile", mobileValidator);
        } else {
            validators.put("phone", phoneValidator);
            if (input.getMobile() != null) {
                validators.put("mobile", mobileValidator);
            }
        }
        if (input.getSchoolIdCode() != null) {
            validators.put("schoolIdCode", schoolIdCodeValidator);
        }
        if (input.getEmail() != null) {
            validators.put("email", emailValidator);
        }
    }

    private static boolean ownerChanged(Order realOrder, Order order) {
        boolean changed = false;
        if (!Objects.equals(realOrder.getOwner(), order.getOwner())) {
            changed = true;
            realOrder.setOwner(order.getOwner());
        }
        if (!Objects.equals(realOrder.getMobile(), order.getMobile())) {
            changed = true;
            realOrder.setMobile(order.getMobile());
        }
        if (!Objects.equals(realOrder.getPhone(), order.getPhone())) {
            changed = true;
            realOrder.setPhone(order.getPhone());
        }
        if (!Objects.equals(realOrder.getEmail(), order.getEmail())) {
            changed = true;
            realOrder.setEmail(order.getEmail());
        }
        if (!Objects.equals(realOrder.getSchoolIdCode(), order.getSchoolIdCode())) {
            changed = true;
            realOrder.setSchoolIdCode(order.getSchoolIdCode());
        }
        return changed;
    }
}
