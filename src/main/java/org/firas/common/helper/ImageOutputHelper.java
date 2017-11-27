package org.firas.common.helper;

import java.util.Date;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public final class ImageOutputHelper {

    private ImageOutputHelper() {}

    public static final String FORMAT_JPG = "jpg", FORMAT_PNG = "png";

    public static byte[] getBytesFromImage(
            final BufferedImage image,
            final String format
    ) throws IOException {
        final ByteArrayOutputStream bytesOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, format, bytesOutputStream);
        return bytesOutputStream.toByteArray();
    }

    public static HttpHeaders getHttpHeadersForImage(final MediaType mediaType) {
        final long now = new Date().getTime();
        final HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("no-cache, no-store");
        headers.setDate(now);
        headers.setExpires(now);
        headers.setLastModified(now);
        headers.setContentType(mediaType);
        return headers;
    }
}
