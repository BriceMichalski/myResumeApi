#!/bin/bash

CLUSTER=127.0.0.1
BUCKET=myresume-data
ADMIN=Administrator
ADMINPWD=password
APPUSR=myResumeApp
APPPWD=6EQUJ5wow!

couchbase-cli cluster-init -c $CLUSTER \
        --cluster-username $ADMIN \
        --cluster-password $ADMINPWD \
        --services data,index,query \
        --cluster-ramsize 512 \
        --cluster-index-ramsize 256

sleep 3

couchbase-cli bucket-create -c $CLUSTER \
        --username $ADMIN \
        --password $ADMINPWD \
        --bucket $BUCKET \
        --bucket-type couchbase \
        --bucket-ramsize 512

sleep 3

couchbase-cli user-manage -c $CLUSTER \
       -u $ADMIN -p $ADMINPWD --set \
       --rbac-username $APPUSR \
       --rbac-password $APPPWD \
       --rbac-name "MyResumeAPI Application" \
       --roles bucket_full_access[$BUCKET] \
       --auth-domain local