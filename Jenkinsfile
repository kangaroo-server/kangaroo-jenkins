
/**
 * This jenkinsfile creates a quick sanity-check run on all the groovy scripts used on our jenkins host.
 */
pipeline {

    agent {
        label 'worker'
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }

    /**
     * This block makes use of the GIT environment variable to ensure we test the correct ref of the library.
     */
    libraries {
        lib("kangaroo-jenkins@${env.GIT_COMMIT}")
    }

    /**
     * Declare the stages of our build.
     */
    stages {

        /**
         * Environment scan.
         */
        stage('Env') {
            steps {
                sh('env')
            }
        }

        /**
         * Checkout required projects.
         */
        stage('Test') {
            steps {
                parallel(
                        'getLatestVersion': {
                            script {
                                getLatestVersion('net.krotscheck', 'kangaroo-common')
                            }
                        },
                        'getLatestReleaseVersion': {
                            script {
                                getLatestReleaseVersion('net.krotscheck', 'kangaroo-common')
                            }
                        },
                        'getAllMavenVersions': {
                            script {
                                getAllMavenVersions('net.krotscheck', 'kangaroo-common')
                            }
                        },
                        'notifySlack': {
                            script {
                                notifySlack('Test message to slack')
                            }
                        },
                        failFast: false
                )
            }
        }
    }
}
