pipeline {
    agent any
    environment {
        REPOSITORY = "${project.gitHost}/${project.name}-backend.git"
        version = "jenkins-$BUILD_NUMBER"

    }
    stages {
        stage('get code') {
            steps {
                echo "start fetch code from git:$REPOSITORY"
                echo "git checkout master"
                deleteDir()
                git "$REPOSITORY"
                sh "git checkout master"
            }
        }
        stage("compile & build image") {
            steps {
                echo "start compile"
                script {
                    apps.split(',').each { app ->
                        dir(app) {
                            sh 'gradle buildDocker -xtest'
                        }
                    }
                }
            }
        }
        stage('push image') {
            steps {
                echo "start push image"
                script {
                    apps.split(',').each { app ->
                        dir(app) {
                            sh "docker tag dockerhub.cfg-global.com/$app:latest dockerhub.cfg-global.com/$app:$version"
                            sh "docker push dockerhub.cfg-global.com/$app:$version"
                        }
                    }
                }
            }
        }
        stage('start service') {
            steps {
                echo "start service"
                    script{
                        apps.split(',').each { app ->
                            sh "pwd"
                            sh "chmod +x rancher/finish_upgrade_develop.sh"
                            sh "chmod +x rancher/upgrade_develop.sh"
                            sh "rancher/finish_upgrade_develop.sh $app"
                            sh "rancher/upgrade_develop.sh $app $version"
                        }
                    }
            }
        }
    }
}
