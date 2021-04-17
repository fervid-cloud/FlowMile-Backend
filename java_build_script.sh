#!/bin/bash

#global variables
versionTrackerFile="version_tracker.txt"
updatedVersion=0
destination_path=""

#function for assigning the version number to the new jar build
updateVersionNumber () {
	parameterGivenPath=$1
	version_info_container_directory=".build_meta_info";
	cd "$parameterGivenPath"
	mkdir -p $version_info_container_directory
	cd $version_info_container_directory
	if [ ! -e $versionTrackerFile ]; then
	  echo "$versionTrackerFile doesn't exist, making the file and putting the default value 0 there"
	  echo 0 >> $versionTrackerFile
	fi

	valueArray=($(cat $versionTrackerFile))

	currentVersion=${valueArray[0]}
	updatedVersion=$((currentVersion + 1))
	echo $updatedVersion > $versionTrackerFile	
}


#function for copying the jar to the destination directory and then running it
copy_jar_and_run() {

	source_path=$1
	current_user=$USER
	current_time=$(date +%Y-%m-%dT%H-%M-%S)
	destination_parent_directory="/home/$current_user/Documents/-1/java_build"

	updateVersionNumber "$destination_parent_directory"

	destination_path="$destination_parent_directory/$current_time------------------------version-$updatedVersion"

	mkdir -p "$destination_path"

	echo "destination_path : $destination_path made successfully "
	cd $source_path

	requiredFileName=$(ls | head -1)

	echo "source directory is $source_path"
	echo "source file name is $requiredFileName"
	echo "copying it to the $destination_path"

	cp "$source_path/$requiredFileName" "$destination_path"

	cd $destination_path

	fileName=$(ls | head -1)

	echo "file is copied to the destination_path"
	echo "destination file name is $fileName"
	echo "running the java jar build: $fileName now"

	#java -jar $fileName

}

source_jar_directory=/home/mss/Documents/0/PolyFlow-Backend/build/libs

copy_jar_and_run $source_jar_directory





