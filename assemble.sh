#!/bin/bash

set -e

PID=`ps aux|grep spring|grep java|awk '{print $2}'`
/home/jdk/gradle/bin/gradle compileJava && ([[ $PID -eq "" ]] || kill $PID)
/home/jdk/gradle/bin/gradle assemble
