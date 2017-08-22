package org.firas.wrap.service;

import lombok.extern.slf4j.Slf4j;
import org.firas.wrap.datatype.ComponentIdNotFoundException;
import org.firas.wrap.datatype.ComponentNameNotFoundException;
import org.firas.wrap.datatype.ComponentNameNotUniqueException;
import org.firas.wrap.entity.Component;
import org.firas.wrap.repository.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<Component> findByNameContaining(String name) {
        return componentRepository.findByNameContainingAndStatus(
                name, Component.STATUS_DELETED);
    }
    
    @Transactional
    public Component create(Component component) throws ComponentNameNotUniqueException {
        component.setId(null);
        ensureNameUnique(component.getName());
        return componentRepository.save(component);
    }

    @Transactional
    public Component update(Component component) throws ComponentIdNotFoundException {
        Component c = getById(component.getId());
        boolean changed = false;
        if (!c.getName().equals(component.getName())) {
            changed = true;
            c.setName(component.getName());
        }
        if (changed) {
            return componentRepository.save(c);
        }
        return c;
    }


    @Transactional
    public Component remove(Component component) throws ComponentIdNotFoundException {
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
}
