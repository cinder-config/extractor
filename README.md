# CINDER - Extractor

Extracts the features for a given project / configuration file

## Setup

- Install dependencies with maven `mvn install`
- Install [travis-yml](https://github.com/travis-ci/travis-yml) and run it (CLI Version)
- `cp .env.dist .env`
- Fill ENV variables (or provide otherwise)

## CLI Version

There is a CLI version, but it needs configuration -> ExtractorCLI.java

## Webserver

Extractor.java exposes a SpringBoot Webserver. Post the configuration file or the github repository url to the endpoint `/extract`

Example:

HTTP: `/extract`  
JSON-Payload: `{'target': 'https://github.com/cinder-config/extractor'}`
