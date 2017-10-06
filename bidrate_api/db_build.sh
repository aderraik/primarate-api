#!/bin/bash
#
# Rebuild a database
# 
# Check parameters
if [ "$1" == "" ] ; then
    echo "Usage: $0 <database> [username]"
    echo "  The [username] is used to create and grant access to user into database."
    exit 1
fi

# Set vars
DB=$1
DBUSER=$2

# Drop and create the database
mysqladmin -u root -p drop $DB
mysqladmin -u root -p create $DB

# Need to run the first time
# Check parameters
if [ "$DBUSER" != "" ] ; then
    # Create user and permission
    DBPWD=zuzuga
    G="CREATE USER '$DBUSER'@'localhost' IDENTIFIED BY '$DBPWD';"
    echo $G | mysql -u root -p $DB
    G="CREATE USER '$DBUSER'@'%' IDENTIFIED BY '$DBPWD';"
    echo $G | mysql -u root -p $DB
    G="GRANT ALL ON $DB.* TO '$DBUSER'@'localhost';"
    echo $G | mysql -u root -p $DB
    G="GRANT ALL ON $DB.* TO '$DBUSER'@'%';"
    echo $G | mysql -u root -p $DB
    G="FLUSH PRIVILEGES;"
    echo $G | mysql -u root -p $DB
fi
