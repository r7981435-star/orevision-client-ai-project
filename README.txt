OREVISION — Fabric 1.21.11

Features
- Press X to toggle X-ray on/off.
- Change the key in Options > Controls > Key Binds > OreVision.
- Shows vanilla ore blocks, ancient debris, raw ore blocks, water and lava.
- Hides other block models while enabled.
- Client-side mod. It cannot reveal blocks that a multiplayer server does not send to your client (for example, server-side anti-xray).

Requirements
- Minecraft Java Edition 1.21.11
- Fabric Loader
- Fabric API for 1.21.11
- Java 21 (for building)

BUILDING THE MOD
1. Install Java 21 and Gradle (or open this folder in IntelliJ IDEA with the Gradle plugin).
2. Open a terminal in this project folder.
3. Run: gradle build
4. The compiled mod will be in: build/libs/orevision-1.0.0.jar
5. Put that JAR in your Minecraft instance's mods folder.

NOTE
This is the complete source project. The JAR is produced by the Gradle build on your computer.
If you use a multiplayer server, check its rules before enabling X-ray.
