package org.firas.wrap.service;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.firas.common.request.PageInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.firas.common.validator.IValidator;
import org.firas.common.validator.IntegerValidator;
import org.firas.common.validator.StringValidator;
import org.firas.common.validator.ValidationException;
import org.firas.wrap.datatype.ComponentIdNotFoundException;
import org.firas.wrap.datatype.ComponentNameNotFoundException;
import org.firas.wrap.datatype.ComponentNameNotUniqueException;
import org.firas.wrap.entity.Component;
import org.firas.wrap.input.ComponentInput;
import org.firas.wrap.repository.ComponentRepository;

@Slf4j
@Service
public class ComponentService {

    private ComponentRepository componentRepository;
    @Autowired
    public void setComponentRepository(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    public Component getById(int id) throws ComponentIdNotFoundException {
        Component component = componentRepository.findOne(id);
        if (null == component) {
            throw new ComponentIdNotFoundException(id, "该ID的零部件不存在");
        }
        return component;
    }

    public Component getByName(String name)
            throws ComponentNameNotFoundException {
        Component component = componentRepository.findFirstByNameAndStatusNot(
                name, Component.STATUS_DELETED);
        if (null == component) {
            throw new ComponentNameNotFoundException(name, "该名称的零部件不存在");
        }
        return component;
    }

    public Page<Component> findByNameContaining(
            String name, int page, int size) {
        return componentRepository.findByNameContainingAndStatus(
                name, Component.STATUS_DELETED,
                new PageRequest(page - 1, size));
    }

    public Page<Component> findComponents(PageInput input)
            throws ValidationException {
        return componentRepository.findByStatusNot(
                Component.STATUS_DELETED,
                input.toPageRequest(true));
    }

    @Transactional
    public Component create(ComponentInput input)
            throws ValidationException, ComponentNameNotUniqueException {
        Map<String, IValidator> validators = new HashMap<>(1, 1f);
        validators.put("name", nameValidator);
        Component component = input.toComponent(validators);
        ensureNameUnique(component.getName());
        component.setNumber(0);
        return componentRepository.save(component);
    }

    @Transactional
    public Component update(ComponentInput input)
            throws ValidationException,
            ComponentIdNotFoundException,
            ComponentNameNotUniqueException {
        Map<String, IValidator> validators = new HashMap<>(2, 1f);
        validators.put("id", idValidator);
        validators.put("name", nameValidator);
        Component component = input.toComponent(validators);

        Component c = getById(component.getId());
        boolean changed = false;
        if (!c.getName().equals(component.getName())) {
            changed = true;
            ensureNameUnique(component.getName());
            c.setName(component.getName());
        }
        if (changed) {
            return componentRepository.save(c);
        }
        return c;
    }

    @Transactional
    public Component remove(ComponentInput input)
            throws ValidationException,
            ComponentIdNotFoundException {
        Map<String, IValidator> validators = new HashMap<>(1, 1f);
        validators.put("id", idValidator);
        Component component = input.toComponent(validators);

        Component c = getById(component.getId());
        if (!c.isStatusDeleted()) {
            c.statusDeleted();
            return componentRepository.save(c);
        }
        return c;
    }
    
    private void ensureNameUnique(String name) throws ComponentNameNotUniqueException {
        try {
            getByName(name);
            throw new ComponentNameNotUniqueException(name, "名称为" + name + "的零部件已存在");
        } catch (ComponentNameNotFoundException ex) {}
    }

    private static final String ID_MESSAGE = "零部件的ID必须是一个正整数";
    private static final IntegerValidator idValidator = new IntegerValidator(
            ID_MESSAGE, 1, null, ID_MESSAGE, ID_MESSAGE);

    private static final String NAME_MIN_MESSAGE =
            "零部件的名称至少" + Component.NAME_MIN_LENGTH + "个字符";
    private static final String NAME_MAX_MESSAGE =
            "零部件的名称最多" + Component.NAME_MAX_LENGTH + "个字符";
    private static final StringValidator nameValidator = new StringValidator(
            Component.NAME_MAX_LENGTH, NAME_MAX_MESSAGE,
            Component.NAME_MIN_LENGTH, NAME_MIN_MESSAGE);
}
