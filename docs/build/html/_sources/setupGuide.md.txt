SetupGuide
==========

Install via Docker
---------------------------------------------------

* Install docker in your system

* Download Dockerfile from https://github.com/NaveenRudra/RTTM

* Change directory to RTTM

* execute *docker build .*

* Run *docker exec -it <container id> /bin/bash*

* Now once in docker navigate to */opt/RTTM/script*

* Run *intialize.sh* script. This will boot mysql server and starts kafka.

* Run *db_setup.sh* this will created needed table.

* Now from */opt/RTTM* run command *java -jar scraptool/target/scraptool-1.0-SNAPSHOT-standalone.jar -t test -c /home/n0r00ij/RTS/scrapper_config/*







