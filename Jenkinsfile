pipeline {
    agent any
    tools { maven 'Maven'; jdk 'JDK17' }

    stages {
        stage('1- Checkout') {
            steps { git branch: 'master', credentialsId: 'github-credentials', url: 'https://github.com/EnesCatalbas/WestCast.git' }
        }

        stage('2- Build') {
            steps { bat 'mvn clean compile -DskipTests' }
        }

        stage('3- Start Backend') {
            steps {
                echo '🚀 Starting backend...'
                bat '''
                    start "" cmd /c "mvn spring-boot:run -Dserver.port=8081 > backend.log 2>&1"
                    powershell -Command "Start-Sleep -Seconds 30"
                '''
            }
        }

        stage('4- Run All Tests') {
            steps {
                // Hata alsa bile durmaması için catchError kullanabilirsin
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
            echo '🟢 Temizlik yapılıyor...'
            bat '''
                @echo off
                :: 8081 portunu kullanan süreci bul ve öldür (Hata verse de devam et)
                for /f "tokens=5" %%p in ('netstat -ano ^| find ":8081" ^| find "LISTENING"') do taskkill /PID %%p /F /T 2>nul
                
                :: Kalan tüm Chrome ve Driver süreçlerini temizle (Hata kodunu yoksay)
                taskkill /F /IM chromedriver.exe /T 2>nul || exit 0
                taskkill /F /IM chrome.exe /T 2>nul || exit 0
                
                exit 0
            '''
        }
    }
}