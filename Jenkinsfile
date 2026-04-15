pipeline {
    agent any

    tools {
        maven 'Maven'
        jdk 'JDK17'
    }

    stages {

        stage('1- Checkout') {
            steps {
                echo 'Cloning repo...'
                git branch: 'master',
                    credentialsId: 'github-credentials',
                    url: 'https://github.com/EnesCatalbas/WestCast.git'
            }
        }

        stage('2- Build Maven Project') {
            steps {
                echo 'Building project...'
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('3- Build Docker Image') {
            steps {
                echo 'Building Docker image...'
                bat 'docker build -t westcast-app .'
            }
        }

        stage('4- Run Container') {
            steps {
                echo 'Running Docker container...'
                bat '''
                    docker stop westcast || exit 0
                    docker rm westcast || exit 0
                    docker run -d -p 8081:8081 --name westcast westcast-app
                '''
            }
        }
    }

    post {
        success {
            echo 'BUILD + DOCKER RUN SUCCESS!'
        }
        failure {
            echo 'BUILD FAILED'
        }
    }
}