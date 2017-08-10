# Windowlicker Dependencies

These dependencies are kept here because they are not available through any public maven repository.  The windowlicker
project does not seem to be a heavily supported project, but since there don't seem to be other popular alternatives it
didn't seem appropriate to try replacing it.

## Get dependencies into local maven

Since maven is being used to develop this project it seemed a good idea to get the required dependencies into local
maven repository so they could be found.

### Build dependencies

The windowlicker project can be found on [github](https://github.com/petercoulton/windowlicker).  Clone or download
the repository, then run the `build.sh` script.  This should generated all of the desired jars under the `build/jars`
folder.

### Add dependencies to maven

Use maven install command to put files into our local `.m2` repository folder.  Here's is an example for the
`windowlicker-core-DEV.jar` file.  Repeat the command for the `windowlicker-swing-DEV.jar` file changing the
`file` and `artifactId` parameters.

    mvn install:install-file
        -Dfile=lib/windowlicker-core-DEV.jar
        -DgroupId=com.objogate.wl
        -DartifactId=windowlicker-core
        -Dversion=1.0.0
        -Dpackaging=jar
