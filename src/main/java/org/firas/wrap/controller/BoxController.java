package org.firas.wrap.controller;

import java.util.HashMap;
import java.io.IOException;
import java.io.Serializable;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

import org.firas.wrap.input.BoxInput;
import org.firas.wrap.datatype.BoxIdNotFoundException;
import org.firas.wrap.datatype.BoxNameNotUniqueException;
import org.firas.wrap.datatype.ComponentIdNotFoundException;
import org.firas.wrap.entity.Box;
import org.firas.wrap.service.BoxService;
import org.firas.wrap.helper.BarcodeHelper;
import org.firas.wrap.helper.IntegerPaddingHelper;

/**
 *
 */
@Slf4j
@Controller
@RequestMapping("/box")
public class BoxController extends RequestController {

    private BoxService boxService;
    @Autowired
    public void setBoxService(BoxService boxService) {
        this.boxService = boxService;
    }

    private static ModelParser<Box> boxParser = (box) -> box.toMap();

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public JsonResponse listAllBoxes(
            PageInput input
    ) {
        try {
            Page<Box> result = boxService.findBoxes(input);
            return new JsonResponseSuccess("查询出货箱列表成功",
                    parsePage(result, boxParser));
        } catch (ValidationException ex) {
            return ex.toResponse();
        } catch (Exception ex) {
            return new JsonResponseFailUndefined("查询出货箱列表失败",
                    ex.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/components", method = RequestMethod.GET)
    public JsonResponse findComponentsByBoxId(
            BoxInput input
    ) {
        try {
            HashMap<String, Object> result =
                    boxService.getWithComponentsById(input);
            return new JsonResponseSuccess("查询出货箱的零部件列表成功",
                    result);
        } catch (ValidationException ex) {
            return ex.toResponse();
        } catch (BoxIdNotFoundException ex) {
            return new JsonResponseNotFound(ex.getMessage());
        } catch (Exception ex) {
            return new JsonResponseFailUndefined("查询出货箱列表失败",
                    ex.getMessage());
        }
    }

    @RequestMapping(value = "/barcode", method = RequestMethod.GET)
    public ResponseEntity<? extends Serializable> getBarcodeByBoxId(
            BoxInput input
    ) throws IOException {
        try {
            try {
                final Box box = boxService.getById(input);
                final BufferedImage image = BarcodeHelper.draw(
                        'B' + IntegerPaddingHelper.padTo(box.getId(), 9));
                final BufferedImage result = BarcodeHelper.createResultImage();
                final Graphics2D canvas = BarcodeHelper.getGraphics2D(result);
                final int x = (result.getWidth() - image.getWidth()) >> 1;
                BarcodeHelper.placeOnResult(canvas, image,
                        30, "--== 出货箱 ==--", box.getName(),
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
            } catch (BoxIdNotFoundException ex) {
                String json = new ObjectMapper().writeValueAsString(
                        new JsonResponseNotFound(ex.getMessage()));
                return new ResponseEntity<String>(
                        json, jsonHeaders, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            String message = "查询出货箱条形码失败";
            String json = new ObjectMapper().writeValueAsString(
                    new JsonResponseFailUndefined(message, ex.getMessage()));
            return new ResponseEntity<String>(
                    json, jsonHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public JsonResponse createBox(
            BoxInput input
    ) {
        try {
            Box box = boxService.create(input);
            return new JsonResponseSuccess("添加出货箱成功",
                    boxParser.parse(box));
        } catch (ValidationException ex) {
            return ex.toResponse();
        } catch (ComponentIdNotFoundException ex) {
            return new JsonResponseNotFound(ex.getMessage());
        } catch (BoxNameNotUniqueException ex) {
            return new JsonResponseNameOccupied(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            return new JsonResponseFailUndefined("添加出货箱失败",
                    ex.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PATCH)
    public JsonResponse updateBox(
            BoxInput input
    ) {
        try {
            Box box = boxService.update(input);
            return new JsonResponseSuccess("修改出货箱成功",
                    boxParser.parse(box));
        } catch (ValidationException ex) {
            return ex.toResponse();
        } catch (BoxIdNotFoundException|ComponentIdNotFoundException ex) {
            return new JsonResponseNotFound(ex.getMessage());
        } catch (BoxNameNotUniqueException ex) {
            return new JsonResponseNameOccupied(ex.getMessage());
        } catch (Exception ex) {
            return new JsonResponseFailUndefined("修改出货箱失败",
                    ex.getMessage());
        }
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public JsonResponse removeBox(
            BoxInput input
    ) {
        try {
            Box box = boxService.remove(input);
            return new JsonResponseSuccess("删除出货箱成功",
                    box.getStatusInfo());
        } catch (ValidationException ex) {
            return ex.toResponse();
        } catch (BoxIdNotFoundException ex) {
            return new JsonResponseNotFound(ex.getMessage());
        } catch (Exception ex) {
            return new JsonResponseFailUndefined("删除出货箱失败",
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
