#!/bin/sh

#EDIT CONFIGURATION ACCORDING TO DEPLOY ENVIRONMENT

asset_dir=/home/msdp/ingestion/output/
msdp_inbox=/opt/drutt/msdp/3pi/upload/partners/P-d0avx1PuX34/inbox/
msdp_error=/opt/drutt/msdp/3pi/upload/partners/P-d0avx1PuX34/error/
ingestion_error=/home/msdp/ingestion/ingestion_error

if [ $# -ne 1 ]; then
          echo 'Script Usage: \nSpecify argument <phones> if you want to ingest all contents in "$asset_dir"\nSpecify <phone name directory> to ingest only one phone'
exit 1
fi
if [ "$(ls -A $asset_dir)" ]; then
        if [ "$1" = "phones" ]; then
                echo 'phones'
                cd $asset_dir
                file=$(ls -l $1|grep "^d" | awk '{print $9}');
                for f in $file
                        do
                        echo moving $f to msdp inbox directory...
                        mv $asset_dir/$f $msdp_inbox
                        echo  creating $f/create.start
                        touch $msdp_inbox/$f/create.start
                        done
                        echo checking ingestion errors...
                        sleep 60s
                        cd $msdp_error
                        find . -maxdepth 1 -mmin -5 > $ingestion_error/error.log
                        echo Ingestion completed. Check error.log files for further details.


        else
			if [ ! -d "$asset_dir/$1" ]; then
			echo "Input directory provided does not exist. Please insert a valid directory name."
			else
			echo moving $1 to msdp inbox directory...
			mv $asset_dir/$1 $msdp_inbox
			echo creating create.start
			touch $msdp_inbox/$1/create.start
			sleep 60s
			cd $msdp_error
			find . -maxdepth 1 -mmin -5 > $ingestion_error/error.log
			echo Ingestion completed. Check error.log files for further details.
			fi
		fi
else
echo 'asset directory empty, no assets to ingest'
fi
