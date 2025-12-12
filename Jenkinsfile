pipeline {
    agent any

    tools {
        maven 'Maven-3.8.4'
        jdk 'JDK-17'
    }

    // 🔄 ПЕРЕВІРЯЄ GITHUB КОЖНІ 2 ХВИЛИНИ
    triggers {
        pollSCM('H/2 * * * *')
    }

    stages {

        // 🧹 ОЧИЩАЄМО WORKSPACE ПЕРЕД ВСЕМ (виправляє твою помилку)
        stage('Cleanup Workspace') {
            steps {
                cleanWs()
            }
        }

        // 🔽 КЛОНУЄМО РЕПОЗИТОРІЙ З GITHUB
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/ТВОЙ_ЮЗЕР/ТВОЙ_РЕПО.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
                junit 'target/surefire-reports/**/*.xml'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
                archiveArtifacts artifacts: 'target/*.jar'
            }
        }
    }

    // 🧹 очищення після завершення pipeline
    post {
        always {
            cleanWs()
        }
    }
}
