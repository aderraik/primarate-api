#!/usr/bin/env bash

CATALINA_HOME=/var/lib/tomcat7
POM_PROJECT_NAME=visian-bid-management
WAR_PROJECT_NAME=visian
PROJECT_PATH=$(dirname $0)

function build {
	printMessage "-----> Running VISIAN build ..."

	PARAM=$1
	cd $PROJECT_PATH;

	if [ -z "$PARAM" ] ; then
		tryStep "mvn clean package";
		tryStep deploy
	elif [ "$PARAM" == "deploy" ] ; then
		tryStep deploy
	elif [ "$PARAM" == "compile" ] ; then
		tryStep "mvn clean compile";
	elif [ "$PARAM" == "db_generator" ] ; then
		tryStep "mvn clean compile -Pdb_generator";
		tryStep "mvn exec:java -Dexec.mainClass=org.visian.service.runner.DatabaseGeneratorServiceRunner -Pdb_generator";
	elif [ "$PARAM" == "update_monetarydata" ] ; then
		tryStep "mvn clean compile";
		tryStep "mvn exec:java -Dexec.mainClass=org.visian.service.runner.MonetaryDataUpdateServiceRunner";
	else
		printMessage "Invalid argument!";
	fi
}

function deploy {
	printMessage "-----> Deploying VISIAN war file on local VM ..."

	if [ -d $PROJECT_PATH/target/$POM_PROJECT_NAME ]
	then
		sudo rm -rf $CATALINA_HOME/webapps/${WAR_PROJECT_NAME}*
		sudo cp $PROJECT_PATH/target/$POM_PROJECT_NAME.war $CATALINA_HOME/webapps/$WAR_PROJECT_NAME.war
	else
		echo "ERROR: could not find target webapp, make sure you have run mvn clean package from the parent directory."
	fi
}

function printMessage {
	LINE="-------------------------------------------------------------------------------"
	echo $LINE
	echo $1
	echo $LINE
}

function tryStep {
	CMD=$1
	$CMD

	RESULT=$?
	if [ $RESULT != 0 ];
	then
		echo "-----> Failed to complete previous step. Exit code: '$RESULT'"
		exit 0
	fi
}

build $1
printMessage "-----> Build VISIAN components completed"
