def call(String dockerhubUser, String imageName, String imageTag){
  withCredentials([usernamePassword(
                    'credentialsId':"dockerHubCred",
                    passwordVariable:"dockerHubPass",
                    usernameVariable:"dockerHubUser")]){
                sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPass}" 
                sh "docker image tag ${imageName}:${imageTag} ${dockerHubUser}/${imageName}:${imageTag}"
                sh "docker push ${dockerHubUser}/${imageName}:${imageTag}"
                }
}
