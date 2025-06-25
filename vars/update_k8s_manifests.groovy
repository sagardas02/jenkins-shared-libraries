#!/usr/bin/env groovy

/**
 * Update Kubernetes manifests with new image tags
 */
def call(Map config = [:]) {
    def imageTag = config.imageTag ?: error("Image tag is required")
    def manifestsPath = config.manifestsPath ?: 'kubernetes'
    def gitCredentials = config.gitCredentials ?: 'github-credentials'
    def gitUserName = config.gitUserName ?: 'sagardas02'
    def gitUserEmail = config.gitUserEmail ?: 'sagardas4work@gmail.com'
    def gitBranch = config.gitBranch ?: 'master'
    
    echo "Updating Kubernetes manifests with image tag: ${imageTag}"
    
    withCredentials([usernamePassword(
        credentialsId: gitCredentials,
        usernameVariable: 'GIT_USERNAME',
        passwordVariable: 'GIT_PASSWORD'
    )]) {
        // Configure Git
        sh """
            git config user.name "${gitUserName}"
            git config user.email "${gitUserEmail}"
        """
        
        // Update deployment manifests with new image tags - using proper Linux sed syntax
        sh """
            # Update main application deployment - note the correct image name 
            sed -i "s|image: sagar4work/bankapp:.*|image: sagar4work/bankapp:${imageTag}|g" ${manifestsPath}/bankapp-deployment.yml
            
                git add ${manifestsPath}/*.yml
                git commit -m "Update image tags to ${imageTag} and ensure correct domain [ci skip]"
                
                # Set up credentials for push
                git remote set-url origin https://\${GIT_USERNAME}:\${GIT_PASSWORD}@github.com/sagardas02/Springboot-BankApp.git
                git push origin -u ${gitBranch}
        """
    }
}
