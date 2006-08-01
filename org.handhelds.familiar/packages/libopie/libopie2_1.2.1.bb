include ${PN}.inc

PR = "r6"

SRC_URI = "${HANDHELDS_CVS};tag=${TAG};module=opie/libopie2 \
	   file://openzaurus-branding.patch;patch=1 \
	   file://prelim-h191x-hx4700-supp.patch;patch=1;pnum=2 \
	   file://ipaq-2.6-sys-class-backlight-support.patch;patch=1 \
	   file://h2200-keys.patch;patch=1 \
	   file://hx4700-keys.patch;patch=1;pnum=0 \
           file://include.pro"
