package com.esafirm.imagepicker.helper;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ImagePickerConfig;

import java.io.Serializable;

public class ConfigUtils {

    public static ImagePickerConfig checkConfig(ImagePickerConfig config) {
        if (config == null) {
            throw new IllegalStateException("ImagePickerConfig cannot be null");
        }
        if (config.getMode() == ImagePicker.MODE_MULTIPLE && config.isReturnAfterFirst()) {
            throw new IllegalStateException("return after first only usable with MODE_SINGLE");
        }
        if (config.getImageLoader() != null && !(config.getImageLoader() instanceof Serializable)) {
            throw new IllegalStateException("Custom image loader must be a class that implement ImageLoader." +
                    " This limitation due to Serializeable");
        }
        return config;
    }

    public static boolean isReturnAfterFirst(ImagePickerConfig config) {
        return config.getMode() == ImagePicker.MODE_SINGLE && config.isReturnAfterFirst();
    }
}
