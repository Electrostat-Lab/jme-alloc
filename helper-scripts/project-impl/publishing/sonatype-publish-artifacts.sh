#!/bin/bash

source "./helper-scripts/abstract/abstract-sonatype-publish.sh"
source "./helper-scripts/project-impl/variables.sh"

# obtain dependencies in the form 'groupId:artifact:version'
version=${1}
desktop_artifact="${build_dir}/jme3-alloc-desktop-${version}.jar"
android_artifact="${build_dir}/jme3-alloc-android-${version}.jar"

sources_jar="${build_dir}/${java_module}-${version}-sources.jar"
javadoc_jar="${build_dir}/${java_module}-${version}-javadoc.jar"

generateGenericPom ${groupId} ${desktop_artifactId_release} ${version} "${desktop_pomFile}"
generateGenericPom ${groupId} ${android_artifactId_release} ${version} "${android_pomFile}"
# publish 'android' and 'desktop' builds to maven sonatype
publishBuild "${desktop_artifactId_release}" "${desktop_artifact}" "${version}" "${javadoc_jar}" "${sources_jar}" "${desktop_pomFile}"
publishBuild "${android_artifactId_release}" "${android_artifact}" "${version}" "${javadoc_jar}" "${sources_jar}" "${android_pomFile}"
