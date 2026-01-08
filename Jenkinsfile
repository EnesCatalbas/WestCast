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
                    echo Cleaning and compiling project...
                    if exist target (rmdir /s /q target)
                    mvn clean compile
                '''
            }
        }

        stage('3- Start Backend (port 8081)') {
            steps {
                echo '🚀 Starting backend on port 8081...'
                // Jenkins Windows ortamında timeout yerine powershell kullan
                bat '''
                    start "" cmd /c "mvn spring-boot:run -Dserver.port=8081 > backend.log 2>&1"
                    powershell -Command "Start-Sleep -Seconds 15"
                '''
            }
        }

        stage('4- Unit Tests') {
            steps {
                bat 'mvn test -Dtest=UserServiceTest'
            }
            post {
                always {
                    junit allowEmptyResults: true, keepLongStdio: true, skipPublishingChecks: true, testResults: 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('5- Integration Tests') {
            steps {
                bat 'mvn test -Dtest=UserControllerIT'
            }
            post {
                always {
                    junit allowEmptyResults: true, keepLongStdio: true, skipPublishingChecks: true, testResults: 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('6- Selenium - General Tests') {
            steps {
                bat 'mvn test -Pselenium -Dapp.url=http://localhost:8081 -Dtest=GeneralSeleniumTest'
            }
            post {
                always {
                    junit allowEmptyResults: true, keepLongStdio: true, skipPublishingChecks: true, testResults: 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('7- Selenium - Login Tests') {
            steps {
                bat 'mvn test -Pselenium -Dapp.url=http://localhost:8081 -Dtest=LoginSeleniumTest'
            }
            post {
                always {
                    junit allowEmptyResults: true, keepLongStdio: true, skipPublishingChecks: true, testResults: 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('8- Selenium - Movie Search Tests') {
            steps {
                bat 'mvn test -Pselenium -Dapp.url=http://localhost:8081 -Dtest=MovieSearchSeleniumTest'
            }
            post {
                always {
                    junit allowEmptyResults: true, keepLongStdio: true, skipPublishingChecks: true, testResults: 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('9- Selenium - Signup Tests') {
            steps {
                bat 'mvn test -Pselenium -Dapp.url=http://localhost:8081 -Dtest=SignupSeleniumTest'
            }
            post {
                always {
                    junit allowEmptyResults: true, keepLongStdio: true, skipPublishingChecks: true, testResults: 'target/surefire-reports/*.xml'
                }
            }
        }
    }

    post {
        always {
            echo '🟢 Cleaning up backend process...'
            // Eğer backend hâlâ 8081’de dinliyorsa kapat
            bat '''
                for /f "tokens=5" %%p in ('netstat -ano ^| find ":8081" ^| find "LISTENING"') do taskkill /PID %%p /F
                exit 0
            '''
        }
    }
}
