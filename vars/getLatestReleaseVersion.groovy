#!/usr/bin/env groovy
/**
 * Resolves the latest release version of an artifact on nexus central, for
 * a given groupId and artifactId.
 *
 * @param groupId The group ID.
 * @param artifactId The artifact ID.
 * @return The latest release version, as a string.
 */
@NonCPS
String[] call(String groupId, String artifactId) {
    def groupPath = groupId.split('\\.').join('/')

    return new XmlSlurper()
            .parse("https://repo.maven.apache.org/maven2/${groupPath}/${artifactId}/maven-metadata.xml")
            .versioning
            .release
            .text()
}
