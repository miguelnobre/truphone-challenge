# truphone-challenge

### Requirements
 * Maven
 * You must have Docker installed on your machine to run the application locally. 
    * Docker is being used for running the DB container and to run the Integration Tests.
    * You can download Docker Desktop from [here](https://www.docker.com/products/docker-desktop).

#
### Run the Application
 #### You can run the application in 2 modes:
 1. **Fully inside the Docker**
    * You just need to build the Docker image:
        * `sh docker-build.sh`
    * and then launch Docker:
        * `sh docker-run.sh`
 2. **Using Maven**
    * `sh server-run.sh`
        * Remember, you still need to have Docker available to run the DB container dependency.

### Stop the Application Containers
To kill the containers running inside Docker:
 * `sh docker-stop.sh`
        
###### _ps. All the script files are under the root folder._

#
### Swaguer-UI
   * **Url:** http://localhost:8080/swagger-ui.html
   
#
### Postman Collection

Can be found a Postman collection file under the source root with all the requests available by this API:   
 * Thruphone Challenge.postman_collection.json