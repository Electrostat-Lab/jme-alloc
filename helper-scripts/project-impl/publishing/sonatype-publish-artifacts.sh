#!/bin/bash

source "./helper-scripts/project-impl/variables.sh"

# obtain dependencies in the form 'groupId:artifact:version'
version=${1}
desktop_artifact="${build_dir}/jme3-alloc-desktop-${version}.jar"
android_artifact="${build_dir}/jme3-alloc-android-${version}.jar"

sources_jar="${build_dir}/${java_module}-${version}-sources.jar"
javadoc_jar="${build_dir}/${java_module}-${version}-javadoc.jar"

function publishBuild() {
    local artifactId=$1
    local artifact=$2
    local javadoc_jar=$3
    local sources_jar=$4

    ${maven_bin} gpg:sign-and-deploy-file -s ${settings} -Durl=${sonatype_url} \
                                 -DartifactId=${artifactId} \
                                 -DrepositoryId=${repository} \
                                 -DpomFile=${pomFile} \
                                 -Dgpg.passphrase=${passphrase} \
                                 -Dversion=${version} \
                                 -Dfile=${artifact} \
                                 -Djavadoc=${javadoc_jar} \
                                 -Dsources=${sources_jar}


    return $?
}
# publish 'android' and 'desktop' builds to maven sonatype
publishBuild "${artifactIds[0]}" "${desktop_artifact}" "${javadoc_jar}" "${sources_jar}"
publishBuild "${artifactIds[1]}" "${android_artifact}" "${javadoc_jar}" "${sources_jar}"