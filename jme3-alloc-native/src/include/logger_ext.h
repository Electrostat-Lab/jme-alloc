#ifndef _LOGGER_EXTENSION
#define _LOGGER_EXTENSION

#include<logger.h>
#include<string_utils.h>

/**
 * A quick implementation to log the allocation of a buffer with size on the [stdout] of level [error].
 * 
 * @param address the newly allocated buffer address to log
 * @param size the newly created buffer size in bytes to log
 */
static inline void LOGI_ALLOCMEM(void* address, size_t size) {
    #ifdef __ENABLE_LOGGER
        const char* memAddress = pointerToString(address);
        const char* memSize = ulongToString(size);
        LOGI(DEFAULT_BUFFER_SIZE, 5, "Allocated a new buffer = [ Address = ", memAddress, ", Size = ", memSize, "]");
        free((void*) memAddress);
        free((void*) memSize);
    #endif
}

/**
 * A quick implementation to log the allocation of a buffer with size on the [stderr] of level [error].
 * 
 * @param address the newly allocated buffer address to log
 * @param size the newly created buffer size in bytes to log
 */
static inline void LOGE_ALLOCMEM(void* address, size_t size) {
    #ifdef __ENABLE_LOGGER
        const char* memAddress = pointerToString(address);
        const char* memSize = ulongToString(size);
        LOGE(DEFAULT_BUFFER_SIZE, 5, "Allocated a new buffer = [ Address = ", memAddress, ", Size = ", memSize, "]");
        free((void*) memAddress);
        free((void*) memSize);
    #endif
}

/**
 * A quick implementation to log the allocation of a buffer with size on the [jme3-alloc-debug.log].
 * 
 * @param level log level in string
 * @param address the newly allocated buffer address to log
 * @param size the newly created buffer size in bytes to log
 */
static inline void LOGD_ALLOCMEM(const char* level, void* address, size_t size) {
    #ifdef __ENABLE_DEBUG_LOGGER
        const char* memAddress = pointerToString(address);
        const char* memSize = ulongToString(size);
        LOGD(level, DEFAULT_BUFFER_SIZE, 5, "Allocated a new buffer = [ Address = ", memAddress, ", Size = ", memSize, "]");
        free((void*) memAddress);
        free((void*) memSize);
    #endif
}

/**
 * A quick implementation to log the allocation of a cleared buffer with size on the [stdout] of level [info].
 * 
 * @param address the newly created buffer address to log
 * @param size the newly created buffer size in bytes to log
 */
static inline void LOGI_CALLOCMEM(void* address, size_t size) {
    #ifdef __ENABLE_LOGGER
        const char* memAddress = pointerToString(address);
        const char* memSize = ulongToString(size);
        LOGI(DEFAULT_BUFFER_SIZE, 5, "Allocated a new cleared buffer = [ Address = ", memAddress, ", Size = ", memSize, "]");
        free((void*) memAddress);
        free((void*) memSize);
    #endif
}

/**
 * A quick implementation to log the allocation of a cleared buffer with size on the [stderr] of level [err].
 * 
 * @param address the newly created buffer address to log
 * @param size the newly created buffer size in bytes to log
 */
static inline void LOGE_CALLOCMEM(void* address, size_t size) {
    #ifdef __ENABLE_LOGGER
        const char* memAddress = pointerToString(address);
        const char* memSize = ulongToString(size);
        LOGE(DEFAULT_BUFFER_SIZE, 5, "Allocated a new cleared buffer = [ Address = ", memAddress, ", Size = ", memSize, "]");
        free((void*) memAddress);
        free((void*) memSize);
    #endif
}

/**
 * A quick implementation to log the allocation of a cleared buffer with size on the [jme3-alloc-debug.log].
 * 
 * @param level log level in string
 * @param address the newly created buffer address to log
 * @param size the newly created buffer size in bytes to log
 */
static inline void LOGD_CALLOCMEM(const char* level, void* address, size_t size) {
    #ifdef __ENABLE_DEBUG_LOGGER
        const char* memAddress = pointerToString(address);
        const char* memSize = ulongToString(size);
        LOGD(level, DEFAULT_BUFFER_SIZE, 5, "Allocated a new cleared buffer = [ Address = ", memAddress, ", Size = ", memSize, "]");
        free((void*) memAddress);
        free((void*) memSize);
    #endif
}


/**
 * A quick implementation to log the memory set of a buffer with size on the [stdout] of level [info].
 * 
 * @param address the newly created buffer address to log
 * @param size the newly created buffer size in bytes to log
 * @param value the value of the buffer elements to set
 */
