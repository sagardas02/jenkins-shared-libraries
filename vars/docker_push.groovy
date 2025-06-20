def call(String dockerhubUser, String imageName, String imageTag) {
  withCredentials([usernamePassword(
    credentialsId: 'dockerHubCred',
    usernameVariable: 'DOCKER_HUB_USER',
    passwordVariable: 'DOCKER_HUB_PASS'
  )]) {
sh """
  echo "\$DOCKER_HUB_PASS" | docker login -u "\$DOCKER_HUB_USER" --password-stdin
  docker push \$DOCKER_HUB_USER/${imageName}:${imageTag}
"""
  }
}
