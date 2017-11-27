package org.firas.wrap.service;

import java.util.Date;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.firas.common.request.PageInput;
import org.firas.common.validator.IValidator;
import org.firas.common.validator.ValidationException;
import org.firas.common.validator.IntegerValidator;
import org.firas.common.validator.StringValidator;
import org.firas.common.service.ManyToOneUpdater;

import org.firas.wrap.repository.BoxRepository;
import org.firas.wrap.repository.BoxComponentRepository;
import org.firas.wrap.entity.Box;
import org.firas.wrap.entity.Component;
import org.firas.wrap.entity.BoxComponent;
import org.firas.wrap.input.BoxInput;
import org.firas.wrap.validator.BoxComponentValidator;
import org.firas.wrap.datatype.BoxIdNotFoundException;
import org.firas.wrap.datatype.BoxNameNotFoundException;
import org.firas.wrap.datatype.BoxNameNotUniqueException;
import org.firas.wrap.datatype.ComponentIdNotFoundException;

@Slf4j
@Service
public class BoxService {

    private BoxRepository boxRepository;
    @Autowired
    public void setBoxRepository(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }

    private BoxComponentRepository boxComponentRepository;
    @Autowired
    public void setBoxComponentRepository(
            BoxComponentRepository boxComponentRepository) {
        this.boxComponentRepository = boxComponentRepository;
    }

    private ComponentService componentService;
    @Autowired
    public void setComponentService(ComponentService componentService) {
        this.componentService = componentService;
    }

    public Box getById(int id) throws BoxIdNotFoundException {
        Box box = boxRepository.findOne(id);
        if (null == box) {
            throw new BoxIdNotFoundException(id, "该ID的出货箱不存在");
        }
        return box;
    }

    public Box getById(BoxInput input)
            throws BoxIdNotFoundException,
            ValidationException {
        Map<String, IValidator> validators = new HashMap<>(1, 1f);
        validators.put("id", idValidator);
        Box b = input.toBox(validators);
        return getById(b.getId());
    }

    public Box getByName(String name)
            throws BoxNameNotFoundException {
        Box box = boxRepository.findFirstByNameAndStatusNot(
                name, Box.STATUS_DELETED);
        if (null == box) {
            throw new BoxNameNotFoundException(name, "该名称的出货箱不存在");
        }
        return box;
    }

    public HashMap<String, Object> getWithComponentsById(BoxInput input)
            throws BoxIdNotFoundException,
            ValidationException {
        Box box = getById(input);
        List<BoxComponent> components =
                boxComponentRepository.findByBoxAndStatus(
                        box, BoxComponent.STATUS_NORMAL);

        List<HashMap<String, Object>> list = new ArrayList<>(components.size());
        for (BoxComponent item : components) {
            list.add(item.toMap());
        }

        HashMap<String, Object> result = new HashMap<>(2, 1f);
        result.put("box", box.toMap());
        result.put("components", list);
        return result;
    }

    public Page<Box> findByNameContaining(String name, PageInput input)
            throws ValidationException {
        return boxRepository.findByNameContainingAndStatus(
                name, Box.STATUS_NORMAL, input.toPageRequest(true));
    }

    public Page<Box> findUsedByNameContaining(String name, PageInput input)
            throws ValidationException {
        return boxRepository.findByNameContainingAndStatus(
                name, Box.STATUS_USED, input.toPageRequest(true));
    }

    public Page<Box> findBoxes(PageInput input)
            throws ValidationException {
        return boxRepository.findByStatus(
                Box.STATUS_NORMAL,
                input.toPageRequest(true));
    }

    public Page<Box> findUsedBoxes(PageInput input)
            throws ValidationException {
        return boxRepository.findByStatus(
                Box.STATUS_USED,
                input.toPageRequest(true));
    }

    @Transactional
    public Box create(BoxInput input)
            throws ValidationException,
            BoxNameNotUniqueException,
            ComponentIdNotFoundException {
        Map<String, IValidator> validators = new HashMap<>(2, 1f);
        validators.put("name", nameValidator);
        validators.put("components", componentsValidator);
        Box box = input.toBox(validators);
        box.setId(null);
        ensureNameUnique(null, box.getName());
        log.debug(String.valueOf(null == box.getComponents()));
        box = boxRepository.save(box);
        log.debug(String.valueOf(null == box.getComponents()));
        updateBoxComponents(box);
        return box;
    }

    @Transactional
    public Box update(BoxInput input)
            throws ValidationException,
            BoxIdNotFoundException,
            BoxNameNotUniqueException,
            ComponentIdNotFoundException {
        Map<String, IValidator> validators = new HashMap<>(3, 1f);
        validators.put("id", idValidator);
        validators.put("name", nameValidator);
        validators.put("components", componentsValidator);
        Box box = input.toBox(validators);

        Box b = getById(box.getId());
        updateBoxComponents(box);
        boolean changed = false;
        if (!b.getName().equals(box.getName())) {
            changed = true;
            ensureNameUnique(box.getId(), box.getName());
            b.setName(box.getName());
        }
        if (changed) {
            return boxRepository.save(b);
        }
        return b;
    }

