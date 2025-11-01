# Updating the Mod

## Updating for a New Minecraft Version

1. Check the [Fabric Example Mod](https://github.com/FabricMC/fabric-example-mod) commits for examples of upgrade changes

2. Update `gradle.properties` with new versions:
   - `minecraft_version` - Target Minecraft version
   - `yarn_mappings` - Check [Fabric Develop](https://fabricmc.net/develop) for the latest mappings
   - `loader_version` - Latest Fabric Loader version
   - `fabric_version` and `fabric_version_prefix` - Latest Fabric API version for your Minecraft version
   - `mod_version` - Update to reflect the new Minecraft version (e.g., `1.0.0-mc1.21.10`)

3. Test the build
   
   locally:
   ```bash
   ./gradlew build
   ```

   or open a PR and download the artifact from the build step to try

4. Update the local server Docker image if needed (see [DEVELOPMENT.md](./DEVELOPMENT.md))

5. Test that the game boots at all with the mod installed. If it boots, it's probably fine.


## Creating a Release

The project uses GitHub Actions to automatically build and publish releases when version tags are pushed.

### Release Process

1. Ensure `gradle.properties` has the correct `mod_version` for the release (e.g., `1.0.0-mc1.21.10`)

2. Create and push a version tag matching the mod version:
   ```bash
   git tag v1.0.0-mc1.21.10
   git push origin v1.0.0-mc1.21.10
   ```

3. The GitHub Action will automatically:
   - Build the mod with Java 21
   - Create a GitHub release
   - Upload the main mod JAR (e.g., `event-notifications-1.0.0-mc1.21.10.jar`)
   - Generate release notes from recent commits

4. The release will be available at: `https://github.com/zdwolfe/minecraft-fabric-event-notifications/releases`

### Versioning Convention

Tags should follow the format: `v{MOD_VERSION}` where `{MOD_VERSION}` matches the `mod_version` in `gradle.properties`.

Example: If `mod_version=1.0.0-mc1.21.10`, create tag `v1.0.0-mc1.21.10`
