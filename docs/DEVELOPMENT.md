# Build
The below instructions build the mod from source, build a local server, then start that server running the previously-compiled mod. 


## Build jar
Permissions:

```bash
 sudo chown -R $(id -u):$(id -g) . && chmod -R 777 build && chmod -R 777 .gradle
```


Build and 'install' the jar to the local server:
```bash
 docker run --rm -u gradle -v "${PWD}:/home/gradle/project" -w /home/gradle/project gradle:jdk21 gradle --stacktrace clean build localInstallJar localInstallFabricApi
```


## Build local server
Build the local server. This needs to be done once, unless you're modifying the minecraft or mod version. Replace MINECRAFT_VERSION and MOD_VERSION if changed:

```bash
docker build --file local/Dockerfile --build-arg MINECRAFT_VERSION=1.21 --tag event-notification-local .
```

## Run local server
Run the local server. The server needs to be restarted very time the mod is changed to pick up the new changes.
```bash
docker run -it -p 25565:25565 --volume "${PWD}/local/fs/data:/data" event-notification-local
```

## Connect to local server
Connect to the local server (with mod installed):

![](./localhost-server-config.png)

