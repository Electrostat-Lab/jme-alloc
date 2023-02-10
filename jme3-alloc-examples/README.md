# jme3-alloc-examples gradle module

This module holds a couple of simple techdemos to test the api.

## Here is the list of the available examples and how to run and build a runnable jar for stress testing: 
- [x] [TestNativeBufferUtils](link):
```bash
└──╼ $ ./gradlew :jme3-alloc-examples:copyLibs && 
       ./gradlew :jme3-alloc-examples:TestNativeBufferUtils \
                 :jme3-alloc-examples:createJar \
                 :jme3-alloc-examples:run
```
- [x] [TestMemorySet](link):
```bash
└──╼ $ ./gradlew :jme3-alloc-examples:copyLibs && 
       ./gradlew :jme3-alloc-examples:TestMemorySet \
                 :jme3-alloc-examples:createJar \
                 :jme3-alloc-examples:run
```
- [x] [TestMemoryCopy](link): 
```bash
└──╼ $ ./gradlew :jme3-alloc-examples:copyLibs && 
       ./gradlew :jme3-alloc-examples:TestMemoryCopy \
                 :jme3-alloc-examples:createJar \ 
                 :jme3-alloc-examples:run
```
- [x] [TestJvmCrashlogs](link):
```bash
└──╼ $ ./gradlew :jme3-alloc-examples:copyLibs && 
       ./gradlew :jme3-alloc-examples:TestJvmCrashlogs \
                 :jme3-alloc-examples:createJar \ 
                 :jme3-alloc-examples:run
```
- [x] [TestDirtyMultithreading](link):
```bash
└──╼ $ ./gradlew :jme3-alloc-examples:copyLibs && 
       ./gradlew :jme3-alloc-examples:TestDirtyMultithreading \
                 :jme3-alloc-examples:createJar \ 
                 :jme3-alloc-examples:run
```
- [x] [StressLauncher](link):
```bash
└──╼ $ ./gradlew :jme3-alloc-examples:copyLibs && 
       ./gradlew :jme3-alloc-examples:StressLauncher \
                 :jme3-alloc-examples:createJar \ 
                 :jme3-alloc-examples:run
```
- [x] [SimpleLaunhcer](link):
```bash
└──╼ $ ./gradlew :jme3-alloc-examples:copyLibs && 
       ./gradlew :jme3-alloc-examples:SimpleLaunhcer \
                 :jme3-alloc-examples:createJar \ 
                 :jme3-alloc-examples:run
```

## Stress testing and memory profiling: 

- Prepare a memory profiling monitor, this example is demonstrated using the [GNOME-System-monitor](https://gitlab.gnome.org/GNOME/gnome-system-monitor).
- Build a jar with your runnable main class, better to choose the `StressLauncher` test, for example: 
```bash
└──╼ $./gradlew :jme3-alloc-examples:copyLibs &&  
      ./gradlew :jme3-alloc-examples:StressLauncher \
                :jme3-alloc-examples:createJar
```
- Now run the GNOME-System monitor and search for java on, and sort the memory descendingly.
- Now focus on the cpu and memory usage.
- Run the output example:
```bash
└──╼ $java -jar ./build/libs/jme3-alloc-examples.jar 
```
- Now observe for the first java application to jump with the maximum memory and cpu usages.
- Focus on how the memory is freed during the application lifecycle.
- For more, visualize the memory mappings using the start and the end address of buffers, you can find these information on the `jme3-alloc-debug.log` file.
- For example, on `jme3-alloc-debug.log`: 
```log
Sat Feb 11 01:31:04 2023/Jme3-alloc/DEBUG: Allocated a new cleared buffer = [ Address = 0x7f952194c010, Size = 2000000000] 
Sat Feb 11 01:31:04 2023/Jme3-alloc/DEBUG: Buffer = [ Address = 0x7f952194c010, Size = 2000000000] 
Sat Feb 11 01:31:04 2023/Jme3-alloc/DEBUG: Buffer = [ Address = 0x7f9598ca6010, Size = 2000000000] 
Sat Feb 11 01:31:04 2023/Jme3-alloc/DEBUG: Copied memory from buffer = [ Address = 0x7f9598ca6010, Size = 2000000000 ] to buffer = [ Address = 0x7f952194c010, Size = 2000000000 ] 
Sat Feb 11 01:31:04 2023/Jme3-alloc/DEBUG: Buffer = [ Address = 0x7f9598ca6010, Size = 2000000000] 
Sat Feb 11 01:31:04 2023/Jme3-alloc/DEBUG: Destructed buffer = [ Address = 0x7f9598ca6010, Size = 2000000000] 
```
The buffer `0x7f952194c010` of size 2 GB is not deallocated and it appears on the application memory mappings with size 1.9 GB:
![](link)