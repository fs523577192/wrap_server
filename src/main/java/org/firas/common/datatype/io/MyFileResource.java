package org.firas.common.datatype.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Base64;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

public class MyFileResource extends ByteArrayResource {

    private String fileName;

    public MyFileResource(byte[] bytes, String fileName) {
        super(bytes);
        this.fileName = fileName;
    }

    public static MyFileResource getInstance(MultipartFile file)
            throws IOException {
        return new MyFileResource(file.getBytes(),
                file.getOriginalFilename());
    }

    public static MyFileResource getInstance(File file)
            throws FileNotFoundException, IOException {
        int length = (int)file.length();
        byte[] buffer = new byte[length];
        FileInputStream fis = new FileInputStream(file);
        int i;
        for (i = 0; i < length; ++i) {
            int temp = fis.read();
            if (temp < 0) break;
            buffer[i] = (byte)temp;
        }
        if (i >= length) {
            return new MyFileResource(buffer, file.getName());
        }

        byte[] content = new byte[i];
        for (int j = 0; j < i; ++j) {
            content[j] = buffer[j];
        }
        return new MyFileResource(content, file.getName());
    }

    public static MyFileResource getInstanceFromBase64(
            String content, String fileName) {
        byte[] data = Base64.getDecoder().decode(content);
        return new MyFileResource(data, fileName);
    }

    @Override
    public String getFilename() {
        return fileName;
    }
}
