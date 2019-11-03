FROM openjdk:8

ENV DEBIAN_FRONTEND noninteractive

RUN apt-get update 

RUN apt install -y maven

RUN apt-get install git

RUN apt-get install -y mysql-server

RUN service mysql start

RUN apt-get install -y \
            zookeeper \
            wget \
            dnsutils \
            vim \
            && rm -rf /var/lib/apt/lists/*

ENV KAFKA_VERSION 2.1.1
ENV SCALA_VERSION 2.11
RUN wget -q \
    http://apache.mirrors.spacedump.net/kafka/${KAFKA_VERSION}/kafka_${SCALA_VERSION}-${KAFKA_VERSION}.tgz \
    -O /tmp/kafka.tgz \
    && tar xfz /tmp/kafka.tgz -C /opt \
    && rm /tmp/kafka.tgz \
    && mv /opt/kafka_${SCALA_VERSION}-${KAFKA_VERSION} /opt/kafka


ADD script/run.sh /usr/local/bin/run.sh

RUN chmod a+x /usr/local/bin/run.sh

WORKDIR /opt/RTTM

RUN git clone https://github.com/NaveenRudra/RTTM.git

RUN git clone https://github.com/dxa4481/truffleHog.git

RUN wget https://bootstrap.pypa.io/get-pip.py

RUN python get-pip.py

RUN rm get-pip.py

RUN apt-get update 

RUN apt-get install -y python3

RUN apt install -y python3-pip

RUN pip install -r /opt/RTTM/truffleHog/requirements.txt

RUN python /opt/RTTM/truffleHog/setup.py install

RUN apt-get update 





WORKDIR /opt/RTTM/RTTM
  
RUN mvn install -D skipTests




