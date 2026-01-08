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
                dir('backend') {
                    bat 'mvn clean compile'
                }
            }
        }

        stage('3- Unit Tests') {
            steps {
                dir('backend') {
                    bat 'mvn test -Dtest=UserServiceTest'
                }
            }
            post {
                always {
                    junit 'backend/target/surefire-reports/*.xml'
                }
            }
        }

        stage('4- Integration Tests') {
            steps {
                dir('backend') {
                    bat 'mvn verify -Dtest=UserControllerIT'
                }
            }
            post {
                always {
                    junit 'backend/target/failsafe-reports/*.xml'
                }
            }
        }

        stage('5- Docker Build & Run') {
            steps {
                bat 'docker build -t westcast-app ./backend'
                bat 'docker rm -f westcast-container || exit 0'
                bat 'docker run -d -p 8080:8080 --name westcast-container westcast-app'
                bat 'timeout /t 15'
            }
        }

        stage('6- Selenium Tests') {
            parallel {
                stage('General Tests') {
                    steps {
                        dir('backend') {
                            bat 'mvn test -Dtest=GeneralSeleniumTest'
                        }
                    }
                }
                stage('Login Tests') {
                    steps {
                        dir('backend') {
                            bat 'mvn test -Dtest=LoginSeleniumTest'
                        }
                    }
                }
                stage('Movie Search Tests') {
                    steps {
                        dir('backend') {
                            bat 'mvn test -Dtest=MovieSearchSeleniumTest'
                        }
                    }
                }
                stage('Signup Tests') {
                    steps {
                        dir('backend') {
                            bat 'mvn test -Dtest=SignupSeleniumTest'
                        }
                    }
                }
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