    @Transactional
    public Box use(BoxInput input)
            throws ValidationException,
            BoxIdNotFoundException {
        Box b = getById(input);
        if (!b.isStatusUsed()) {
            b.statusUsed();
            return boxRepository.save(b);
        }
        return b;
    }

    @Transactional
    public Box remove(BoxInput input)
            throws ValidationException,
            BoxIdNotFoundException {
        Box b = getById(input);
        if (!b.isStatusDeleted()) {
            b.statusDeleted();
            return boxRepository.save(b);
        }
        return b;
    }

    private static class BoxComponentUpdater
            extends ManyToOneUpdater<Integer, BoxComponent> {

        private Integer boxId;
        private BoxComponentRepository boxComponentRepository;

        public BoxComponentUpdater(Integer boxId,
                BoxComponentRepository boxComponentRepository) {
            this.boxId = boxId;
            this.boxComponentRepository = boxComponentRepository;
        }

        @Override
        public Integer getId(BoxComponent model) {
            return model.getBoxComponentId().getComponentId();
        }

        @Override
        protected boolean modelEquals(BoxComponent a, BoxComponent b) {
            return a.getNumber() == b.getNumber();
        }

        @Override
        public int setStatusByIds(List<Integer> ids, Byte status) {
            Date now = new Date();
            return boxComponentRepository.setStatusByIds(now, status, boxId, ids);
        }

        @Override
        public void batchCreate(Collection<BoxComponent> collection) {
            boxComponentRepository.save(collection);
        }

        @Override
        public void batchUpdate(Collection<BoxComponent> collection) {
            boxComponentRepository.save(collection);
        }
    }

    private void updateBoxComponents(Box box)
            throws ComponentIdNotFoundException {
        ensureComponents(box);
        List<BoxComponent> oldOnes = boxComponentRepository.findByBox(box);
        Collection<BoxComponent> newOnes = box.getComponents().values();
        for (BoxComponent item : newOnes) {
            item.setBox(box);
            item.getBoxComponentId().setBoxId(box.getId());
        }
        new BoxComponentUpdater(box.getId(), boxComponentRepository).
                update(oldOnes, newOnes);
    }

    private void ensureNameUnique(Integer id, String name)
            throws BoxNameNotUniqueException {
        try {
            Box box = getByName(name);
            if (!box.getId().equals(id)) {
                throw new BoxNameNotUniqueException(
                        name, "名称为" + name + "的出货箱已存在");
            }
        } catch (BoxNameNotFoundException ex) {}
    }

    private void ensureComponents(Box box) throws ComponentIdNotFoundException {
        Set<Integer> newIds = box.getComponents().keySet();
        List<Component> components = componentService.findByIds(newIds);
        Map<Integer, Component> map = new HashMap<>(components.size(), 1f);
        for (Component component : components) {
            map.put(component.getId(), component);
        }
        for (Integer newId : newIds) {
            Component component = map.get(newId);
            if (null == component) {
                throw new ComponentIdNotFoundException(newId,
                        "ID为" + newId + "的零部件不存在");
            }
            box.getComponents().get(newId).setComponent(component);
        }
    }

    private static final String ID_MESSAGE = "出货箱的ID必须是一个正整数";
    private static final IntegerValidator idValidator = new IntegerValidator(
            ID_MESSAGE, 1, null, ID_MESSAGE, ID_MESSAGE);

    private static final String NAME_MIN_MESSAGE =
            "出货箱的名称至少" + Box.NAME_MIN_LENGTH + "个字符";
    private static final String NAME_MAX_MESSAGE =
            "出货箱的名称最多" + Box.NAME_MAX_LENGTH + "个字符";
    private static final StringValidator nameValidator = new StringValidator(
            Box.NAME_MAX_LENGTH, NAME_MAX_MESSAGE,
            Box.NAME_MIN_LENGTH, NAME_MIN_MESSAGE);

    private static final String COMPONENT_MESSAGE =
            "零部件输入出错";
    private static final String COMPONENT_ID_MESSAGE =
            "零部件的ID必须是一个正整数";
    private static final String COMPONENT_NUMBER_MESSAGE =
            "一个出货箱至少要有一个零部件";
    private static final BoxComponentValidator componentsValidator =
            new BoxComponentValidator(COMPONENT_MESSAGE,
                        COMPONENT_ID_MESSAGE, COMPONENT_NUMBER_MESSAGE);
}
