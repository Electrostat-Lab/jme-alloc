#!/bin/bash

source "./helper-scripts/project-impl/variables.sh"

# obtain dependencies in the form 'groupId:artifact:version'
version=${1}
desktop_artifact="${build_dir}/jme3-alloc-desktop-${version}.jar"
android_artifact="${build_dir}/jme3-alloc-android-${version}.jar"

sources_jar="${build_dir}/${java_module}-${version}-sources.jar"
javadoc_jar="${build_dir}/${java_module}-${version}-javadoc.jar"

function generateGenericPom() {
    local groupId=$1
    local artifactId=$2
    local version=$3
    local output=$4

    config="<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd\"> \
    <modelVersion>4.0.0</modelVersion> \
    <groupId>${groupId}</groupId> \
    <artifactId>${artifactId}</artifactId> \
    <version>${version}</version> \
    <name>jme-alloc API</name> \
    <packaging>jar</packaging> \
    <description>A direct dynamic memory allocation api for jMonkeyEngine lwjgl-2 and android games.</description> \
    <url>https://github.com/Software-Hardware-Codesign/jme-alloc</url> \
    <licenses>  \
      <license> \
          <name>jMonkeyEngine BSD-3 Clause License</name> \
          <url>https://github.com/Software-Hardware-Codesign/jme-alloc/blob/master/LICENSE.md</url> \
      </license> \
    </licenses> \
    <developers> \
      <developer> \
        <name>Pavly Gerges aka. pavl_g</name> \
        <email>pepogerges33@gmail.com</email> \
        <organization>jMonkeyEngine-Community</organization> \
        <organizationUrl>https://hub.jmonkeyengine.org/t/jme-alloc-project/46356</organizationUrl> \
      </developer> \
    </developers> \
    <scm> \
      <connection>scm:git:git://github.com/Software-Hardware-Codesign/jme-alloc.git</connection> \
      <developerConnection>scm:git:ssh://github.com/Software-Hardware-Codesign/jme-alloc.git</developerConnection> \
      <url>https://github.com/Software-Hardware-Codesign/jme-alloc/tree/master</url> \
    </scm> \
</project> \
"
    echo $config > ${output}
}

function publishBuild() {
    local artifactId=$1
    local artifact=$2
    local javadoc_jar=$3
    local sources_jar=$4
    local pomFile=$5

    ${maven_bin} gpg:sign-and-deploy-file -s ${settings} -Durl=${sonatype_url} \
                                                         -DartifactId=${artifactId} \
                                                         -DrepositoryId=${repository} \
                                                         -Dversion=${version} \
                                                         -DpomFile=${pomFile} \
                                                         -Dgpg.passphrase=${passphrase} \
                                                         -Dfile=${artifact} \
                                                         -Djavadoc=${javadoc_jar} \
                                                         -Dsources=${sources_jar} 


    return $?
}
generateGenericPom ${groupId} ${artifactIds[0]} ${version} "${desktop_pomFile}"
generateGenericPom ${groupId} ${artifactIds[1]} ${version} "${android_pomFile}"
# publish 'android' and 'desktop' builds to maven sonatype
publishBuild "${artifactIds[0]}" "${desktop_artifact}" "${javadoc_jar}" "${sources_jar}" "${desktop_pomFile}"
publishBuild "${artifactIds[1]}" "${android_artifact}" "${javadoc_jar}" "${sources_jar}" "${android_pomFile}"
