# Apache Karaf and bnd remote launcher #

This project shows how to combine Apache Karaf and the the bndtools OSGi toolchain through the remote launcher capability implemented in bnd.

This is very useful when you want to leverage both the power of Apache Karaf feature system to manage complex OSGi runtimes like Apache CXF, Camel or JPA and the very productive development workflow provided by bndtools and OSGi enRoute.

## Requirements ##
* Eclipse Mars with [bndtools 3.2.0](http://bndtools.org/installation.html) installed
* Apache Karaf 4.0.5

## Set up the Karaf runtime
You can download the [latest Apache Karaf distro](http://karaf.apache.org/download.html) and you can unpack it somewhere in your filesystem. After that launch Karaf with the following command (put the karaf right version in the command):

```
$ apache-karaf-4.0.5/bin/karaf debug
```
The `debug` switch opens a listening jdb port on 5005.

The bundle under development (*BUD*) that we are going to debug provides a declarative service so we need to install SCR Karaf feature:

```
karaf@root(bundle)> feature:install scr
```

Now the Karaf runtime is ready to be used for our debugging purposed. To deploy and debug our BUD we need to install the bnd remote agent on Karaf and start it:

```
karaf@root(bundle)> install mvn:biz.aQute.bnd/biz.aQute.remote.agent/3.2.0
Bundle ID: 86
karaf@root(bundle)> start 86
Host localhost 29998
karaf@root(bundle)> 
```

The line `Host localhost 29998` means the remote agent is ready to interact with our eclipse IDE through the default bnd agent port (29998).

## The *bundle under development*

The bundle under development is quite simple: it is the default bundle you get when you create a new project through the OSGi enRoute project wizard. 

You can import it in your Eclipse IDE using the `Import -> Existing Projects into Workspace menu`.  It is called `com.flairbit.example.karaf.simplebundle` and it is contained in this repo along with its companion bnd workspace project, `cnf`: you need to import them both in your Eclipse.

The BUD provides a declarative service (`com.flairbit.example.karaf.simplebundle.api`) and a simple gogo command (`com.flairbit.example.karaf.simplebundle.command`). The remote launch configuration is contained in the `debug.bndrun` file. The `debug.bndrun` contains the remote debugger configuration, in particular it teaches the remote launcher how to contact the agent installed on Karaf:

```
# Override the default bnd launcher
-runpath: biz.aQute.remote.launcher

# Contact the remote agent on Karaf
-runremote: test;\
	shell   =   -1; \
	jdb     =   5005; \
	host    =   localhost; \
	agent   =   29998; \
	timeout =   10000
```
In particular, the `shell = -1` option allows the remote launcher to play nicely with the native Karaf OSGi console. The details are better explained in the [bndtools docs](http://bnd.bndtools.org/chapters/300-launching.html).

## Launching
Right click on `debug.bndrun` file and select `Debug As -> Bnd Native Launcher`. As soon as the debug session starts, the followin message pops up in Karaf

```
karaf@root(bundle)> World:Hello
```

That means the agent received the BUD and successfully installed it on the target Karaf runtime!
You can set your breakpoints around now, for instance in the `com.flairbit.example.karaf.simplebundle.command.SimplebundleCommand.smplbnd` method. 

The gogo shell and the Karaf shell are not mutually exclusive: it is possible to invoke the `SimplebundleCommand` right from the Karaf console

```
karaf@root(bundle)> smplbnd:smplbnd your-name-here
World:your-name-here
karaf@root(bundle)> 
```

Cool! Happy debugging... 

## Stop the debug session
To terminate the debugging session and automatically clean up all the things in the target Karaf runtime, just **disconnect** the eclipse remote debugger and after that **stop** the debug session. As soon as you do that, the remote agent uninstalls all the BUDs:

```
karaf@root(bundle)> Listening for transport dt_socket at address: 5005
World:Goodbye

karaf@root(bundle)> 
```
Everything is now ready for a new debug session.