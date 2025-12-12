pipeline {
    agent any  
    
    tools {
        jdk 'JDK-11'
        maven 'Maven-3.8.4' 
    }
    
    // ← ДОБАВЬТЕ СЮДА environment ↓
    environment {
        DOCKER_IMAGE = 'dischk/my-java-app'
        DOCKER_TAG = "${env.BUILD_ID}"
        DOCKER_REGISTRY = 'https://index.docker.io/v1/'
    }

    triggers {
        pollSCM('H/2 * * * *')
    }

    stages {
        stage('Cleanup Workspace') {
            steps {
                cleanWs()
            }
        }

        stage('Checkout') {
            steps {
                git branch: 'master',
                    url: 'https://github.com/Diaschk/Laba4.git'
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
        
        // ← ДОБАВЬТЕ НОВЫЕ СТЕЙДЖИ ДЛЯ DOCKER ↓
        
        stage('Build Docker Image') {
            steps {
                script {
                    // Создаем Docker образ
                    dockerImage = docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                }
            }
        }
        
        stage('Push to Docker Hub') {
            steps {
                script {
                    // Авторизуемся в Docker Hub и пушим образ
                    docker.withRegistry(DOCKER_REGISTRY, 'docker-hub-token') {
                        dockerImage.push()
                        // Также пушим с тегом latest
                        dockerImage.push('latest')
                    }
                }
            }
        }
        
        stage('Cleanup Local Images') {
            steps {
                script {
                    // Очищаем локальные образы чтобы не засорять диск
                    sh 'docker image prune -f'
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
        success {
            echo "✅ Pipeline успешно завершен!"
            echo "📦 Образ доступен в Docker Hub: ${DOCKER_IMAGE}:${DOCKER_TAG}"
        }
        failure {
            echo "❌ Pipeline завершился с ошибкой"
        }
    }
}
