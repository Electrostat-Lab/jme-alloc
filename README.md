# jme-alloc project 

An initiator template for the jme-alloc project providing a native allocation api through gradle modules.
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
#### API:
- [x] Native extraction according to the system variant (OS + architecture).
- [x] Dynamic linking code.
#### Build-system:
- [x] Separate jvm and native modules.
- [x] Generating header files for java sources.
- [x] Packaging java and natives in a jar.
- [x] Github-actions.
- [x] Handling different variants build (linux-x86-64).
- [x] Handling different variants build (linux-x86).
- [x] Handling different variants build (windows-x86-64).
- [x] Handling different variants build (macos-x86-64).
- [ ] Handling different variants build (windows-x86).
- [ ] Handling different variants build (macos-x86).


