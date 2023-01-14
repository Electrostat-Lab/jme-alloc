# jme-alloc project 

An initiator template for the jme-alloc project providing a native allocation api through gradle modules.

### To build, run:
```bash
./gradlew :jvm-alloc:build && ./gradlew :native-alloc:build
```
### WIP:
- [x] Separate jvm and native modules.
- [x] Generating header files for java sources.
- [ ] Packaging java and natives in a jar.
- [ ] Native extraction according to the system variant (OS + architecture).
- [ ] Dynamic linking code.
- [ ] Handling different variants build.
- [ ] Github-actions.
