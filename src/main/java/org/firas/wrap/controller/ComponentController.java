package org.firas.wrap.controller;

import java.io.IOException;
import java.io.Serializable;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.firas.common.controller.RequestController;
import org.firas.common.datatype.ModelParser;
import org.firas.common.response.JsonResponse;
import org.firas.common.response.JsonResponseFailUndefined;
import org.firas.common.response.JsonResponseSuccess;
import org.firas.common.response.notfound.JsonResponseNotFound;
import org.firas.common.response.occupied.JsonResponseNameOccupied;
import org.firas.common.response.input.JsonResponseInvalidInput;
import org.firas.common.request.PageInput;
import org.firas.common.validator.ValidationException;
import org.firas.common.helper.ImageOutputHelper;
import org.firas.common.helper.JsonHelper;

import org.firas.wrap.input.ComponentInput;
import org.firas.wrap.datatype.ComponentIdNotFoundException;
import org.firas.wrap.datatype.ComponentNameNotUniqueException;
import org.firas.wrap.entity.Component;
import org.firas.wrap.service.ComponentService;
import org.firas.wrap.helper.BarcodeHelper;
import org.firas.wrap.helper.IntegerPaddingHelper;

/**
 *
 */
@Slf4j
@Controller
@RequestMapping("/component")
public class ComponentController extends RequestController {

    private ComponentService componentService;
    @Autowired
    public void setComponentService(ComponentService componentService) {
        this.componentService = componentService;
    }

    private static ModelParser<Component> componentParser = (component) -> component.toMap();

    @ResponseBody
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

    @ResponseBody
    @RequestMapping(value = "/id", method = RequestMethod.GET)
    public JsonResponse getById(
            ComponentInput input
    ) {
        try {
            Component component = componentService.getById(input);
            return new JsonResponseSuccess("按ID查询零部件成功",
                    component.toMap());
        } catch (ValidationException ex) {
            return ex.toResponse();
        } catch (ComponentIdNotFoundException ex) {
            return new JsonResponseNotFound(ex.getMessage());
        } catch (Exception ex) {
            return new JsonResponseFailUndefined("按ID查询零部件失败",
                    ex.getMessage());
        }
    }

    @RequestMapping(value = "/barcode", method = RequestMethod.GET)
    public ResponseEntity<? extends Serializable> getBarcodeByComponentId(
            ComponentInput input
    ) throws IOException {
        try {
            try {
                final Component component = componentService.getById(input);
                final BufferedImage image = BarcodeHelper.draw(
                        'C' + IntegerPaddingHelper.padTo(component.getId(), 9));
                final BufferedImage result = BarcodeHelper.createResultImage();
                final Graphics2D canvas = BarcodeHelper.getGraphics2D(result);
                final int x = (result.getWidth() - image.getWidth()) >> 1;
                BarcodeHelper.placeOnResult(canvas, image,
                        30, "--== 零部件 ==--", component.getName(),
                        x, 90, 40, 80);
                final byte[] binary = ImageOutputHelper.getBytesFromImage(
                        result, ImageOutputHelper.FORMAT_PNG);
                return new ResponseEntity<byte[]>(
                        binary, pngHeaders, HttpStatus.OK);
            } catch (ValidationException ex) {
                String json = new ObjectMapper().writeValueAsString(
                        ex.toResponse());
                return new ResponseEntity<String>(
                        json, jsonHeaders, HttpStatus.BAD_REQUEST);
            } catch (ComponentIdNotFoundException ex) {
                String json = new ObjectMapper().writeValueAsString(
                        new JsonResponseNotFound(ex.getMessage()));
                return new ResponseEntity<String>(
                        json, jsonHeaders, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            String message = "查询零部件条形码失败";
            String json = new ObjectMapper().writeValueAsString(
                    new JsonResponseFailUndefined(message, ex.getMessage()));
            return new ResponseEntity<String>(
                    json, jsonHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
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
            return new JsonResponseFailUndefined("添加零部件失败",
                    ex.getMessage());
        }
    }

    @ResponseBody
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
            return new JsonResponseFailUndefined("修改零部件失败",
                    ex.getMessage());
        }
    }

    @ResponseBody
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
            return new JsonResponseFailUndefined("删除零部件失败",
                    ex.getMessage());
        }
    }

    private static final HttpHeaders jsonHeaders = new HttpHeaders();
    private static final HttpHeaders pngHeaders = new HttpHeaders();
    static {
        jsonHeaders.add(HttpHeaders.CONTENT_TYPE,
                MediaType.APPLICATION_JSON_UTF8_VALUE);
        pngHeaders.add(HttpHeaders.CONTENT_TYPE,
                MediaType.IMAGE_PNG_VALUE);
    }
}
