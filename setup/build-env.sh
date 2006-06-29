#!/bin/sh

# Familiar Build Setup Script
#
# Copyright (C) 2006  Rene Wagner <rw@handhelds.org>
# 
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; either version 2 of the License, or
# (at your option) any later version.
# 
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
# 
# You should have received a copy of the GNU General Public License
# along with this program; if not, write to the Free Software
# Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
#

BASE_TITLE="Familiar Build Setup:"
FAMILIAR_RELEASE="0.8.4"

fatal() {
	echo "Fatal: $1"
	exit 1
}

error() {
	zenity --error --text="$1"
}

abort() {
	zenity --info --text="Setup aborted."
	exit 1
}

which zenity > /dev/null || fatal "You must have zenity installed."

BASE_DIR=${PWD}
while [ ! -d ${BASE_DIR}/org.handhelds.familiar ]; do
	 BASE_DIR=`zenity --file-selection --directory --filename=${BASE_DIR} --title="${BASE_TITLE} Select Build Tree Base Directory"`
	 case $? in
	 	0)
			if [ ! -d ${BASE_DIR}/org.handhelds.familiar ]; then
				error "Does not look like a Familiar build tree: ${BASE_DIR}"
			fi
			;;
		1)
			abort;;
		-1)
			abort;;
	esac
done

DL_DIR="${BASE_DIR}/downloads"
DL_DIR=`zenity  --entry \
	--title="${BASE_TITLE} Downloads Directory" \
        --text="Where do you want downloaded files to be stored?" \
        --entry-text="${DL_DIR}"`
case $? in
	1)
		abort;;
	-1)
		abort;;
esac

while [ -z "$MACHINE" ]; do
MACHINE=`zenity  --list \
	--height=280 \
	--title="${BASE_TITLE} Target Machine" \
	--text="Select a target machine type from the list below:" \
	--column="Name" --column="Description" \
	"h3600" "HP iPAQ h36xx/h37xx/h38xx Series" \
	"h3900" "HP iPAQ h39xx/h51xx/h54xx/h55xx Series" \
	"h2200" "HP iPAQ h22xx Series" \
	"ipaq-pxa270" "HP iPAQ hx4700 Series" \
	"h6300" "HP iPAQ h63xx Series"`
case $? in
	0)
		if [ -z "$MACHINE" ]; then
			error "Please select a target machine."
		fi
		;;
	1)
		abort;;
	-1)
		abort;;
esac
done

while [ -z "$GRAPHICAL_ENV" ]; do
GRAPHICAL_ENV=`zenity  --list \
	--height=280 \
	--title="${BASE_TITLE} Primary Graphical Environment" \
	--text="Select the graphical environment you want to build for from the list below:" \
	--column="Name" --column="Description" \
	"gpe" "The GPE Palmtop Environment. X11 based." \
	"opie" "The Open Palmtop Integrated Environment. Qt/Embedded based." \
	"any" "No preference. This may break certain things."`
case $? in
	0)
		if [ -z "$GRAPHICAL_ENV" ]; then
			error "Please select a graphical environment."
		fi
		;;
	1)
		abort;;
	-1)
		abort;;
esac
done

BUILD_DIR="${BASE_DIR}/build-${MACHINE}-${GRAPHICAL_ENV}"
while true; do
	BUILD_DIR=`zenity  --entry \
		--title="${BASE_TITLE} Build Directory" \
		--text="A build directory will be created for the configuration you selected at the following location:" \
		--entry-text="${BUILD_DIR}"`
	case $? in
		1)
			abort;;
		-1)
			abort;;
	esac
	if [ -z ${BUILD_DIR} ]; then
		error "Please specify a build directory."
		continue
	fi
	if [ -d ${BUILD_DIR} ]; then
		error "${BUILD_DIR} exists."
		continue
	fi
	
	break
done

mkdir -p ${BUILD_DIR}/conf
CONFFILE="${BUILD_DIR}/conf/auto.conf"
cat >  ${CONFFILE} <<EOF
# Auto-generated Familiar Build Configuration

DISTRO="familiar-${FAMILIAR_RELEASE}"

# where to store downloaded files
DL_DIR = "${DL_DIR}"

# list of .bb files as a shell glob
BBFILES = "${BASE_DIR}/org.handhelds.familiar/packages/*/*bb"

# top level build directory for this configuration
TOPDIR = "${BUILD_DIR}"

# target MACHINE
MACHINE = "${MACHINE}"

EOF

if [ "${GRAPHICAL_ENV}" == "gpe" ]; then
cat >> ${CONFFILE} <<EOF
# X11 specific settings
PREFERRED_PROVIDERS += " virtual/libsdl:libsdl-x11"

EOF
elif [ "${GRAPHICAL_ENV}" == "opie" ]; then
cat >> ${CONFFILE} <<EOF
# Opie specific settings
PREFERRED_PROVIDERS += " virtual/libsdl:libsdl-qpe"

EOF
fi

ENVSCRIPT="${BUILD_DIR}/conf/env.sh"
cat > ${ENVSCRIPT} <<EOF
PATH="${BASE_DIR}/bitbake/bin:${PATH}"
BBPATH="${BUILD_DIR}:${BASE_DIR}/org.handhelds.familiar"
EOF

zenity  --info \
	--title="${BASE_TITLE} Setup Complete" \
	--text="Your configuration was written to ${CONFFILE}. It will be displayed after this dialog. Please verify that all settings are correct. To re-start the setup please delete the build directory and re-run the setup tool."

zenity  --text-info \
	--width=600 --height=400 \
	--title="${BASE_TITLE} Configuration Written to ${CONFFILE}" \
	--filename="${CONFFILE}"

zenity  --info \
	--title="${BASE_TITLE} Running a Build" \
	--text="Please run the following command before attempting to run a build: \"$ source ${ENVSCRIPT}\". It will configure the environment so you can run bitbake."
