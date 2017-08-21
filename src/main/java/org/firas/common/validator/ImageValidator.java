package org.firas.common.validator;

import java.util.List;
import java.util.LinkedList;
import java.io.InputStream;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.firas.common.datatype.ImageInfo;

@Slf4j
public class ImageValidator {

    @Getter protected boolean onlyOneError;
    @Getter protected List<ValidationError> errors;

    @Getter @Setter protected String message;
    protected @Getter ImageInfo converted;

    protected static final String
            DEFAULT_MESSAGE = "The input is not an image in the supported format";

    public static final String CODE = "Image";

    protected String code = CODE;


    public ImageValidator() {
        this(DEFAULT_MESSAGE);
    }

    public ImageValidator(String message) {
        this.message = message;
        this.converted = null;
        this.onlyOneError = false;
        this.errors = new LinkedList<ValidationError>();
    }


    public ImageValidator setOnlyOneError(boolean onlyOneError) {
        this.onlyOneError = onlyOneError;
        return this;
    }

    public boolean convertType() {
        return true;
    }

    /**
     * Checks whether file is an image in a supported format
     *
     * @param  MultipartFile file  the file to be validated
     * @return boolean  true if file is an image supported,
     *                  false if the file is not an image or
     *                  the image is in an unsupported format
     */
    public boolean validate(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            if (likeJpg(bytes)) {
                readImage(file.getInputStream(), "jpg");
                return true;
            }
            if (likePng(bytes)) {
                readImage(file.getInputStream(), "png");
                return true;
            }
        } catch (Exception e) {
        }
        this.errors.add(new ValidationError(code, message));
        return false;
    }

    private boolean likeJpg(byte[] bytes) {
        int length = bytes.length;
        if (length < 8) return false;
        return (byte)0xFF == bytes[0] && (byte)0xD8 == bytes[1] &&
                (byte)0xFF == bytes[length - 2] && (byte)0xD9 == bytes[length - 1];
    }

    private boolean likePng(byte[] bytes) {
        int length = bytes.length;
        if (length < 8) return false;
        return (byte)0x89 == bytes[0] && 0x50 == bytes[1] && 0x4E == bytes[2] &&
                0x47 == bytes[3] && 0x0D == bytes[4] && 0x0A == bytes[5] &&
                0x1A == bytes[6] && 0x0A == bytes[7];
    }

    private ImageInfo readImage(InputStream inputStream, String format)
            throws Exception {
        BufferedImage image = ImageIO.read(inputStream);
        if (null == image) throw new Exception(
                "The image is in an unsupported format");
        return converted = new ImageInfo(format, image);
    }
}
