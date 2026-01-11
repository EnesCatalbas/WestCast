pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK17'
    }

    stages {
        stage('0- Clean Workspace') {
            steps {
                echo '🧹 Cleaning old artifacts...'
                // Maven clean ile eski target klasörünü siliyoruz
                bat 'mvn clean' 
            }
        }

        stage('1- Checkout') {
            steps {
                echo '📥 Checking out from GitHub...'
                git branch: 'master', credentialsId: 'github-credentials', url: 'https://github.com/EnesCatalbas/WestCast.git'
            }
        }

        stage('2- Build Project') {
            steps {
                echo '🔧 Building project...'
                // Build aşamasında sadece compile yapıyoruz
                bat 'mvn compile -DskipTests'
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
                echo '🧪 Running all tests (unit + integration)...'
                // Burada tekrar clean demenize gerek yok, verify yeterlidir.
                bat 'mvn verify -Pselenium -Dapp.url=http://localhost:8081'
            }
            post {
                always {
                    echo '📊 Publishing test results...'
                    // Çift yıldız (**) kullanarak alt klasörlerdeki tüm xml'leri taramasını sağlıyoruz
                    junit testResults: '**/target/*-reports/*.xml', allowEmptyResults: true
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