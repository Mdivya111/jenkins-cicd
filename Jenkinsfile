
pipeline {
    agent any

    tools {
        maven 'Maven-3.9.12'
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package'
                archiveArtifacts artifacts: 'target/*.jar'
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                    set -e

                    DEPLOY_DIR=/var/jenkins_home/deployment
                    JAR=$DEPLOY_DIR/jenkins-cicd-1.0-SNAPSHOT.jar

                    mkdir -p "$DEPLOY_DIR"

                    cp target/jenkins-cicd-1.0-SNAPSHOT.jar "$JAR"

                    if [ -f "$DEPLOY_DIR/app.pid" ]; then
                        OLD_PID=$(cat "$DEPLOY_DIR/app.pid")

                        if kill -0 "$OLD_PID" 2>/dev/null; then
                            kill "$OLD_PID"
                            sleep 2
                        fi
                    fi

                    nohup java -jar "$JAR" \
                        > "$DEPLOY_DIR/app.log" 2>&1 < /dev/null &

                    echo $! > "$DEPLOY_DIR/app.pid"

                    sleep 3

                    curl -fsS http://127.0.0.1:8081
                '''
            }
        }
    }
}
