package com.jme3.alloc.examples;

import com.jme3.alloc.util.loader.NativeBinaryLoader;
import com.jme3.alloc.util.NativeBufferUtils;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.UnsatisfiedLinkError;

/**
 * Tests {@link NativeBinaryLoader#setAutoLoad(boolean)} to disable the auto-extracting and loading of binaries 
 * at the {@link NativeBufferUtils#loadNativeBinary()}.
 * Note: to run type: └──╼ $./gradlew :jme3-alloc-examples:TestDisableAutoLoad :jme3-alloc-examples:run
 * 
 * @author pavl_g
 */
public final class TestDisableAutoLoad {

    static {
        /* disable binary auto-loading */
        NativeBinaryLoader.setAutoLoad(false);
        /* add your custom loader here! */
    }

    private static final Logger LOGGER = Logger.getLogger(TestDisableAutoLoad.class.getName());

    public static void main(String[] args) {
        try {
            LOGGER.log(Level.INFO, "**************** " + TestDisableAutoLoad.class.getName() + "****************");

            ByteBuffer buffer = NativeBufferUtils.clearAlloc(1000);
            buffer.putInt(200);
            NativeBufferUtils.destroy(buffer);
            buffer = null;
        } catch (UnsatisfiedLinkError error) {
            error.printStackTrace();
        } finally {
            LOGGER.log(Level.INFO, "**************** " + TestDisableAutoLoad.class.getName() + "****************");
        }
    }
    
}
