package org.firas.wrap.service;

import java.util.HashMap;
import java.util.Map;

import org.firas.common.request.PageInput;
import org.firas.common.validator.IValidator;
import org.firas.common.validator.ValidationException;
import org.firas.wrap.input.BoxInput;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.firas.common.validator.IntegerValidator;
import org.firas.common.validator.StringValidator;
import org.firas.wrap.repository.BoxRepository;
import org.firas.wrap.entity.Box;
import org.firas.wrap.datatype.BoxIdNotFoundException;
import org.firas.wrap.datatype.BoxNameNotFoundException;
import org.firas.wrap.datatype.BoxNameNotUniqueException;

@Slf4j
@Service
public class BoxService {

    private BoxRepository boxRepository;
    @Autowired
    public void setBoxRepository(BoxRepository boxRepository) {
        this.boxRepository = boxRepository;
    }

    public Box getById(int id) throws BoxIdNotFoundException {
        Box box = boxRepository.findOne(id);
        if (null == box) {
            throw new BoxIdNotFoundException(id, "该ID的出货箱不存在");
        }
        return box;
    }

    public Box getByName(String name)
            throws BoxNameNotFoundException {
        Box box = boxRepository.findFirstByNameAndStatusNot(name, Box.STATUS_DELETED);
        if (null == box) {
            throw new BoxNameNotFoundException(name, "该名称的出货箱不存在");
        }
        return box;
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

    public Page<Box> findBoxs(PageInput input)
            throws ValidationException {
        return boxRepository.findByStatus(
                Box.STATUS_NORMAL,
                input.toPageRequest(true));
    }

    public Page<Box> findUsedBoxs(PageInput input)
            throws ValidationException {
        return boxRepository.findByStatus(
                Box.STATUS_USED,
                input.toPageRequest(true));
    }

    @Transactional
    public Box create(BoxInput input)
            throws ValidationException,
            BoxNameNotUniqueException {
        Map<String, IValidator> validators = new HashMap<>(1, 1f);
        validators.put("name", nameValidator);
        Box box = input.toBox(validators);
        box.setId(null);
        ensureNameUnique(box.getName());
        return boxRepository.save(box);
    }

    @Transactional
    public Box update(BoxInput input)
            throws ValidationException,
            BoxIdNotFoundException,
            BoxNameNotUniqueException {
        Map<String, IValidator> validators = new HashMap<>(2, 1f);
        validators.put("id", idValidator);
        validators.put("name", nameValidator);
        Box box = input.toBox(validators);

        Box b = getById(box.getId());
        boolean changed = false;
        if (!b.getName().equals(box.getName())) {
            changed = true;
            ensureNameUnique(box.getName());
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
        Map<String, IValidator> validators = new HashMap<>(1, 1f);
        validators.put("id", idValidator);
        Box box = input.toBox(validators);

        Box b = getById(box.getId());
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
        Map<String, IValidator> validators = new HashMap<>(1, 1f);
        validators.put("id", idValidator);
        Box box = input.toBox(validators);

        Box b = getById(box.getId());
        if (!b.isStatusDeleted()) {
            b.statusDeleted();
            return boxRepository.save(b);
        }
        return b;
    }

    private void ensureNameUnique(String name) throws BoxNameNotUniqueException {
        try {
            getByName(name);
            throw new BoxNameNotUniqueException(name, "名称为" + name + "的出货箱已存在");
        } catch (BoxNameNotFoundException ex) {}
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
}
