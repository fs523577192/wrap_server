package org.firas.wrap.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.firas.wrap.repository.BoxRepository;
import org.firas.wrap.entity.Box;
import org.firas.wrap.datatype.BoxIdNotFoundException;

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

    @Transactional
    public Box create(String name) {
        Box box = new Box(name);
        return boxRepository.save(box);
    }


    @Transactional
    public Box update(int id, String name) throws BoxIdNotFoundException {
        Box box = getById(id);
        boolean changed = false;
        if (!box.getName().equals(name)) {
            changed = true;
            box.setName(name);
        }
        if (changed) {
            return boxRepository.save(box);
        }
        return box;
    }


    @Transactional
    public Box remove(int id) throws BoxIdNotFoundException {
        Box box = getById(id);
        if (!box.isStatusDeleted()) {
            box.statusDeleted();
            return boxRepository.save(box);
        }
        return box;
    }
}
