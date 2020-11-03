## Table of Contents

* [About the Project](#about-the-project)
  * [Built With](#built-with)
* [Getting Started](#getting-started)
  * [Maven](#maven)
  * [Docker](#docker)
  * [Kubernetes](#kubernetes-using-skaffold)
  * [Code Inspection using Sonarqube](#code-inspection-using-sonarqube)
* [API Spec](#api-spec)
* [Contributions](#contributions)
* [Appendix](#appendix)

<!-- ABOUT THE PROJECT -->
## About The Project
Spring kotlin restful is project for explore Spring Boot, Kotlin, Docker, Kubernetes, Sonarqube etc

### Built With
* [Spring Boot](https://start.spring.io/)
* [Kotlin](https://kotlinlang.org/)
* [MongoDB](https://www.mongodb.com/)
* [Sonarqube](https://www.sonarqube.org/)
* [JaCoCo](https://www.jacoco.org/)
* [Docker](https://www.docker.com/)
* [Jib](https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin)
* [Kubernetes](https://kubernetes.io/)
* [Skaffold](https://skaffold.dev/docs/install/)

<!-- GETTING STARTED -->
## Getting Started
This project can run using maven, docker or kubernetes with skaffold

## Maven
This project use mongodb as database, so make sure you have running mongodb in local environment, or you can run docker-compose file inside docker-compose folder
* Make sure you have docker installation, if not see [Docker Get Started](https://www.docker.com/get-started)
* Run mongodb using docker compose, see [Docker Compose](https://docs.docker.com/compose/) for more info

```
docker-compose -f docker-compose/mongodb-compose.yaml up -d
```

* Open `localhost:8081` to se mongo-express using default user password `admin:admin`

* Run project using maven (skip test and using local profile) `Ctrl + C` for close running project

```
mvn spring-boot:run -DskipTests -Plocal
```
* Explore api, see [API Spec](#api-spec)

## Docker
* Before you can run project using docker make sure mongodb run using docker compose with `mongodb-compose.yaml` file
* Docker compose `mongodb-compose.yaml` using `spring-kotlin` as network so make sure you run with set network to `spring-kotlin`
* You can run this common (add -d to run in background)

```
docker run --name spring-kotlin --network=spring-kotlin -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=docker" sukendakenda/spring-kotlin-restful
```
* Docker will pull image from docker hub with name `sukendakenda/spring-kotlin-restful`
* You can build image using [Jib Maven Plugin](https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin) and push to your docker hub
* Make sure you change docker id in `pom.xml` to `<image>[your-docker-id]/spring-kotlin-restful</image>` 
* Login to your docker hub using
```
docker login -u <your-docker-id>
```
* Run this common for build image
```
mvn compile jib:dockerBuild
```
* Run this common for build image and push image to your docker hub
```
mvn compile jib:build
```

## Kubernetes using skaffold

## Code Inspection using Sonarqube
Before you can inspect code make sure you run sonarqube server using docker compose
* Run this common for run sonarqube using docker compose
```
docker-compose -f docker-compose/sonar-compose.yaml up -d
```
* Open `localhost:9000` to see if sonarqube running
* Run inspect code using common
```
mvn clean verify sonar:sonar
```
## API Spec

## Contributions

## Appendix
- `skaffold.yml` is used by the Skaffold CLI
- `k8s-app-deployment.yml` are used for deployment into the K8s cluster and they are integrated into the `skaffold.yml` for continuous deployment
- `skaffold build` is used for building docker images from `skaffold.yml`
- `skaffold run` is used for building and deploying docker images on to a Kubernetes cluster
- `skaffold dev` is used to watch for files and trigger build and deployment based on that
- `minikube service spring-kotlin-restful` - opens my service url which is configured as type `LoadBalancer`

## Mongodb
- `kubectl exec -it <POD> /bin/bash`
- `mongo`
- `mongo -u spring-kotlin-user -p --authenticationDatabase spring-kotlin-restful`

## SSH Github
* Generate ssh-keygen
```
ssh-keygen -t rsa -C "soekenda09@gmail.com" -f "github-sukenda"
```
```
ssh-keygen -t rsa -C "sukenda09@gmail.com" -f "github-sukendakenda"
```
* Add ssh-keygen
```
ssh-add -K ~/.ssh/github-sukenda
```
```
ssh-add -K ~/.ssh/github-sukendakenda
```

* Copy public key
```
pbcopy < ~/.ssh/github-sukenda.pub
```
```
pbcopy < ~/.ssh/github-sukendakenda.pub
```

* Edit config
```
vi ~/.ssh/config
```

* Config File
```
Host github.com-sukenda
     HostName github.com
     User git
     IdentityFile ~/.ssh/github-sukenda
     
 Host github.com-sukendakenda
     HostName github.com
     User git
     IdentityFile ~/.ssh/github-sukendakenda
```

## Default github account
```
git config --global user.name "sukenda"
```
```
git config --global user.email "soekenda09@gmail.com"
```

```
git config --global user.name "sukendakenda"
```
```
git config --global user.email "sukenda09@gmail.com"
```
## Clone repo using specific account
```
git clone git@github.com-{your-username}:{the-repo-organisation-or-owner-user-name}/{the-repo-name}.git
```
```
git clone git@github.com-sukenda:sukenda/spring-boilerplate.git
```
```
git clone git@github.com-sukendakenda:sukenda/spring-boilerplate.git
```

## Git common collaboration
```
git clone git@github.com-sukendakenda:sukendakenda/spring-boilerplate.git
```
```
git remote
```
```
git remote add upstream git@github.com-sukendakenda:sukenda/spring-boilerplate.git
```
```
git fetch upstream
```
```
git branch --set-upstream-to=upstream/master master
```
```
git checkout -b new-branch
```
