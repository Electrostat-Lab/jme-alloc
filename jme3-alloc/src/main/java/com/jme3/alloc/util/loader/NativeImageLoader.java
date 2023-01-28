package com.jme3.alloc.util.loader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Helper utility for loading native images.
 * 
 * @author pavl_g.
 */
public final class NativeImageLoader {
    
    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final int EOF = -1;
    private static boolean isLoaded = false;
	
    private NativeImageLoader() {
    }

    public static void loadLibrary() throws IOException {
        if (NativeVariant.NAME.getData().contains(NativeVariant.Linux)) {
            loadLinux();
        } else if (NativeVariant.NAME.getData().contains(NativeVariant.Windows)) {
            loadWindows();
        } else if (NativeVariant.NAME.getData().contains(NativeVariant.Mac)) {
            loadMac();
        }
    }

    private static void loadLinux() throws IOException {
        if (NativeVariant.is_x86_64(NativeVariant.ARCH.getData())) {
            extractImage(NativeDynamicLibrary.LINUX_x86_64);
        } else {
            extractImage(NativeDynamicLibrary.LINUX_x86);
        }
    }

    private static void loadWindows() throws IOException {
        if (NativeVariant.is_x86_64(NativeVariant.ARCH.getData())) {
            extractImage(NativeDynamicLibrary.WIN_x86_64);
        } else {
            extractImage(NativeDynamicLibrary.WIN_x86);
        }
    }

    private static void loadMac() throws IOException {
        if (NativeVariant.is_x86_64(NativeVariant.ARCH.getData())) {
            extractImage(NativeDynamicLibrary.MAC_x86_64);
        } else {
            extractImage(NativeDynamicLibrary.MAC_x86);
        }
    }

    /**
     * Extracts an appropriate system-based native image from sources.
     * 
     * @param image the image to extract to the user directory.
     */
    private static void extractImage(final NativeDynamicLibrary library) throws IOException {        
        if (isLoaded) {
            return;
        }

        LOCK.lock();
        
        /* CRITICAL SECTION STARTS */

        final String workingDirectory = System.getProperty("user.dir");
        final InputStream nativeLib = NativeImageLoader.class.getClassLoader().getResourceAsStream(library.getAbsoluteLibraryLocation());
        final String extractionLocation = workingDirectory + "/" + library.getLibrary();
        final FileOutputStream fos = new FileOutputStream(extractionLocation);  
        try {
            // extract the shipped native files
            final byte[] buffer = new byte[nativeLib.available()];
            for (int bytes = 0; bytes != EOF; bytes = nativeLib.read(buffer)) {
                /* use the bytes as the buffer length to write valid data */
                fos.write(buffer, 0, bytes);
            }
            System.load(extractionLocation);
            isLoaded = true;
        } finally {
            nativeLib.close();
            fos.close();
            LOCK.unlock();
            /* CRITICAL SECTION ENDS */
        }
    }

    private enum NativeVariant {
        NAME(System.getProperty("os.name")),
        ARCH(System.getProperty("os.arch"));
        
        public static final String Linux = "Linux";
        public static final String Windows = "Windows";
        public static final String Mac = "Mac";
    
        private final String data;
    
        NativeVariant(final String data) {
            this.data = data;
        }
        
        public String getData() {
            return data;
        }
    
        public static boolean is_x86_64(final String arch) {
            return arch.equals("amd64");
        }
        public static boolean is_x86(final String arch) {
            return arch.equals("x86");
        }
    }
}
   
