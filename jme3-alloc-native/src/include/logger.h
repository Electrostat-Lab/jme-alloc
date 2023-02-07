/**
 * @file logger.h
 * @brief jme3-alloc logger api
 * @author pavl_g
 * @copyright 
 * Copyright (c) 2009-2023 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
#ifndef _LOGGER
#define _LOGGER

#include<stdio.h>
#include<stdlib.h>
#include<string.h>
#include<unistd.h>
#include<time.h>
#include<stdarg.h>

#ifdef _WIN32
    #define FILE_SEPARATOR "\\"
#else
    #define FILE_SEPARATOR "/"
#endif

#define API "Jme3-alloc"
#define DEBUG_FILE "jme3-alloc-debug.txt"
#define INFO "INFO"
#define DEBUG "DEBUG"
#define WARNING "WARNING"
#define ERROR "ERROR"

/**
 * Defines a logger level with [type] and [name].
 */
typedef struct {
    /**
     * Defines a log level.
     */
    const char* level;
    /**
     * Defines the api domain name.
     */
    const char* name;
} Level;

/**
 * Log to the [stdout] standard output file with a specific level.
 * 
 * @param level the logger level
 * @param msg a message to log
 */
static inline void LOG_STDOUT(Level level, const char* msg) {
    #ifdef __ENABLE_LOGGER
        const time_t* currentTime = (const time_t*) calloc(1, sizeof(time_t));
        time((time_t*) currentTime);
        char* calendarTime = ctime(currentTime);
        /* remove line feed */
        calendarTime[strlen(calendarTime) - 1] = '\0';
        fprintf(stdout, "%s/%s/%s: %s \n", calendarTime, level.name, level.level, msg);
        free((void*) currentTime);
    #endif
}

/**
 * Log to the [stderr] standard error file with a specific level.
 * 
 * @param level the logger level
 * @param msg a message to log
 */
static inline void LOG_STDERR(Level level, const char* msg) {
    #ifdef __ENABLE_LOGGER
        const time_t* currentTime = (const time_t*) calloc(1, sizeof(time_t));
        time((time_t*) currentTime);
        char* calendarTime = ctime(currentTime);
        /* remove line feed */
        calendarTime[strlen(calendarTime) - 1] = '\0';
        fprintf(stderr, "%s/%s/%s: %s \n", calendarTime, level.name, level.level, msg);
        free((void*) currentTime);
    #endif
}

/**
 * Log to the [./[pwd]/jme3-alloc-debug.txt] jme3-alloc debug file with a specific level.
 * 
 * @param level the logger level
 * @param msg a message to log
 */
static inline void LOG_DEBUG(Level level, const char* msg) {
    #ifdef __ENABLE_DEBUG_LOGGER
        /* get current working directory */
        const char* debugFileName = (const char*) calloc(255, sizeof(char));
        /* append to a debug output file */
        getcwd((char*) debugFileName, 255);
        strcat((char*) debugFileName, FILE_SEPARATOR);
        strcat((char*) debugFileName, DEBUG_FILE);
        FILE* debug = fopen(debugFileName, "a+");
        /* find the current time */
        const time_t* currentTime = (const time_t*) calloc(1, sizeof(time_t));
        time((time_t*) currentTime);
        char* calendarTime = ctime(currentTime);
        /* remove the line feed */
        calendarTime[strlen(calendarTime) - 1] = '\0';
        fprintf(debug, "%s/%s/%s: %s \n", calendarTime, level.name, level.level, msg);
        /* release the resources */
        fclose(debug);
        free((void*) debugFileName);
        free((void*) currentTime);
    #endif
}

/**
 * Log to the standard output (stdout) file with [info] level
 */
static inline void LOGI(const int count, ...) {
    #ifdef __ENABLE_LOGGER
        va_list args;
        va_start(args, count);
        char* buffer = (char*) calloc(255, sizeof(char));

        for (int i = 0; i < count; i++) {
            strcat(buffer, va_arg(args, const char*));
        }

        Level* level = (Level*) calloc(1, sizeof(Level));
        level->name = API;
        level->level = INFO;
        LOG_STDOUT(*level, buffer);
        free(buffer);
        free(level);

        va_end(args);
    #endif
}

/**
 * Log to the stderr with an error level
 * 
 */
static inline void LOGE(const int count, const size_t capacity, ...) {
    #ifdef __ENABLE_LOGGER
        va_list args;
        va_start(args, count);
        char* buffer = (char*) calloc(capacity, sizeof(char));

        for (int i = 0; i < count; i++) {
            strcat(buffer, va_arg(args, const char*));
        }

        Level* level = (Level*) calloc(1, sizeof(Level));
        level->name = API;
        level->level = ERROR;
        LOG_STDERR(*level, buffer);
        free(buffer);
        free(level);

        va_end(args);
    #endif
}

/**
 * Log to the stdout with a warning level
 * 
 */
static inline void LOGW(const int count, const size_t capacity, ...) {
    #ifdef __ENABLE_LOGGER
        va_list args;
        va_start(args, count);
        char* buffer = (char*) calloc(capacity, sizeof(char));

        for (int i = 0; i < count; i++) {
            strcat(buffer, va_arg(args, const char*));
        }

        Level* level = (Level*) calloc(1, sizeof(Level));
        level->name = API;
        level->level = WARNING;
        LOG_STDOUT(*level, buffer);
        free(buffer);
        free(level);

        va_end(args);
    #endif
}

/**
 * Log to a library log file for debugging purposes
 */
static inline void LOGD(const int count, const size_t capacity, ...) {
    #ifdef __ENABLE_DEBUG_LOGGER
        va_list args;
        va_start(args, count);
        char* buffer = (char*) calloc(capacity, sizeof(char));

        for (int i = 0; i < count; i++) {
            strcat(buffer, va_arg(args, const char*));
        }

        Level* level = (Level*) calloc(1, sizeof(Level));
        level->name = API;
        level->level = DEBUG;
        LOG_DEBUG(*level, buffer);
        free(buffer);
        free(level);

        va_end(args);
    #endif
}
  
#endif