## Contribute to the jme-alloc project: 
1) Clone the repository locally on your disk: 
```bash
$ git clone https://github.com/Software-Hardware-Codesign/jme-alloc.git
```
2) Checkout to your new feature branch (always use good descriptive names for what you will do): 
```bash
$ git checkout master -b 'feature-branch'
```
3) Add your changes.
4) Compile and assemble the library locally using:
```bash
$ ./gradlew clean && \
  ./gradlew :jme3-alloc:compileJava && \
  ./gradlew :jme3-alloc-native:compileX86_64 && \
  ./gradlew :jme3-alloc-native:copyNatives && \
  ./gradlew :jme3-alloc:assemble
```
Notice: change between `task compileX86_64` and `task compileX86` depending on your system architecture.
5) Test the library by running the examples using: 
```bash
$ ./gradlew :jme3-alloc-examples:run
```
6) Add your changes and commit:
```bash
$ git add ./jme3-alloc-examples && git commit -m 'file: changes made' \
  && git push origin 'feature-branch'
```
