
pipeline {
    agent any

    tools {
        maven 'Maven-3'
    }

    triggers {
        githubPush()
    }

    environment {
        APP_SERVER = '172.31.4.78'
        APP_USER = 'ubuntu'
        DEPLOY_DIR = '/var/lib/tomcat10/webapps'
        WAR_NAME = 'hello-world.war'
    }

    stages {

        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Check Java and Maven') {
            steps {
                sh 'java -version'
                sh 'mvn -version'
            }
        }

        stage('Build WAR') {
            steps {
                sh 'mvn clean package'
                sh 'test -f target/hello-world.war'
            }
        }

        stage('Deploy to Tomcat') {
            steps {
                sshagent(credentials: ['tomcat-ssh-key']) {
                    sh '''
                        set -e

                        scp target/${WAR_NAME} \
                            ${APP_USER}@${APP_SERVER}:/tmp/${WAR_NAME}

                        ssh ${APP_USER}@${APP_SERVER} \
                            "sudo install -o tomcat -g tomcat -m 0644 \
                            /tmp/${WAR_NAME} \
                            ${DEPLOY_DIR}/${WAR_NAME}"
                    '''
                }
            }
        }
    }

    post {
        success {
            echo 'SUCCESS: WAR deployed to Tomcat!'
            echo 'URL: http://YOUR_TOMCAT_PUBLIC_IP:8080/hello-world/'
        }

        failure {
            echo 'FAILED: Check the Jenkins Console Output.'
        }
    }
}
