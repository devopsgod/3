node {
  def app

  stage('Clone repository') {
    /* Let's make sure we have the repository cloned to our workspace */

    checkout([$class: 'GitSCM', branches: [[name: '*/master']],
      doGenerateSubmoduleConfigurations: false,
      extensions: [], submoduleCfg: [],
      userRemoteConfigs: [[url: 'https://github.com/devopsgod/3']]])
  }

  stage('Build image') {
    /* This agent directive tells Jenkins to allocate an executor and workspace for the pipeline on any available agent */

    app = docker.build("auth-service")
  }

  stage('Deploy image') {
    bat(script: 'C:\\Windows\\System32\\cmd.exe /c docker rm -f auth-service')
    bat(script: 'C:\\Windows\\System32\\cmd.exe /c docker run -d -p 5000:5000 --name auth-service auth-service')
  }
}
