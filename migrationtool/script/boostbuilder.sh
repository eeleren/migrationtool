#!/bin/sh

#EDIT CONFIGURATION ACCORDING TO DEPLOY ENVIRONMENT

base_dir=/home/msdp/migrationtool
output_dir=/home/msdp/migrationtool/output
msdp_dir=/home/msdp/ingestion/output
msdp_3pi=144.229.209.143

java_path=/usr/lib/jvm/java-7-openjdk-amd64/bin
config_path=${base_dir}/config
lib_dir=${base_dir}/lib

echo builder start...
${java_path}/java -Dlog4j.configuration=file:${config_path}/log4j.properties -Dparser.file.config=${config_path}/config.properties -cp ${lib_dir} -jar migrationtool.jar PhoneParser > /dev/null 2>&1
echo builder end...
if [ "$(ls -A $output_dir)" ]; then
    echo copy start...
	cd $output_dir
	file=$(ls -l $1|grep "^d" | awk '{print $9}');
		for f in $file
         do
			echo moving $f to msdp 3pi directory...
			scp -r $f msdp@$msdp_3pi:$msdp_dir
         done
echo  copy end
else
echo 'Error! Output directory is empty. Check migrationtool log files for further details.'
fi