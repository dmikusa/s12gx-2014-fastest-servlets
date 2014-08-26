#!/bin/bash
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
"$DIR/../../../scripts/inst-ctl.sh" "$(dirname "$DIR")" "shutdown"
