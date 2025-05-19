# Read Me First

### Data Service
This Service is responsible for collecting blog posts
from multiple sources. This service serves the posts to
the client-backend. The service is built in a way that it is really easy
to add new third party blogging service to it. Without too much of a code
change. Added Unit tests where its needed.


### How to Run this application

* clone the project from github
* run "mvn clean install"
* Run the server locally "mvn spring-boot:run"
* You can also access the endpoints by CURL "curl -N -H "Accept: text/event-stream" http://localhost:8080/api/v1/posts/stream" and "curl -H "Accept: application/json" http://localhost:8080/api/v1/posts "
* For running inside Docker container:
* * install Docker on your local machine
* * go inside the project folder
* * run command "docker build -t coop-steamer-service ."
* * run "docker network create my-network"
* * run "docker run --network my-network --name coop-steamer-service -p 8080:8080 coop-steamer-service"
* * the service should be running after this
