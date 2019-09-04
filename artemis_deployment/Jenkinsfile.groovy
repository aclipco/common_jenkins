#!/usr/bin/env groovy
package com.lib
import groovy.json.JsonSlurper

node('master') {
 properties([parameters([
    booleanParam(defaultValue: false, description: 'If you press this parameter it will apply all the changes', name: 'terraformApply'), 
    booleanParam(defaultValue: false, description: 'if you press this parameter it will destroy everything', name: 'terraformDestroy')
    ]
    )])
    stage('Checkout SCM') {
      git 'https://github.com/fuchicorp/terraform.git'
   }
    
    stage('Generate Vars') {
        def file = new File("${WORKSPACE}/google_grafana/grafana.tfvars")
        file.write """
        password              =  "${password}"
        namespace             = "${namespace}"

        """
      }
    stage("Terraform init") {
      dir("${WORKSPACE}/google_grafana/") {
        sh "terraform init"
      }
    }
    stage('Terraform Apply/Plan') {
      if (params.terraformApply) {
        dir("${WORKSPACE}/google_grafana/") {
          echo "##### Terraform Applying the Changes ####"
          sh "terraform apply  --auto-approve  -var-file=grafana.tfvars"
        }
    } else {
        dir("${WORKSPACE}/google_grafana/") {
          echo "##### Terraform Plan (Check) the Changes ####"
          sh "terraform plan -var-file=grafana.tfvars"
      } 
    }
}
    stage('Terraform Destoy') {
      if (params.terraformDestroy) {
        dir("${WORKSPACE}/google_grafana/") {
          echo "##### Terraform Destroying the Changes ####"
          sh "terraform destroy  --auto-approve  -var-file=grafana.tfvars"
        }
      } 
    }
}