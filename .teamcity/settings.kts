import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs
import jetbrains.buildServer.configs.kotlin.v2019_2.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2021.1"

project {

    vcsRoot(HttpsGithubComIyankeBigdataRefsHeadsMaster)

    buildType(Build)

    features {
        feature {
            id = "PROJECT_EXT_3"
            type = "JetBrains.SharedResources"
            param("values", """
                a
                b
                c
            """.trimIndent())
            param("name", "val1")
            param("type", "custom")
        }
    }
}

object Build : BuildType({
    name = "Build"

    params {
        password("pass13", "credentialsJSON:b4910e9a-ed93-4136-8cb7-c6558018795a")
        password("pass12", "credentialsJSON:84998233-9ff1-468d-99fc-1c14f9119bf6")
        password("pass11", "credentialsJSON:9718412a-19bf-483e-b10c-58830157cca3")
    }

    vcs {
        root(HttpsGithubComIyankeBigdataRefsHeadsMaster)
    }

    steps {
        script {
            scriptContent = "call test.bat"
        }
        script {
            scriptContent = """
                echo %pass11%
                echo %pass12%
                echo %pass13%
            """.trimIndent()
        }
    }

    triggers {
        vcs {
        }
    }
})

object HttpsGithubComIyankeBigdataRefsHeadsMaster : GitVcsRoot({
    name = "https://github.com/iyanke/bigdata#refs/heads/master"
    url = "https://github.com/iyanke/bigdata"
    authMethod = password {
        userName = "iyanke"
        password = "credentialsJSON:f79215b2-e8c3-480e-9933-e54dd6108704"
    }
    param("useAlternates", "true")
})
