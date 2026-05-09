#!/usr/bin/env bash
set -euo pipefail

script_dir="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"
cd "$script_dir"

if [[ ! -f .env ]]; then
    echo "Missing .env file with export_path defined." >&2
    exit 1
fi

set -a
# shellcheck disable=SC1091
source .env
set +a

if [[ -z "${export_path:-}" ]]; then
    echo "Missing export_path in .env." >&2
    exit 1
fi

java_candidates=(
    "${JAVA_HOME:-}"
    /usr/lib/jvm/java-25-graalvm
    /usr/lib/jvm/java-25-openjdk
)

java_home=""
for candidate in "${java_candidates[@]}"; do
    [[ -n "$candidate" && -x "$candidate/bin/java" ]] || continue

    java_version="$("$candidate/bin/java" -version 2>&1 | awk -F '"' '/version/ { print $2; exit }')"
    if [[ "$java_version" == 25* ]]; then
        java_home="$candidate"
        break
    fi
done

if [[ -z "$java_home" ]]; then
    echo "Java 25 is required to build this mod. Install JDK 25 or set JAVA_HOME to a Java 25 JDK." >&2
    exit 1
fi

export JAVA_HOME="$java_home"
export PATH="$JAVA_HOME/bin:$PATH"

./gradlew build

mkdir -p "$export_path"

mapfile -t jars < <(
    find build/libs -maxdepth 1 -type f -name '*.jar' \
        ! -name '*-sources.jar' \
        ! -name '*-dev.jar' \
        ! -name '*-javadoc.jar' \
        | sort
)

if [[ "${#jars[@]}" -ne 1 ]]; then
    echo "Expected exactly one built mod jar in build/libs, found ${#jars[@]}." >&2
    printf 'Found: %s\n' "${jars[@]:-none}" >&2
    exit 1
fi

cp -f "${jars[0]}" "$export_path/"
echo "Exported $(basename "${jars[0]}") to $export_path"
