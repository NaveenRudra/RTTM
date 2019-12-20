FROM openjdk:8

ENV DEBIAN_FRONTEND noninteractive

RUN apt-get update 

RUN apt install -y maven

RUN apt-get install git

RUN git config --global user.email "test@rttm.com"

RUN apt-get install -y mysql-server

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


WORKDIR /opt/RTTM

RUN git clone https://github.com/NaveenRudra/RTTM.git

RUN git clone https://github.com/dxa4481/truffleHog.git

RUN wget https://bootstrap.pypa.io/get-pip.py

RUN python get-pip.py

RUN rm get-pip.py


WORKDIR /opt/RTTM/truffleHog

RUN pip install -r requirements.txt

RUN python setup.py install


WORKDIR /opt/RTTM/RTTM
  
RUN mvn install -D skipTests




