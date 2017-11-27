package org.firas.wrap.helper;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;
import org.krysalis.barcode4j.BarcodeGenerator;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;

@Slf4j
public final class BarcodeHelper {

    private BarcodeHelper() {}

    public static BufferedImage draw(String msg)
            throws IOException {
        validateMsg(msg);

        // 精细度
        final int dpi = 150;
        final BarcodeGenerator generator = getGenerator(dpi);

        BitmapCanvasProvider provider = new BitmapCanvasProvider(
                    300, BufferedImage.TYPE_BYTE_BINARY, true, 0);
        generator.generateBarcode(provider, msg);
        provider.finish();
        return provider.getBufferedImage();
    }

    private static BarcodeGenerator getGenerator(final int dpi) {
        final Code39Bean generator = new Code39Bean();

        // module宽度
        final double moduleWidth = UnitConv.in2mm(1f / dpi);

        // 配置对象
        generator.setModuleWidth(moduleWidth);
        generator.setWideFactor(3);
        generator.doQuietZone(false);
        return generator;
    }

    public static BufferedImage createResultImage() {
        return new BufferedImage(
                420, 320, BufferedImage.TYPE_BYTE_GRAY);
    }

    public static Graphics2D getGraphics2D(final BufferedImage result) {
        final Graphics2D canvas = result.createGraphics();
        canvas.setColor(Color.BLACK);
        canvas.setBackground(Color.WHITE);
        canvas.clearRect(0, 0, result.getWidth(), result.getHeight());
        return canvas;
    }

    public static void placeOnResult(
            final Graphics2D canvas, final BufferedImage barcode,
            final int height, final String title, final String content,
            final int x, final int y0, final int y1, final int y2)
            throws Exception {
        canvas.drawImage(barcode, x, y0,
                x + barcode.getWidth(), 90 + barcode.getHeight(),
                0, 20, barcode.getWidth(), barcode.getHeight(), null);
        canvas.setFont(getFont(height));
        canvas.drawString(title, x, y1);
        canvas.drawString(StringTruncateHelper.truncateTo(
                content, 18), x, y2);
    }

    private static void validateMsg(final String msg) {
        if (null == msg || msg.length() <= 0) {
            throw new IllegalArgumentException("消息不能为空");
        }
    }

    public static Font getFont(final Integer height) throws Exception {
        if (!fontCache.containsKey(height)) {
            synchronized (fontCache) {
                if (!fontCache.containsKey(height)) {
                    if (null == baseFont) {
                        setBaseFont();
                    }
                    final Font font = baseFont.deriveFont(0.75f * height);
                    if (!GraphicsEnvironment.getLocalGraphicsEnvironment()
                            .registerFont(font)) {
                        throw new Exception("Cannot register the font");
                    }
                    fontCache.put(height, font);
                }
            }
        }
        return fontCache.get(height);
    }

    private static synchronized void setBaseFont()
            throws FontFormatException, IOException {
        if (null == baseFont) {
            final InputStream fontInputStream =
                    BarcodeHelper.class.getClassLoader()
                    .getResourceAsStream("wqy-zenhei.ttc");
            baseFont = Font.createFont(
                    Font.TRUETYPE_FONT, fontInputStream);
        }
    }

    private static volatile Font baseFont = null;
    private static HashMap<Integer, Font> fontCache = new HashMap<>();
}
