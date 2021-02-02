pipeline {
    agent any

    stages {
        
        stage('getGit') {
            steps {
                git branch: 'main', url: 'https://github.com/rubenhnorman/jgsu-spring-petclinic.git'
            }
        }
        
        stage('Build') {
            steps {
                sh './mvnw clean package'
                // Run Maven on a Unix agent.
                //sh "mvn -Dmaven.test.failure.ignore=true clean package"

                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }

            post {
                // If Maven was able to run the tests, even if some of the test
                // failed, record the test results and archive the jar file.
                always {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }
    }
}
