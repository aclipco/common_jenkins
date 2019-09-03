FROM jenkins/jenkins
RUN apt-get update -y && apt-get install -y git curl && rm -rf /var/lib/apt/lists/*
ARG user=jenkins
ARG group=jenkins
ARG uid=1000
ARG gid=1000
ARG http_port=8080
ARG agent_port=50000
ARG JENKINS_HOME=/var/jenkins_home

ENV JENKINS_HOME $JENKINS_HOME
ENV JENKINS_SLAVE_AGENT_PORT ${agent_port}
ENV REF $REF

RUN mkdir -p $JENKINS_HOME \
  && chown ${uid}:${gid} $JENKINS_HOME \
  && groupadd -g ${gid} ${group} \
  && useradd -d "$JENKINS_HOME" -u ${uid} -g ${gid} -m -s /bin/bash ${user}




COPY jenkins_plugins.sh /usr/local/bin/jenkins_plugins.sh

EXPOSE ${http_port}
EXPOSE ${agent_port}