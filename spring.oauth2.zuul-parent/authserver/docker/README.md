#To run mysql in a docker container we have to execute this command
sudo docker run -d \
    --name oauth2test-mysql \
    -e MYSQL_ROOT_PASSWORD=exito \
    -e MYSQL_DATABASE=oauth2 \
    -e MYSQL_USER=dani \
    -e MYSQL_PASSWORD=test \
    mysql:latest

#We need to figure out the container_ID, for that we will execute
sudo docker ps

#To figure out the docker container IP we have to execute 
sudo docker inspect container_ID

#Once the docker container is started and we have the container IP we have to modify the jdbc.url in the persistence.properties(authserver and the resourceserver)rin
jdbc.url=jdbc:mysql://ip_docker_container:3306/oauth2test?createDatabaseIfNotExist=true

#To restart the container
sudo docker container start CONTAINER_ID

