pipeline {
      agent any  
    
    tools {
        jdk 'JDK-11'
         maven 'Maven-3.8.4' 
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
    }

 
    post {
        always {
            cleanWs()
        }
    }
}
