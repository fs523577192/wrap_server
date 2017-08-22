package org.firas.wrap.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

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

    public List<Box> findByNameContaining(String name) {
        return boxRepository.findByNameContainingAndStatus(
                name, Box.STATUS_DELETED);
    }

    @Transactional
    public Box create(Box box) throws BoxNameNotUniqueException {
        box.setId(null);
        ensureNameUnique(box.getName());
        return boxRepository.save(box);
    }

    @Transactional
    public Box update(Box box) throws BoxIdNotFoundException {
        Box b = getById(box.getId());
        boolean changed = false;
        if (!b.getName().equals(box.getName())) {
            changed = true;
            b.setName(box.getName());
        }
        if (changed) {
            return boxRepository.save(b);
        }
        return b;
    }


    @Transactional
    public Box remove(Box box) throws BoxIdNotFoundException {
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
}
