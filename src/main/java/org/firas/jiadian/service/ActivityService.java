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
import org.firas.common.validator.DateTimeValidator;
import org.firas.common.validator.ValidationException;
import org.firas.common.helper.DateTimeHelper;
import org.firas.jiadian.datatype.ActivityIdNotFoundException;
import org.firas.jiadian.datatype.ActivityNameNotFoundException;
import org.firas.jiadian.datatype.ActivityNameNotUniqueException;
import org.firas.jiadian.entity.Activity;
import org.firas.jiadian.input.ActivityInput;
import org.firas.jiadian.repository.ActivityRepository;

@Slf4j
@Service
public class ActivityService {

    private ActivityRepository activityRepository;
    @Autowired
    public void setActivityRepository(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public Activity getById(int id) throws ActivityIdNotFoundException {
        Activity activity = activityRepository.findOne(id);
        if (null == activity) {
            throw new ActivityIdNotFoundException(id, "该ID的维修活动不存在");
        }
        return activity;
    }

    public Activity getById(ActivityInput input)
            throws ValidationException,
            ActivityIdNotFoundException {
        Map<String, IValidator> validators = new HashMap<>(2, 1f);
        validators.put("id", idValidator);
        Activity activity = input.toActivity(validators);
        return getById(activity.getId());
    }

    public Activity getByName(String name)
            throws ActivityNameNotFoundException {
        Activity activity = activityRepository.findFirstByNameAndStatusNot(
                name, Activity.STATUS_DELETED);
        if (null == activity) {
            throw new ActivityNameNotFoundException(name, "该名称的维修活动不存在");
        }
        return activity;
    }

    public Page<Activity> findByNameContaining(String name, PageInput input)
            throws ValidationException {
        return activityRepository.findByNameContainingAndStatusNot(
                name, Activity.STATUS_DELETED,
                input.toPageRequest(true));
    }

    public Page<Activity> findActivitys(PageInput input)
            throws ValidationException {
        return activityRepository.findByStatusNot(
                Activity.STATUS_DELETED,
                input.toPageRequest(true));
    }

    @Transactional
    public Activity create(ActivityInput input)
            throws ValidationException, ActivityNameNotUniqueException {
        Map<String, IValidator> validators = new HashMap<>(5, 1f);
        validators.put("name", nameValidator);
        validators.put("year", yearValidator);
        validators.put("semester", semesterValidator);
        validators.put("beginTime", beginTimeValidator);
        validators.put("endTime", endTimeValidator);
        Activity activity = input.toActivity(validators);
        activity.setId(null);
        ensureNameUnique(activity.getName());
        return activityRepository.save(activity);
    }

    @Transactional
    public Activity update(ActivityInput input)
            throws ValidationException,
            ActivityIdNotFoundException,
            ActivityNameNotUniqueException {
        Map<String, IValidator> validators = new HashMap<>(6, 1f);
        validators.put("id", idValidator);
        validators.put("name", nameValidator);
        validators.put("year", yearValidator);
        validators.put("semester", semesterValidator);
        validators.put("beginTime", beginTimeValidator);
        validators.put("endTime", endTimeValidator);
        Activity activity = input.toActivity(validators);

        Activity c = getById(activity.getId());
        boolean changed = false;
        if (!c.getName().equals(activity.getName())) {
            changed = true;
            ensureNameUnique(activity.getName());
            c.setName(activity.getName());
        }
        if (!Objects.equals(c.getYear(), activity.getYear())) {
            changed = true;
            c.setYear(activity.getYear());
        }
        if (!Objects.equals(c.getSemester(), activity.getSemester())) {
            changed = true;
            c.setSemester(activity.getSemester());
        }
        if (!Objects.equals(c.getBeginTime(), activity.getBeginTime())) {
            changed = true;
            c.setBeginTime(activity.getBeginTime());
        }
        if (!Objects.equals(c.getEndTime(), activity.getEndTime())) {
            changed = true;
            c.setEndTime(activity.getEndTime());
        }
        if (changed) {
            return activityRepository.save(c);
        }
        return c;
    }

    @Transactional
    public Activity remove(ActivityInput input)
            throws ValidationException,
            ActivityIdNotFoundException {
        Map<String, IValidator> validators = new HashMap<>(1, 1f);
        validators.put("id", idValidator);
        Activity activity = input.toActivity(validators);

        Activity a = getById(activity.getId());
        if (!a.isStatusDeleted()) {
            a.statusDeleted();
            return activityRepository.save(a);
        }
        return a;
    }
    
    private void ensureNameUnique(String name) throws ActivityNameNotUniqueException {
        try {
            getByName(name);
            throw new ActivityNameNotUniqueException(name, "名称为" + name + "的维修活动已存在");
        } catch (ActivityNameNotFoundException ex) {}
    }

    private static final String ID_MESSAGE = "维修活动的ID必须是一个正整数";
    public static final IntegerValidator idValidator = new IntegerValidator(
            ID_MESSAGE, 1, null, ID_MESSAGE, ID_MESSAGE);

    private static final String NAME_MIN_MESSAGE =
            "维修活动的名称至少" + Activity.NAME_MIN_LENGTH + "个字符";
    private static final String NAME_MAX_MESSAGE =
            "维修活动的名称最多" + Activity.NAME_MAX_LENGTH + "个字符";
    private static final StringValidator nameValidator = new StringValidator(
            Activity.NAME_MAX_LENGTH, NAME_MAX_MESSAGE,
            Activity.NAME_MIN_LENGTH, NAME_MIN_MESSAGE);

    private static final String YEAR_MESSAGE =
            "学年必须是" + Activity.MIN_YEAR + "到" + Activity.MAX_YEAR;
    private static final IntegerValidator yearValidator = new IntegerValidator(
            YEAR_MESSAGE, Activity.MIN_YEAR, Activity.MAX_YEAR,
            YEAR_MESSAGE, YEAR_MESSAGE);

    private static final String SEMESTER_MESSAGE =
            "学期必须是" + Activity.MIN_SEMESTER + "到" + Activity.MAX_SEMESTER;
    private static final IntegerValidator semesterValidator = new IntegerValidator(
            SEMESTER_MESSAGE, Activity.MIN_SEMESTER, Activity.MAX_SEMESTER,
            SEMESTER_MESSAGE, SEMESTER_MESSAGE);

    private static final String BEGIN_TIME_MESSAGE = "开始时间格式不正确";
    private static final String END_TIME_MESSAGE = "结束时间格式不正确";
    private static final String BEGIN_LATER_END_MESSAGE = "结束时间必须晚于开始时间";
    private static final DateTimeValidator beginTimeValidator = new DateTimeValidator(
            DateTimeHelper.DATETIME_PATTERN, BEGIN_TIME_MESSAGE);
    private static final DateTimeValidator endTimeValidator = new DateTimeValidator(
            DateTimeHelper.DATETIME_PATTERN, END_TIME_MESSAGE);
}
