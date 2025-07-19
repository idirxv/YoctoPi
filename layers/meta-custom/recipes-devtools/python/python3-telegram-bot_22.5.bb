SUMMARY = "Python library for creating Telegram bots"
DESCRIPTION = "Provides an API for the Telegram Bot API."
HOMEPAGE = "https://github.com/python-telegram-bot/python-telegram-bot"
LICENSE = "LGPL-3.0-only"
LIC_FILES_CHKSUM = "file://LICENSE;md5=63f576b3c3e14b4ffb122c8f9014e151"

inherit pypi python_hatchling

PYPI_PACKAGE = "python_telegram_bot"

SRC_URI += " \
    file://telegram-bot-fix-metadata-for-hatchling.patch \
"

SRC_URI[sha256sum] = "82d4efd891d04132f308f0369f5b5929e0b96957901f58bcef43911c5f6f92f8"

RDEPENDS:${PN} += "\
    python3-httpx \
"
