#!/bin/sh

#EDIT CONFIGURATION ACCORDING TO DEPLOY ENVIRONMENT

base_dir=/home/elena/migrationtool

java_path=/usr/lib/jvm/java-7-openjdk-amd64/bin
config_path=${base_dir}/config
lib_dir=${base_dir}/lib

${java_path}/java -Dlog4j.configuration=file:${config_path}/log4j.properties -Dparser.file.config=${config_path}/config.properties -cp ${lib_dir} -jar migrationtool.jar PhoneParser