SUMMARY = "Fast, extensible, multi-platform web server with automatic HTTPS"
DESCRIPTION = "Caddy is an HTTP/2 web server with automatic HTTPS written in Go."
HOMEPAGE = "https://caddyserver.com/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

inherit go-mod systemd useradd

GO_IMPORT = "github.com/caddyserver/caddy"
GO_INSTALL = "${GO_IMPORT}"
GO_LINKSHARED = ""
CGO_ENABLED = "0"

SRC_URI = "\
    git://${GO_IMPORT}.git;branch=master;protocol=https \
    file://caddy.service \
    file://caddy.tmpfiles \
    file://Caddyfile \
"
SRCREV = "0e570e0cc717f02cf3800ae741df70cd074c7275"

SYSTEMD_SERVICE:${PN} = "caddy.service"

# Create caddy user and group
USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = "--system --home /var/lib/caddy --shell /sbin/nologin caddy"

do_compile() {
    cd "${S}/src/${GO_IMPORT}/cmd/caddy"
    ${GO} build ${GOBUILDFLAGS} -o "${B}/caddy"
}

do_install() {
    install -d ${D}${prefix}/local/bin
    install -m 0755 ${B}/caddy ${D}${prefix}/local/bin/caddy

    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/caddy.service ${D}${systemd_unitdir}/system/

    install -d ${D}${sysconfdir}/tmpfiles.d
    install -m 0644 ${WORKDIR}/caddy.tmpfiles ${D}${sysconfdir}/tmpfiles.d/caddy.conf

    install -d ${D}${sysconfdir}/caddy
    install -m 0644 ${WORKDIR}/Caddyfile ${D}${sysconfdir}/caddy/Caddyfile
}

FILES:${PN} = "\
    ${prefix}/local/bin/caddy \
    ${systemd_unitdir}/system/caddy.service \
    ${sysconfdir}/tmpfiles.d/caddy.conf \
    ${sysconfdir}/caddy/Caddyfile \
"
