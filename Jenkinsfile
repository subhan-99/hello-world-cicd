pipeline {
    agent any

    tools {
        jdk 'Java21'
        maven 'Maven3'
    }

    triggers {
        githubPush()
    }

    environment {
        // Change this to the Tomcat EC2 private IP if it differs.
        APP_SERVER = '172.31.4.78'
        APP_USER = 'ubuntu'
        DEPLOY_DIR = '/var/lib/tomcat10/webapps'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build WAR') {
            steps {
                sh 'mvn clean package'
                sh 'test -f target/hello-world.war'
            }
        }

        stage('Deploy WAR') {
            steps {
                sshagent(credentials: ['tomcat-ssh-key']) {
                    sh '''
                        scp target/hello-world.war ${APP_USER}@${APP_SERVER}:/tmp/hello-world.war
                        ssh ${APP_USER}@${APP_SERVER} "sudo install -o tomcat -g tomcat -m 0644 /tmp/hello-world.war ${DEPLOY_DIR}/hello-world.war"
                    '''
                }
            }
        }
    }

    post {
        success { echo 'WAR deployed successfully.' }
        failure { echo 'Build/deployment failed. Check Console Output.' }
    }
}
