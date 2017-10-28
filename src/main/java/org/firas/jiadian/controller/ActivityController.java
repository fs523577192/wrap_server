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
import org.firas.jiadian.input.ActivityInput;
import org.firas.jiadian.datatype.ActivityIdNotFoundException;
import org.firas.jiadian.datatype.ActivityNameNotUniqueException;
import org.firas.jiadian.entity.Activity;
import org.firas.jiadian.service.ActivityService;

/**
 *
 */
@Slf4j
@RestController("/jiadian/activity")
public class ActivityController extends RequestController {

    private ActivityService activityService;
    @Autowired
    public void setActivityService(ActivityService activityService) {
        this.activityService = activityService;
    }

    private static ModelParser<Activity> activityParser = (activity) -> activity.toMap();

    @RequestMapping(method = RequestMethod.GET)
    public JsonResponse listAllActivities(
            PageInput input
    ) {
        try {
            Page<Activity> result = activityService.findActivitys(input);
            return new JsonResponseSuccess("查询维修活动列表成功",
                    parsePage(result, activityParser));
        } catch (ValidationException ex) {
            return ex.toResponse();
        } catch (Exception ex) {
            return new JsonResponseFailUndefined("查询维修活动列表失败",
                    ex.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public JsonResponse createActivity(
            ActivityInput input,
            HttpServletRequest request
    ) {
        try {
            Activity activity = activityService.create(input);
            return new JsonResponseSuccess("添加维修活动成功",
                    activityParser.parse(activity));
        } catch (ValidationException ex) {
            return ex.toResponse();
        } catch (ActivityNameNotUniqueException ex) {
            return new JsonResponseNameOccupied(ex.getMessage());
        } catch (Exception ex) {
            return new JsonResponseFailUndefined("查询维修活动列表失败",
                    ex.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public JsonResponse updateActivity(
            ActivityInput input,
            HttpServletRequest request
    ) {
        try {
            Activity activity = activityService.update(input);
            return new JsonResponseSuccess("修改维修活动成功",
                    activityParser.parse(activity));
        } catch (ValidationException ex) {
            return ex.toResponse();
        } catch (ActivityIdNotFoundException ex) {
            return new JsonResponseNotFound(ex.getMessage());
        } catch (ActivityNameNotUniqueException ex) {
            return new JsonResponseNameOccupied(ex.getMessage());
        } catch (Exception ex) {
            return new JsonResponseFailUndefined("修改维修活动失败",
                    ex.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public JsonResponse removeActivity(
            ActivityInput input,
            HttpServletRequest request
    ) {
        try {
            Activity activity = activityService.remove(input);
            return new JsonResponseSuccess("删除维修活动成功",
                    activity.getStatusInfo());
        } catch (ValidationException ex) {
            return ex.toResponse();
        } catch (ActivityIdNotFoundException ex) {
            return new JsonResponseNotFound(ex.getMessage());
        } catch (Exception ex) {
            return new JsonResponseFailUndefined("删除维修活动失败",
                    ex.getMessage());
        }
    }

    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public JsonResponse getById(
            ActivityInput input,
            HttpServletRequest request
    ) {
        try {
            Activity activity = activityService.getById(input);
            return new JsonResponseSuccess("查询维修活动成功",
                    activity.toMap());
        } catch (ValidationException ex) {
            return ex.toResponse();
        } catch (ActivityIdNotFoundException ex) {
            return new JsonResponseNotFound(ex.getMessage());
        } catch (Exception ex) {
            return new JsonResponseFailUndefined("查询维修活动失败",
                    ex.getMessage());
        }
    }
}
