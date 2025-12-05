pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Test Java') {
            steps {
                script {
                    // Перевірка чи є Java
                    try {
                        sh 'java -version'
                        echo '✅ Java is available'
                    } catch (Exception e) {
                        echo '❌ Java NOT found'
                    }
                    
                    // Перевірка чи є Maven
                    try {
                        sh 'mvn -version'
                        echo '✅ Maven is available'
                    } catch (Exception e) {
                        echo '❌ Maven NOT found'
                    }
                }
            }
        }
    }
}
