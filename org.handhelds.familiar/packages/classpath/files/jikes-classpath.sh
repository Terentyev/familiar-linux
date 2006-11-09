#!/bin/sh -e

if [ -r DATADIR/classpath/glibj.zip ]; then
       jikes -bootclasspath DATADIR/classpath/glibj.zip "$@"
else
        echo
        echo "-----------   ERROR   -----------"
        echo "Cannot find/read classpath classes. Please report."
        echo
       exit 1
fi
