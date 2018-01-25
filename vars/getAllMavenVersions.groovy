#!/usr/bin/env groovy
/**
 * Resolves an array of all published maven versions from nexus central, for
 * a given groupId and artifactId.
 *
 * @param groupId The group ID.
 * @param artifactId The artifact ID.
 * @return An array of all published versions.
 */
String[] call(String groupId, String artifactId) {
    def groupPath = groupId.split('.').join('/')
    def versioning = new XmlSlurper()
            .parse("https://repo.maven.apache.org/maven2/${groupPath}/${artifactId}/maven-metadata.xml")
            .versioning

    def versions = []
    versioning.versions.version.each {
        versions.push(it.text())
    }

    return versions
}
