pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK17'
    }

    stages {

        stage('1- Checkout from GitHub') {
            steps {
                git branch: 'master',
                    credentialsId: 'github-credentials',
                    url: 'https://github.com/EnesCatalbas/WestCast.git'
            }
        }

        stage('2- Build Project') {
            steps {
                bat 'mvn clean compile'
            }
        }

        stage('3- Unit Tests') {
            steps {
                bat 'mvn test -Dtest=UserServiceTest'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('4- Integration Tests') {
            steps {
                bat 'mvn verify -Dtest=UserControllerIT'
            }
            post {
                always {
                    junit 'target/failsafe-reports/*.xml'
                }
            }
        }

        stage('5- Docker Build & Run') {
            steps {
                script {
                    bat 'docker build -t westcast-app .'
                    bat 'docker rm -f westcast-container || exit 0'
                    bat 'docker run -d -p 8080:8080 --name westcast-container westcast-app'
                    bat 'timeout /t 15'
                }
            }
        }

        stage('6- Selenium - General Tests') {
            steps {
                bat 'mvn test -Dtest=GeneralSeleniumTest'
            }
        }

        stage('7- Selenium - Login Tests') {
            steps {
                bat 'mvn test -Dtest=LoginSeleniumTest'
            }
        }

        stage('8- Selenium - Movie Search Tests') {
            steps {
                bat 'mvn test -Dtest=MovieSearchSeleniumTest'
            }
        }

        stage('9- Selenium - Signup Tests') {
            steps {
                bat 'mvn test -Dtest=SignupSeleniumTest'
            }
        }
    }

    post {
        always {
            script {
                if (isUnix()) {
                    sh 'docker stop westcast-container || true'
                    sh 'docker rm westcast-container || true'
                } else {
                    bat 'docker stop westcast-container || exit 0'
                    bat 'docker rm westcast-container || exit 0'
                }
            }
        }
    }
}