static inline void LOGI_SETMEM(void* address, size_t size, int value) {
    #ifdef __ENABLE_LOGGER
        const char* memAddress = pointerToString(address);
        const char* memSize = ulongToString(size);
        const char* bufferValue = intToString(value);
        LOGI(DEFAULT_BUFFER_SIZE, 9, "Memory set buffer = ", "[ Address = ", memAddress, ", Size = ", memSize, " ] elements to = ", "[ Value = ", bufferValue, " ]");
        free((void*) memAddress);
        free((void*) memSize);
        free((void*) bufferValue);
    #endif
}

/**
 * A quick implementation to log the memory set of a buffer with size on the [stderr] of level [error].
 * 
 * @param address the newly created buffer address to log
 * @param size the newly created buffer size in bytes to log
 * @param value the value of the buffer elements to set
 */
static inline void LOGE_SETMEM(void* address, size_t size, int value) {
    #ifdef __ENABLE_LOGGER
        const char* memAddress = pointerToString(address);
        const char* memSize = ulongToString(size);
        const char* bufferValue = intToString(value);
        LOGE(DEFAULT_BUFFER_SIZE, 9, "Memory set buffer = ", "[ Address = ", memAddress, ", Size = ", memSize, " ] elements to = ", "[ Value = ", bufferValue, " ]");
        free((void*) memAddress);
        free((void*) memSize);
        free((void*) bufferValue);
    #endif
}

/**
 * A quick implementation to log the memory set of a buffer with size on the [jme3-alloc-debug.log].
 * 
 * @param level log level in string 
 * @param address the newly created buffer address to log
 * @param size the newly created buffer size in bytes to log
 * @param value the value of the buffer elements to set
 */
static inline void LOGD_SETMEM(const char* level, void* address, size_t size, int value) {
    #ifdef __ENABLE_DEBUG_LOGGER
        const char* memAddress = pointerToString(address);
        const char* memSize = ulongToString(size);
        const char* bufferValue = intToString(value);
        LOGD(level, DEFAULT_BUFFER_SIZE, 9, "Memory set buffer = ", "[ Address = ", memAddress, ", Size = ", memSize, " ] elements to ", "[ Value = ", bufferValue, " ]");
        free((void*) memAddress);
        free((void*) memSize);
        free((void*) bufferValue);
    #endif
}

/**
 * A quick implementation to log the buffer memory copy from a buffer to another on the [stdout] stream.
 * 
 * @param toSize the size of the (substrate) to-buffer
 * @param toAddress the start address of the (substrate) to-buffer
 * @param fromSize the size of the (subject) from-buffer
 * @param fromAddress the start address of the (subject) from-buffer
 */
static inline void LOGI_COPYMEM(size_t toSize, void* toAddress, size_t fromSize, void* fromAddress) {
    #ifdef __ENABLE_LOGGER
        const char* toMemAddress = pointerToString(toAddress);
        const char* fromMemAddress = pointerToString(fromAddress);
        const char* toMemSize = longToString(toSize);
        const char* fromMemSize = longToString(fromSize);
        LOGI(DEFAULT_BUFFER_SIZE, 9, "Copied memory from buffer = [ Address = ", fromMemAddress, ", Size = ", fromMemSize, " ] to buffer = [ Address = ",
             toMemAddress, ", Size = ", fromMemAddress, " ]");
        free((void*) toMemAddress);
        free((void*) fromMemAddress);
        free((void*) toMemSize);
        free((void*) fromMemSize);
    #endif
}

/**
 * A quick implementation to log the buffer memory copy from a buffer to another on the [jme3-alloc-debug.log] file.
 * 
 * @param level log level in string
 * @param toSize the size of the (substrate) to-buffer
 * @param toAddress the start address of the (substrate) to-buffer
 * @param fromSize the size of the (subject) from-buffer
 * @param fromAddress the start address of the (subject) from-buffer
 */
static inline void LOGD_COPYMEM(const char* level, size_t toSize, void* toAddress, size_t fromSize, void* fromAddress) {
    #ifdef __ENABLE_DEBUG_LOGGER
        const char* toMemAddress = pointerToString(toAddress);
        const char* fromMemAddress = pointerToString(fromAddress);
        const char* toMemSize = longToString(toSize);
        const char* fromMemSize = longToString(fromSize);
        LOGD(level, DEFAULT_BUFFER_SIZE, 9, "Copied memory from buffer = [ Address = ", fromMemAddress, ", Size = ", fromMemSize, " ] to buffer = [ Address = ",
             toMemAddress, ", Size = ", toMemSize, " ]");
        free((void*) toMemAddress);
        free((void*) fromMemAddress);
        free((void*) toMemSize);
        free((void*) fromMemSize);
    #endif
}

