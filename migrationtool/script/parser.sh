#!/bin/sh

#EDIT CONFIGURATION ACCORDING TO DEPLOY ENVIRONMENT

base_dir=/home/drutt/ebragan_2

java_path=/opt/drutt/local/jdk1.7.0_25/bin
config_path=${base_dir}/app/config
lib_dir=${base_dir}/app/lib

${java_path}/java -Dlog4j.configuration=file:${config_path}/log4j.properties -Dparser.file.config=${config_path}/config.properties -cp ${lib_dir} -jar migrationtool.jar PhoneParser