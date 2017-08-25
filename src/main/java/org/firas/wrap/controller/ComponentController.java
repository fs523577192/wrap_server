package org.firas.wrap.controller;

import lombok.extern.slf4j.Slf4j;
import org.firas.common.request.PageInput;
import org.firas.common.response.input.JsonResponseInvalidInput;
import org.firas.common.validator.ValidationException;
import org.firas.wrap.input.ComponentInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.firas.common.controller.RequestController;
import org.firas.common.datatype.ModelParser;
import org.firas.common.response.JsonResponse;
import org.firas.common.response.JsonResponseFailUndefined;
import org.firas.common.response.JsonResponseSuccess;
import org.firas.common.response.notfound.JsonResponseNotFound;
import org.firas.common.response.occupied.JsonResponseNameOccupied;
import org.firas.wrap.datatype.ComponentIdNotFoundException;
import org.firas.wrap.datatype.ComponentNameNotUniqueException;
import org.firas.wrap.entity.Component;
import org.firas.wrap.service.ComponentService;

/**
 *
 */
@Slf4j
@RestController
public class ComponentController extends RequestController {

    private ComponentService componentService;
    @Autowired
    public void setComponentService(ComponentService componentService) {
        this.componentService = componentService;
    }

    private static ModelParser<Component> componentParser = (component) -> component.toMap();

    @RequestMapping(method = RequestMethod.GET)
    public JsonResponse listAllComponents(
            PageInput input
    ) {
        try {
            Page<Component> result = componentService.findComponents(input);
            return new JsonResponseSuccess("查询零部件列表成功",
                    parsePage(result, componentParser));
        } catch (ValidationException ex) {
            return ex.toResponse();
        } catch (Exception ex) {
            return new JsonResponseFailUndefined("查询零部件列表失败",
                    ex.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public JsonResponse createComponent(
            ComponentInput input
    ) {
        try {
            Component component = componentService.create(input);
            return new JsonResponseSuccess("添加零部件成功",
                    componentParser.parse(component));
        } catch (ValidationException ex) {
            return ex.toResponse();
        } catch (ComponentNameNotUniqueException ex) {
            return new JsonResponseNameOccupied(ex.getMessage());
        } catch (Exception ex) {
            return new JsonResponseFailUndefined("查询零部件列表失败",
                    ex.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public JsonResponse updateComponent(
            ComponentInput input
    ) {
        try {
            Component component = componentService.update(input);
            return new JsonResponseSuccess("修改零部件成功",
                    componentParser.parse(component));
        } catch (ValidationException ex) {
            return ex.toResponse();
        } catch (ComponentIdNotFoundException ex) {
            return new JsonResponseNotFound(ex.getMessage());
        } catch (ComponentNameNotUniqueException ex) {
            return new JsonResponseNameOccupied(ex.getMessage());
        } catch (Exception ex) {
            return new JsonResponseFailUndefined("查询零部件列表失败",
                    ex.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public JsonResponse removeComponent(
            ComponentInput input
    ) {
        try {
            Component component = componentService.remove(input);
            return new JsonResponseSuccess("删除零部件成功",
                    component.getStatusInfo());
        } catch (ValidationException ex) {
            return ex.toResponse();
        } catch (ComponentIdNotFoundException ex) {
            return new JsonResponseNotFound(ex.getMessage());
        } catch (Exception ex) {
            return new JsonResponseFailUndefined("查询零部件列表失败",
                    ex.getMessage());
        }
    }
}