/**
 * A quick implementation to log the destruction of a buffer with size on the [stdout] as [info].
 * 
 * @param address the newly created buffer address to log
 * @param size the newly created buffer size in bytes to log
 */
static inline void LOGI_DESTROYMEM(void* address, size_t size) {
    #ifdef __ENABLE_LOGGER
        const char* memAddress = pointerToString(address);
        const char* memSize = ulongToString(size);
        LOGI(DEFAULT_BUFFER_SIZE, 5, "Destructed buffer = [ Address = ", memAddress, ", Size = ", memSize, "]");
        free((void*) memAddress);
        free((void*) memSize);
    #endif
}

/**
 * A quick implementation to log the destruction of a buffer with size on the [stderr] as [error].
 * 
 * @param address the newly created buffer address to log
 * @param size the newly created buffer size in bytes to log
 */
static inline void LOGE_DESTROYMEM(void* address, size_t size) {
    #ifdef __ENABLE_LOGGER
        const char* memAddress = pointerToString(address);
        const char* memSize = ulongToString(size);
        LOGE(DEFAULT_BUFFER_SIZE, 5, "Destructed buffer = [ Address = ", memAddress, ", Size = ", memSize, "]");
        free((void*) memAddress);
        free((void*) memSize);
    #endif
}

/**
 * A quick implementation to log the destruction of a buffer with size on the [jme3-alloc-debug.log].
 * 
 * @param level the log level in string
 * @param address the newly created buffer address to log
 * @param size the newly created buffer size in bytes to log
 */
static inline void LOGD_DESTROYMEM(const char* level, void* address, size_t size) {
    #ifdef __ENABLE_DEBUG_LOGGER
        const char* memAddress = pointerToString(address);
        const char* memSize = ulongToString(size);
        LOGD(level, DEFAULT_BUFFER_SIZE, 5, "Destructed buffer = [ Address = ", memAddress, ", Size = ", memSize, "]");
        free((void*) memAddress);
        free((void*) memSize);
    #endif
}

/**
 * A quick implementation to log the destruction of a buffer with size on the [jme3-alloc-debug.log].
 * 
 * @param level the log level in string
 * @param address the newly created buffer address to log
 */
static inline void LOGD_DESTROYMEM_BY_ADDRESS(const char* level, void* address) {
    #ifdef __ENABLE_DEBUG_LOGGER
        const char* memAddress = pointerToString(address);
        LOGD(level, DEFAULT_BUFFER_SIZE, 3, "Destructed buffer = [ Address = ", memAddress, " ]");
        free((void*) memAddress);
    #endif
}

/**
 * A quick implementation to log a buffer with size on the [stdout] as [info].
 * 
 * @param address the newly created buffer address to log
 * @param size the newly created buffer size in bytes to log
 */
static inline void LOGI_MEMORY(void* address, size_t size) {
    #ifdef __ENABLE_LOGGER
        const char* memAddress = pointerToString(address);
        const char* memSize = ulongToString(size);
        LOGI(DEFAULT_BUFFER_SIZE, 5, "Buffer = [ Address = ", memAddress, ", Size = ", memSize, "]");
        free((void*) memAddress);
        free((void*) memSize);
    #endif
}

/**
 * A quick implementation to log a buffer with size on the [stderr] as [error].
 * 
 * @param address the newly created buffer address to log
 * @param size the newly created buffer size in bytes to log
 */
static inline void LOGE_MEMORY(void* address, size_t size) {
    #ifdef __ENABLE_LOGGERs
        const char* memAddress = pointerToString(address);
        const char* memSize = ulongToString(size);
        LOGE(DEFAULT_BUFFER_SIZE, 5, "Buffer = [ Address = ", memAddress, ", Size = ", memSize, "]");
        free((void*) memAddress);
        free((void*) memSize);
    #endif
}

/**
 * A quick implementation to log a buffer with size on the [jme3-alloc-debug.log].
 * 
 * @param level the log level in string
 * @param address the newly created buffer address to log
 * @param size the newly created buffer size in bytes to log
 */
static inline void LOGD_MEMORY(const char* level, void* address, size_t size) {
    #ifdef __ENABLE_DEBUG_LOGGER
        const char* memAddress = pointerToString(address);
        const char* memSize = ulongToString(size);
        LOGD(level, DEFAULT_BUFFER_SIZE, 5, "Buffer = [ Address = ", memAddress, ", Size = ", memSize, "]");
        free((void*) memAddress);
        free((void*) memSize);
    #endif
}

#endif 
