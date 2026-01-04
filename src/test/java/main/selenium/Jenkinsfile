pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK17'
    }

    environment {
        MAVEN_OPTS = "-Dmaven.test.failure.ignore=false"
    }

    stages {

        stage('1- Checkout from GitHub') {
            steps {
                git branch: 'main',
                    credentialsId: 'github-credentials',
                    url: 'https://github.com/KULLANICI_ADIN/WESTCAST.git'
            }
        }

        stage('2- Build Project') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('3- Unit Tests') {
            steps {
                sh 'mvn test -Dtest=UserServiceTest'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('4- Integration Tests') {
            steps {
                sh 'mvn verify -Dtest=UserControllerIT'
            }
            post {
                always {
                    junit 'target/failsafe-reports/*.xml'
                }
            }
        }

        stage('5- Docker Build & Run') {
            steps {
                sh 'docker build -t westcast-app .'
                sh 'docker run -d -p 8080:8080 --name westcast-container westcast-app'
            }
        }

        stage('6- Selenium - General Tests') {
            steps {
                sh 'mvn test -Dtest=GeneralSeleniumTest'
            }
        }

        stage('7- Selenium - Login Tests') {
            steps {
                sh 'mvn test -Dtest=LoginSeleniumTest'
            }
        }

        stage('8- Selenium - Movie Search Tests') {
            steps {
                sh 'mvn test -Dtest=MovieSearchSeleniumTest'
            }
        }

        stage('9- Selenium - Signup Tests') {
            steps {
                sh 'mvn test -Dtest=SignupSeleniumTest'
            }
        }
    }

    post {
        always {
            sh 'docker stop westcast-container || true'
            sh 'docker rm westcast-container || true'
        }
    }
}
