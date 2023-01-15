# jme-alloc project 

An initiator template for the jme-alloc project providing a native allocation api through gradle modules.

### To build, run:
```bash
./gradlew :jvm-alloc:build && ./gradlew :native-alloc:build
```
### WIP:
- [x] Separate jvm and native modules.
- [x] Generating header files for java sources.
- [x] Packaging java and natives in a jar [PR#1](https://github.com/Software-Hardware-Codesign/jme-alloc/pull/1).
- [ ] Native extraction according to the system variant (OS + architecture).
- [ ] Dynamic linking code.
- [ ] Handling different variants build.
- [ ] Github-actions.
