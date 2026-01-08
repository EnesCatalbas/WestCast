pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK17'
    }

    stages {
        stage('1- Checkout') {
            steps {
                git branch: 'master', credentialsId: 'github-credentials', url: 'https://github.com/EnesCatalbas/WestCast.git'
            }
        }

        stage('2- Build Project') {
            steps {
                bat 'mvn clean compile -DskipTests'
            }
        }

        stage('3- Start Backend') {
            steps {
                echo '🚀 Starting backend on port 8081...'
                bat '''
                    start "" cmd /c "mvn spring-boot:run -Dserver.port=8081 > backend.log 2>&1"
                    powershell -Command "Start-Sleep -Seconds 30"
                '''
            }
        }

        stage('4- Run All Tests') {
            steps {
                // Tüm testleri tek seferde veya ayrı ayrı çalıştırabilirsiniz
                bat 'mvn test -Pselenium -Dapp.url=http://localhost:8081'
            }
            post {
                always {
                    junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
                }
            }
        }
    }

    post {
        always {
            echo '🟢 Cleaning up backend process...'
            bat '''
                for /f "tokens=5" %%p in ('netstat -ano ^| find ":8081" ^| find "LISTENING"') do (
                    taskkill /PID %%p /F
                )
                exit 0
            '''
        }
    }
}