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
                bat '''
                    echo Starting clean build...
                    if exist target (rmdir /s /q target)
                    mvn clean compile
                '''
            }
        }

        stage('3- Unit Tests') {
            steps {
                bat 'mvn test -Dtest=UserServiceTest'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('4- Integration Tests') {
            steps {
                bat 'mvn test -Dtest=UserControllerIT'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                }
            }
        }

        // Docker aşaması şimdilik devre dışı
        // İstersen sonra açarız
        /*
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
        */

        stage('5- Selenium - General Tests') {
            steps {
                bat 'mvn test -Pselenium -Dtest=GeneralSeleniumTest'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('6- Selenium - Login Tests') {
            steps {
                bat 'mvn test -Pselenium -Dtest=LoginSeleniumTest'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('7- Selenium - Movie Search Tests') {
            steps {
                bat 'mvn test -Pselenium -Dtest=MovieSearchSeleniumTest'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('8- Selenium - Signup Tests') {
            steps {
                bat 'mvn test -Pselenium -Dtest=SignupSeleniumTest'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                }
            }
        }
    }

    post {
        always {
            echo '🟢 Build completed successfully. Cleaning up (if needed)...'

            // Docker cleanup (şimdilik etkisiz)
            // if (isUnix()) {
            //     sh 'docker stop westcast-container || true'
            //     sh 'docker rm westcast-container || true'
            // } else {
            //     bat 'docker stop westcast-container || exit 0'
            //     bat 'docker rm westcast-container || exit 0'
            // }
        }
    }
}
