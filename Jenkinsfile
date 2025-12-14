pipeline {
    agent any  
    
    tools {
        jdk 'JDK-11'
        maven 'Maven-3.8.4' 
    }
    
    environment {
        DOCKER_IMAGE = 'dischk/my-java-app'  // Убедитесь что репозиторий с таким именем существует
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
        
        // ВАЖНО: Docker должен быть установлен на агенте Jenkins!
        stage('Build Docker Image') {
            steps {
                script {
                    // Создаем Docker образ из Dockerfile
                    dockerImage = docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                }
            }
        }
        
        stage('Push to Docker Hub') {
            steps {
                script {
                    // Авторизуемся в Docker Hub и пушим образ
                    // 'docker-hub-token' - это ID credentials в Jenkins
                    docker.withRegistry(DOCKER_REGISTRY, 'docker-hub-token') {
                        dockerImage.push()  // С тегом BUILD_ID
                        dockerImage.push('latest')  // И с тегом latest
                    }
                }
            }
        }
        
        stage('Cleanup Local Images') {
            steps {
                script {
                    // Удаляем собранный образ чтобы не засорять диск
                    sh "docker rmi ${DOCKER_IMAGE}:${DOCKER_TAG} || true"
                    sh "docker rmi ${DOCKER_IMAGE}:latest || true"
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
            echo "🔗 Ссылка: https://hub.docker.com/r/dischk/my-java-app"
        }
        failure {
            echo "❌ Pipeline завершился с ошибкой"
        }
    }
}
