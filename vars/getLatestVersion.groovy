#!/usr/bin/env groovy
/**
 * Resolves the latest version (snapshot or otherwise) of an artifact on nexus
 * central, for a given groupId and artifactId.
 *
 * @param groupId The group ID.
 * @param artifactId The artifact ID.
 * @return The latest version, as a string.
 */
String[] call(String groupId, String artifactId) {
    def groupPath = groupId.split('.').join('/')

    return new XmlSlurper()
            .parse("https://repo.maven.apache.org/maven2/${groupPath}/${artifactId}/maven-metadata.xml")
            .versioning
            .latest
            .text()
}
