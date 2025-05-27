def call(String dockerhubUser, String imageName, String imageTag){
  sh "docker build -t ${dockerhubUser}/${imageName}:${imageTag} ."
}
