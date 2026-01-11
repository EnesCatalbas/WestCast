pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK17'
    }

    stages {

        stage('1- Checkout') {
            steps {
                echo ' Checking out from GitHub...'
                git branch: 'master', credentialsId: 'github-credentials', url: 'https://github.com/EnesCatalbas/WestCast.git'
            }
        }

        stage('2- Build Project') {
            steps {
                echo ' Building project (skipping tests for faster compile)...'
                bat 'mvn clean compile -DskipTests'
            }
        }

        stage('3- Start Backend') {
            steps {
                echo ' Starting backend on port 8081...'
                bat '''
                    start "" cmd /c "mvn spring-boot:run -Dserver.port=8081 > backend.log 2>&1"
                    powershell -Command "Start-Sleep -Seconds 30"
                '''
            }
        }

        stage('4- Run All Tests') {
            steps {
                echo ' Running all tests (unit + integration)...'
                bat 'mvn verify -Pselenium -Dapp.url=http://localhost:8081'
            }
            post {
                always {
                    echo 'Publishing JUnit test results...'
                    junit testResults: 'target/surefire-reports/*.xml, target/failsafe-reports/*.xml', allowEmptyResults: true
                }
            }
        }
    }

    post {
        always {
            echo '🟢 Cleaning up backend process on port 8081...'
            bat '''
                for /f "tokens=5" %%p in ('netstat -ano ^| find ":8081" ^| find "LISTENING"') do (
                    taskkill /PID %%p /F
                )
                exit 0
            '''
        }
        success {
            echo ' BUILD SUCCESSFUL: All tests passed!'
        }
        failure {
            echo ' BUILD FAILED: Check the test reports for details.'
        }
    }
}
