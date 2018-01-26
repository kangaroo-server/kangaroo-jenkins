#!/usr/bin/env groovy

import java.net.URLDecoder;

/**
 * Notify slack of this build's status.
 *
 * @param buildStatus The current build status.
 */
@NonCPS
String call(String buildStatus) {
    def url = env.BUILD_URL, message, color
    def linkTitle = URLDecoder.decode(env.JOB_NAME, 'UTF-8');

    if (env.CHANGE_URL && buildStatus == 'SUCCESS') {
        url = env.CHANGE_URL
    }

    switch (buildStatus) {
        case 'FAILURE':
            message = "Build <${url}|${linkTitle}> failed."
            color = '#AA0000'
            break
        case 'SUCCESS':
            message = "Build <${url}|${linkTitle}> passed."
            color = '#00AA00'
            break
        case 'UNSTABLE':
        default:
            message = "Build <${url}|${linkTitle}> unstable."
            color = '#FFAA00'
    }

    slackSend(
            channel: '#build-notifications',
            tokenCredentialId: 'kangaroo-server-slack-id',
            teamDomain: 'kangaroo-server',
            color: color,
            message: message
    )
}
