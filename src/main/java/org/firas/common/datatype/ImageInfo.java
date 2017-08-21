package org.firas.common.datatype;

import java.awt.image.BufferedImage;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ImageInfo {

    @Getter @Setter private String format;
    @Getter @Setter private BufferedImage image;
}
