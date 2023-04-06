# <p align=left> <img src="https://user-images.githubusercontent.com/60224159/220400745-2582342f-5f4f-4827-b65f-a037e078c890.svg" height=100 width=100 />  jme-alloc project </p>
[![](https://github.com/Software-Hardware-Codesign/jme-alloc/actions/workflows/build-test.yml/badge.svg)](https://software-hardware-codesign.github.io/jme-alloc/)
[![](https://github.com/Software-Hardware-Codesign/jme-alloc/actions/workflows/doc-website.yml/badge.svg)](https://software-hardware-codesign.github.io/jme-alloc/) [![](https://github.com/Software-Hardware-Codesign/jme-alloc/actions/workflows/build-deploy.yml/badge.svg)](https://repo.maven.apache.org/maven2/io/github/software-hardware-codesign/jme3-alloc-desktop/)
[![](https://github.com/Software-Hardware-Codesign/jme-alloc/actions/workflows/debug-deploy.yml/badge.svg)](https://repo.maven.apache.org/maven2/io/github/software-hardware-codesign/jme3-alloc-desktop-debug/)

A direct dynamic memory allocation api for jMonkeyEngine lwjgl-2 and android games.
### To build locally, use: 
```bash
┌─[pavl-machine@pavl-machine]─[/home/twisted/GradleProjects/jme-alloc]
└──╼ $./gradlew clean && 
      ./gradlew :jme3-alloc:compileJava && \
      ./gradlew :jme3-alloc-native:compileX86_64 && \
      ./gradlew :jme3-alloc-native:copyNatives && \
      ./gradlew :jme3-alloc:assemble
```
### To test locally, use: 
```bash
┌─[pavl-machine@pavl-machine]─[/home/twisted/GradleProjects/jme-alloc]
└──╼ $./gradlew :jme3-alloc-examples:run
```
### For more about, building, testing and contributing, visit:
> [CONTRIBUTING.md](https://github.com/Software-Hardware-Codesign/jme-alloc/blob/master/CONTRIBUTING.md)

### For quick use: 
```java
/* select your platform here */
final String platform = "desktop"
final String binaryType = "debug"
final String version = "1.0.0-pre-gamma-1"

repositories {
    mavenCentral()
}
dependencies {
    implementation "io.github.software-hardware-codesign:jme3-alloc-${platform}-${binaryType}:${version}"
}
```

#### API:
- [x] Native extraction according to the system variant (OS + architecture) using [`com.jme3.alloc.util.loader.NativeBinaryLoader`](https://github.com/Software-Hardware-Codesign/jme-alloc/blob/master/jme3-alloc/src/main/java/com/jme3/alloc/util/loader/NativeBinaryLoader.java).
- [x] Dynamic linking code.
- [x] Base Allocator/De-allocator API: [`com.jme3.alloc.util.NativeBufferUtils`](https://github.com/Software-Hardware-Codesign/jme-alloc/blob/master/jme3-alloc/src/main/java/com/jme3/alloc/util/NativeBufferUtils.java) and [`com.jme3.alloc.NativeBufferAllocator`](https://github.com/Software-Hardware-Codesign/jme-alloc/blob/master/jme3-alloc/src/main/java/com/jme3/alloc/NativeBufferAllocator.java).
- [x] Native logging api with output to an external log file stream.
- [x] Garbage collectible buffers API.

#### Build-system:
- [x] Separate jvm and native modules.
- [x] Generating header files for java sources.
- [x] Packaging java and natives in a jar.
- [x] Github-actions.
- [x] Handling different variants build (linux-x86-64).
- [x] Handling different variants build (linux-x86).
- [x] Handling different variants build (windows-x86-64).
- [x] Handling different variants build (macos-x86-64).
- [x] Handling different `android` build architectures (aarch-64, arm32, intel-x86-64, intel-x86).
- [ ] Handling different variants build (windows-x86).
- [ ] Handling different variants build (macos-x86).

#### Documentation: 
- [x] JavaDocs.
- [x] NativeDocs.
- [x] Running Examples.
- [x] Contribution guide. 
- [ ] Documentation website for different releases (not only the latest).

