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
                    script {
                        def reports = findFiles(glob: 'target/surefire-reports/*.xml')
                        if (reports.length > 0) {
                            junit 'target/surefire-reports/*.xml'
                        } else {
                            echo '⚠️ No surefire test reports found.'
                        }
                    }
                }
            }
        }

        stage('4- Integration Tests') {
            steps {
                bat 'mvn verify -Dtest=UserControllerIT'
            }
            post {
                always {
                    script {
                        def reports = findFiles(glob: 'target/failsafe-reports/*.xml')
                        if (reports.length > 0) {
                            junit 'target/failsafe-reports/*.xml'
                        } else {
                            echo '⚠️ No failsafe reports found.'
                        }
                    }
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

        // ======================
        // Selenium Test Stages
        // ======================

        stage('6- Selenium - General Tests') {
            steps {
                bat 'mvn test -Pselenium -Dtest=GeneralSeleniumTest'
            }
            post {
                always {
                    script {
                        def reports = findFiles(glob: 'target/surefire-reports/*.xml')
                        if (reports.length > 0) {
                            junit 'target/surefire-reports/*.xml'
                        } else {
                            echo '⚠️ No Selenium (General) reports found.'
                        }
                    }
                }
            }
        }

        stage('7- Selenium - Login Tests') {
            steps {
                bat 'mvn test -Pselenium -Dtest=LoginSeleniumTest'
            }
            post {
                always {
                    script {
                        def reports = findFiles(glob: 'target/surefire-reports/*.xml')
                        if (reports.length > 0) {
                            junit 'target/surefire-reports/*.xml'
                        } else {
                            echo '⚠️ No Selenium (Login) reports found.'
                        }
                    }
                }
            }
        }

        stage('8- Selenium - Movie Search Tests') {
            steps {
                bat 'mvn test -Pselenium -Dtest=MovieSearchSeleniumTest'
            }
            post {
                always {
                    script {
                        def reports = findFiles(glob: 'target/surefire-reports/*.xml')
                        if (reports.length > 0) {
                            junit 'target/surefire-reports/*.xml'
                        } else {
                            echo '⚠️ No Selenium (Movie Search) reports found.'
                        }
                    }
                }
            }
        }

        stage('9- Selenium - Signup Tests') {
            steps {
                bat 'mvn test -Pselenium -Dtest=SignupSeleniumTest'
            }
            post {
                always {
                    script {
                        def reports = findFiles(glob: 'target/surefire-reports/*.xml')
                        if (reports.length > 0) {
                            junit 'target/surefire-reports/*.xml'
                        } else {
                            echo '⚠️ No Selenium (Signup) reports found.'
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
