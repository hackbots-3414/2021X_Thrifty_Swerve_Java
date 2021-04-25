# Swerve Testing

Minimal code used for testing for new, modern swerve modules.

# Hackbots 2021X Swerve Drive
The Thrify Bot created a very simple example Java program using Strykeforce's Swerve Drive code for their Thrifty Swerve module kit.  This is a great starting point for us.

Many thanks to The Thrify Bot (www.thethriftybot.com).

## To Work With This
1. Clone https://github.com/hackbots-3414/2021X_thirdcoast
2. Follow the 2021X_thirdcoast README.md.  You must run the `gradlew publishToMavenLocal` to see your local .m2 folder.
3. Clone this repository
4. From VS Code, perform a build.  The team number should be set, and the Strykeforce "thirdcoast" swerve library should be available locally with any Hackbot customizations.

Any time the 2021X_thirdcoast library project is updated, from GitBash, you will need to:
1. cd to your 2021X_thirdcoast folder
2. `git fetch`
3. `git checkout master`
4. `git pull`
5. `gradlew publishToMavenLocal`

Then rebuild your 2021X_Thrift_Swerve project.
